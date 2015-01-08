package com.gbourquet.yaph.service.password.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.service.PasswordService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;
import com.gbourquet.yaph.service.handler.AbstractHandler;
import com.gbourquet.yaph.service.password.in.DeletePasswordAction;
import com.gbourquet.yaph.service.password.out.DeletePasswordResult;

public class DeletePasswordHandler extends AbstractHandler<DeletePasswordAction, DeletePasswordResult> {

	public DeletePasswordResult exec(DeletePasswordAction in, ExecutionContext context)
			throws ActionException {

		final PasswordCard password = in.getPasswordCard();
		
		PasswordService service = (PasswordService) BeanFactory.getInstance().getService("passwordService");
		try {
			service.delete(password);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}

		return new DeletePasswordResult();

	}

	@Override
	public void rollback(final DeletePasswordAction action, final DeletePasswordResult result,
			final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<DeletePasswordAction> getActionType() {
		return DeletePasswordAction.class;
	}
}