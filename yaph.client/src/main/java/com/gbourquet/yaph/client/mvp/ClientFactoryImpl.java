package com.gbourquet.yaph.client.mvp;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.mvp.presenter.AppPresenter;
import com.gbourquet.yaph.client.mvp.presenter.InlinePresenter;
import com.gbourquet.yaph.client.mvp.presenter.LoginPresenter;
import com.gbourquet.yaph.client.mvp.presenter.LogoutPresenter;
import com.gbourquet.yaph.client.mvp.presenter.LogoutPresenter.View;
import com.gbourquet.yaph.client.mvp.presenter.MenuPresenter;
import com.gbourquet.yaph.client.mvp.presenter.NewPasswordPresenter;
import com.gbourquet.yaph.client.mvp.presenter.PasswordPresenter;
import com.gbourquet.yaph.client.mvp.view.AppView;
import com.gbourquet.yaph.client.mvp.view.InlineView;
import com.gbourquet.yaph.client.mvp.view.LoginView;
import com.gbourquet.yaph.client.mvp.view.LogoutView;
import com.gbourquet.yaph.client.mvp.view.MenuView;
import com.gbourquet.yaph.client.mvp.view.NewPasswordView;
import com.gbourquet.yaph.client.mvp.view.PasswordView;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {
	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);

	private final AppPresenter.View appView = new AppView();
	private final PasswordPresenter.View passwordView = new PasswordView();
	private final NewPasswordPresenter.View newPasswordView = new NewPasswordView();
	private final LoginPresenter.View loginView = new LoginView();
	private final LogoutPresenter.View logoutView = new LogoutView();
	private final MenuPresenter.View menuView = new MenuView();
	private final InlinePresenter.View inlineView = new InlineView();

	private final DispatchAsync dispatcher = new StandardDispatchAsync(new DefaultExceptionHandler());

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public AppPresenter.View getAppView() {
		return appView;
	}

	@Override
	public PasswordPresenter.View getPasswordView() {
		return passwordView;
	}

	@Override
	public NewPasswordPresenter.View getNewPasswordView() {
		return newPasswordView;
	}

	@Override
	public DispatchAsync getDistpatcher() {
		return dispatcher;
	}

	@Override
	public LocalSession getLocalSession() {
		return LocalSession.getInstance();
	}

	@Override
	public LoginPresenter.View getLoginView() {

		return loginView;
	}

	@Override
	public MenuPresenter.View getMenuView() {

		return menuView;
	}

	@Override
	public InlinePresenter.View getInlineView() {

		return inlineView;
	}

	@Override
	public View getLogoutView() {
		return logoutView;
	}
}
