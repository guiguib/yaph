package com.gbourquet.yaph.client.event.password.saved;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class SavingErrorPasswordEvent extends SavedPasswordEvent  {
	public static Type<SavedPasswordEventHandler> TYPE = new Type<SavedPasswordEventHandler>();

	private String errorMessage;
	
	
	public SavingErrorPasswordEvent(final PasswordCard password, List<PasswordField> fields, String message) {
		super(password,fields);
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Type<SavedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final SavedPasswordEventHandler handler) {
		handler.onRemoteErrorPassword(this);
	}
}
