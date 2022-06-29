package uz.oyatjon.ui;

import uz.oyatjon.dao.auth.AuthUserDao;
import uz.oyatjon.mapper.AuthUserMapper;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.auth.AuthService;
import uz.jl.utils.Input;


public class AuthUI extends BaseUI {
    static AuthService service = AuthService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public static void login() {
        String username = Input.getStr("username = ");
        String password = Input.getStr("password = ");
        ResponseEntity<String> response = service.login(username, password);
        showResponse(response);
    }

    public static void register() {

    }

    public static void profile() {

    }

    public static void logout() {

    }
}
