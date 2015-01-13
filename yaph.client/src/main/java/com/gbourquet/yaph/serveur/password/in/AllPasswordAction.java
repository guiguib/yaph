package com.gbourquet.yaph.serveur.password.in;

import com.gbourquet.yaph.serveur.in.AbstractAction;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.password.out.AllPasswordResult;

public class AllPasswordAction extends AbstractAction<AllPasswordResult> {

	private Account account;

	/** For serialization only. */
	AllPasswordAction() {
	}

	public AllPasswordAction(Account account) {
		super();
		this.account = account;
	}

	public Account getAccount() {
		return this.account;
	}

}