package com.gbourquet.yaph.client.mvp.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.LoginEvent;
import com.gbourquet.yaph.client.event.LoginEventHandler;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.AppPlace;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.client.utils.PasswordGenerator;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.password.in.AllPasswordAction;
import com.gbourquet.yaph.service.password.in.PasswordAction;
import com.gbourquet.yaph.service.password.out.AllPasswordResult;
import com.gbourquet.yaph.service.password.out.PasswordResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class PasswordPresenter extends AbstractPresenter {

	private DispatchAsync dispatcher;

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {

		HasClickHandlers getNewPasswordButton();

		HasClickHandlers getCancelNewPasswordButton();

		HasClickHandlers getValidNewPasswordButton();

		HasClickHandlers getGeneratePasswordButton();

		String getTitleText();

		String getPasswordText();

		void setPasswordText(String password);

		Label getErrorNewPasswordLabel();

		void addPassword(PasswordCard password);

		void clearNewPasswordBox();

		void showNewPasswordBox();

		void closeNewPasswordBox();

		void updatePasswordList(List<PasswordCard> passwords);

	}

	public View view;

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
				Boolean disconnected = ((Boolean)LocalSession.getInstance().getAttribute("disconnected")==null) ? false : (Boolean)LocalSession.getInstance().getAttribute("disconnected");
				if (disconnected) {
					getView().updatePasswordList(DataAccess.getInstance().getPasswords(account));
				} else {
					dispatcher.execute(new AllPasswordAction(account),

					new MyAsyncCallback<AllPasswordResult>(getEventBus()) {
						public void success(AllPasswordResult result) {
							// On met à jour la base locale
							DataAccess.getInstance().setPasswords(result.getPasswordCardList(), account);

							// getView().updatePasswordList(result.getPasswordCardList());
							getView().updatePasswordList(DataAccess.getInstance().getPasswords(account));

						}

						public void failure(Throwable caught) {
							getView().getErrorNewPasswordLabel().setText(caught.getLocalizedMessage());
						}
					});
				}
			}
		});
		
		// Nouveau mot de passe
		getView().getNewPasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().showNewPasswordBox();
			}
		});

		// Valid nouveau mot de passe
		getView().getValidNewPasswordButton().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final PasswordCard password = new PasswordCard();
						final Account account = (Account) LocalSession.getInstance().getAttribute("account");
						if (account != null)
							password.setAccount(account.getId());
						password.setTitre(getView().getTitleText());
						password.setPassword(getView().getPasswordText());
						
						dispatcher.execute(new PasswordAction(password,null),
								new MyAsyncCallback<PasswordResult>(
										getEventBus()) {
									public void success(PasswordResult result) {
										getView().addPassword(result.getPasswordCard());
										getView().clearNewPasswordBox();
										getView().closeNewPasswordBox();
									}

									public void failure(Throwable caught) {
										//On insère en base locale avec un id negatif
										DataAccess.getInstance().insertPassword(account, password);
										getView().addPassword(password);
										getView().clearNewPasswordBox();
										getView().closeNewPasswordBox();
										//getView().getErrorNewPasswordLabel().setText(caught.getLocalizedMessage());
									}
								});

					}
				});

		// Cancel nouveau mot de passe
		getView().getCancelNewPasswordButton().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getView().clearNewPasswordBox();
						getView().closeNewPasswordBox();
					}
				});
		// Generate nouveau mot de passe
		getView().getGeneratePasswordButton().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						PasswordGenerator generator = new PasswordGenerator(10,
								true);
						getView().setPasswordText(generator.exec());
					}
				});
	}

	@Override
	public void start() {
		if (LocalSession.getInstance().getAttribute("account") != null) {
			RootPanel.get("container").clear();
			RootPanel.get("container").add(getView().asWidget());
			getEventBus().fireEvent(new MenuEvent("password", true));
		} else {
			// On redirige vers la page d'acceuil
			getEventBus().fireEvent(new MenuEvent("app", true));
			getFactory().getPlaceController().goTo(new AppPlace(""));
		}

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