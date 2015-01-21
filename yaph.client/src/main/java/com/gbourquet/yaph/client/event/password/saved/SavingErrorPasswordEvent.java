package com.gbourquet.yaph.client.event.password.saved;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public abstract class SavingErrorPasswordEvent extends SavedPasswordEvent  {
	
	private String errorMessage;
	
	
	public SavingErrorPasswordEvent(final PasswordCard password, List<PasswordField> fields, String message,boolean modeUpdate) {
		super(password,fields,modeUpdate);
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
