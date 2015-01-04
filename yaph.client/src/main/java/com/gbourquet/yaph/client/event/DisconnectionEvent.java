package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DisconnectionEvent extends GwtEvent<DisconnectionEventHandler> {

	public static Type<DisconnectionEventHandler> TYPE = new Type<DisconnectionEventHandler>();

	public DisconnectionEvent() {
	}

	@Override
	public Type<DisconnectionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final DisconnectionEventHandler handler) {
		handler.onDisconnect(this);
	}
}
