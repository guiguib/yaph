package com.gbourquet.yaph.client.event.login;

import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

	private final Account account;
	private final String token;
	
	public LoginEvent(final Account account, String token) {
		this.account = account;
		this.token = token;
	}

	public Account getAccount() {
		return account;
	}

	public String getToken() {
		return token;
	}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final LoginEventHandler handler) {
		handler.onLogin(this);
	}
}
