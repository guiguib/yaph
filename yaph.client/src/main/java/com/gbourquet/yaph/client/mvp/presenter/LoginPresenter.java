package com.gbourquet.yaph.client.mvp.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.LoginEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.AppPlace;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.login.in.LoginAction;
import com.gbourquet.yaph.service.login.out.LoginResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public class LoginPresenter extends AbstractPresenter {

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {
		HasClickHandlers getLoginButton();

		String getLoginText();

		String getPasswdText();

		HasKeyPressHandlers getLoginKeyPress();

		HasKeyPressHandlers getPasswdKeyPress();

		void setLoginText(String loginText);

		void setPasswdText(String passwdText);

		void errorLogin(String message);
	}

	public View view;
	private DispatchAsync dispatcher;

	public LoginPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getLoginView();
		dispatcher = factory.getDistpatcher();
		bind();
	}

	public void bind() {
		RootPanel.get("container").add(getView().asWidget());
		getView().asWidget().setVisible(false);
		view.getLoginButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				login();
			}
		});

		KeyPressHandler handler = new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				// Si on appuie sur "Entrée"
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
					login();
			}
		};

		getView().getLoginKeyPress().addKeyPressHandler(handler);
		getView().getPasswdKeyPress().addKeyPressHandler(handler);
		getView().asWidget().setVisible(true);
	}

	@Override
	public void start() {
		getView().setLoginText("");
		getView().setPasswdText("");
		RootPanel.get("container").clear();
		RootPanel.get("container").add(getView().asWidget());
	}

	public View getView() {
		return view;
	}

	@Override
	public String mayStop() {
		getView().setLoginText("");
		getView().setPasswdText("");
		getView().errorLogin("");
		return null;
	}

	private void login() {
		Boolean disconnected = ((Boolean) LocalSession.getInstance().getAttribute("disconnected") == null) ? false : (Boolean) LocalSession.getInstance().getAttribute(
				"disconnected");
		if (disconnected) {
			Account account = DataAccess.getInstance().getAccount(view.getLoginText(), view.getPasswdText());
			if (account == null)
				getView().errorLogin("Connexion impossible");
			else
				loginOK(account, "");
		} else {
			dispatcher.execute(new LoginAction(view.getLoginText(), view.getPasswdText()), new MyAsyncCallback<LoginResult>(getEventBus()) {
				public void success(final LoginResult result) {

					Account account = result.getAccount();
					String token = result.getToken();
					loginOK(account, token);
				}

				public void failure(final Throwable e) {
					Account account = null;
					if ("Connexion au serveur impossible".equals(e.getMessage())) {
						account = DataAccess.getInstance().getAccount(view.getLoginText(), view.getPasswdText());
					}
					if (account == null)
						getView().errorLogin(e.getMessage());
					else
						loginOK(account, "");

				}
			});
		}
	}

	private void loginOK(Account account, String token) {
		LocalSession.getInstance().setAttribute("token", token);
		LocalSession.getInstance().setAttribute("account", account);
		DataAccess.getInstance().setAccount(account);

		// On envoie un message dans le bus
		getEventBus().fireEvent(new LoginEvent(account));
		getView().setLoginText("");
		getView().setPasswdText("");
		getView().errorLogin("");
		getFactory().getPlaceController().goTo(new AppPlace(""));

	}
}