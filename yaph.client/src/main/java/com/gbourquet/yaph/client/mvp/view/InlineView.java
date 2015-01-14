package com.gbourquet.yaph.client.mvp.view;

import com.gbourquet.yaph.client.mvp.presenter.InlinePresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class InlineView extends Composite implements InlinePresenter.View {

	private static InlineViewUiBinder uiBinder = GWT.create(InlineViewUiBinder.class);

	interface InlineViewUiBinder extends UiBinder<Widget, InlineView> {
	}

	@UiField
	Button button;
	
	@UiField
	HTMLPanel indication;
	
	@UiField
	Label state;
	
	public InlineView() {
		initWidget(uiBinder.createAndBindUi(this));
		button.setHeight("20px");
		button.setWidth("100%");
		state.setHeight("20px");
		state.setWidth("100%");
	}


	@Override
	public void setInline(Boolean inline) {
		if (inline){
			indication.setStyleName("online");
			state.setText("Online");
		}
		else {
			indication.setStyleName("offline");
			state.setText("Offline");
		}
	}


	@Override
	public HasClickHandlers getZone() {
		return button;
	}

	
}
