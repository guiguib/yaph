package com.gbourquet.yaph.client.mvp.place;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({AppPlace.Tokenizer.class,ProgressionPlace.Tokenizer.class,PasswordPlace.Tokenizer.class,LoginPlace.Tokenizer.class,LogoutPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper
{
}
