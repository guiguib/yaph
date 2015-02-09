package com.gbourquet.yaph.client.event.widget;

import com.google.gwt.event.shared.GwtEvent;

public abstract class TagEvent extends GwtEvent<TagEventHandler> {
	
	String tagName;
	
	public static Type<TagEventHandler> TYPE = new Type<TagEventHandler>();

	public TagEvent(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return this.tagName;
	}
	@Override
	public Type<TagEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected abstract void dispatch(final TagEventHandler handler);
}
