package com.gbourquet.yaph.client.event.password.created;

import com.google.gwt.event.shared.EventHandler;

public interface CreatedPasswordEventHandler extends EventHandler {

	void onRemoteCreatedPassword(CreatedPasswordEvent event);
	void onLocalCreatedPassword(CreatedPasswordEvent event);
	void onRemoteErrorPassword(CreatingErrorPasswordEvent event);
	void onLocalErrorPassword(CreatingErrorPasswordEvent event);
}
