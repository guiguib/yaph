package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DisconnectionEventHandler extends EventHandler {

	void onDisconnect(DisconnectionEvent event);
}
