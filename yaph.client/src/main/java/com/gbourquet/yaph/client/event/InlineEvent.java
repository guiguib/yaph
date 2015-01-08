package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class InlineEvent extends GwtEvent<InlineEventHandler> {
	
	boolean isInline=true;
	
	public static Type<InlineEventHandler> TYPE = new Type<InlineEventHandler>();

	public InlineEvent(boolean isInline) {
		this.isInline = isInline;
	}

	public boolean isInline() {
		return this.isInline;
	}
	@Override
	public Type<InlineEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final InlineEventHandler handler) {
		if (this.isInline)
			handler.onInline(this);
		else
			handler.onOffline(this);
	}
}
