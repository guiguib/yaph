package com.gbourquet.yaph.serveur.password.handler;

import java.util.HashMap;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.handler.AbstractHandler;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.in.AllPasswordAction;
import com.gbourquet.yaph.serveur.password.out.AllPasswordResult;
import com.gbourquet.yaph.serveur.service.PasswordService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;

public class AllPasswordFromAccountHandler extends AbstractHandler<AllPasswordAction, AllPasswordResult> {

	public AllPasswordResult exec(AllPasswordAction in, ExecutionContext context) throws ActionException {

		final Account account = in.getAccount();

		HashMap<PasswordCard, List<PasswordField>> outData = null;
		PasswordService service = (PasswordService) BeanFactory.getInstance().getService("passwordService");
		try {
			outData = service.getPasswords(account);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}
		
		return new AllPasswordResult(outData);

	}

	@Override
	public void rollback(final AllPasswordAction action, final AllPasswordResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<AllPasswordAction> getActionType() {
		return AllPasswordAction.class;
	}
}