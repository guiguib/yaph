package com.gbourquet.yaph.client.mvp.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.event.password.NewPasswordEvent;
import com.gbourquet.yaph.client.event.password.UpdatePasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.deleted.DeletingErrorPasswordEvent;
import com.gbourquet.yaph.client.event.password.read.ReadErrorPasswordEvent;
import com.gbourquet.yaph.client.event.password.read.ReadPasswordEvent;
import com.gbourquet.yaph.client.event.password.read.ReadPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.saved.SavedPasswordEvent;
import com.gbourquet.yaph.client.event.password.saved.SavedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.saved.SavingErrorPasswordEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.service.crypt.CryptService;
import com.gbourquet.yaph.client.service.crypt.DefaultCryptServiceImpl;
import com.gbourquet.yaph.client.service.password.PasswordOfflineLocalServiceImpl;
import com.gbourquet.yaph.client.service.password.PasswordOnlineLocalServiceImpl;
import com.gbourquet.yaph.client.service.password.PasswordRemoteServiceImpl;
import com.gbourquet.yaph.client.service.password.PasswordService;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
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
	private CryptService cryptService;

	public PasswordPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getPasswordView();
		// TODO à fabriquer par la factory
		remoteService = new PasswordRemoteServiceImpl(factory);
		localOnlineService = new PasswordOnlineLocalServiceImpl(factory);
		localOfflineService = new PasswordOfflineLocalServiceImpl(factory);
		cryptService = new DefaultCryptServiceImpl();

		bind();
	}

	public void bind() {
		// Evenements de la vue
		getView().getNewPasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// On envoi un message au presenter de la vue de création de mot
				// de passe
				getEventBus().fireEvent(new NewPasswordEvent());
			}
		});

		getView().getUpdatePasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// on retire le mot de passe des données du presenter
				PasswordCard uPassword = getView().getSelectedPassword();
				List<PasswordField> uFields = fields.get(uPassword);

				// On envoi un message au presenter de la vue de création de mot
				// de passe avec le mot de passe à modifier et les champs
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

		// Evenements du bus
		/*getEventBus().addHandler(LoginEvent.TYPE, new LoginEventHandler() {

			@Override
			public void onLogin(final LoginEvent event) {
				final Account account = event.getAccount();
				CryptService cryptService = new DefaultCryptServiceImpl();
				passwords = new ArrayList<PasswordCard>();
				fields = new HashMap<PasswordCard, List<PasswordField>>();
				List<PasswordCard> cPasswords = DataAccess.getInstance().getPasswords(account);
				for (PasswordCard sPassword : cPasswords) {
					PasswordCard dsPassword = cryptService.decrypt(sPassword);
					List<PasswordField> sFields = cryptService.decrypt(DataAccess.getInstance().getFields(dsPassword));
					passwords.add(dsPassword);
					fields.put(dsPassword, sFields);
				}
				getView().updatePasswordList(passwords);
			}

			@Override
			public void onNotLogged(NotLoggedEvent notLoggedEvent) {
				// rien à faire
			}

		});*/

		getEventBus().addHandler(SavedPasswordEvent.TYPE, new SavedPasswordEventHandler() {

			@Override
			public void onRemoteSavedPassword(SavedPasswordEvent event) {
				// Rien à faire
			}

			@Override
			public void onLocalSavedPassword(SavedPasswordEvent event) {
				// On met à jour la liste et on actualise la vue
				// On dechiffre les données
				PasswordCard sPassword = cryptService.decrypt(event.getPasswordCard());
				List<PasswordField> sFields = cryptService.decrypt(event.getFields());

				PasswordCard selectedPassword = getView().getSelectedPassword();
				if (event.isModeUpdate()) {
					getView().removePassword(selectedPassword);
					passwords.remove(selectedPassword);
					fields.remove(selectedPassword);
					getView().refreshPasswordList();
				} 
				getView().addPassword(sPassword);
				getView().selectPassword(sPassword);
				passwords.add(sPassword);
				fields.put(sPassword, sFields);
			
				getView().clearFields();
				for (PasswordField field : sFields) {
					getView().addField(field);
				}
				getView().selectPassword(sPassword);
				getView().setFieldsVisible(true);

			}

			@Override
			public void onRemoteErrorPassword(SavingErrorPasswordEvent event) {
				Window.alert(event.getErrorMessage());
			}

			@Override
			public void onLocalErrorPassword(SavingErrorPasswordEvent event) {
				Window.alert(event.getErrorMessage());
			}
		});

		getEventBus().addHandler(DeletedPasswordEvent.TYPE, new DeletedPasswordEventHandler() {

			@Override
			public void onRemoteErrorPassword(DeletingErrorPasswordEvent event) {
				// On supprime en local
				localOfflineService.deletePassword(event.getPasswordCard());
			}

			@Override
			public void onRemoteDeletedPassword(DeletedPasswordEvent event) {
				// On supprime en local
				localOnlineService.deletePassword(event.getPasswordCard());
			}

			@Override
			public void onLocalErrorPassword(DeletingErrorPasswordEvent event) {
				// On affiche l'erreur
			}

			@Override
			public void onLocalDeletedPassword(DeletedPasswordEvent event) {
				PasswordCard sPassword = event.getPasswordCard();
				// On met les data à jour
				passwords.remove(sPassword);
				fields.remove(sPassword);

				// On met la vue à jour
				getView().removePassword(sPassword);
				getView().clearFields();
				getView().unselectPassword();

			}
		});

		getEventBus().addHandler(ReadPasswordEvent.TYPE, new ReadPasswordEventHandler() {

			@Override
			public void onRemoteReadPassword(ReadPasswordEvent event) {

				passwords = new ArrayList<PasswordCard>();
				fields = new HashMap<PasswordCard, List<PasswordField>>();

				for (PasswordCard lPassword : event.getData().keySet()) {
					List<PasswordField> lFields = event.getData().get(lPassword);
					
					PasswordCard clearPassword = cryptService.decrypt(lPassword);
					List<PasswordField> clearFields = cryptService.decrypt(lFields);
					passwords.add(clearPassword);
					fields.put(clearPassword, clearFields);
				
				}
				getView().updatePasswordList(passwords);
			}

			@Override
			public void onRemoteErrorPassword(ReadErrorPasswordEvent event) {
				Window.alert(event.getErrorMessage());
			}

			@Override
			public void onLocalReadPassword(ReadPasswordEvent event) {
			
			}

			@Override
			public void onLocalErrorPassword(ReadErrorPasswordEvent event) {
				Window.alert(event.getErrorMessage());
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