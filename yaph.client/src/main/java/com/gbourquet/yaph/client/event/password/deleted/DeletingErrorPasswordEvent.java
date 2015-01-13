package com.gbourquet.yaph.client.event.password.deleted;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;

public class DeletingErrorPasswordEvent extends DeletedPasswordEvent  {
	public static Type<DeletedPasswordEventHandler> TYPE = new Type<DeletedPasswordEventHandler>();

	private String errorMessage;
	
	
	public DeletingErrorPasswordEvent(final PasswordCard password, String message) {
		super(password);
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Type<DeletedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final DeletedPasswordEventHandler handler) {
		handler.onRemoteErrorPassword(this);
	}
}
