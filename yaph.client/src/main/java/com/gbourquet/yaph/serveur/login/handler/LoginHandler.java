package com.gbourquet.yaph.serveur.login.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.handler.AbstractHandler;
import com.gbourquet.yaph.serveur.handler.CryptoServeur;
import com.gbourquet.yaph.serveur.login.in.LoginAction;
import com.gbourquet.yaph.serveur.login.out.LoginResult;
import com.gbourquet.yaph.serveur.service.LoginService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;

public class LoginHandler extends AbstractHandler<LoginAction, LoginResult> {

	public LoginResult exec(LoginAction in, ExecutionContext context) throws ActionException {

		final String login = in.getLogin();
		final String passwd = in.getPasswd();

		com.gbourquet.yaph.serveur.metier.generated.Account account = null;
		LoginService service = (LoginService) BeanFactory.getInstance().getService("loginService");
		try {
			account = service.login(login, passwd);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}

		session().setAttribute("account", account);
		String token = generateToken();
		session().setAttribute("token", token);
		return new LoginResult(account, token);

	}

	@Override
	public void rollback(final LoginAction action, final LoginResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<LoginAction> getActionType() {
		return LoginAction.class;
	}

	private final static String generateToken() {
		String result = new CryptoServeur().encrypt(Math.random() + "", "è-JHGè-tJHVè_-tJ");
		return result;
	}

}