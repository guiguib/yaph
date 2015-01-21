package com.gbourquet.yaph.client.service.password;

import java.util.HashMap;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.read.ReadPasswordEvent;
import com.gbourquet.yaph.client.event.password.read.ReadPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.saved.SavedPasswordEvent;
import com.gbourquet.yaph.client.event.password.saved.SavedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.saved.SavingErrorPasswordEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.callback.MyAsyncCallback;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.in.AllPasswordAction;
import com.gbourquet.yaph.serveur.password.in.DeletePasswordAction;
import com.gbourquet.yaph.serveur.password.in.SavePasswordAction;
import com.gbourquet.yaph.serveur.password.in.SyncPasswordAction;
import com.gbourquet.yaph.serveur.password.out.AllPasswordResult;
import com.gbourquet.yaph.serveur.password.out.DeletePasswordResult;
import com.gbourquet.yaph.serveur.password.out.SavePasswordResult;
import com.gbourquet.yaph.serveur.password.out.SyncPasswordResult;
import com.google.gwt.core.shared.GWT;
import com.google.web.bindery.event.shared.EventBus;

public class PasswordRemoteServiceImpl implements PasswordService {

	private DispatchAsync dispatcher;
	private EventBus eventBus;

	public PasswordRemoteServiceImpl(ClientFactory factory) {

		dispatcher = factory.getDistpatcher();
		eventBus = factory.getEventBus();
	}

	@Override
	public void savePassword(final PasswordCard password, final List<PasswordField> fields,final boolean modeUpdate) {

		dispatcher.execute(new SavePasswordAction(password, fields), new MyAsyncCallback<SavePasswordResult>(eventBus) {
			public void success(SavePasswordResult result) {
				eventBus.fireEvent(new SavedPasswordEvent(result.getPasswordCard(), result.getPasswordFields(),modeUpdate) {

					@Override
					protected void dispatch(SavedPasswordEventHandler handler) {
						handler.onRemoteSavedPassword(this);
					}
				});
			}

			public void failure(Throwable caught) {
				GWT.log("Remote Erreur " + caught.getMessage());
				eventBus.fireEvent(new SavingErrorPasswordEvent(password, fields, caught.getLocalizedMessage(),modeUpdate) {
					
					@Override
					protected void dispatch(SavedPasswordEventHandler handler) {
						handler.onRemoteErrorPassword(this);
						
					}
				});
			}
		});
	}

	@Override
	public void deletePassword(final PasswordCard password) {
		dispatcher.execute(new DeletePasswordAction(password), new MyAsyncCallback<DeletePasswordResult>(eventBus) {

			@Override
			public void success(DeletePasswordResult result) {
				eventBus.fireEvent(new DeletedPasswordEvent(password) {

					@Override
					protected void dispatch(DeletedPasswordEventHandler handler) {
						handler.onRemoteDeletedPassword(this);
					}
				});

			}

			@Override
			public void failure(Throwable caught) {
			}

		});

	}

	@Override
	public void getAllPassword(Account account) {
		// On récupère la base distante
		dispatcher.execute(new AllPasswordAction(account), new MyAsyncCallback<AllPasswordResult>(eventBus) {
			public void success(AllPasswordResult result) {

				eventBus.fireEvent(new ReadPasswordEvent(result.getData()) {

					@Override
					protected void dispatch(ReadPasswordEventHandler handler) {
						handler.onRemoteReadPassword(this);

					}
				});
			}

			public void failure(Throwable caught) {
			}
		});

	}

	@Override
	public void syncData(Account account) {

		GWT.log("Début synchro");
		// on supprime les password de la table delete
		List<PasswordCard> passwordsToDelete = DataAccess.getInstance().getDelPasswd();

		// On sauvegarde les passwd de la base locale
		List<PasswordCard> passwordsToUpdate = DataAccess.getInstance().getPasswords(account);
		HashMap<PasswordCard, List<PasswordField>> dataToUpdate = new HashMap<PasswordCard, List<PasswordField>>();

		if (passwordsToUpdate != null && passwordsToUpdate.size() != 0) {
			for (PasswordCard lPassword : passwordsToUpdate) {
				List<PasswordField> lFields = DataAccess.getInstance().getFields(lPassword);
				if (lPassword.getId() < 0)
					lPassword.setId(null);
				for (PasswordField lfield : lFields)
					if (lfield.getId() < 0)
						lfield.setId(null);

				dataToUpdate.put(lPassword,lFields);
			}
		}

		// On met à jour la base distante
		dispatcher.execute(new SyncPasswordAction(account, passwordsToDelete, dataToUpdate), new MyAsyncCallback<SyncPasswordResult>(eventBus) {

			@Override
			public void success(SyncPasswordResult result) {
				GWT.log("Synchro terminée. "+ result.getData().size()+" passwords récupérés");
				DataAccess.getInstance().deleteToDelete();
				eventBus.fireEvent(new ReadPasswordEvent(result.getData()) {
					
					@Override
					protected void dispatch(ReadPasswordEventHandler handler) {
						handler.onRemoteReadPassword(this);
					}
				});

			}

			@Override
			public void failure(Throwable caught) {
				GWT.log("Synchro terminée. "+ caught.getLocalizedMessage());
				
			}

		});
	}

	@Override
	public void savePasswords(Account account, List<PasswordCard> passwords, List<PasswordField> fields) {
		// Sans interet
		
	}
}
