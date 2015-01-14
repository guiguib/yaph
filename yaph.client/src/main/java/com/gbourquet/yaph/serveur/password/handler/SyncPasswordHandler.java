package com.gbourquet.yaph.serveur.password.handler;

import java.util.HashMap;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.gbourquet.yaph.serveur.handler.AbstractHandler;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.in.SyncPasswordAction;
import com.gbourquet.yaph.serveur.password.out.SyncPasswordResult;
import com.gbourquet.yaph.serveur.service.PasswordService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;

public class SyncPasswordHandler extends AbstractHandler<SyncPasswordAction, SyncPasswordResult> {

	public SyncPasswordResult exec(SyncPasswordAction in, ExecutionContext context) throws ActionException {

		final List<PasswordCard> passwordToDelete = in.getPasswordsToDelete();
		final HashMap<PasswordCard, List<PasswordField>> dataToUpdate = in.getDataToUpdate();
		final Account account = in.getAccount();
		HashMap<PasswordCard, List<PasswordField>> outData;
		PasswordService service = (PasswordService) BeanFactory.getInstance().getService("passwordService");
		try {
			outData = service.sync(account,passwordToDelete, dataToUpdate);
		} catch (ServiceException e) {
			throw new ActionException(e.getMessage());
		}

		return new SyncPasswordResult(outData);

	}

	@Override
	public void rollback(final SyncPasswordAction action, final SyncPasswordResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<SyncPasswordAction> getActionType() {
		return SyncPasswordAction.class;
	}
}