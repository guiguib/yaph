package com.gbourquet.yaph.client.mvp.view;

import com.gbourquet.yaph.client.mvp.presenter.InlinePresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class InlineView extends Composite implements InlinePresenter.View {

	private static InlineViewUiBinder uiBinder = GWT.create(InlineViewUiBinder.class);

	interface InlineViewUiBinder extends UiBinder<Widget, InlineView> {
	}

	@UiField
	Label indication;
	
	public InlineView() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public void setInline(Boolean inline) {
		if (inline)
			indication.setText("Inline");
		else
			indication.setText("Offline");
	}


	@Override
	public HasClickHandlers getZone() {
		// TODO Auto-generated method stub
		return indication;
	}

	
}
