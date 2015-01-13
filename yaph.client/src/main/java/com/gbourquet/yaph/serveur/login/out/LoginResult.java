package com.gbourquet.yaph.serveur.login.out;

import net.customware.gwt.dispatch.shared.Result;

import com.gbourquet.yaph.serveur.metier.generated.Account;

public class LoginResult implements Result {

	private Account account;
	private String token;

	/** For serialization only. */
	LoginResult() {
	}

	public LoginResult(Account account, String token) {
		this.account = account;
		this.token = token;
	}

	public Account getAccount() {
		return account;
	}

	public String getToken() {
		return token;
	}

}