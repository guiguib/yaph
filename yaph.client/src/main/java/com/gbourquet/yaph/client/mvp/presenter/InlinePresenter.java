package com.gbourquet.yaph.client.mvp.presenter;

import com.gbourquet.yaph.client.event.InlineEvent;
import com.gbourquet.yaph.client.event.InlineEventHandler;
import com.gbourquet.yaph.client.event.LoadApplicationEvent;
import com.gbourquet.yaph.client.event.LoadApplicationEventHandler;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public class InlinePresenter extends AbstractPresenter {

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {
		public void setInline(Boolean inline);
		public HasClickHandlers getZone();
	}

	public View view;
	private boolean state=true;
	
	public InlinePresenter(ClientFactory factory) {
		super(factory);
		view = factory.getInlineView();
		bind();
	}

	public void bind() {
		// On s'inscrit aux evenements du Bus
		getEventBus().addHandler(InlineEvent.TYPE, new InlineEventHandler() {

			@Override
			public void onInline(final InlineEvent event) {
				getView().setInline(true);
				state=true;
			}

			@Override
			public void onOffline(final InlineEvent event) {
				getView().setInline(false);
				state=false;
			}
		});

		getEventBus().addHandler(LoadApplicationEvent.TYPE,	new LoadApplicationEventHandler() {

					@Override
					public void onLoad(final LoadApplicationEvent event) {
						RootPanel.get("inline").clear();
						RootPanel.get("inline").add(getView().asWidget());
					}
				});
		getView().getZone().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				state=!state;
				getEventBus().fireEvent(new InlineEvent(state));
			}
		});
	}

	@Override
	public void start() {
	}

	public View getView() {
		return view;
	}
}