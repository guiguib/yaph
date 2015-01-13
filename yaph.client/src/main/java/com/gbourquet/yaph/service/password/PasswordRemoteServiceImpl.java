package com.gbourquet.yaph.service.password;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.event.password.created.CreatedPasswordEvent;
import com.gbourquet.yaph.client.event.password.created.CreatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.created.CreatingErrorPasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEvent;
import com.gbourquet.yaph.client.event.password.deleted.DeletedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.updated.UpdatedPasswordEvent;
import com.gbourquet.yaph.client.event.password.updated.UpdatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.password.updated.UpdatingErrorPasswordEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.serveur.callback.MyAsyncCallback;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.in.DeletePasswordAction;
import com.gbourquet.yaph.serveur.password.in.PasswordAction;
import com.gbourquet.yaph.serveur.password.out.DeletePasswordResult;
import com.gbourquet.yaph.serveur.password.out.PasswordResult;
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
	public void insertPassword(final PasswordCard password, final List<PasswordField> fields) {

		dispatcher.execute(new PasswordAction(password, fields), new MyAsyncCallback<PasswordResult>(eventBus) {
			public void success(PasswordResult result) {
				eventBus.fireEvent(new CreatedPasswordEvent(result.getPasswordCard(), result.getPasswordFields()) {

					@Override
					protected void dispatch(CreatedPasswordEventHandler handler) {
						handler.onRemoteCreatedPassword(this);
					}
				});
			}

			public void failure(Throwable caught) {
				GWT.log("Remote Erreur" + caught.getMessage());
				eventBus.fireEvent(new CreatingErrorPasswordEvent(password, fields, caught.getLocalizedMessage()));
			}
		});
	}

	@Override
	public void updatePassword(final PasswordCard password, final List<PasswordField> fields) {
		dispatcher.execute(new PasswordAction(password, fields), new MyAsyncCallback<PasswordResult>(eventBus) {
			public void success(PasswordResult result) {
				eventBus.fireEvent(new UpdatedPasswordEvent(result.getPasswordCard(), result.getPasswordFields()) {

					@Override
					protected void dispatch(UpdatedPasswordEventHandler handler) {
						handler.onRemoteUpdatedPassword(this);
					}
				});
			}

			public void failure(Throwable caught) {
				GWT.log("Remote Erreur" + caught.getMessage());
				eventBus.fireEvent(new UpdatingErrorPasswordEvent(password, fields, caught.getLocalizedMessage()));
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
}
