package com.gbourquet.yaph.client.mvp.view;

import com.gbourquet.yaph.client.mvp.presenter.ProgressionPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProgressionView extends Composite implements ProgressionPresenter.View {

	private static ProgressionViewUiBinder uiBinder = GWT.create(ProgressionViewUiBinder.class);

	interface ProgressionViewUiBinder extends UiBinder<Widget, ProgressionView> {
	}

	@UiField
	StackLayoutPanel stack;

	public ProgressionView() {
		initWidget(uiBinder.createAndBindUi(this));
		String height = Integer.toString(Window.getClientHeight()) + "px";
		stack.setHeight(height);
	}
}
