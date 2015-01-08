package com.gbourquet.yaph.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PasswordWidget extends Composite {

	@UiField
	Label title;

	@UiField
	Label value;

	@UiField
	Label enclair;

	String valuePasswd = "";
	Boolean valueVisible = false;

	public enum TypePassword {
		TEXT, ADRESS, PASSWD, DATE;
	}

	TypePassword type;

	private static PasswordFieldWidgetUiBinder uiBinder = GWT.create(PasswordFieldWidgetUiBinder.class);

	interface PasswordFieldWidgetUiBinder extends UiBinder<Widget, PasswordWidget> {
	}

	public PasswordWidget(TypePassword type) {
		initWidget(uiBinder.createAndBindUi(this));
		this.type = type;
		enclair.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setValueVisible(!valueVisible);
			}
		});
	}

	public String getTitleText() {
		return title.getText();
	}

	public void setTitleText(String title) {
		this.title.setText(title + " : ");
	}

	public String getValueText() {
		return value.getText();
	}

	public void setValueText(String value) {
		this.valuePasswd = value;
		if (this.type == TypePassword.PASSWD && !valueVisible) {
			this.value.setText("******");
			this.enclair.setText("en clair");
		} else {
			this.value.setText(value);
			this.enclair.setText(this.type == TypePassword.PASSWD ? "dissimuler" : "");
		}
	}

	public void setValueVisible(Boolean visible) {
		if (this.type == TypePassword.PASSWD && !visible) {
			this.value.setText("******");
			this.enclair.setText("en clair");
		} else {
			this.value.setText(valuePasswd);
			this.enclair.setText(this.type == TypePassword.PASSWD ? "dissimuler" : "");
		}
		this.valueVisible = visible;
	}

	public TypePassword getType() {
		return type;
	}

	public void setType(TypePassword type) {
		this.type = type;
	}

}
