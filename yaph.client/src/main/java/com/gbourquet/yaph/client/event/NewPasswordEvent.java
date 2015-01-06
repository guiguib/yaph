package com.gbourquet.yaph.client.event;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.google.gwt.event.shared.GwtEvent;


public class NewPasswordEvent extends GwtEvent<NewPasswordEventHandler> {
public static Type<NewPasswordEventHandler> TYPE = new Type<NewPasswordEventHandler>();
	
	private final PasswordCard password;
	
	public NewPasswordEvent(final PasswordCard password) {
		this.password = password;
	}
	
	public PasswordCard getPasswordCard() {
		return password;
	}
	
	@Override
	public Type<NewPasswordEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final NewPasswordEventHandler handler) {
		handler.onNewPassword(this);
	}
}
