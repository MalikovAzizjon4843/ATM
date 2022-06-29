package uz.oyatjon.ui;

import uz.oyatjon.response.ResponseEntity;
import uz.jl.utils.Color;
import uz.jl.utils.Print;


public abstract class BaseUI {
    protected static void showResponse(ResponseEntity response) {
        if (response.getStatus().equals(200))
            Print.println(Color.GREEN, response.getData());
        else
            Print.println(Color.RED, response.getData());
    }
}
