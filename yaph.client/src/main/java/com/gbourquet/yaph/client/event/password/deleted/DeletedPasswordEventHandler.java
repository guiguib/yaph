package com.gbourquet.yaph.client.event.password.deleted;

import com.google.gwt.event.shared.EventHandler;

public interface DeletedPasswordEventHandler extends EventHandler {

	void onRemoteDeletedPassword(DeletedPasswordEvent event);
	void onLocalDeletedPassword(DeletedPasswordEvent event);
	void onRemoteErrorPassword(DeletingErrorPasswordEvent event);
	void onLocalErrorPassword(DeletingErrorPasswordEvent event);
}
