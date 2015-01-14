package com.gbourquet.yaph.client.event.password.read;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.event.shared.GwtEvent;

public abstract class ReadPasswordEvent extends GwtEvent<ReadPasswordEventHandler> {
	public static Type<ReadPasswordEventHandler> TYPE = new Type<ReadPasswordEventHandler>();

	private final List<PasswordCard> passwords;
	private final List<PasswordField> fields;

	public ReadPasswordEvent(List<PasswordCard> passwords, List<PasswordField> fields) {
		this.passwords = passwords;
		this.fields = fields;
	}

	public List<PasswordCard> getPasswords() {
		return passwords;
	}

	public List<PasswordField> getFields() {
		return fields;
	}

	@Override
	public Type<ReadPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected abstract void dispatch(final ReadPasswordEventHandler handler);
	
}
