package com.gbourquet.yaph.client.event.password.saved;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.event.shared.GwtEvent;

public abstract class SavedPasswordEvent extends GwtEvent<SavedPasswordEventHandler> {
	public static Type<SavedPasswordEventHandler> TYPE = new Type<SavedPasswordEventHandler>();

	private final PasswordCard password;
	private final List<PasswordField> fields;
	private final boolean modeUpdate;
	
	public SavedPasswordEvent(final PasswordCard password, List<PasswordField> fields,boolean modeUpdate) {
		this.password = password;
		this.fields = fields;
		this.modeUpdate = modeUpdate;
	}

	public PasswordCard getPasswordCard() {
		return password;
	}

	public List<PasswordField> getFields() {
		return fields;
	}

	public boolean isModeUpdate() {
		return modeUpdate;
	}

	@Override
	public Type<SavedPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected abstract void dispatch(final SavedPasswordEventHandler handler);
	
}
