package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UpdatedPasswordEventHandler extends EventHandler {

	void onUpdatedPassword(UpdatedPasswordEvent event);
}
