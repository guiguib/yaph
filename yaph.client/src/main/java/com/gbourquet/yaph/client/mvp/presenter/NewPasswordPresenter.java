package com.gbourquet.yaph.client.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.CreatedPasswordEvent;
import com.gbourquet.yaph.client.event.NewPasswordEvent;
import com.gbourquet.yaph.client.event.NewPasswordEventHandler;
import com.gbourquet.yaph.client.event.UpdatePasswordEvent;
import com.gbourquet.yaph.client.event.UpdatePasswordEventHandler;
import com.gbourquet.yaph.client.event.UpdatedPasswordEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.PasswordPlace;
import com.gbourquet.yaph.client.utils.CryptoClient;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.password.in.PasswordAction;
import com.gbourquet.yaph.service.password.out.PasswordResult;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class NewPasswordPresenter extends AbstractPresenter {

	private DispatchAsync dispatcher;

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

	public NewPasswordPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getNewPasswordView();
		dispatcher = factory.getDistpatcher();
		bind();
	}

	public void bind() {

		RootPanel.get("dialog").add(getView().asWidget());

		// Valid nouveau mot de passe
		getView().getValidButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final Account account = (Account) LocalSession.getInstance().getAttribute("account");
				if (account != null)
					passwordData.setAccount(account.getId());
				passwordData.setTitre(getView().getTitleText());
				fieldsData = getView().getPasswordFields();

				// On encrypte les données
				final PasswordCard cryptedPasswordData = crypt(passwordData);
				final List<PasswordField> cryptedfieldsData = crypt(fieldsData);

				Boolean disconnected = ((Boolean) LocalSession.getInstance().getAttribute("disconnected") == null) ? false : (Boolean) LocalSession.getInstance().getAttribute(
						"disconnected");
				if (disconnected) {
					// On insère en base locale
					int idPassword = DataAccess.getInstance().insertOrUpdatePassword(account, cryptedPasswordData);
					cryptedPasswordData.setId(idPassword);
					DataAccess.getInstance().insertOrUpdatePasswordFields(cryptedPasswordData, cryptedfieldsData);
					// On envoit un message dans le bus pour actualiser les
					// autres vues
					if (idPassword == passwordData.getId())
						getEventBus().fireEvent(new UpdatedPasswordEvent(passwordData, fieldsData));
					else {
						passwordData.setId(idPassword);
						getEventBus().fireEvent(new CreatedPasswordEvent(passwordData, fieldsData));
					}
				} else {
					final int updateId = cryptedPasswordData.getId();
					dispatcher.execute(new PasswordAction(cryptedPasswordData, cryptedfieldsData), new MyAsyncCallback<PasswordResult>(getEventBus()) {
						public void success(PasswordResult result) {

							// On insère en base locale
							DataAccess.getInstance().insertOrUpdatePassword(account, result.getPasswordCard());
							DataAccess.getInstance().insertOrUpdatePasswordFields(result.getPasswordCard(), result.getPasswordFields());

							passwordData.setId(result.getPasswordCard().getId());
							// On envoit un message dans le bus pour actualiser
							// les autres vues
							if (result.getPasswordCard().getId().equals(updateId))
								getEventBus().fireEvent(new UpdatedPasswordEvent(passwordData, fieldsData));
							else
								getEventBus().fireEvent(new CreatedPasswordEvent(passwordData, fieldsData));
							passwordData = new PasswordCard();
						}

						public void failure(Throwable caught) {
							GWT.log("Remote Erreur" + caught.getMessage());
							// On insère en base locale
							int idPassword = DataAccess.getInstance().insertOrUpdatePassword(account, cryptedPasswordData);
							passwordData.setId(idPassword);
							DataAccess.getInstance().insertOrUpdatePasswordFields(cryptedPasswordData, cryptedfieldsData);
							passwordData = new PasswordCard();
						}
					});
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
				passwordData = new PasswordCard();
				passwordData.setId(0);
				passwordData.setTitre("");
				fieldsData = new ArrayList<PasswordField>();
				getView().clear();
				getView().show();
			}
		});

		getEventBus().addHandler(UpdatePasswordEvent.TYPE, new UpdatePasswordEventHandler() {

			@Override
			public void onUpdatePassword(UpdatePasswordEvent event) {
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

	private PasswordCard crypt(PasswordCard password) {
		CryptoClient crypt = new CryptoClient();
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		String cryptKey = account.getPassword();
		// String key = crypt.decrypt(cryptKey, "MacleLogiciel");
		String key = cryptKey;
		int ln = Math.min(key.length(), 23);
		key = key.substring(0, ln);
		StringBuffer sb = new StringBuffer(key);
		for (int i = ln; i < 23; i++)
			sb.append("0");
		key = sb.toString();
		PasswordCard local = crypt.crypt(password, key);

		return local;

	}

	private List<PasswordField> crypt(List<PasswordField> fieldsData) {
		CryptoClient crypt = new CryptoClient();
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		String cryptKey = account.getPassword();
		// String key = crypt.decrypt(cryptKey, "MacleLogiciel");
		String key = cryptKey;
		int ln = Math.min(key.length(), 23);
		key = key.substring(0, ln);
		StringBuffer sb = new StringBuffer(key);
		for (int i = ln; i < 23; i++)
			sb.append("0");
		key = sb.toString();
		List<PasswordField> ret = new ArrayList<PasswordField>();
		for (PasswordField field : fieldsData) {
			PasswordField local = crypt.crypt(field, key);
			ret.add(local);
		}

		return ret;
	}

}