package com.gbourquet.yaph.client.event;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.event.shared.GwtEvent;

public class CreatedPasswordEvent extends GwtEvent<CreatedPasswordEventHandler> {
	public static Type<CreatedPasswordEventHandler> TYPE = new Type<CreatedPasswordEventHandler>();

	private final PasswordCard password;
	private final List<PasswordField> fields;

	public CreatedPasswordEvent(final PasswordCard password, List<PasswordField> fields) {
		this.password = password;
		this.fields = fields;
	}

	public PasswordCard getPasswordCard() {
		return password;
	}

	public List<PasswordField> getFields() {
		return fields;
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
