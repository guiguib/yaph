package com.gbourquet.yaph.serveur.password.handler;

import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.handler.AbstractHandler;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.in.SavePasswordAction;
import com.gbourquet.yaph.serveur.password.out.SavePasswordResult;
import com.gbourquet.yaph.serveur.service.PasswordService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;

public class SavePasswordHandler extends AbstractHandler<SavePasswordAction, SavePasswordResult> {

	public SavePasswordResult exec(SavePasswordAction in, ExecutionContext context) throws ActionException {

		final PasswordCard password = in.getPasswordCard();
		final List<PasswordField> fields = in.getFields();

		final Account account = (Account) session().getAttribute("account");
		
		PasswordCard outPassword;
		List<PasswordField> outFields;
		PasswordService service = (PasswordService) BeanFactory.getInstance().getService("passwordService");
		try {
			outPassword = service.save(password, fields,account);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}

		try {
			outFields = service.getFields(outPassword,account);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}

		return new SavePasswordResult(outPassword, outFields);

	}

	@Override
	public void rollback(final SavePasswordAction action, final SavePasswordResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<SavePasswordAction> getActionType() {
		return SavePasswordAction.class;
	}
}