package com.gbourquet.yaph.client.event.password.read;

import java.util.HashMap;
import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.event.shared.GwtEvent;

public abstract class ReadPasswordEvent extends GwtEvent<ReadPasswordEventHandler> {
	public static Type<ReadPasswordEventHandler> TYPE = new Type<ReadPasswordEventHandler>();

	private final HashMap<PasswordCard,List<PasswordField>> data;
	
	

	public ReadPasswordEvent(HashMap<PasswordCard, List<PasswordField>> data) {
		super();
		this.data = data;
	}

	public HashMap<PasswordCard, List<PasswordField>> getData() {
		return data;
	}

	@Override
	public Type<ReadPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected abstract void dispatch(final ReadPasswordEventHandler handler);
	
}
