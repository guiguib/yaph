package com.gbourquet.yaph.service.login.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.service.handler.AbstractHandler;
import com.gbourquet.yaph.service.login.in.LoginFromSessionAction;
import com.gbourquet.yaph.service.login.out.LoginResult;

public class LoginFromSessionHandler extends AbstractHandler<LoginFromSessionAction, LoginResult> {

	public LoginResult exec(LoginFromSessionAction in, ExecutionContext context)
			throws ActionException {

		Account account = (Account)session().getAttribute("account");
		String token = (String)session().getAttribute("token");
		
		return new LoginResult(account,token);

	}

	@Override
	public void rollback(final LoginFromSessionAction action, final LoginResult result,
			final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<LoginFromSessionAction> getActionType() {
		return LoginFromSessionAction.class;
	}
}