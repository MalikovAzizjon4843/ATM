package uz.oyatjon.ui;

import uz.oyatjon.dao.branch.BranchDao;
import uz.oyatjon.mapper.BranchMapper;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.branch.BranchService;
import uz.jl.utils.Input;

import static uz.oyatjon.ui.BaseUI.showResponse;

public class BranchUI {
    static BranchService service = BranchService.getInstance(BranchDao.getInstance(), BranchMapper.getInstance());

    public static void create() {
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.create(name);
        showResponse(response);
    }

    public static void update() {
        list();
        String name = Input.getStr("Branch name : ");
        String newBranchName = Input.getStr("New branch Name : ");
        ResponseEntity<String>response = service.updateBranch(name,newBranchName);
        showResponse(response);



    }

    public static void delete() {
        list();
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.delete(name);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.block(name);
        showResponse(response);
    }

    public static void unblock() {
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.unblock(name);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }

    public static void unblockList() {
        service.unblockList();
    }
}
