package com.gbourquet.yaph.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import com.gbourquet.yaph.client.mvp.presenter.NewPasswordPresenter;
import com.gbourquet.yaph.client.widget.PasswordFieldWidget;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NewPasswordView extends Composite implements NewPasswordPresenter.View {

	private static NewPasswordViewUiBinder uiBinder = GWT
			.create(NewPasswordViewUiBinder.class);

	interface NewPasswordViewUiBinder extends UiBinder<Widget, NewPasswordView> {
	}

	@UiField
	DialogBox dialog;

	@UiField
	Label error;

	@UiField
	TextBox title;

	@UiField
	Button validButton;

	@UiField
	Button cancelButton;

	@UiField
	Button addFieldButton;
	
	@UiField 
	Panel fieldsPanel;
	
	//Liste des champs
	List<PasswordFieldWidget> fields=new ArrayList<PasswordFieldWidget>();
		
	public NewPasswordView() {

		initWidget(uiBinder.createAndBindUi(this));

		title.getElement().setPropertyString("placeholder", "Password Title");
		
		dialog.center();
		dialog.hide();
		
		addField();
		addField();
		addField();
		
	}

	/**
	 * The UiBinder interface used by this example.
	 */
	interface Binder extends UiBinder<Widget, NewPasswordView> {
	}
	
	@Override
	public void show() {
		dialog.center();
		dialog.setVisible(true);
		dialog.show();
	}

	@Override
	public HasClickHandlers getCancelButton() {

		return cancelButton;
	}

	@Override
	public void close() {
		dialog.hide();
	}

	@Override
	public void clear() {
		title.setText("");
		for (PasswordFieldWidget fieldWidget : fields)
		{
			fieldsPanel.remove(fieldWidget);
		}
		fields = new ArrayList<PasswordFieldWidget>();
		addField();
		addField();
		addField();
	}

	@Override
	public HasClickHandlers getValidButton() {
		return validButton;
	}

	@Override
	public String getTitleText() {
		return title.getText();
	}

	@Override
	public Label getErrorLabel() {
		return error;
	}

	@Override
	public HasClickHandlers getAddFieldButton() {
		return addFieldButton;
	}

	@Override
	public void addField() {
		final PasswordFieldWidget field=new PasswordFieldWidget();
		field.setTitlePlaceHolder("Field Title");
		field.setValuePlaceHolder("Value");
		field.addDelClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fieldsPanel.remove(field);
				fields.remove(field);
			}
		});
		
		fields.add(field);
		fieldsPanel.add(field);
	}

	@Override
	public List<PasswordField> getPasswordFields() {
		List<PasswordField> result = new ArrayList<PasswordField>();
		for (PasswordFieldWidget fieldWidget : fields)
		{
			PasswordField field = new PasswordField();
			field.setLibelle(fieldWidget.getTitleBox().getText());
			field.setType(fieldWidget.getType().getValue(fieldWidget.getType().getSelectedIndex()));
			field.setValue(fieldWidget.getValue().getText());
			
			result.add(field);
		}
		return result;
	}

}
