package com.gbourquet.yaph.client.mvp.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.CreatedPasswordEvent;
import com.gbourquet.yaph.client.event.CreatedPasswordEventHandler;
import com.gbourquet.yaph.client.event.LoginEvent;
import com.gbourquet.yaph.client.event.LoginEventHandler;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.event.NewPasswordEvent;
import com.gbourquet.yaph.client.event.UpdatePasswordEvent;
import com.gbourquet.yaph.client.event.UpdatedPasswordEvent;
import com.gbourquet.yaph.client.event.UpdatedPasswordEventHandler;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.password.in.AllFieldAction;
import com.gbourquet.yaph.service.password.in.AllPasswordAction;
import com.gbourquet.yaph.service.password.in.DeletePasswordAction;
import com.gbourquet.yaph.service.password.out.AllFieldResult;
import com.gbourquet.yaph.service.password.out.AllPasswordResult;
import com.gbourquet.yaph.service.password.out.DeletePasswordResult;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class PasswordPresenter extends AbstractPresenter {

	private DispatchAsync dispatcher;

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {

		HasClickHandlers getNewPasswordButton();

		HasClickHandlers getUpdatePasswordButton();

		HasClickHandlers getDeletePasswordButton();

		void addPassword(PasswordCard password);

		void removePassword(PasswordCard password);

		void addField(PasswordField field);

		void clearFields();

		void unselectPassword();
		void selectPassword(PasswordCard password);
		
		void updatePasswordList(List<PasswordCard> passwords);

		void addSelectionChangeHandler(Handler handler);

		PasswordCard getSelectedPassword();

		void setFieldsVisible(Boolean isVisible);

	}

	public View view;

	private List<PasswordField> fields;

	public PasswordPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getPasswordView();
		dispatcher = factory.getDistpatcher();
		bind();
	}

	public void bind() {
		getEventBus().addHandler(LoginEvent.TYPE, new LoginEventHandler() {

			@Override
			public void onLogin(final LoginEvent event) {
				final Account account = event.getAccount();
				Boolean disconnected = ((Boolean) LocalSession.getInstance()
						.getAttribute("disconnected") == null) ? false
						: (Boolean) LocalSession.getInstance().getAttribute(
								"disconnected");
				if (disconnected) {
					getView().updatePasswordList(
							DataAccess.getInstance().getPasswords(account));
				} else {
					dispatcher.execute(new AllPasswordAction(account),

					new MyAsyncCallback<AllPasswordResult>(getEventBus()) {
						public void success(AllPasswordResult result) {
							// On met à jour la base locale
							DataAccess.getInstance().setPasswords(
									result.getPasswordCardList(), account);

							// getView().updatePasswordList(result.getPasswordCardList());
							getView().updatePasswordList(
									DataAccess.getInstance().getPasswords(
											account));

						}

						public void failure(Throwable caught) {
						}
					});
				}
			}
		});

		getEventBus().addHandler(CreatedPasswordEvent.TYPE,
				new CreatedPasswordEventHandler() {

					@Override
					public void onCreatedPassword(CreatedPasswordEvent event) {
						getView().addPassword(event.getPasswordCard());
						getView().selectPassword(event.getPasswordCard());
					}
				});

		getEventBus().addHandler(UpdatedPasswordEvent.TYPE,
				new UpdatedPasswordEventHandler() {

					@Override
					public void onUpdatedPassword(UpdatedPasswordEvent event) {
						PasswordCard password = getView().getSelectedPassword();
						dispatcher.execute(new AllFieldAction(password),
								new MyAsyncCallback<AllFieldResult>(
										getEventBus()) {

									@Override
									public void success(AllFieldResult result) {
										fields = result.getFieldList();
										getView().clearFields();
										for (PasswordField field : fields) {
											getView().addField(field);
										}

									}

									@Override
									public void failure(Throwable caught) {
										GWT.log(caught.getMessage());
									}

								});
						getView().setFieldsVisible(true);

					}
				});

		getView().getNewPasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// On envoi un message à la vue de création de mot de passe avec
				// le mot de passe à modifier et les champs
				getEventBus().fireEvent(
						new NewPasswordEvent(getView().getSelectedPassword()));

			}
		});

		getView().getUpdatePasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// On envoi un message à la vue de création de mot de passe avec
				// le mot de passe à modifier et les champs
				getEventBus().fireEvent(
						new UpdatePasswordEvent(
								getView().getSelectedPassword(), fields));
			}
		});

		getView().getDeletePasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dispatcher
						.execute(new DeletePasswordAction(getView().getSelectedPassword()),
								new MyAsyncCallback<DeletePasswordResult>(getEventBus()) {

									@Override
									public void success(
											DeletePasswordResult result) {
										if (Window.confirm("Confirmez vous la suppression ?")) {
											getView().clearFields();
											getView().setFieldsVisible(false);
											getView().removePassword(getView().getSelectedPassword());
											getView().unselectPassword();
										}
									}

									@Override
									public void failure(Throwable caught) {
									}

								});
			}
		});

		getView().addSelectionChangeHandler(new Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if (getView().getSelectedPassword() != null) {
					PasswordCard password = getView().getSelectedPassword();
					dispatcher.execute(new AllFieldAction(password),
							new MyAsyncCallback<AllFieldResult>(getEventBus()) {

								@Override
								public void success(AllFieldResult result) {
									fields = result.getFieldList();
									getView().clearFields();
									for (PasswordField field : fields) {
										getView().addField(field);
									}

								}

								@Override
								public void failure(Throwable caught) {
									GWT.log(caught.getMessage());
								}

							});
					getView().setFieldsVisible(true);
				}
			}
		});
	}

	@Override
	public void start() {
		RootPanel.get("container").clear();
		RootPanel.get("container").add(getView().asWidget());
		getEventBus().fireEvent(new MenuEvent("password", true));
		// On nettoie
		getView().setFieldsVisible(false);
		getView().unselectPassword();
	}

	@Override
	public String mayStop() {
		getEventBus().fireEvent(new MenuEvent("password", false));
		return null;
	}

	public View getView() {
		return view;
	}
}