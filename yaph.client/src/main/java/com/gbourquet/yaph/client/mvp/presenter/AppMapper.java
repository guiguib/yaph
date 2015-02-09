package com.gbourquet.yaph.client.mvp.presenter;

import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.AppPlace;
import com.gbourquet.yaph.client.mvp.place.LoginPlace;
import com.gbourquet.yaph.client.mvp.place.LogoutPlace;
import com.gbourquet.yaph.client.mvp.place.NewPasswordPlace;
import com.gbourquet.yaph.client.mvp.place.PasswordPlace;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppMapper implements ActivityMapper {
	private AppPresenter appPresenter;
	private PasswordPresenter passwordPresenter;
	private NewPasswordPresenter newPasswordPresenter;
	private LoginPresenter loginPresenter;
	private LogoutPresenter logoutPresenter;
	private MenuPresenter menuPresenter;
	private InlinePresenter inlinePresenter;

	public AppMapper(ClientFactory clientFactory) {
		super();
		this.passwordPresenter = new PasswordPresenter(clientFactory);
		this.newPasswordPresenter = new NewPasswordPresenter(clientFactory);
		this.loginPresenter = new LoginPresenter(clientFactory);
		this.logoutPresenter = new LogoutPresenter(clientFactory);
		this.setMenuPresenter(new MenuPresenter(clientFactory));
		this.setInlinePresenter(new InlinePresenter(clientFactory));
		this.appPresenter = new AppPresenter(clientFactory);
		DataAccess.getInstance().init();
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AppPlace) {
			return appPresenter;
		} else if (place instanceof PasswordPlace) {
			return passwordPresenter;
		} else if (place instanceof NewPasswordPlace) {
			return newPasswordPresenter;
		} else if (place instanceof LoginPlace) {
			return loginPresenter;
		} else if (place instanceof LogoutPlace) {
			return logoutPresenter;
		}
		return null;
	}

	// Presenter qui n'ont pas de "Place"
	public MenuPresenter getMenuPresenter() {
		return menuPresenter;
	}

	public void setMenuPresenter(MenuPresenter menuPresenter) {
		this.menuPresenter = menuPresenter;
	}

	public InlinePresenter getInlinePresenter() {
		return inlinePresenter;
	}

	public void setInlinePresenter(InlinePresenter inlinePresenter) {
		this.inlinePresenter = inlinePresenter;
	}
}
