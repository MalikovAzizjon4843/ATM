package uz.oyatjon.configs;

import uz.oyatjon.dao.db.FRWAuthUser;
import uz.oyatjon.enums.auth.Role;
import uz.oyatjon.enums.auth.UserStatus;
import uz.oyatjon.enums.http.HttpStatus;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.models.auth.AuthUser;
import uz.oyatjon.models.settings.Language;
import uz.oyatjon.utils.BaseUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class AppConfig {
    public static Language language;

    private static final Properties properties = new Properties();

    public static void init() throws APIException {
        load();
        language = Language.getByCode(get("bank.default.language"));
    }

    public static void initSuperUser() {
        AuthUser user = new AuthUser();
        user.setId(BaseUtils.genId());
        user.setUsername(get("bank.super.username"));
        user.setPassword(get("bank.super.password"));
        user.setRole(Role.ADMIN);
        user.setLanguage(Language.getByCode(get("bank.default.language")));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedBy("-1");
        user.setPhoneNumber(get("bank.super.phone"));
        user.setCreatedBy(Session.getInstance().getUser().getId());
        FRWAuthUser.getInstance().writeAll(user);
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    private static void load() throws APIException {
        try {
            properties.load(new FileReader("src/main/resources/app.properties"));
        } catch (IOException e) {
            throw new APIException("File not found", HttpStatus.HTTP_404);
        }
    }
}
