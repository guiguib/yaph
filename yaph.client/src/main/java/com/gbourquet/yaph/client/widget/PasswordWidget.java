package com.gbourquet.yaph.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PasswordWidget extends Composite {

	@UiField
	TextBox title;

	@UiField
	TextBox value;

	private static PasswordFieldWidgetUiBinder uiBinder = GWT
			.create(PasswordFieldWidgetUiBinder.class);

	interface PasswordFieldWidgetUiBinder extends
			UiBinder<Widget, PasswordWidget> {
	}

	public PasswordWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	
	}

	public String getTitleText() {
		return title.getText();
	}

	public void setTitleText(String title) {
		this.title.setText(title);
	}

	public String getValueText() {
		return value.getText();
	}

	public void setValueText(String value) {
		this.value.setText(value);
	}

}
