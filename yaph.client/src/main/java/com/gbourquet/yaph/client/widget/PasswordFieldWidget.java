package com.gbourquet.yaph.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PasswordFieldWidget extends Composite {

	@UiField
	Button del;

	@UiField
	TextBox title;

	@UiField
	TextBox value;

	@UiField
	Button generate;

	@UiField
	ListBox type;

	private static PasswordFieldWidgetUiBinder uiBinder = GWT
			.create(PasswordFieldWidgetUiBinder.class);

	interface PasswordFieldWidgetUiBinder extends
			UiBinder<Widget, PasswordFieldWidget> {
	}

	public PasswordFieldWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		type.addItem("Texte", "TEXT");
		type.addItem("Mot de passe", "PASSWD");
		type.addItem("Adresse", "ADRESS");
		type.addItem("Date", "DATE");

		generate.setVisible(false);
		type.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if ("PASSWD".equals(type.getValue(type.getSelectedIndex()))) 
					setGenerateVisible(true);
				else
					setGenerateVisible(false);

			}
		});
	}

	private void setGenerateVisible(boolean visible)
	{
		if (visible)
			value.setWidth("351px");
		else
			value.setWidth("500px");
		generate.setVisible(visible);
	}
	
	public void setTitlePlaceHolder(String placeHolder)
	{
		title.getElement().setPropertyString("placeholder", placeHolder);
	}
	
	public void setValuePlaceHolder(String placeHolder)
	{
		value.getElement().setPropertyString("placeholder", placeHolder);
	}
	
}
