package com.gbourquet.yaph.client.event;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.google.gwt.event.shared.GwtEvent;

public class UpdatedPasswordEvent extends GwtEvent<UpdatedPasswordEventHandler> {
	public static Type<UpdatedPasswordEventHandler> TYPE = new Type<UpdatedPasswordEventHandler>();

	private final PasswordCard password;

	public UpdatedPasswordEvent(final PasswordCard password) {
		this.password = password;
	}

	public PasswordCard getPasswordCard() {
		return password;
	}

	@Override
	public Type<UpdatedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final UpdatedPasswordEventHandler handler) {
		handler.onUpdatedPassword(this);
	}
}
