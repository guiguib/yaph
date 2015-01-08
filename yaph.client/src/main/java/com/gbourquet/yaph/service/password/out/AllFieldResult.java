package com.gbourquet.yaph.service.password.out;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class AllFieldResult implements Result {

	private List<PasswordField> fieldList;

	/** For serialization only. */
	AllFieldResult() {
	}

	public AllFieldResult(List<PasswordField> fieldList) {
		this.fieldList = fieldList;
	}

	public List<PasswordField> getFieldList() {
		return fieldList;
	}
}