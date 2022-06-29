package com.example.ui;

import com.example.dao.atm.ATMDao;
import com.example.mapper.ATMMapper;
import com.example.models.atm.ATMEntity;
import com.example.response.ResponseEntity;
import com.example.services.atm.AtmService;
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
