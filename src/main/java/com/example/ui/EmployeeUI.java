package com.example.ui;

import com.example.dao.auth.AuthUserDao;
import com.example.exceptions.APIException;
import com.example.mapper.AuthUserMapper;
import com.example.models.auth.AuthUser;
import com.example.response.ResponseEntity;
import com.example.services.employee.EmployeeService;

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
        BaseUI.showResponse(response);
    }

    public static void delete() {
        String userName = getStr("Username-> ");
        ResponseEntity<String> response = service.delete(userName);
        BaseUI.showResponse(response);
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
