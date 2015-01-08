package com.gbourquet.yaph.client.mvp.view;

import com.gbourquet.yaph.client.mvp.presenter.MenuPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MenuView extends Composite implements MenuPresenter.View {

	private static MenuViewUiBinder uiBinder = GWT.create(MenuViewUiBinder.class);

	interface MenuViewUiBinder extends UiBinder<Widget, MenuView> {
	}

	@UiField
	Label userLabel;

	@UiField
	Label disconnectedLabel;

	@UiField
	Hyperlink actionLabel;

	@UiField
	Hyperlink appAction;

	@UiField
	Hyperlink passwordAction;

	public MenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public MenuView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void updateUserLabel(String newLabel) {
		userLabel.setText(newLabel);
	}

	@Override
	public void updateActionLabel(String newLabel, String newAction) {
		actionLabel.setText(newLabel);
		actionLabel.setTargetHistoryToken(newAction);
	}

	@Override
	public void setEnableApp(boolean enabled) {
		if (enabled)
			appAction.addStyleName("active");
		else
			appAction.removeStyleName("active");
	}

	@Override
	public void setEnablePassword(boolean enabled) {
		if (enabled)
			passwordAction.addStyleName("active");
		else
			passwordAction.removeStyleName("active");
	}

	@Override
	public void setVisiblePassword(boolean visible) {
		passwordAction.setVisible(visible);
	}

	@Override
	public void setDisconnection(boolean disconnected) {
		disconnectedLabel.setText(disconnected ? "Disconnected" : "");
	}

}
