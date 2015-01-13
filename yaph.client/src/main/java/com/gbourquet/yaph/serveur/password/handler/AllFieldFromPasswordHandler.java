package com.gbourquet.yaph.serveur.password.handler;

import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.handler.AbstractHandler;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.in.AllFieldAction;
import com.gbourquet.yaph.serveur.password.out.AllFieldResult;
import com.gbourquet.yaph.serveur.service.PasswordService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;

public class AllFieldFromPasswordHandler extends AbstractHandler<AllFieldAction, AllFieldResult> {

	public AllFieldResult exec(AllFieldAction in, ExecutionContext context) throws ActionException {

		final PasswordCard password = in.getPassword();

		List<PasswordField> out;
		PasswordService service = (PasswordService) BeanFactory.getInstance().getService("passwordService");
		try {
			out = service.getFields(password);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}

		return new AllFieldResult(out);

	}

	@Override
	public void rollback(final AllFieldAction action, final AllFieldResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<AllFieldAction> getActionType() {
		return AllFieldAction.class;
	}
}