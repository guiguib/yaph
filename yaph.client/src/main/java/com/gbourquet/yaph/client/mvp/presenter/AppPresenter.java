package com.gbourquet.yaph.client.mvp.presenter;

import java.util.List;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.InlineEvent;
import com.gbourquet.yaph.client.event.InlineEventHandler;
import com.gbourquet.yaph.client.event.LoadApplicationEvent;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.event.login.LoginEvent;
import com.gbourquet.yaph.client.event.login.LoginEventHandler;
import com.gbourquet.yaph.client.event.login.NotLoggedEvent;
import com.gbourquet.yaph.client.event.password.read.ReadErrorPasswordEvent;
import com.gbourquet.yaph.client.event.password.read.ReadPasswordEvent;
import com.gbourquet.yaph.client.event.password.read.ReadPasswordEventHandler;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.LoginPlace;
import com.gbourquet.yaph.client.service.login.DefaultLoginServiceImpl;
import com.gbourquet.yaph.client.service.login.LoginService;
import com.gbourquet.yaph.client.service.password.PasswordOnlineLocalServiceImpl;
import com.gbourquet.yaph.client.service.password.PasswordRemoteServiceImpl;
import com.gbourquet.yaph.client.service.password.PasswordService;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.core.shared.GWT;
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

	private PasswordService remotePasswordService;
	private PasswordService localOnlinePasswordService;
	private LoginService loginService;
	
	public AppPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getAppView();
		// TODO à fabriquer par la factory
		remotePasswordService = new PasswordRemoteServiceImpl(factory);
		localOnlinePasswordService = new PasswordOnlineLocalServiceImpl(factory);
		loginService = new DefaultLoginServiceImpl(factory);
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
		loginService.sessionLogged();
		getEventBus().addHandler(LoginEvent.TYPE, new LoginEventHandler() {
			
			@Override
			public void onNotLogged(NotLoggedEvent notLoggedEvent) {
				// On redirige vers la vue de connexion
				getFactory().getPlaceController().goTo(new LoginPlace(""));
				
			}
			
			@Override
			public void onLogin(LoginEvent event) {
				Account account = event.getAccount();
				if (account != null) {
					LocalSession.getInstance().setAttribute("token", event.getToken());
					LocalSession.getInstance().setAttribute("account", account);
					synchData(account);
					
				} else {
					// On redirige vers la vue de connexion
					getFactory().getPlaceController().goTo(new LoginPlace(""));
				}
			}
		});
		
		getEventBus().addHandler(ReadPasswordEvent.TYPE, new ReadPasswordEventHandler() {
			
			@Override
			public void onRemoteReadPassword(ReadPasswordEvent event) {
				GWT.log("Lecture des passwd faite");
				// On met à jour la base locale
				Account account = (Account) LocalSession.getInstance().getAttribute("account");
				if (account != null) {
					GWT.log("Nb passwds en base :"+event.getPasswords().size());
					GWT.log("Nb fields en base :"+event.getFields().size());
					localOnlinePasswordService.savePasswords(account,event.getPasswords(), event.getFields());
				}
				

				
			}
			
			@Override
			public void onRemoteErrorPassword(ReadErrorPasswordEvent event) {
				GWT.log("onRemoteErrorPassword");
				
				
			}
			
			@Override
			public void onLocalReadPassword(ReadPasswordEvent event) {
				GWT.log("onLocalReadPassword");
				
				
			}
			
			@Override
			public void onLocalErrorPassword(ReadErrorPasswordEvent event) {
				GWT.log("onLocalErrorPassword");
				
				
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
		GWT.log("Synchro lancée");
		// on supprime les password de la table delete
		List<PasswordCard> passwordsToDelete = DataAccess.getInstance().getDelPasswd();
		GWT.log("Nb table à supprimer :"+passwordsToDelete.size());
		for (PasswordCard lPassword : passwordsToDelete) {
			remotePasswordService.deletePassword(lPassword);
			localOnlinePasswordService.deletePassword(lPassword);
		}

		// On sauvegarde les passwd de la base locale
		List<PasswordCard> passwords = DataAccess.getInstance().getPasswords(account);
		GWT.log("Passwd à MAJ :"+passwords.size());
		if (passwords != null && passwords.size() != 0) {
			for (PasswordCard lPassword : passwords) {
				final List<PasswordField> lFields = DataAccess.getInstance().getFields(lPassword);
				// On detruit en local avant de sauvegarder en remote 
				DataAccess.getInstance().deleteOnlinePasswordCard(lPassword);
				if (lPassword.getId() < 0)
					lPassword.setId(null);
				for (PasswordField lfield : lFields)
					if (lfield.getId() < 0)
						lfield.setId(null);

				remotePasswordService.savePassword(lPassword, lFields); // !!! Async
			}
		}
		
		//On met à jour la base locale
		remotePasswordService.getAllPassword(account);
		GWT.log("Fin Synchro");
		
	}
	
	
}