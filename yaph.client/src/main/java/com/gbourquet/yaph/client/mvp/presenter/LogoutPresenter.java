package com.gbourquet.yaph.client.mvp.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.LogoutEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.login.in.LogoutAction;
import com.gbourquet.yaph.service.login.out.LoginResult;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public class LogoutPresenter extends AbstractPresenter {

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {
	}

	public View view;
	private DispatchAsync dispatcher;
	
	public LogoutPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getLogoutView();
		dispatcher = factory.getDistpatcher();
		bind();
	}

	public void bind() {
		RootPanel.get("container").add(getView().asWidget());
		getView().asWidget().setVisible(true);
	}

	@Override
	public void start() {
		RootPanel.get("container").clear();
		RootPanel.get("container").add(getView().asWidget());
		dispatcher.execute(new LogoutAction(), new MyAsyncCallback<LoginResult>(getEventBus()) {
			public void success(final LoginResult result) {
				
				LocalSession.getInstance().setAttribute("token", "");
				LocalSession.getInstance().setAttribute("account", null);
				// On envoie un message dans le bus
				getEventBus().fireEvent(new LogoutEvent());

			}

			public void failure(final Throwable e) {
				// On n'a pas réussi à se delogguer du serveur, mais on se "déconnecte" en local 
				LocalSession.getInstance().setAttribute("token", "");
				LocalSession.getInstance().setAttribute("account", null);
				
				//On envoie un message dans le bus
				getEventBus().fireEvent(new LogoutEvent());

				
			}
		});
	}

	public View getView() {
		return view;
	}
}