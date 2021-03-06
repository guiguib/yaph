package com.gbourquet.yaph.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.mvp.presenter.AppPresenter;
import com.gbourquet.yaph.client.mvp.presenter.InlinePresenter;
import com.gbourquet.yaph.client.mvp.presenter.LoginPresenter;
import com.gbourquet.yaph.client.mvp.presenter.LogoutPresenter;
import com.gbourquet.yaph.client.mvp.presenter.MenuPresenter;
import com.gbourquet.yaph.client.mvp.presenter.NewPasswordPresenter;
import com.gbourquet.yaph.client.mvp.presenter.PasswordPresenter;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	EventBus getEventBus();

	PlaceController getPlaceController();

	DispatchAsync getDistpatcher();

	AppPresenter.View getAppView();

	PasswordPresenter.View getPasswordView();

	NewPasswordPresenter.View getNewPasswordView();

	LoginPresenter.View getLoginView();

	MenuPresenter.View getMenuView();

	InlinePresenter.View getInlineView();

	LogoutPresenter.View getLogoutView();

	LocalSession getLocalSession();
}
