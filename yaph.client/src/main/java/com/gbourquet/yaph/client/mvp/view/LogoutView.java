package com.gbourquet.yaph.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gbourquet.yaph.client.mvp.presenter.LogoutPresenter;

public class LogoutView extends Composite implements LogoutPresenter.View  {

	private static LogoutViewUiBinder uiBinder = GWT
			.create(LogoutViewUiBinder.class);

	interface LogoutViewUiBinder extends UiBinder<Widget, LogoutView> {
	}

	public LogoutView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
