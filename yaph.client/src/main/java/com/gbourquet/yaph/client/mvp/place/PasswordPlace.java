package com.gbourquet.yaph.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PasswordPlace extends Place {
	private String token;

	public PasswordPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	@Prefix(value = "password")
	public static class Tokenizer implements PlaceTokenizer<PasswordPlace> {
		@Override
		public String getToken(PasswordPlace place) {
			return place.getToken();
		}

		@Override
		public PasswordPlace getPlace(String token) {
			return new PasswordPlace(token);
		}
	}
}
