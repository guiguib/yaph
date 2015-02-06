package com.gbourquet.yaph.client.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.InlineEvent;
import com.gbourquet.yaph.client.event.InlineEventHandler;
import com.gbourquet.yaph.client.event.LoadApplicationEvent;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.event.login.LoginEvent;
import com.gbourquet.yaph.client.event.login.LoginEventHandler;
import com.gbourquet.yaph.client.event.login.NotLoggedEvent;
import com.gbourquet.yaph.client.event.password.read.ReadErrorPasswordEvent;
import com.gbourquet.yaph.client.event.password.read.ReadPasswordEvent;
import com.gbourquet.yaph.client.event.password.read.ReadPasswordEventHandler;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.LoginPlace;
import com.gbourquet.yaph.client.service.login.DefaultLoginServiceImpl;
import com.gbourquet.yaph.client.service.login.LoginService;
import com.gbourquet.yaph.client.service.password.PasswordOnlineLocalServiceImpl;
import com.gbourquet.yaph.client.service.password.PasswordRemoteServiceImpl;
import com.gbourquet.yaph.client.service.password.PasswordService;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public class AppPresenter extends AbstractPresenter {

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {
		void hello(String nom);
	}

	public View view;

	private PasswordService remotePasswordService;
	private PasswordService localOnlinePasswordService;
	private LoginService loginService;

	public AppPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getAppView();
		// TODO à fabriquer par la factory
		remotePasswordService = new PasswordRemoteServiceImpl(factory);
		localOnlinePasswordService = new PasswordOnlineLocalServiceImpl(factory);
		loginService = new DefaultLoginServiceImpl(factory);
		bind();
	}

	public static native void initJS() /*-{
		$wnd.jQuery('.button-collapse').sideNav();
  	}-*/;

	public void bind() {

		RootPanel.get("container").add(getView().asWidget());

		getEventBus().addHandler(InlineEvent.TYPE, new InlineEventHandler() {

			@Override
			public void onInline(InlineEvent event) {
				LocalSession.getInstance().setAttribute("disconnected", false);
				Account account = (Account) LocalSession.getInstance().getAttribute("account");
				// On synchronise les bases
				remotePasswordService.syncData(account);
			}

			@Override
			public void onOffline(InlineEvent event) {
				LocalSession.getInstance().setAttribute("disconnected", true);
			}
		});

		// On est loggué en distant ?
		loginService.sessionLogged();
		getEventBus().addHandler(LoginEvent.TYPE, new LoginEventHandler() {

			@Override
			public void onNotLogged(NotLoggedEvent notLoggedEvent) {
				// On redirige vers la vue de connexion
				getFactory().getPlaceController().goTo(new LoginPlace(""));

			}

			@Override
			public void onLogin(LoginEvent event) {
				Account account = event.getAccount();
				if (account != null) {
					LocalSession.getInstance().setAttribute("token", event.getToken());
					LocalSession.getInstance().setAttribute("account", account);
					remotePasswordService.syncData(account);

				} else {
					// On redirige vers la vue de connexion
					getFactory().getPlaceController().goTo(new LoginPlace(""));
				}
			}
		});

		getEventBus().addHandler(ReadPasswordEvent.TYPE, new ReadPasswordEventHandler() {

			@Override
			public void onRemoteReadPassword(ReadPasswordEvent event) {
				// On met à jour la base locale
				Account account = (Account) LocalSession.getInstance().getAttribute("account");
				if (account != null) {
					List<PasswordCard> lPasswords = new ArrayList<PasswordCard>();
					lPasswords.addAll(event.getData().keySet());
					List<PasswordField> lFields = new ArrayList<PasswordField>();
					for (PasswordCard lPassword : lPasswords) {
						lFields.addAll(event.getData().get(lPassword));
					}
					localOnlinePasswordService.savePasswords(account, lPasswords, lFields);
				}

			}

			@Override
			public void onRemoteErrorPassword(ReadErrorPasswordEvent event) {
				GWT.log("onRemoteErrorPassword :" + event.getErrorMessage());

			}

			@Override
			public void onLocalReadPassword(ReadPasswordEvent event) {

			}

			@Override
			public void onLocalErrorPassword(ReadErrorPasswordEvent event) {
				GWT.log("onLocalErrorPassword :" + event.getErrorMessage());

			}
		});

		// L'application est lancé
		getEventBus().fireEvent(new LoadApplicationEvent());

		initJS();
	}

	@Override
	public void start() {
		RootPanel.get("container").clear();
		RootPanel.get("container").add(getView().asWidget());
		getEventBus().fireEvent(new MenuEvent("app", true));
	}

	@Override
	public String mayStop() {
		getEventBus().fireEvent(new MenuEvent("app", false));
		return null;
	}

	public View getView() {
		return view;
	}
}