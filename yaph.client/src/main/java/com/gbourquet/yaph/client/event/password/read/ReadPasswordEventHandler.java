package com.gbourquet.yaph.client.event.password.read;

import com.google.gwt.event.shared.EventHandler;

public interface ReadPasswordEventHandler extends EventHandler {

	void onRemoteReadPassword(ReadPasswordEvent event);
	void onLocalReadPassword(ReadPasswordEvent event);
	void onRemoteErrorPassword(ReadErrorPasswordEvent event);
	void onLocalErrorPassword(ReadErrorPasswordEvent event);
}
