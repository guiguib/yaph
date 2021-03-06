package com.gbourquet.yaph.client.mvp.presenter;

import com.gbourquet.yaph.client.event.LoadApplicationEvent;
import com.gbourquet.yaph.client.event.LoadApplicationEventHandler;
import com.gbourquet.yaph.client.event.LogoutEvent;
import com.gbourquet.yaph.client.event.LogoutEventHandler;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.event.MenuEventHandler;
import com.gbourquet.yaph.client.event.login.LoginEvent;
import com.gbourquet.yaph.client.event.login.LoginEventHandler;
import com.gbourquet.yaph.client.event.login.NotLoggedEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public class MenuPresenter extends AbstractPresenter {

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {
		public void updateUserLabel(String newLabel);

		public void updateActionLabel(String newLabel, String newAction);
	
		public void hideSideNavBar();
	}

	public View view;

	public MenuPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getMenuView();
		bind();
	}

	public void bind() {
		// On s'inscrit aux evenements du Bus
		getEventBus().addHandler(LoadApplicationEvent.TYPE, new LoadApplicationEventHandler() {

			@Override
			public void onLoad(final LoadApplicationEvent event) {
				RootPanel.get("menu").clear();
				RootPanel.get("menu").add(getView().asWidget());
			}
		});

		getEventBus().addHandler(LoginEvent.TYPE, new LoginEventHandler() {

			@Override
			public void onLogin(final LoginEvent event) {
				Account account = event.getAccount();
				getView().updateUserLabel(account.getPrenom() + " " + account.getNom());
				getView().updateActionLabel("Sign out", "logout:");
			
			}

			@Override
			public void onNotLogged(NotLoggedEvent notLoggedEvent) {
				// rien à faire
				
			}
		});
		getEventBus().addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {

			@Override
			public void onLogout(final LogoutEvent event) {
				getView().updateUserLabel("");
				getView().updateActionLabel("Sign in", "login:");
			}
		});

		getEventBus().addHandler(MenuEvent.TYPE, new MenuEventHandler() {

			@Override
			public void onMenu(final MenuEvent event) {
				getView().hideSideNavBar();;
			}
		});
	}

	@Override
	public void start() {
	}

	public View getView() {
		return view;
	}
}