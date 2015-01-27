package com.gbourquet.yaph.client.widget;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class FolderTree extends Tree {

	TreeItem oldSelectedItem = null;
	
	public FolderTree() {
		super();
		this.addSelectionHandler(new SelectionHandler<TreeItem>() {

			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				//on parcour recursivement 
				if (oldSelectedItem != null)
					oldSelectedItem.getWidget().removeStyleName("selected");
				oldSelectedItem = event.getSelectedItem();
				oldSelectedItem.getWidget().addStyleName("selected");
			}
		});
	}

	@Override
	public HandlerRegistration addSelectionHandler(SelectionHandler<TreeItem> handler) {
		// TODO Auto-generated method stub
		return super.addSelectionHandler(handler);
	}
}
