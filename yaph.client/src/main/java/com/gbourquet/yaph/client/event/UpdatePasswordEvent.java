package com.gbourquet.yaph.client.event;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.event.shared.GwtEvent;

public class UpdatePasswordEvent extends GwtEvent<UpdatePasswordEventHandler> {
	public static Type<UpdatePasswordEventHandler> TYPE = new Type<UpdatePasswordEventHandler>();

	private final PasswordCard password;
	private final List<PasswordField> fields;

	public UpdatePasswordEvent(final PasswordCard password,final List<PasswordField> fields) {
		this.password = password;
		this.fields = fields;
	}

	public PasswordCard getPasswordCard() {
		return password;
	}

	public List<PasswordField> getPasswordFields() {
		return fields;
	}

	@Override
	public Type<UpdatePasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final UpdatePasswordEventHandler handler) {
		handler.onUpdatePassword(this);
	}
}
