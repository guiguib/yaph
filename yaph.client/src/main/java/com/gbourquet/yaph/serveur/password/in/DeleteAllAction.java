package com.gbourquet.yaph.serveur.password.in;

import java.util.List;

import com.gbourquet.yaph.serveur.in.AbstractAction;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.out.DeleteAllResult;

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