package com.gbourquet.yaph.client.mvp;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.mvp.presenter.AppPresenter;
import com.gbourquet.yaph.client.mvp.presenter.LoginPresenter;
import com.gbourquet.yaph.client.mvp.presenter.LoginPresenter.View;
import com.gbourquet.yaph.client.mvp.presenter.LogoutPresenter;
import com.gbourquet.yaph.client.mvp.presenter.MenuPresenter;
import com.gbourquet.yaph.client.mvp.presenter.NewPasswordPresenter;
import com.gbourquet.yaph.client.mvp.presenter.PasswordPresenter;
import com.gbourquet.yaph.client.mvp.presenter.ProgressionPresenter;
import com.gbourquet.yaph.client.mvp.view.MenuView;
import com.gbourquet.yaph.client.mvp.view.mobile.AppView;
import com.gbourquet.yaph.client.mvp.view.mobile.LoginView;
import com.gbourquet.yaph.client.mvp.view.mobile.LogoutView;
import com.gbourquet.yaph.client.mvp.view.mobile.NewPasswordView;
import com.gbourquet.yaph.client.mvp.view.mobile.PasswordView;
import com.gbourquet.yaph.client.mvp.view.mobile.ProgressionView;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryMobileImpl implements ClientFactory {
	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);

	private final AppPresenter.View appView = new AppView();
	private final ProgressionPresenter.View progressionView = new ProgressionView();
	private final PasswordPresenter.View passwordView = new PasswordView();
	private final NewPasswordPresenter.View newPasswordView = new NewPasswordView();
	private final LoginPresenter.View loginView = new LoginView();
	private final LogoutPresenter.View logoutView = new LogoutView();
	private final MenuPresenter.View menuView = new MenuView();

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
	public ProgressionPresenter.View getProgressionView() {
		return progressionView;
	}

	@Override
	public PasswordPresenter.View getPasswordView() {
		return passwordView;
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
	public View getLoginView() {
		return loginView;
	}

	@Override
	public MenuPresenter.View getMenuView() {
		return menuView;
	}

	@Override
	public LogoutPresenter.View getLogoutView() {
		return logoutView;
	}

	@Override
	public NewPasswordPresenter.View getNewPasswordView() {
		return newPasswordView;
	}

}
