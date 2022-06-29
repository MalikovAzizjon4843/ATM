package uz.oyatjon.ui;

import uz.oyatjon.dao.auth.AuthUserDao;
import uz.oyatjon.mapper.AuthUserMapper;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.hr.HRService;

import static uz.oyatjon.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

public class HrUI {
    static HRService hrService = HRService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String userName = getStr("Username = ");
        String password = getStr("password = ");
        ResponseEntity<String> response = hrService.create(userName,password);
        showResponse(response);

    }

    public static void delete() {
        list();
        String userName  = getStr("UserName :");
        ResponseEntity<String> response = hrService.deleteHR(userName);
        showResponse(response);

    }

    public static void list() {
        hrService.listHR();

    }

    public static void block() {

    }

    public static void unBlock() {

    }

    public static void blockList() {

    }
}
