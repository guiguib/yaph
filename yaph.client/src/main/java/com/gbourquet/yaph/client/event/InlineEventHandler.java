package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface InlineEventHandler extends EventHandler {

	void onInline(InlineEvent event);
	void onOffline(InlineEvent event);
}
