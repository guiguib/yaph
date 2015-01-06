package com.gbourquet.yaph.client.mvp.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.NewPasswordEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.PasswordPlace;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.service.callback.MyAsyncCallback;
import com.gbourquet.yaph.service.password.in.PasswordAction;
import com.gbourquet.yaph.service.password.out.PasswordResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class NewPasswordPresenter extends AbstractPresenter {

	private DispatchAsync dispatcher;

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {

		HasClickHandlers getCancelButton();
		HasClickHandlers getValidButton();
		HasClickHandlers getAddFieldButton();
		
		String getTitleText();
		
		Label getErrorLabel();

		void addField();
		List<PasswordField> getPasswordFields();
		
		void clear();
		void show();
		void close();
	}

	public View view;

	public NewPasswordPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getNewPasswordView();
		dispatcher = factory.getDistpatcher();
		bind();
	}

	public void bind() {
		
		RootPanel.get("dialog").add(getView().asWidget());
		
		// Valid nouveau mot de passe
		getView().getValidButton().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final PasswordCard password = new PasswordCard();
						final Account account = (Account) LocalSession.getInstance().getAttribute("account");
						if (account != null)
							password.setAccount(account.getId());
						password.setTitre(getView().getTitleText());
						
						dispatcher.execute(new PasswordAction(password,getView().getPasswordFields()),
								new MyAsyncCallback<PasswordResult>(
										getEventBus()) {
									public void success(PasswordResult result) {
										//On envoit un message dans le bus pour actualiser les autres vues
										//getView().addPassword(result.getPasswordCard());
										getEventBus().fireEvent(new NewPasswordEvent(result.getPasswordCard()));
										getView().clear();
										getView().close();
									}

									public void failure(Throwable caught) {
										//On insère en base locale
										int idPassword = DataAccess.getInstance().insertPassword(account, password);
										password.setId(idPassword);
										DataAccess.getInstance().insertPasswordFields(password, getView().getPasswordFields());
										//On envoit un message dans le bus pour actualiser les autres vues
										//getView().addPassword(password);
										getView().clear();
										getView().close();
									}
								});
						getFactory().getPlaceController().goTo(new PasswordPlace(""));
					}
				});

		// Cancel nouveau mot de passe
		getView().getCancelButton().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getView().clear();
						getView().close();
						getFactory().getPlaceController().goTo(new PasswordPlace(""));
					}
				});
		
		//Ajout d'un champ
		getView().getAddFieldButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().addField();
				
			}
		});
	}

	@Override
	public void start() {
		getView().show();
	}

	@Override
	public String mayStop() {
		return null;
	}

	public View getView() {
		return view;
	}
}