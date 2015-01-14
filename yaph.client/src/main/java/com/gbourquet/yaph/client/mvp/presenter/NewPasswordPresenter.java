package com.gbourquet.yaph.client.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.password.NewPasswordEvent;
import com.gbourquet.yaph.client.event.password.NewPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.UpdatePasswordEvent;
import com.gbourquet.yaph.client.event.password.UpdatePasswordEventHandler;
import com.gbourquet.yaph.client.event.password.created.CreatedPasswordEvent;
import com.gbourquet.yaph.client.event.password.created.CreatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.created.CreatingErrorPasswordEvent;
import com.gbourquet.yaph.client.event.password.updated.UpdatedPasswordEvent;
import com.gbourquet.yaph.client.event.password.updated.UpdatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.updated.UpdatingErrorPasswordEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.PasswordPlace;
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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class NewPasswordPresenter extends AbstractPresenter {

	boolean modeUpdate = false;

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {

		HasClickHandlers getCancelButton();

		HasClickHandlers getValidButton();

		HasClickHandlers getAddFieldButton();

		String getTitleText();

		void setTitleText(String title);

		void setHeaderText(String header);

		Label getErrorLabel();

		void addField(PasswordField field);

		List<PasswordField> getPasswordFields();

		void delFields();

		void clear();

		void show();

		void close();

	}

	public View view;

	private PasswordCard passwordData = new PasswordCard();
	private List<PasswordField> fieldsData = new ArrayList<PasswordField>();

	private PasswordService remoteService;
	private PasswordService localOnlineService;
	private PasswordService localOfflineService;

	public NewPasswordPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getNewPasswordView();
		// TODO à fabriquer par la factory
		remoteService = new PasswordRemoteServiceImpl(factory);
		localOnlineService = new PasswordOnlineLocalServiceImpl(factory);
		localOfflineService = new PasswordOfflineLocalServiceImpl(factory);
		bind(factory);
	}

	public void bind(final ClientFactory factory) {

		RootPanel.get("dialog").add(getView().asWidget());

		//Evenements de la vue
		// Valid nouveau mot de passe
		getView().getValidButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final Account account = (Account) LocalSession.getInstance().getAttribute("account");
				if (account != null)
					passwordData.setAccount(account.getId());
				passwordData.setTitre(getView().getTitleText());
				fieldsData = getView().getPasswordFields();
				
				// On chiffre les données
				CryptService cryptService = new DefaultCryptServiceImpl();
				final PasswordCard cryptedPasswordData = cryptService.crypt(passwordData);
				final List<PasswordField> cryptedfieldsData = cryptService.crypt(fieldsData);
				
				
				Boolean disconnected = ((Boolean) LocalSession.getInstance().getAttribute("disconnected") == null) ? false : (Boolean) LocalSession.getInstance().getAttribute(
						"disconnected");
				if (disconnected) {
					//On enregistre en local
					if (modeUpdate)
						localOfflineService.updatePassword(cryptedPasswordData, cryptedfieldsData);
					else
						localOfflineService.insertPassword(cryptedPasswordData, cryptedfieldsData);
				} else {
					if (modeUpdate)
						remoteService.updatePassword(cryptedPasswordData, cryptedfieldsData);
					else
						remoteService.insertPassword(cryptedPasswordData, cryptedfieldsData);
				}

				getView().clear();
				getView().close();
			}

		});

		// Cancel nouveau mot de passe
		getView().getCancelButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().clear();
				getView().close();
				getFactory().getPlaceController().goTo(new PasswordPlace(""));
			}
		});

		// Ajout d'un champ
		getView().getAddFieldButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				PasswordField field = new PasswordField();
				field.setId(0);
				field.setIdCard(passwordData.getId());
				field.setLibelle("");
				field.setType("TEXT");
				field.setValue("");
				getView().addField(field);
				fieldsData.add(field);

			}
		});

		// Messages du bus
		getEventBus().addHandler(NewPasswordEvent.TYPE, new NewPasswordEventHandler() {

			@Override
			public void onNewPassword(NewPasswordEvent event) {
				modeUpdate = false;
				passwordData = new PasswordCard();
				passwordData.setId(0);
				passwordData.setTitre("");
				fieldsData = new ArrayList<PasswordField>();
				getView().clear();
				getView().show();
			}
		});

		getEventBus().addHandler(CreatedPasswordEvent.TYPE,new CreatedPasswordEventHandler() {
			
			@Override
			public void onRemoteErrorPassword(CreatingErrorPasswordEvent event) {
				// Erreur lors de l'enregistrement en base serveur
				//On enregistre en local 
				// On chiffre les données
				localOfflineService.insertPassword(event.getPasswordCard(), event.getFields());
				
			}
			
			@Override
			public void onRemoteCreatedPassword(CreatedPasswordEvent event) {
				//Le mot de passe est enregistré en base serveur
				//On l'enregistre en local
				localOnlineService.insertPassword(event.getPasswordCard(), event.getFields());
			}
			
			@Override
			public void onLocalErrorPassword(CreatingErrorPasswordEvent event) {
				//On affiche le message d'erreur
				getView().getErrorLabel().setText(event.getErrorMessage());
			}
			
			@Override
			public void onLocalCreatedPassword(CreatedPasswordEvent event) {
				//Password enregistré en base local.On réinitialise les données
				passwordData = new PasswordCard();
				fieldsData = new ArrayList<PasswordField>();
				//On ferme la fenetre
				getView().clear();
				getView().close();
			}
		});
			
		getEventBus().addHandler(UpdatePasswordEvent.TYPE, new UpdatePasswordEventHandler() {

			@Override
			public void onUpdatePassword(UpdatePasswordEvent event) {
				modeUpdate = true;
				passwordData = event.getPasswordCard();
				fieldsData = event.getPasswordFields();
				getView().clear();
				getView().setHeaderText("Update Password");
				getView().delFields();
				getView().setTitleText(passwordData.getTitre());
				for (PasswordField field : fieldsData) {
					getView().addField(field);
				}
				getView().show();
			}
		});
		
		getEventBus().addHandler(UpdatedPasswordEvent.TYPE,new UpdatedPasswordEventHandler() {
			
			@Override
			public void onRemoteErrorPassword(UpdatingErrorPasswordEvent event) {
				// Erreur lors de l'enregistrement en base serveur
				//On enregistre en local 
				// On chiffre les données
				localOfflineService.updatePassword(event.getPasswordCard(), event.getFields());
				
			}
			
			@Override
			public void onRemoteUpdatedPassword(UpdatedPasswordEvent event) {
				//Le mot de passe est enregistré en base serveur
				//On l'enregistre en local
				localOnlineService.updatePassword(event.getPasswordCard(), event.getFields());
			}
			
			@Override
			public void onLocalErrorPassword(UpdatingErrorPasswordEvent event) {
				//On affiche le message d'erreur
				getView().getErrorLabel().setText(event.getErrorMessage());
			}
			
			@Override
			public void onLocalUpdatedPassword(UpdatedPasswordEvent event) {
				//Password enregistré en base local.On réinitialise les données
				passwordData = new PasswordCard();
				fieldsData = new ArrayList<PasswordField>();
				//On ferme la fenetre
				getView().clear();
				getView().close();
			}
		});
	}

	@Override
	public void start() {
	}

	@Override
	public String mayStop() {
		return null;
	}

	public View getView() {
		return view;
	}

}