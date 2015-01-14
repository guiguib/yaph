package com.gbourquet.yaph.client.event.login;

import com.google.gwt.event.shared.GwtEvent;

public class NotLoggedEvent extends GwtEvent<LoginEventHandler> {
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

	public NotLoggedEvent() {
	}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final LoginEventHandler handler) {
		handler.onNotLogged(this);
	}
}
