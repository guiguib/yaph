package com.gbourquet.yaph.client.mvp.presenter;

import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.AppPlace;
import com.gbourquet.yaph.client.mvp.place.PasswordPlace;
import com.gbourquet.yaph.client.mvp.place.ProgressionPlace;
import com.gbourquet.yaph.client.mvp.place.LoginPlace;
import com.gbourquet.yaph.client.mvp.place.LogoutPlace;
import com.gbourquet.yaph.client.utils.DataAccess;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppMapper implements ActivityMapper {
	private AppPresenter appPresenter;
	private ProgressionPresenter progressionPresenter;
	private PasswordPresenter passwordPresenter;
	private LoginPresenter loginPresenter;
	private LogoutPresenter logoutPresenter;
	private MenuPresenter menuPresenter;
	
	public AppMapper(ClientFactory clientFactory) {
		super();
		this.progressionPresenter = new ProgressionPresenter(clientFactory);
		this.passwordPresenter = new PasswordPresenter(clientFactory);
		this.loginPresenter = new LoginPresenter(clientFactory);
		this.logoutPresenter = new LogoutPresenter(clientFactory);
		this.setMenuPresenter(new MenuPresenter(clientFactory));
		this.appPresenter = new AppPresenter(clientFactory);
		DataAccess.getInstance().init();
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AppPlace) {
			return appPresenter;
		} else if (place instanceof ProgressionPlace) {
			return progressionPresenter;
		} else if (place instanceof PasswordPlace) {
			return passwordPresenter;
		} else if (place instanceof LoginPlace) {
			return loginPresenter;
		} else if (place instanceof LogoutPlace) {
			return logoutPresenter;
		}
		return null;
	}

	//Presenter qui n'ont pas de "Place"
	public MenuPresenter getMenuPresenter() {
		return menuPresenter;
	}

	public void setMenuPresenter(MenuPresenter menuPresenter) {
		this.menuPresenter = menuPresenter;
	}
}
