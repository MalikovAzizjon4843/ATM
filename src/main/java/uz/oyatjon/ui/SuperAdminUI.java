package uz.oyatjon.ui;

import uz.oyatjon.dao.auth.AuthUserDao;
import uz.oyatjon.dao.branch.BranchDao;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.mapper.AuthUserMapper;
import uz.oyatjon.models.branch.Branch;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.superAdmin.SuperAdminService;

import static uz.oyatjon.ui.BaseUI.showResponse;
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
        showResponse(response);
    }

    public static void delete() {
        list();
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.delete(userName);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.block(userName);
        showResponse(response);
    }

    public static void unblock() {
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.unblock(userName);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }
}
