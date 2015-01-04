package com.gbourquet.yaph.client.mvp.presenter;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.event.MenuEvent;
import com.gbourquet.yaph.client.mvp.ClientFactory;
import com.gbourquet.yaph.client.mvp.place.AppPlace;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public class ProgressionPresenter extends AbstractPresenter {

	/*
	 * Contrat echangé avec la vue
	 */
	public interface View extends IsWidget {
	}

	public View view;

	public ProgressionPresenter(ClientFactory factory) {
		super(factory);
		view = factory.getProgressionView();
		bind();
	}

	public void bind() {
	}

	@Override
	public void start() {
		if (LocalSession.getInstance().getAttribute("account") != null) {
			RootPanel.get("container").clear();
			RootPanel.get("container").add(getView().asWidget());
			getEventBus().fireEvent(new MenuEvent("progression", true));
		}
		else
		{
			// On redirige vers la page d'acceuil
			getEventBus().fireEvent(new MenuEvent("app", true));
			getFactory().getPlaceController().goTo(new AppPlace(""));
		}
			
	}

	@Override
	public String mayStop() {
		getEventBus().fireEvent(new MenuEvent("progression", false));
		return null;
	}

	public View getView() {
		return view;
	}
}