package com.gbourquet.yaph.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class LoginPlace extends Place {
	private String token;

	public LoginPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	@Prefix(value = "login")
	public static class Tokenizer implements PlaceTokenizer<LoginPlace> {
		@Override
		public String getToken(LoginPlace place) {
			return place.getToken();
		}

		@Override
		public LoginPlace getPlace(String token) {
			return new LoginPlace(token);
		}
	}
}
