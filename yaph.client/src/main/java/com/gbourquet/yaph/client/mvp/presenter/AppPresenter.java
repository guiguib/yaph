package com.gbourquet.yaph.client.mvp.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.DisconnectionEvent;
import com.gbourquet.yaph.client.event.DisconnectionEventHandler;
import com.gbourquet.yaph.client.event.LoadApplicationEvent;
import com.gbourquet.yaph.client.event.LoginEvent;
import com.gbourquet.yaph.client.event.LoginEventHandler;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.LoginPlace;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.login.in.LoginFromSessionAction;
import com.gbourquet.yaph.service.login.out.LoginResult;
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
	private DispatchAsync dispatcher;

	public AppPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getAppView();
		dispatcher = factory.getDistpatcher();
		bind();
	}

	public void bind() {

		RootPanel.get("container").add(getView().asWidget());
		// On s'inscrit aux evenements du Bus
		getEventBus().addHandler(LoginEvent.TYPE, new LoginEventHandler() {

			@Override
			public void onLogin(final LoginEvent event) {
			}
		});

		getEventBus().addHandler(DisconnectionEvent.TYPE,
				new DisconnectionEventHandler() {

					@Override
					public void onDisconnect(DisconnectionEvent event) {
						LocalSession.getInstance().setAttribute("disconnected",
								true);
					}
				});

		// On est loggué en distant ?
		dispatcher.execute(new LoginFromSessionAction(),
				new MyAsyncCallback<LoginResult>(getEventBus()) {
					public void success(final LoginResult result) {

						Account account = result.getAccount();
						if (account == null) {
							// On a un compte en local ?
							account = DataAccess.getInstance().getAccount();
						}
						if (account != null) {
							LocalSession.getInstance().setAttribute("token",result.getToken());
							LocalSession.getInstance().setAttribute("account",account);
							// On envoie un message dans le bus
							getEventBus().fireEvent(new LoginEvent(account));
						} else {
							// On redirige vers la vue de connexion
							getFactory().getPlaceController().goTo(new LoginPlace(""));
						}
					}

					public void failure(final Throwable e) {
						// On redirige vers la vue de connexion
						getFactory().getPlaceController().goTo(new LoginPlace(""));

					}
				});

		// L'application est lancé
		getEventBus().fireEvent(new LoadApplicationEvent());
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