package com.gbourquet.yaph.client.event.password.read;

import java.util.HashMap;
import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class ReadErrorPasswordEvent extends ReadPasswordEvent  {
	public static Type<ReadPasswordEventHandler> TYPE = new Type<ReadPasswordEventHandler>();

	private String errorMessage;
	
	
	public ReadErrorPasswordEvent(HashMap<PasswordCard,List<PasswordField>> data, String message) {
		super(data);
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Type<ReadPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final ReadPasswordEventHandler handler) {
		handler.onRemoteErrorPassword(this);
	}
}
