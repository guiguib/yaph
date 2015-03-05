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

	public static native int initMenu() /*-{
		// Initialize collapsible
		$wnd.jQuery('#bMenu').sideNav({
			menuWidth : 240, // Default is 240
			edge : 'right', // Choose the horizontal origin
			closeOnClick : true
		// Closes side-nav on <a> clicks, useful for Angular/Meteor
		});
	}-*/;

	public static native int hideSideNav() /*-{
		// Initialize collapsible
		$wnd.jQuery('.button-collapse').sideNav('hide');
	}-*/;

	@UiField
	Label userLabel;

	@UiField
	Hyperlink actionLabel;

	@UiField
	Hyperlink appAction;

	@UiField
	Hyperlink passwordAction;

	public MenuView() {
		initWidget(uiBinder.createAndBindUi(this));
		initMenu();
	}

	public MenuView(String firstName) {
		this();
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
	public void hideSideNavBar() {
		hideSideNav();
	}
}
