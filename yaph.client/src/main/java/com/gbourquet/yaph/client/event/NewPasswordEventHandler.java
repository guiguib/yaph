package com.gbourquet.yaph.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface NewPasswordEventHandler extends EventHandler {

	void onNewPassword(NewPasswordEvent event);
}
