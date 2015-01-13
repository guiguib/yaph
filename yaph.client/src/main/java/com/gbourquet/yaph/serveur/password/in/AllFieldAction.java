package com.gbourquet.yaph.serveur.password.in;

import com.gbourquet.yaph.serveur.in.AbstractAction;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.password.out.AllFieldResult;

public class AllFieldAction extends AbstractAction<AllFieldResult> {

	private PasswordCard password;

	/** For serialization only. */
	AllFieldAction() {
	}

	public AllFieldAction(PasswordCard password) {
		super();
		this.password = password;
	}

	public PasswordCard getPassword() {
		return this.password;
	}

}