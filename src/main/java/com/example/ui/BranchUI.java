package com.example.ui;

import com.example.dao.branch.BranchDao;
import com.example.mapper.BranchMapper;
import com.example.response.ResponseEntity;
import com.example.services.branch.BranchService;
import uz.jl.utils.Input;

public class BranchUI {
    static BranchService service = BranchService.getInstance(BranchDao.getInstance(), BranchMapper.getInstance());

    public static void create() {
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.create(name);
        BaseUI.showResponse(response);
    }

    public static void update() {
        list();
        String name = Input.getStr("Branch name : ");
        String newBranchName = Input.getStr("New branch Name : ");
        ResponseEntity<String>response = service.updateBranch(name,newBranchName);
        BaseUI.showResponse(response);



    }

    public static void delete() {
        list();
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.delete(name);
        BaseUI.showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.block(name);
        BaseUI.showResponse(response);
    }

    public static void unblock() {
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.unblock(name);
        BaseUI.showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }

    public static void unblockList() {
        service.unblockList();
    }
}
