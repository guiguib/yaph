package com.gbourquet.yaph.service.in;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.gbourquet.yaph.client.LocalSession;

public abstract class AbstractAction<R extends Result> implements Action<R> {

	private String token;

	/** For serialization only. */
	protected AbstractAction() {
		// On récupère le token pour vérification côté serveur
		this.token = (String) LocalSession.getInstance().getAttribute("token");
	}

	public final String getToken() {
		return this.token;
	}

	public final void setToken(String token) {
		this.token = token;
	}

}