package com.gbourquet.yaph.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ProgressionPlace extends Place {
    private String token;

    public ProgressionPlace(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Prefix(value="progression")
    public static class Tokenizer implements PlaceTokenizer<ProgressionPlace> {
        @Override
        public String getToken(ProgressionPlace place) {
            return place.getToken();
        }

        @Override
        public ProgressionPlace getPlace(String token) {
            return new ProgressionPlace(token);
        }
    }
}
