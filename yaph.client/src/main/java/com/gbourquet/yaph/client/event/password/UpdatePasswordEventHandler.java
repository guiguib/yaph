package com.gbourquet.yaph.client.event.password;

import com.google.gwt.event.shared.EventHandler;

public interface UpdatePasswordEventHandler extends EventHandler {

	void onUpdatePassword(UpdatePasswordEvent event);
}
