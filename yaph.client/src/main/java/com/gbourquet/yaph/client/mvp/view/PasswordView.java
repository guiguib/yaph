package com.gbourquet.yaph.client.mvp.view;

import java.util.HashMap;
import java.util.List;

import com.gbourquet.yaph.client.event.widget.TagEventHandler;
import com.gbourquet.yaph.client.mvp.presenter.PasswordPresenter;
import com.gbourquet.yaph.client.widget.PasswordWidget;
import com.gbourquet.yaph.client.widget.TagsWidget;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class PasswordView extends Composite implements PasswordPresenter.View {

	private static PasswordViewUiBinder uiBinder = GWT.create(PasswordViewUiBinder.class);

	interface PasswordViewUiBinder extends UiBinder<Widget, PasswordView> {
	}

	

	public static native int activePassword() /*-{
	// Initialize collapsible
	return $wnd.jQuery('ul[class="collapsible"] li[class="active"]').attr('id'); 
}-*/;
	
	public static native void removeTooltipsJS() /*-{
		$wnd.jQuery('.tooltipped').mouseleave();
}-*/;
	
	
	public static native void setActivePassword(int i) /*-{
	
	$wnd.jQuery('ul[class="collapsible"] li[id="'+i+'"]').attr('class','active');
	$wnd.jQuery('ul[class="collapsible"] li[id="'+i+'"] div[class="collapsible-body"]').show();
	 
	
}-*/;

	
	/**
	 * The tags Area
	 */
	@UiField(provided=true)
	TagsWidget tags;

	/**
	 * The main DataGrid.
	 */
	@UiField
	HTMLPanel data;

	/**
	 * The "new" Button.
	 */
	@UiField
	Button newPasswordButton;

	HashMap<Integer,HTMLPanel> wPasswords;

	private ClickHandler updateHandler;

	private ClickHandler removeHandler;
	
	public PasswordView() {
		initWidget(uiBinder.createAndBindUi(this));
		data.getElement().setAttribute("data-collapsible", "accordion");
		wPasswords = new HashMap<Integer, HTMLPanel>();
	}

	/**
	 * The UiBinder interface used by this example.
	 */
	interface Binder extends UiBinder<Widget, PasswordView> {
	}

	@Override
	public HasClickHandlers getNewPasswordButton() {
		return newPasswordButton;
	}

	@Override
	public void addPassword(PasswordCard password, List<PasswordField> fields, boolean open) {
		HTMLPanel li = new HTMLPanel("li", "");
		if (open)
			li.getElement().setAttribute("class", "active");
		HTMLPanel header = new HTMLPanel("div", "<i class='mdi-action-turned-in-not'></i>"+password.getTitre());
		header.setStyleName("collapsible-header");

		HTMLPanel body = new HTMLPanel("div", "");
		HTMLPanel bodyP = new HTMLPanel("p", "");
		
		body.setStyleName("collapsible-body");
		body.add(bodyP);
		
		for (PasswordField lField : fields)
		{
			PasswordWidget.TypePassword type = "PASSWD".equals(lField.getType()) ? PasswordWidget.TypePassword.PASSWD : PasswordWidget.TypePassword.TEXT;
			PasswordWidget wField = new PasswordWidget(type);
			wField.setTitleText(lField.getLibelle());
			wField.setValueText(lField.getValue());
			wField.setType(type);
			bodyP.add(wField);
		}
		
		HTMLPanel actionBar = new HTMLPanel("div", "");
		actionBar.setStylePrimaryName("row");
		
		HTMLPanel actionButtonDel = new HTMLPanel("div", "");
		actionButtonDel.setStylePrimaryName("col s2 left-align");
		Anchor deleteButton = new Anchor("<i class='mdi-content-remove'></i>",true);
		deleteButton.addClickHandler(this.removeHandler);
		deleteButton.setStylePrimaryName("btn-floating tooltipped waves-effect waves-light");
		deleteButton.getElement().setAttribute("data-position", "top");
		deleteButton.getElement().setAttribute("data-delay", "5");
		deleteButton.getElement().setAttribute("data-tooltip", "Supprimer");
		  
		actionButtonDel.add(deleteButton);
		actionBar.add(actionButtonDel);
		
		HTMLPanel actionButtonEdit = new HTMLPanel("div", "");
		actionButtonEdit.setStylePrimaryName("col s2 left-align");
		Anchor editButton = new Anchor("<i class='mdi-editor-mode-edit'></i>",true);
		editButton.addClickHandler(this.updateHandler);
		editButton.setStylePrimaryName("btn-floating tooltipped waves-effect waves-light");
		editButton.getElement().setAttribute("data-position", "top");
		editButton.getElement().setAttribute("data-delay", "5");
		editButton.getElement().setAttribute("data-tooltip", "Modifier");
		actionButtonEdit.add(editButton);
		actionBar.add(actionButtonEdit);
		
		bodyP.add(actionBar);
		
		li.add(header);
		li.add(body);
		li.getElement().setAttribute("id", password.getId()+"");
		data.add(li);
		wPasswords.put(password.getId(), li);
	}

	@Override
	public void removePassword(PasswordCard password) {
		HTMLPanel li = wPasswords.get(password.getId());
		if (li!=null)
			data.remove(li);
	}

	@Override
	public void updatePasswordList(HashMap<PasswordCard, List<PasswordField>> passwords) {
		data.clear();
		for (PasswordCard lPassword : passwords.keySet()) {
			List<PasswordField> lFields = passwords.get(lPassword);
			addPassword(lPassword,lFields,false);
		}
	}

	@Override
	public void selectPassword(PasswordCard password) {
		setActivePassword(password.getId().intValue());
	}

	@Override
	public void setUpdateHandler(ClickHandler handler) {
		this.updateHandler = handler;
		
	}

	@Override
	public void setRemoveHandler(ClickHandler handler) {
		this.removeHandler = handler;
	}

	@Override
	public Integer getOpenPasswordId() {
		return activePassword();
	}

	@Override
	public void removeTooltips() {
		removeTooltipsJS();
	}

	@Override
	public void addTagHandler(TagEventHandler handler) {
		this.tags.addTagEventHandler(handler);
	}
}
