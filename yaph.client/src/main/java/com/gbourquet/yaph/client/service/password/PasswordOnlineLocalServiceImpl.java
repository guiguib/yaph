package com.gbourquet.yaph.client.service.password;

import java.util.List;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.saved.SavedPasswordEvent;
import com.gbourquet.yaph.client.event.password.saved.SavedPasswordEventHandler;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.web.bindery.event.shared.EventBus;

public class PasswordOnlineLocalServiceImpl implements PasswordService {

	private EventBus eventBus;

	public PasswordOnlineLocalServiceImpl(ClientFactory factory) {

		eventBus = factory.getEventBus();
	}

	@Override
	public void savePassword(PasswordCard password, List<PasswordField> fields) {

		// On insère en base locale
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		password.setAccount(account.getId());
		int idPassword = DataAccess.getInstance().savePassword(password);
		password.setId(idPassword);
		DataAccess.getInstance().savePasswordFields(password, fields);

		// On envoit un message dans le bus
		eventBus.fireEvent(new SavedPasswordEvent(password, fields) {

			@Override
			protected void dispatch(SavedPasswordEventHandler handler) {
				handler.onLocalSavedPassword(this);
			}
		});

	}

	@Override
	public void deletePassword(PasswordCard password) {
		DataAccess.getInstance().deleteOnlinePasswordCard(password);

		// On envoit un message dans le bus
		eventBus.fireEvent(new DeletedPasswordEvent(password) {

			@Override
			protected void dispatch(DeletedPasswordEventHandler handler) {
				handler.onLocalDeletedPassword(this);
			}
		});
		
	}

	@Override
	public void getAllPassword(Account account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void savePasswords(Account account, List<PasswordCard> passwords, List<PasswordField> fields) {
		DataAccess.getInstance().setPasswords(passwords, account);
		DataAccess.getInstance().setFields(fields);
		
		
	}

	@Override
	public void syncData(Account account) {
		// Sans interet
		
	}

}
