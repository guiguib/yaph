package com.gbourquet.yaph.client.mvp.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.LoginEvent;
import com.gbourquet.yaph.client.event.LoginEventHandler;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.event.password.NewPasswordEvent;
import com.gbourquet.yaph.client.event.password.UpdatePasswordEvent;
import com.gbourquet.yaph.client.event.password.created.CreatedPasswordEvent;
import com.gbourquet.yaph.client.event.password.created.CreatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.created.CreatingErrorPasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.deleted.DeletingErrorPasswordEvent;
import com.gbourquet.yaph.client.event.password.updated.UpdatedPasswordEvent;
import com.gbourquet.yaph.client.event.password.updated.UpdatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.updated.UpdatingErrorPasswordEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.service.crypt.CryptService;
import com.gbourquet.yaph.service.crypt.DefaultCryptServiceImpl;
import com.gbourquet.yaph.service.password.PasswordOfflineLocalServiceImpl;
import com.gbourquet.yaph.service.password.PasswordOnlineLocalServiceImpl;
import com.gbourquet.yaph.service.password.PasswordRemoteServiceImpl;
import com.gbourquet.yaph.service.password.PasswordService;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class PasswordPresenter extends AbstractPresenter {

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {

		HasClickHandlers getNewPasswordButton();
		HasClickHandlers getUpdatePasswordButton();
		HasClickHandlers getDeletePasswordButton();

		void addPassword(PasswordCard password);
		void removePassword(PasswordCard password);
		void addField(PasswordField field);

		void clearFields();
		void unselectPassword();
		void selectPassword(PasswordCard password);
		void refreshPasswordList();
		void updatePasswordList(List<PasswordCard> passwords);
		void addSelectionChangeHandler(Handler handler);
		PasswordCard getSelectedPassword();
		void setFieldsVisible(Boolean isVisible);

	}

	public View view;

	private List<PasswordCard> passwords;
	private HashMap<PasswordCard, List<PasswordField>> fields;
	
	private PasswordService remoteService;
	private PasswordService localOnlineService;
	private PasswordService localOfflineService;
	
	public PasswordPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getPasswordView();
		//TODO à fabriquer par la factory
		remoteService = new PasswordRemoteServiceImpl(factory);
		localOnlineService = new PasswordOnlineLocalServiceImpl(factory);
		localOfflineService = new PasswordOfflineLocalServiceImpl(factory);
		
		bind();
	}

	public void bind() {
		//Evenements de la vue
		getView().getNewPasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// On envoi un message au presenter de la vue de création de mot de passe
				getEventBus().fireEvent(new NewPasswordEvent());
			}
		});

		getView().getUpdatePasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//on retire le mot de passe des données du presenter
				PasswordCard uPassword = getView().getSelectedPassword();
				List<PasswordField> uFields = fields.get(uPassword);
				
				passwords.remove(uPassword);
				fields.remove(uFields);
				
				// On envoi un message au presenter de la vue de création de mot de passe
				// avec le mot de passe à modifier et les champs
				getEventBus().fireEvent(new UpdatePasswordEvent(uPassword, uFields));
			}
		});

		getView().getDeletePasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Confirmez vous la suppression ?")) {

					PasswordCard sPassword = getView().getSelectedPassword(); 
					Boolean disconnected = ((Boolean) LocalSession.getInstance().getAttribute("disconnected") == null) ? false : (Boolean) LocalSession.getInstance().getAttribute(
							"disconnected");
					if (disconnected) {
						localOfflineService.deletePassword(sPassword);
					} else {
						remoteService.deletePassword(sPassword);
					}
				}
			}
		});

		getView().addSelectionChangeHandler(new Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if (getView().getSelectedPassword() != null) {
					PasswordCard sPassword = getView().getSelectedPassword();
					List<PasswordField> sFields = fields.get(sPassword);
					getView().clearFields();
					for (PasswordField field : sFields) {
						getView().addField(field);
					}

					getView().setFieldsVisible(true);
				}
			}

		});
		
		//Evenements du bus
		getEventBus().addHandler(LoginEvent.TYPE, new LoginEventHandler() {

			@Override
			public void onLogin(final LoginEvent event) {
				final Account account = event.getAccount();
				CryptService cryptService = new DefaultCryptServiceImpl();
				passwords = new ArrayList<PasswordCard>();
				fields = new HashMap<PasswordCard, List<PasswordField>>();
				List<PasswordCard> cPasswords = DataAccess.getInstance().getPasswords(account);
				for (PasswordCard sPassword : cPasswords)
				{
					PasswordCard dsPassword = cryptService.decrypt(sPassword);
					List<PasswordField> sFields = cryptService.decrypt(DataAccess.getInstance().getFields(dsPassword));
					passwords.add(dsPassword);
					fields.put(dsPassword, sFields);
				}
				getView().updatePasswordList(passwords);
			}

		});

		getEventBus().addHandler(CreatedPasswordEvent.TYPE, new CreatedPasswordEventHandler() {

			@Override
			public void onRemoteCreatedPassword(CreatedPasswordEvent event) {
				// Rien à faire
				
			}

			@Override
			public void onLocalCreatedPassword(CreatedPasswordEvent event) {
				// On met à jour la liste et on actualise la vue
				//On dechiffre les données
				CryptService cryptService = new DefaultCryptServiceImpl();
				PasswordCard sPassword = cryptService.decrypt(event.getPasswordCard());
				List<PasswordField> sFields = cryptService.decrypt(event.getFields());
				getView().addPassword(sPassword);
				getView().selectPassword(sPassword);
				getView().clearFields();
				for (PasswordField field : sFields) {
					getView().addField(field);
				}
				getView().setFieldsVisible(true);
				
				passwords.add(sPassword);
				fields.put(sPassword, sFields);
				
			}

			@Override
			public void onRemoteErrorPassword(CreatingErrorPasswordEvent event) {
				// Rien à faire
				
			}

			@Override
			public void onLocalErrorPassword(CreatingErrorPasswordEvent event) {
				// Rien à faire
				
			}
		});

		getEventBus().addHandler(UpdatedPasswordEvent.TYPE, new UpdatedPasswordEventHandler() {

			@Override
			public void onRemoteUpdatedPassword(UpdatedPasswordEvent event) {
				// Rien à faire
			}

			@Override
			public void onLocalUpdatedPassword(UpdatedPasswordEvent event) {
				CryptService cryptService = new DefaultCryptServiceImpl();
				PasswordCard sPassword = getView().getSelectedPassword();
				sPassword.setAccount(event.getPasswordCard().getAccount());
				sPassword.setId(event.getPasswordCard().getId());
				sPassword.setTitre(cryptService.decrypt(event.getPasswordCard().getTitre()));
				getView().refreshPasswordList();

				List<PasswordField> sFields = cryptService.decrypt(event.getFields());
				getView().clearFields();
				for (PasswordField field : sFields) {
					getView().addField(field);
				}
				getView().setFieldsVisible(true);
				
				passwords.add(sPassword);
				fields.put(sPassword, sFields);
				
			}

			@Override
			public void onRemoteErrorPassword(UpdatingErrorPasswordEvent event) {
				// Rien à faire
				
			}

			@Override
			public void onLocalErrorPassword(UpdatingErrorPasswordEvent event) {
				// Rien à faire
				
			}

		});

		getEventBus().addHandler(DeletedPasswordEvent.TYPE, new DeletedPasswordEventHandler() {
			
			@Override
			public void onRemoteErrorPassword(DeletingErrorPasswordEvent event) {
				//On supprime en local
				localOfflineService.deletePassword(event.getPasswordCard());
			}
			
			@Override
			public void onRemoteDeletedPassword(DeletedPasswordEvent event) {
				// On supprime en local
				localOnlineService.deletePassword(event.getPasswordCard());
			}
			
			@Override
			public void onLocalErrorPassword(DeletingErrorPasswordEvent event) {
				//On affiche l'erreur				
			}
			
			@Override
			public void onLocalDeletedPassword(DeletedPasswordEvent event) {
				PasswordCard sPassword=event.getPasswordCard();
				//On met les data à jour
				passwords.remove(sPassword);
				fields.remove(sPassword);
				
				//On met la vue à jour
				getView().removePassword(sPassword);
				getView().clearFields();
				getView().unselectPassword();
				
			}
		});
	}

	@Override
	public void start() {
		RootPanel.get("container").clear();
		RootPanel.get("container").add(getView().asWidget());
		getEventBus().fireEvent(new MenuEvent("password", true));
		// On nettoie
		getView().setFieldsVisible(false);
		getView().unselectPassword();
	}

	@Override
	public String mayStop() {
		getEventBus().fireEvent(new MenuEvent("password", false));
		return null;
	}

	public View getView() {
		return view;
	}

}