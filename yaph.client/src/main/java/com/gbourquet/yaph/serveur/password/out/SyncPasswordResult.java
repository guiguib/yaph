package com.gbourquet.yaph.serveur.password.out;

import java.util.HashMap;
import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class SyncPasswordResult implements Result {

	private HashMap<PasswordCard,List<PasswordField>> data;
	
	/** For serialization only. */
	SyncPasswordResult() {
	}

	public SyncPasswordResult(HashMap<PasswordCard, List<PasswordField>> data) {
		super();
		this.data = data;
	}

	public HashMap<PasswordCard, List<PasswordField>> getData() {
		return data;
	}

	
}