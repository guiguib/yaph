package com.gbourquet.yaph.client.mvp.view.mobile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gbourquet.yaph.client.mvp.presenter.ProgressionPresenter;

public class ProgressionView extends Composite implements ProgressionPresenter.View {

	private static ProgressionViewUiBinder uiBinder = GWT
			.create(ProgressionViewUiBinder.class);

	interface ProgressionViewUiBinder extends UiBinder<Widget, ProgressionView> {
	}

	public ProgressionView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
