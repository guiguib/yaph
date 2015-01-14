package com.gbourquet.yaph.client.service.login;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.event.login.LoginEvent;
import com.gbourquet.yaph.client.event.login.NotLoggedEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.serveur.callback.MyAsyncCallback;
import com.gbourquet.yaph.serveur.login.in.LoginFromSessionAction;
import com.gbourquet.yaph.serveur.login.out.LoginResult;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.google.web.bindery.event.shared.EventBus;

public class DefaultLoginServiceImpl implements LoginService {

	private DispatchAsync dispatcher;
	private EventBus eventBus;

	public DefaultLoginServiceImpl(ClientFactory factory) {

		dispatcher = factory.getDistpatcher();
		eventBus = factory.getEventBus();
	}
	
	@Override
	public void sessionLogged() {
		dispatcher.execute(new LoginFromSessionAction(), new MyAsyncCallback<LoginResult>(eventBus) {
			public void success(final LoginResult result) {

				Account account = result.getAccount();
				
				
				if (account != null) {
					eventBus.fireEvent(new LoginEvent(account, result.getToken()));
				} else {
					eventBus.fireEvent(new NotLoggedEvent());
				}
			}

			public void failure(final Throwable e) {
				eventBus.fireEvent(new NotLoggedEvent());
			}
		});

	}

}
