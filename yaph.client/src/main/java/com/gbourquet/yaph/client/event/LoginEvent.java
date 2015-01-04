package com.gbourquet.yaph.client.event;

import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.google.gwt.event.shared.GwtEvent;


public class LoginEvent extends GwtEvent<LoginEventHandler> {
public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	
	private final Account account;
	
	public LoginEvent(final Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
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
