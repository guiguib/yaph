package com.gbourquet.yaph.client.event.password.deleted;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.google.gwt.event.shared.GwtEvent;

public abstract class DeletedPasswordEvent extends GwtEvent<DeletedPasswordEventHandler> {
	public static Type<DeletedPasswordEventHandler> TYPE = new Type<DeletedPasswordEventHandler>();

	private final PasswordCard password;
	
	public DeletedPasswordEvent(final PasswordCard password) {
		this.password = password;
	}

	public PasswordCard getPasswordCard() {
		return password;
	}

	@Override
	public Type<DeletedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected abstract void dispatch(final DeletedPasswordEventHandler handler);
	
}
