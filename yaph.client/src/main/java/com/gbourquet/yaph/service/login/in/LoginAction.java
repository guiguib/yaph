package com.gbourquet.yaph.service.login.in;

import com.gbourquet.yaph.service.in.AbstractAction;
import com.gbourquet.yaph.service.login.out.LoginResult;

public class LoginAction extends AbstractAction<LoginResult> {

	private String login;
	private String passwd;

	/** For serialization only. */
	LoginAction() {
	}

	public LoginAction(String login, String passwd) {
		super();
		this.login = login;
		this.passwd = passwd;
	}

	public String getLogin() {
		return this.login;
	}

	public String getPasswd() {
		return this.passwd;
	}
}