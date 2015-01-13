package com.gbourquet.yaph.serveur.login.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.handler.AbstractHandler;
import com.gbourquet.yaph.serveur.login.in.LogoutAction;
import com.gbourquet.yaph.serveur.login.out.LoginResult;
import com.gbourquet.yaph.serveur.metier.generated.Account;

public class LogoutHandler extends AbstractHandler<LogoutAction, LoginResult> {

	public LoginResult exec(LogoutAction in, ExecutionContext context) throws ActionException {

		Account account = null;
		String token = "";
		session().removeAttribute("account");
		session().removeAttribute("token");

		return new LoginResult(account, token);

	}

	@Override
	public void rollback(final LogoutAction action, final LoginResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<LogoutAction> getActionType() {
		return LogoutAction.class;
	}
}