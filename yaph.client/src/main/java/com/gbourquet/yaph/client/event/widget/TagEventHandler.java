package com.gbourquet.yaph.client.event.widget;

import com.google.gwt.event.shared.EventHandler;

public interface TagEventHandler extends EventHandler {

	void onNewTag(TagEvent event);
	void onRemove(TagEvent event);
}
