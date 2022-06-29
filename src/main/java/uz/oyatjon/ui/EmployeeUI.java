package uz.oyatjon.ui;

import uz.oyatjon.dao.auth.AuthUserDao;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.mapper.AuthUserMapper;
import uz.oyatjon.models.auth.AuthUser;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.employee.EmployeeService;

import static uz.oyatjon.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;


public class EmployeeUI {
    static EmployeeService service = EmployeeService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());


    public static void create() throws APIException {
        String userName = getStr("Username-> ");
        String password = getStr("password-> ");
        String phoneNumber = getStr("Phone Number->");
        AuthUser authUser = new AuthUser();
        authUser.setUsername(userName);
        authUser.setPassword(password);
        authUser.setPhoneNumber(phoneNumber);
        ResponseEntity<String> response = service.create(authUser);
        showResponse(response);
    }

    public static void delete() {
        String userName = getStr("Username-> ");
        ResponseEntity<String> response = service.delete(userName);
        showResponse(response);
    }

    public static void list() {
        EmployeeService.list();
    }

    public static void block() {
        EmployeeService.block();
    }

    public static void unBlock() {
        EmployeeService.unBlock();
    }

    public static void blockList() {
        EmployeeService.blockList();
    }
}
