package uz.oyatjon.dao.atm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uz.oyatjon.models.atm.ATMEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class BaseDao<E> {
    public List<E> BaseList = new ArrayList<>();
    protected static Gson gson;
    protected String path;

    public BaseDao() {
    }

    public BaseDao(String path){
        if (Objects.isNull(gson)){
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        this.path = path;
    }


    public abstract List<ATMEntity> getALL();

    public abstract void writerALL(List<ATMEntity> dataList);
}
