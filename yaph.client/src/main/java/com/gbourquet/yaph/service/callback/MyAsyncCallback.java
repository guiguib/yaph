package com.gbourquet.yaph.service.callback;

import com.gbourquet.yaph.client.event.DisconnectionEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

public abstract class MyAsyncCallback<T> implements AsyncCallback<T> {

	private EventBus eventBus;
	
	@Override
	public final void onFailure(Throwable caught) {
		Throwable newCaught = caught;
		if ("0  ".equals(caught.getMessage())) {
			//On est deconnecté
			eventBus.fireEvent(new DisconnectionEvent());
			newCaught = new Throwable("Connexion au serveur impossible",caught);
		}
				
		this.failure(newCaught);
	}

	public MyAsyncCallback(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public final void onSuccess(T result) {
		this.success(result);
	}
	
	public abstract void failure(Throwable caught);
	
	public abstract void success(T result);
	
}
