package com.gbourquet.yaph.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.mvp.presenter.AppPresenter;
import com.gbourquet.yaph.client.mvp.presenter.LoginPresenter;
import com.gbourquet.yaph.client.mvp.presenter.LogoutPresenter;
import com.gbourquet.yaph.client.mvp.presenter.MenuPresenter;
import com.gbourquet.yaph.client.mvp.presenter.NewPasswordPresenter;
import com.gbourquet.yaph.client.mvp.presenter.PasswordPresenter;
import com.gbourquet.yaph.client.mvp.presenter.ProgressionPresenter;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	EventBus getEventBus();

	PlaceController getPlaceController();

	DispatchAsync getDistpatcher();

	AppPresenter.View getAppView();
	ProgressionPresenter.View getProgressionView();
	PasswordPresenter.View getPasswordView();
	NewPasswordPresenter.View getNewPasswordView();
	LoginPresenter.View getLoginView();
	MenuPresenter.View getMenuView();
	LogoutPresenter.View getLogoutView();
	
	LocalSession getLocalSession();
}
