package com.gbourquet.yaph.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.gbourquet.yaph.client.event.widget.TagEvent;
import com.gbourquet.yaph.client.event.widget.TagEventHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TagsWidget extends Composite implements ClickHandler, HasHandlers {

	private static TagsWidgetUiBinder uiBinder = GWT.create(TagsWidgetUiBinder.class);

	interface TagsWidgetUiBinder extends UiBinder<Widget, TagsWidget> {
	}

	List<Anchor> tags;
	List<String> valueTags;
	TextBox saisiTag;
	
	private HandlerManager handlerManager;

	@UiField
	HTMLPanel container;

	public TagsWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		handlerManager = new HandlerManager(this);
		
		tags = new ArrayList<Anchor>();
		valueTags = new ArrayList<String>();

		saisiTag = new TextBox();
		saisiTag.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				// Si on appuie sur " " ou ENTER
				if (KeyCodes.KEY_SPACE == event.getNativeEvent().getKeyCode() ||
						KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode()) {
					//Si le tag exist déjà, on ne fait rien
					final String vTag = saisiTag.getText().trim().toLowerCase();
					if (valueTags.contains(vTag))
					{
						saisiTag.setText("");
						saisiTag.setFocus(true);
						return;
					}
					final Anchor tag = new Anchor("<i class='mdi-content-clear right'></i>"+vTag, true);
					tag.setStyleName("waves-effect waves-light btn");
					tags.add(tag);
					valueTags.add(vTag);
					tag.addClickHandler(TagsWidget.this);
					container.clear();
					for (Anchor lTag : tags)
					{
						container.add(lTag);
					}
					container.add(saisiTag);
					saisiTag.setText("");
					saisiTag.setFocus(true);
					
					fireEvent(new TagEvent(vTag) {
						
						@Override
						protected void dispatch(TagEventHandler handler) {
							handler.onNewTag(this);
						}
					});
				}

			}
		});
		
		container.add(saisiTag);
	}

	@Override
	public void onClick(ClickEvent event) {
		Anchor tag = (Anchor)event.getSource();
		tags.remove(tag);
		container.remove(tag);
		valueTags.remove(tag.getText());
		saisiTag.setFocus(true);
		fireEvent(new TagEvent(tag.getText()) {
			
			@Override
			protected void dispatch(TagEventHandler handler) {
				handler.onRemove(this);
			}
		});
		
	}
	
	@Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    public HandlerRegistration addTagEventHandler(TagEventHandler handler) {
        return handlerManager.addHandler(TagEvent.TYPE, handler);
    }

}
