package com.example.ui;

import com.example.dao.auth.AuthUserDao;
import com.example.dao.branch.BranchDao;
import com.example.exceptions.APIException;
import com.example.mapper.AuthUserMapper;
import com.example.models.branch.Branch;
import com.example.response.ResponseEntity;
import com.example.services.superAdmin.SuperAdminService;

import static uz.jl.utils.Input.getStr;


public class SuperAdminUI {
    static SuperAdminService service = SuperAdminService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {

        String branchName = getStr(" Branch name: ");
        Branch branch = null;
        try {
            branch = BranchDao.getInstance().findByName(branchName);
        } catch (APIException e) {
            e.printStackTrace();
        }
        String userName = getStr("Username = ");
        String password = getStr("password = ");
        ResponseEntity<String> response = service.create(userName, password,branch);
        BaseUI.showResponse(response);
    }

    public static void delete() {
        list();
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.delete(userName);
        BaseUI.showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.block(userName);
        BaseUI.showResponse(response);
    }

    public static void unblock() {
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.unblock(userName);
        BaseUI.showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }
}
