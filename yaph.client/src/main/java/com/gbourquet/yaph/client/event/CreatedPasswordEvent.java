package com.gbourquet.yaph.client.event;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.google.gwt.event.shared.GwtEvent;

public class CreatedPasswordEvent extends GwtEvent<CreatedPasswordEventHandler> {
	public static Type<CreatedPasswordEventHandler> TYPE = new Type<CreatedPasswordEventHandler>();

	private final PasswordCard password;

	public CreatedPasswordEvent(final PasswordCard password) {
		this.password = password;
	}

	public PasswordCard getPasswordCard() {
		return password;
	}

	@Override
	public Type<CreatedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final CreatedPasswordEventHandler handler) {
		handler.onCreatedPassword(this);
	}
}
