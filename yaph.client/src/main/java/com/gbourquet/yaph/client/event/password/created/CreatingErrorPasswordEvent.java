package com.gbourquet.yaph.client.event.password.created;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class CreatingErrorPasswordEvent extends CreatedPasswordEvent  {
	public static Type<CreatedPasswordEventHandler> TYPE = new Type<CreatedPasswordEventHandler>();

	private String errorMessage;
	
	
	public CreatingErrorPasswordEvent(final PasswordCard password, List<PasswordField> fields, String message) {
		super(password,fields);
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Type<CreatedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final CreatedPasswordEventHandler handler) {
		handler.onRemoteErrorPassword(this);
	}
}
