package uz.oyatjon.ui;

import uz.oyatjon.configs.AppConfig;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.services.atm.AtmService;
import uz.oyatjon.services.branch.BranchService;
import uz.oyatjon.ui.menus.Menu;
import uz.oyatjon.ui.menus.MenuKey;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;


public class MainMenu {
    static {
        try {
            AppConfig.init();
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws APIException {
        AppConfig.initSuperUser();
        Menu.show();
        String choice = Input.getStr("?:");
        MenuKey key = MenuKey.getByValue(choice);

        switch (key) {
            case LOGIN -> AuthUI.login();
            case REGISTER -> AuthUI.register();
            case PROFILE -> AuthUI.profile();
            case LOGOUT -> AuthUI.logout();

            case CREATE_ADMIN -> SuperAdminUI.create();
            case DELETE_ADMIN -> SuperAdminUI.delete();
            case LIST_ADMIN -> SuperAdminUI.list();
            case BLOCK_ADMIN -> SuperAdminUI.block();
            case UN_BLOCK_ADMIN -> SuperAdminUI.unblock();
            case BLOCK_LIST_ADMIN -> SuperAdminUI.blockList();

            case CREATE_HR -> AdminUI.create();
            case DELETE_HR -> AdminUI.delete();
            case LIST_HR -> AdminUI.list();
            case BLOCK_HR -> AdminUI.block();
            case UN_BLOCK_HR -> AdminUI.unBlock();
            case BLOCK_LIST_HR -> AdminUI.blockList();

           case CREATE_USER -> EmployeeUI.create();
            case DELETE_USER -> EmployeeUI.delete();
            case LIST_USER -> EmployeeUI.list();
            case BLOCK_USER -> EmployeeUI.block();
            case UN_BLOCK_USER -> EmployeeUI.unBlock();
            case BLOCK_LIST_USER -> EmployeeUI.blockList();

            case CREATE_EMPLOYEE -> HrUI.create();
            case DELETE_EMPLOYEE -> HrUI.delete();
            case LIST_EMPLOYEE -> HrUI.list();
            case BLOCK_EMPLOYEE -> HrUI.block();
            case UN_BLOCK_EMPLOYEE -> HrUI.unBlock();
            case BLOCK_LIST_EMPLOYEE -> HrUI.blockList();

            case CREATE_BRANCH -> BranchUI.create();
            case UPDATE_BRANCH -> BranchUI.update();
            case DELETE_BRANCH -> BranchUI.delete();
            case LIST_BRANCH -> BranchUI.list();
            case BLOCK_BRANCH -> BranchUI.block();
            case UN_BLOCK_BRANCH -> BranchUI.unblock();
            case BLOCK_LIST_BRANCH -> BranchUI.blockList();

            case CREATE_ATM -> AtmUI.create();
            case UPDATE_ATM -> AtmUI.update();
            case DELETE_ATM -> AtmUI.delete();
            case LIST_ATM -> AtmUI.list();
            case BLOCK_ATM -> AtmUI.block();
            case UN_BLOCK_ATM -> AtmUI.unblock();
            case BLOCK_LIST_ATM -> AtmUI.blockList();

            case NEW_CARD -> BranchService.getNewCard();
            case ATM_SERVICE -> AtmService.atmService();
            case DEPOSIT -> BranchService.deposit();

            case EXIT -> {
                Print.println(Color.YELLOW, "Good bay");
                return;
            }
            default -> // TODO: 12/8/2021 do translations here
                    Print.println(Color.RED, "Wrong Choice");
        }
        main(args);
    }
}
