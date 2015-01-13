package com.gbourquet.yaph.client.mvp.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.InlineEvent;
import com.gbourquet.yaph.client.event.InlineEventHandler;
import com.gbourquet.yaph.client.event.LoadApplicationEvent;
import com.gbourquet.yaph.client.event.LoginEvent;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.LoginPlace;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.callback.MyAsyncCallback;
import com.gbourquet.yaph.serveur.login.in.LoginFromSessionAction;
import com.gbourquet.yaph.serveur.login.out.LoginResult;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.in.AllPasswordAction;
import com.gbourquet.yaph.serveur.password.in.DeleteAllAction;
import com.gbourquet.yaph.serveur.password.in.PasswordAction;
import com.gbourquet.yaph.serveur.password.out.AllPasswordResult;
import com.gbourquet.yaph.serveur.password.out.DeleteAllResult;
import com.gbourquet.yaph.serveur.password.out.PasswordResult;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public class AppPresenter extends AbstractPresenter {

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {
		void hello(String nom);
	}

	public View view;
	private DispatchAsync dispatcher;

	public AppPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getAppView();
		dispatcher = factory.getDistpatcher();
		bind();
	}

	public void bind() {

		RootPanel.get("container").add(getView().asWidget());

		getEventBus().addHandler(InlineEvent.TYPE, new InlineEventHandler() {

			@Override
			public void onInline(InlineEvent event) {
				LocalSession.getInstance().setAttribute("disconnected", false);
				Account account = (Account) LocalSession.getInstance().getAttribute("account");
				// On synchronise les bases
				synchData(account);
			}

			@Override
			public void onOffline(InlineEvent event) {
				LocalSession.getInstance().setAttribute("disconnected", true);
			}
		});

		// On est loggué en distant ?
		dispatcher.execute(new LoginFromSessionAction(), new MyAsyncCallback<LoginResult>(getEventBus()) {
			public void success(final LoginResult result) {

				Account account = result.getAccount();
				if (account != null) {
					LocalSession.getInstance().setAttribute("token", result.getToken());
					LocalSession.getInstance().setAttribute("account", account);
					synchData(account);
				} else {
					// On redirige vers la vue de connexion
					getFactory().getPlaceController().goTo(new LoginPlace(""));
				}
			}

			public void failure(final Throwable e) {
				// On redirige vers la vue de connexion
				getFactory().getPlaceController().goTo(new LoginPlace(""));

			}
		});

		// L'application est lancé
		getEventBus().fireEvent(new LoadApplicationEvent());
	}

	@Override
	public void start() {
		RootPanel.get("container").clear();
		RootPanel.get("container").add(getView().asWidget());
		getEventBus().fireEvent(new MenuEvent("app", true));
	}

	@Override
	public String mayStop() {
		getEventBus().fireEvent(new MenuEvent("app", false));
		return null;
	}

	public View getView() {
		return view;
	}

	private void synchData(final Account account) {
		// On sauvegarde recursivement les passwd de la base locale
		List<PasswordCard> passwords = DataAccess.getInstance().getNewPasswd(account);
		if (passwords != null && passwords.size() != 0) {
			final PasswordCard password = passwords.get(0);
			final List<PasswordField> fields = DataAccess.getInstance().getFields(password);
			dispatcher.execute(new PasswordAction(password, fields), new MyAsyncCallback<PasswordResult>(getEventBus()) {

				@Override
				public void success(PasswordResult result) {
					// On peut supprimer en local
					DataAccess.getInstance().deletePassword(password);
					// On passe au suivant
					synchData(account);
				}

				@Override
				public void failure(Throwable caught) {
					// TODO Auto-generated method stub

				}

			});

		} else {
			// On récupère la base distante
			dispatcher.execute(new AllPasswordAction(account), new MyAsyncCallback<AllPasswordResult>(getEventBus()) {
				public void success(AllPasswordResult result) {
					// On met à jour la base locale
					DataAccess.getInstance().setPasswords(result.getPasswordCardList(), account);
					DataAccess.getInstance().setFields(result.getPasswordFieldList());
					
					// on supprime les password et field de la table delete
					List<PasswordCard> passwordsToDelete = DataAccess.getInstance().getDelPasswd();
					List<PasswordField> fieldsToDelete = DataAccess.getInstance().getDelField();
					dispatcher.execute(new DeleteAllAction(passwordsToDelete, fieldsToDelete), new MyAsyncCallback<DeleteAllResult>(getEventBus()) {

						@Override
						public void success(DeleteAllResult result) {
							// On envoie l'event de login
							getEventBus().fireEvent(new LoginEvent(account));
						}
						
						@Override
						public void failure(Throwable caught) {
							// TODO Stub de la méthode généré automatiquement
							
						}
						
					});
					
				}

				public void failure(Throwable caught) {
				}
			});
		}

	}
}