package com.gbourquet.yaph.serveur.password.in;

import java.util.List;

import com.gbourquet.yaph.serveur.in.AbstractAction;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.out.PasswordResult;

public class PasswordAction extends AbstractAction<PasswordResult> {

	private PasswordCard password;
	private List<PasswordField> fields;

	/** For serialization only. */
	PasswordAction() {
	}

	public PasswordAction(PasswordCard password, List<PasswordField> fields) {
		super();
		this.password = password;
		this.fields = fields;
	}

	public PasswordCard getPasswordCard() {
		return this.password;
	}

	public List<PasswordField> getFields() {
		return fields;
	}
}