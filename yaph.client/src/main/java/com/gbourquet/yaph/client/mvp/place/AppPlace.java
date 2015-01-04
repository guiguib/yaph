package com.gbourquet.yaph.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AppPlace extends Place {
    private String helloName;

    public AppPlace(String token) {
        this.helloName = token;
    }

    public String getHelloName() {
        return helloName;
    }

    @Prefix(value="app")
    public static class Tokenizer implements PlaceTokenizer<AppPlace> {
        @Override
        public String getToken(AppPlace place) {
            return place.getHelloName();
        }

        @Override
        public AppPlace getPlace(String token) {
            return new AppPlace(token);
        }
    }
}
