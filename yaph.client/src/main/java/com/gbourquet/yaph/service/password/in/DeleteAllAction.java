package com.gbourquet.yaph.service.password.in;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.service.in.AbstractAction;
import com.gbourquet.yaph.service.password.out.DeleteAllResult;

public class DeleteAllAction extends AbstractAction<DeleteAllResult> {

	private List<PasswordCard> passwords;
	private List<PasswordField> fields;

	/** For serialization only. */
	DeleteAllAction() {
	}

	public DeleteAllAction(List<PasswordCard> passwords,List<PasswordField> fields) {
		super();
		this.passwords = passwords;
		this.fields = fields;
	}

	public List<PasswordCard> getPasswords() {
		return this.passwords;
	}
	
	public List<PasswordField> getFields() {
		return this.fields;
	}
}