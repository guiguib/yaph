package com.gbourquet.yaph.client.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.CreatedPasswordEvent;
import com.gbourquet.yaph.client.event.CreatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.LoginEvent;
import com.gbourquet.yaph.client.event.LoginEventHandler;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.event.NewPasswordEvent;
import com.gbourquet.yaph.client.event.UpdatePasswordEvent;
import com.gbourquet.yaph.client.event.UpdatedPasswordEvent;
import com.gbourquet.yaph.client.event.UpdatedPasswordEventHandler;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.utils.CryptoClient;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.password.in.DeletePasswordAction;
import com.gbourquet.yaph.service.password.out.DeletePasswordResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class PasswordPresenter extends AbstractPresenter {

	private DispatchAsync dispatcher;

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

	private List<PasswordField> fields;

	public PasswordPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getPasswordView();
		dispatcher = factory.getDistpatcher();
		bind();
	}

	public void bind() {
		getEventBus().addHandler(LoginEvent.TYPE, new LoginEventHandler() {

			@Override
			public void onLogin(final LoginEvent event) {
				final Account account = event.getAccount();
				getView().updatePasswordList(decrypt(DataAccess.getInstance().getPasswords(account)));
			}

		});

		getEventBus().addHandler(CreatedPasswordEvent.TYPE, new CreatedPasswordEventHandler() {

			@Override
			public void onCreatedPassword(CreatedPasswordEvent event) {
				getView().addPassword(event.getPasswordCard());
				getView().selectPassword(event.getPasswordCard());
				fields = event.getFields();
				getView().clearFields();
				for (PasswordField field : fields) {
					getView().addField(field);
				}
				getView().setFieldsVisible(true);
			}
		});

		getEventBus().addHandler(UpdatedPasswordEvent.TYPE, new UpdatedPasswordEventHandler() {

			@Override
			public void onUpdatedPassword(UpdatedPasswordEvent event) {
				PasswordCard password = getView().getSelectedPassword();
				password.setAccount(event.getPasswordCard().getAccount());
				password.setId(event.getPasswordCard().getId());
				password.setTitre(event.getPasswordCard().getTitre());
				getView().refreshPasswordList();

				fields = event.getFields();
				getView().clearFields();
				for (PasswordField field : fields) {
					getView().addField(field);
				}
				getView().setFieldsVisible(true);

			}

		});

		getView().getNewPasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// On envoi un message à la vue de création de mot de passe avec
				// le mot de passe à modifier et les champs
				getEventBus().fireEvent(new NewPasswordEvent(getView().getSelectedPassword()));

			}
		});

		getView().getUpdatePasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// On envoi un message à la vue de création de mot de passe avec
				// le mot de passe à modifier et les champs
				getEventBus().fireEvent(new UpdatePasswordEvent(getView().getSelectedPassword(), fields));
			}
		});

		getView().getDeletePasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Confirmez vous la suppression ?")) {
					// On supprime en local
					DataAccess.getInstance().deletePassword(getView().getSelectedPassword());

					Boolean disconnected = ((Boolean) LocalSession.getInstance().getAttribute("disconnected") == null) ? false : (Boolean) LocalSession.getInstance().getAttribute(
							"disconnected");
					if (disconnected) {
						getView().clearFields();
						getView().setFieldsVisible(false);
						getView().removePassword(getView().getSelectedPassword());
						getView().unselectPassword();
					} else {
						// On supprime en distant
						dispatcher.execute(new DeletePasswordAction(getView().getSelectedPassword()), new MyAsyncCallback<DeletePasswordResult>(getEventBus()) {

							@Override
							public void success(DeletePasswordResult result) {

								getView().clearFields();
								getView().setFieldsVisible(false);
								getView().removePassword(getView().getSelectedPassword());
								getView().unselectPassword();
							}

							@Override
							public void failure(Throwable caught) {
							}

						});
					}
				}
			}
		});

		getView().addSelectionChangeHandler(new Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if (getView().getSelectedPassword() != null) {
					PasswordCard password = getView().getSelectedPassword();
					// On récupère les données en local
					fields = decryptF(DataAccess.getInstance().getFields(password));
					getView().clearFields();
					for (PasswordField field : fields) {
						getView().addField(field);
					}

					getView().setFieldsVisible(true);
				}
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

	private List<PasswordCard> decrypt(List<PasswordCard> passwords) {
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

		List<PasswordCard> ret = new ArrayList<PasswordCard>();
		for (PasswordCard password : passwords) {
			PasswordCard local = crypt.decrypt(password, key);

			ret.add(local);
		}

		return ret;
	}

	private List<PasswordField> decryptF(List<PasswordField> fieldsData) {
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
			PasswordField local = crypt.decrypt(field, key);
			ret.add(local);
		}

		return ret;
	}

}