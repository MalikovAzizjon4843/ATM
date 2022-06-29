package com.example.ui;

import com.example.dao.auth.AuthUserDao;
import com.example.mapper.AuthUserMapper;
import com.example.response.ResponseEntity;
import com.example.services.hr.HRService;

import static uz.jl.utils.Input.getStr;

public class HrUI {
    static HRService hrService = HRService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String userName = getStr("Username = ");
        String password = getStr("password = ");
        ResponseEntity<String> response = hrService.create(userName,password);
        BaseUI.showResponse(response);

    }

    public static void delete() {
        list();
        String userName  = getStr("UserName :");
        ResponseEntity<String> response = hrService.deleteHR(userName);
        BaseUI.showResponse(response);

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
