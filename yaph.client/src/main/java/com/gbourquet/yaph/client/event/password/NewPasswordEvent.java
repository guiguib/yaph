package com.gbourquet.yaph.client.event.password;

import com.google.gwt.event.shared.GwtEvent;

public class NewPasswordEvent extends GwtEvent<NewPasswordEventHandler> {
	public static Type<NewPasswordEventHandler> TYPE = new Type<NewPasswordEventHandler>();

	public NewPasswordEvent() {
	}

	@Override
	public Type<NewPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final NewPasswordEventHandler handler) {
		handler.onNewPassword(this);
	}
}
