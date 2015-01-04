package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MenuEvent extends GwtEvent<MenuEventHandler> {
	public static Type<MenuEventHandler> TYPE = new Type<MenuEventHandler>();

	private String menu;
	private boolean enabled;

	public MenuEvent(String menu, boolean enabled) {
		this.menu = menu;
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public String getMenu() {
		return this.menu;
	}

	@Override
	public Type<MenuEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final MenuEventHandler handler) {
		handler.onMenu(this);
	}
}
