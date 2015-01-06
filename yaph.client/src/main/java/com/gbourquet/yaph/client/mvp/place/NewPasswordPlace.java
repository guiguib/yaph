package com.gbourquet.yaph.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class NewPasswordPlace extends Place {
    private String token;

    public NewPasswordPlace(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Prefix(value="newpassword")
    public static class Tokenizer implements PlaceTokenizer<NewPasswordPlace> {
        @Override
        public String getToken(NewPasswordPlace place) {
            return place.getToken();
        }

        @Override
        public NewPasswordPlace getPlace(String token) {
            return new NewPasswordPlace(token);
        }
    }
}
