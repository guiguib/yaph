package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.GwtEvent;


public class LoadApplicationEvent extends GwtEvent<LoadApplicationEventHandler> {
public static Type<LoadApplicationEventHandler> TYPE = new Type<LoadApplicationEventHandler>();
	
	public LoadApplicationEvent() {
	}
	
	@Override
	public Type<LoadApplicationEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final LoadApplicationEventHandler handler) {
		handler.onLoad(this);
	}
}
