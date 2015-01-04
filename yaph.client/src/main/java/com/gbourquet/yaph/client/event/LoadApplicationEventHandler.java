package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoadApplicationEventHandler extends EventHandler {

	void onLoad(LoadApplicationEvent event);
}
