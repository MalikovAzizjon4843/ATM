package com.example.configs;

import com.example.exceptions.APIException;

public final class Loaders {
    private Loaders() {

    }

    public static void init() throws APIException {
        AppConfig.init();
        LocaleConfig.init();
    }
}
