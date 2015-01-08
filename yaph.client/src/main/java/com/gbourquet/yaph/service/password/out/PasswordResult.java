package com.gbourquet.yaph.service.password.out;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class PasswordResult implements Result {

	private PasswordCard password;
	private List<PasswordField> fields;

	/** For serialization only. */
	PasswordResult() {
	}

	public PasswordResult(PasswordCard password, List<PasswordField> fields) {
		this.password = password;
		this.fields = fields;
	}

	public PasswordCard getPasswordCard() {
		return password;
	}

	public List<PasswordField> getPasswordFields() {
		return fields;
	}

}