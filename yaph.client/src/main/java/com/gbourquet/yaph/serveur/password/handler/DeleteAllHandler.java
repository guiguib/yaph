package com.gbourquet.yaph.serveur.password.handler;

import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.handler.AbstractHandler;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.in.DeleteAllAction;
import com.gbourquet.yaph.serveur.password.out.DeleteAllResult;
import com.gbourquet.yaph.serveur.service.PasswordService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;

public class DeleteAllHandler extends AbstractHandler<DeleteAllAction, DeleteAllResult> {

	public DeleteAllResult exec(DeleteAllAction in, ExecutionContext context) throws ActionException {

		final List<PasswordCard> passwords = in.getPasswords();
		final List<PasswordField> fields = in.getFields();

		PasswordService service = (PasswordService) BeanFactory.getInstance().getService("passwordService");
		try {
			service.delete(passwords, fields);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}

		return new DeleteAllResult();

	}

	@Override
	public void rollback(final DeleteAllAction action, final DeleteAllResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<DeleteAllAction> getActionType() {
		return DeleteAllAction.class;
	}
}