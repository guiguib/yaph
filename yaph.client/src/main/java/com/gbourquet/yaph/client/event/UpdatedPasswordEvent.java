package com.gbourquet.yaph.client.event;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.event.shared.GwtEvent;

public class UpdatedPasswordEvent extends GwtEvent<UpdatedPasswordEventHandler> {
	public static Type<UpdatedPasswordEventHandler> TYPE = new Type<UpdatedPasswordEventHandler>();

	private final PasswordCard password;
	private final List<PasswordField> fields;
	
	public UpdatedPasswordEvent(final PasswordCard password, List<PasswordField> fields) {
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
	public Type<UpdatedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final UpdatedPasswordEventHandler handler) {
		handler.onUpdatedPassword(this);
	}
}
