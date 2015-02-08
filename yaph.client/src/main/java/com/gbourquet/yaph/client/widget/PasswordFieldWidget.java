package com.gbourquet.yaph.client.widget;

import com.gbourquet.yaph.client.utils.PasswordGenerator;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PasswordFieldWidget extends Composite {

	public static native void widgetInitJS() /*-{
	// Initialize collapsible
	$wnd.jQuery('select').material_select();
	$wnd.jQuery('.tooltipped').tooltip();
}-*/;
	
	
	int id=0;
	
	@UiField
	Anchor del;

	@UiField
	TextBox title;

	@UiField
	TextBox value;

	@UiField
	Anchor generate;

	@UiField
	ListBox type;

	private static PasswordFieldWidgetUiBinder uiBinder = GWT.create(PasswordFieldWidgetUiBinder.class);

	interface PasswordFieldWidgetUiBinder extends UiBinder<Widget, PasswordFieldWidget> {
	}

	public void addDelClickHandler(ClickHandler handler) {
		del.addClickHandler(handler);
	}

	public PasswordFieldWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		type.addItem("Texte", "TEXT");
		type.addItem("Mot de passe", "PASSWD");
		type.addItem("Adresse", "ADRESS");
		type.addItem("Date", "DATE");

		//generate.setVisible(false);
		type.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if ("PASSWD".equals(type.getValue(type.getSelectedIndex())))
				{	
					setGenerateVisible(true);
				} else {
					setGenerateVisible(false);
				}
			}
		});

		// Generate nouveau mot de passe
		generate.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				PasswordGenerator generator = new PasswordGenerator(20, true);
				value.setText(generator.exec());
			}
		});

		del.getElement().setAttribute("data-position", "top");
		del.getElement().setAttribute("data-delay", "5");
		del.getElement().setAttribute("data-tooltip", "Supprimer");
		
		generate.getElement().setAttribute("data-position", "top");
		generate.getElement().setAttribute("data-delay", "5");
		generate.getElement().setAttribute("data-tooltip", "Générer");
	}

	private void setGenerateVisible(boolean visible) {
		generate.setVisible(visible);
	}

	public void init()
	{
		widgetInitJS();
	}
	
	public TextBox getTitleBox() {
		return title;
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public TextBox getValueBox() {
		return value;
	}

	public void setValue(String value) {
		this.value.setText(value);
	}

	public ListBox getTypeBox() {
		return type;
	}

	public void setType(String type) {
		int index = 0;
		for (int i = 0; i < this.type.getItemCount(); i++) {
			if (type.equals(this.type.getValue(i)))
				index = i;
		}
		this.type.setItemSelected(index, true);
		DomEvent.fireNativeEvent(Document.get().createChangeEvent(), this.type);
	}

	public void setTitlePlaceHolder(String placeHolder) {
		title.getElement().setPropertyString("placeholder", placeHolder);
	}

	public void setValuePlaceHolder(String placeHolder) {
		value.getElement().setPropertyString("placeholder", placeHolder);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
