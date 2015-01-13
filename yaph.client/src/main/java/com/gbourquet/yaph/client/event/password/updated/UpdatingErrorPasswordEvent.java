package com.gbourquet.yaph.client.event.password.updated;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class UpdatingErrorPasswordEvent extends UpdatedPasswordEvent  {
	public static Type<UpdatedPasswordEventHandler> TYPE = new Type<UpdatedPasswordEventHandler>();

	private String errorMessage;
	
	
	public UpdatingErrorPasswordEvent(final PasswordCard password, List<PasswordField> fields, String message) {
		super(password,fields);
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Type<UpdatedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final UpdatedPasswordEventHandler handler) {
		handler.onRemoteErrorPassword(this);
	}
}
