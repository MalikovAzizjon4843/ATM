package uz.oyatjon.configs;

import uz.oyatjon.exceptions.APIException;

public final class Loaders {
    private Loaders() {

    }

    public static void init() throws APIException {
        AppConfig.init();
        LocaleConfig.init();
    }
}
