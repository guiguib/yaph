package com.gbourquet.yaph.service.password.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.service.PasswordService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;
import com.gbourquet.yaph.service.handler.AbstractHandler;
import com.gbourquet.yaph.service.password.in.PasswordAction;
import com.gbourquet.yaph.service.password.out.PasswordResult;

public class PasswordHandler extends AbstractHandler<PasswordAction, PasswordResult> {

	public PasswordResult exec(PasswordAction in, ExecutionContext context)
			throws ActionException {

		final PasswordCard password = in.getPasswordCard();

		PasswordCard out; 
		PasswordService service = (PasswordService) BeanFactory.getInstance().getService("passwordService");
		try {
			out = service.save(password);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}

		return new PasswordResult(out);

	}

	@Override
	public void rollback(final PasswordAction action, final PasswordResult result,
			final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<PasswordAction> getActionType() {
		return PasswordAction.class;
	}
}