package com.gbourquet.yaph.client.event.password.updated;

import com.google.gwt.event.shared.EventHandler;

public interface UpdatedPasswordEventHandler extends EventHandler {

	void onRemoteUpdatedPassword(UpdatedPasswordEvent event);
	void onLocalUpdatedPassword(UpdatedPasswordEvent event);
	void onRemoteErrorPassword(UpdatingErrorPasswordEvent event);
	void onLocalErrorPassword(UpdatingErrorPasswordEvent event);
}
