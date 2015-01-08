package com.gbourquet.yaph.client.mvp.view;

import com.gbourquet.yaph.client.mvp.presenter.AppPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AppView extends Composite implements AppPresenter.View {

	private static AppViewUiBinder uiBinder = GWT.create(AppViewUiBinder.class);

	interface AppViewUiBinder extends UiBinder<Widget, AppView> {
	}

	public AppView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void hello(String nom) {
		// TODO Auto-generated method stub

	}

}
