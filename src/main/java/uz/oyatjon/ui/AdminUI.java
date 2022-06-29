package uz.oyatjon.ui;

import uz.oyatjon.dao.auth.AuthUserDao;
import uz.oyatjon.mapper.AuthUserMapper;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.admin.AdminService;

import static uz.oyatjon.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;


public class AdminUI {
    static AdminService adminService = AdminService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String userName = getStr("Username = ");
        String password = getStr("password = ");
        ResponseEntity<String> response = adminService.createHR(userName, password);
        showResponse(response);

    }

    public static void delete() {
        list();
        String userName  = getStr("UserName :");
        ResponseEntity<String> response = adminService.deleteHR(userName);
        showResponse(response);

    }

    public static void list() {
            adminService.list();
    }
    public static void unblockList(){
        adminService.unBlocklist();
       
    }

    public static void block() {
        list();
        String userName = getStr(" User Name ");
        ResponseEntity<String> response = adminService.blockHR(userName);
        showResponse(response);

    }

    public static void unBlock() {
        blockList();
        String userName = getStr(" User Name ");
        ResponseEntity<String> response = adminService.unBlock(userName);
showResponse(response);

    }

    public static void blockList() {
      adminService.blockHRList();
    }
}
