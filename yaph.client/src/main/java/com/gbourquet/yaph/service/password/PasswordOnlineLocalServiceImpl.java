package com.gbourquet.yaph.service.password;

import java.util.List;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.password.created.CreatedPasswordEvent;
import com.gbourquet.yaph.client.event.password.created.CreatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.updated.UpdatedPasswordEvent;
import com.gbourquet.yaph.client.event.password.updated.UpdatedPasswordEventHandler;
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
	public void insertPassword(PasswordCard password, List<PasswordField> fields) {

		// On insère en base locale
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		int idPassword = DataAccess.getInstance().insertOrUpdatePassword(account, password);
		password.setId(idPassword);
		DataAccess.getInstance().insertOrUpdatePasswordFields(password, fields);

		// On envoit un message dans le bus
		eventBus.fireEvent(new CreatedPasswordEvent(password, fields) {

			@Override
			protected void dispatch(CreatedPasswordEventHandler handler) {
				handler.onLocalCreatedPassword(this);
			}
		});

	}

	@Override
	public void updatePassword(PasswordCard password, List<PasswordField> fields) {
		// On insère en base locale
				Account account = (Account) LocalSession.getInstance().getAttribute("account");
				int idPassword = DataAccess.getInstance().insertOrUpdatePassword(account, password);
				password.setId(idPassword);
				DataAccess.getInstance().insertOrUpdatePasswordFields(password, fields);

				// On envoit un message dans le bus
				eventBus.fireEvent(new UpdatedPasswordEvent(password, fields) {

					@Override
					protected void dispatch(UpdatedPasswordEventHandler handler) {
						handler.onLocalUpdatedPassword(this);
					}
				});
	}

	@Override
	public void deletePassword(PasswordCard password) {
		DataAccess.getInstance().deletePassword(password);

		// On envoit un message dans le bus
		eventBus.fireEvent(new DeletedPasswordEvent(password) {

			@Override
			protected void dispatch(DeletedPasswordEventHandler handler) {
				handler.onLocalDeletedPassword(this);
			}
		});
		
	}

}
