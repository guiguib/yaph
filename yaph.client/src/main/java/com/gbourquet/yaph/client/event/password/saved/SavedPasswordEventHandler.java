package com.gbourquet.yaph.client.event.password.saved;

import com.google.gwt.event.shared.EventHandler;

public interface SavedPasswordEventHandler extends EventHandler {

	void onRemoteSavedPassword(SavedPasswordEvent event);
	void onLocalSavedPassword(SavedPasswordEvent event);
	void onRemoteErrorPassword(SavingErrorPasswordEvent event);
	void onLocalErrorPassword(SavingErrorPasswordEvent event);
}
