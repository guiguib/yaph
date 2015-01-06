package com.gbourquet.yaph.client.mvp.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.LoginEvent;
import com.gbourquet.yaph.client.event.LoginEventHandler;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.event.NewPasswordEvent;
import com.gbourquet.yaph.client.event.NewPasswordEventHandler;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.AppPlace;
import com.gbourquet.yaph.client.mvp.place.NewPasswordPlace;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.password.in.AllFieldAction;
import com.gbourquet.yaph.service.password.in.AllPasswordAction;
import com.gbourquet.yaph.service.password.out.AllFieldResult;
import com.gbourquet.yaph.service.password.out.AllPasswordResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
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
		
		void addPassword(PasswordCard password);
		void addField(PasswordField field);
		void clearFields();
		
		void updatePasswordList(List<PasswordCard> passwords);

		void addSelectionChangeHandler(SelectionChangeEvent.Handler handler);
		
		PasswordCard getSelectedPassword();
		void setFieldsVisible(Boolean isVisible);
		
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
						}
					});
				}
			}
		});
		
		getEventBus().addHandler(NewPasswordEvent.TYPE, new NewPasswordEventHandler() {
			
			@Override
			public void onNewPassword(NewPasswordEvent event) {
				getView().addPassword(event.getPasswordCard());
			}
		});
		
		getView().getNewPasswordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// On redirige vers la vue de connexion
				getFactory().getPlaceController().goTo(new NewPasswordPlace(""));
			}
		});
		
		getView().addSelectionChangeHandler(new Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				PasswordCard password = getView().getSelectedPassword();
				dispatcher.execute(new AllFieldAction(password), 
						new MyAsyncCallback<AllFieldResult>(getEventBus()) {

							@Override
							public void success(AllFieldResult result) {
								List<PasswordField> fields = result.getFieldList();
								getView().clearFields();
								for (PasswordField field : fields)
								{
									getView().addField(field);
								}
							}
							
							@Override
							public void failure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}
					
				});
				getView().setFieldsVisible(true);
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