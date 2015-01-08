package com.gbourquet.yaph.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class LogoutPlace extends Place {
	private String token;

	public LogoutPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	@Prefix(value = "logout")
	public static class Tokenizer implements PlaceTokenizer<LogoutPlace> {
		@Override
		public String getToken(LogoutPlace place) {
			return place.getToken();
		}

		@Override
		public LogoutPlace getPlace(String token) {
			return new LogoutPlace(token);
		}
	}
}
