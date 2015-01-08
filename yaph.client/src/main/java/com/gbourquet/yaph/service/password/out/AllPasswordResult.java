package com.gbourquet.yaph.service.password.out;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class AllPasswordResult implements Result {

	private List<PasswordCard> passwordList;
	private List<PasswordField> fieldList;

	/** For serialization only. */
	AllPasswordResult() {
	}

	public AllPasswordResult(List<PasswordCard> passwordList, List<PasswordField> fieldList) {
		this.passwordList = passwordList;
		this.fieldList = fieldList;
	}

	public List<PasswordCard> getPasswordCardList() {
		return passwordList;
	}

	public List<PasswordField> getPasswordFieldList() {
		return fieldList;
	}
}