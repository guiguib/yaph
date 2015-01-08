package com.gbourquet.yaph.client.widget;

import com.google.gwt.user.cellview.client.SimplePager;

public class NormalPager extends SimplePager {

	public NormalPager(TextLocation center, Resources pagerResources, boolean b, int i, boolean c) {
		super(center, pagerResources, b, i, c);
	}

	protected String createText() {
		if (getDisplay().getRowCount() == 0) {
			return "0 of 0";
		} else {
			return super.createText();
		}
	}

	@Override
	public void lastPage() {
		super.lastPage();
	}
}
