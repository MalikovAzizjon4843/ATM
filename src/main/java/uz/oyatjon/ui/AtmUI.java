package uz.oyatjon.ui;

import uz.oyatjon.dao.atm.ATMDao;
import uz.oyatjon.mapper.ATMMapper;
import uz.oyatjon.models.atm.ATMEntity;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.atm.AtmService;
import uz.jl.utils.Input;

public class AtmUI {
    static AtmService service = AtmService.getInstance(ATMDao.getInstance(), ATMMapper.getInstance());


    public static void create() {
        ATMEntity myAtm = new ATMEntity();
        String name = Input.getStr("Atm Name: ");
        myAtm.setName(name);
        ResponseEntity<String> response = service.create(myAtm);
        BaseUI.showResponse(response);
    }

    public static void update() {
        String name = Input.getStr("Atm Name: ");
        ResponseEntity<String> response = service.update(name);
        BaseUI.showResponse(response);
    }


    public static void delete() {
        String name = Input.getStr("Atm Name: ");
        ResponseEntity<String> response = service.delete(name);
        BaseUI.showResponse(response);
    }

    public static void list() {
        AtmService.list();
    }

    public static void block() {
        AtmService.block();
    }

    public static void unblock() {
        AtmService.unblock();
    }

    public static void blockList() {
        AtmService.blockList();
    }
}
