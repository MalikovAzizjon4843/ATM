package uz.oyatjon.dao.auth.hr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.oyatjon.dao.atm.BaseDao;
import uz.oyatjon.models.atm.ATMEntity;
import uz.oyatjon.models.auth.AuthUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HRDao extends BaseDao<AuthUser> {
    private static final Type hrListType = new TypeToken<List<AuthUser>>() {
    }.getType();

    public static void create(AuthUser hr) {
        List<AuthUser> entities = list();
        entities.add(hr);
        try (FileWriter fileWriter = new FileWriter("src/main/resources/db/hrs.json"); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonData = new Gson().toJson(entities);
            bufferedWriter.write(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<AuthUser> list() {
        List<AuthUser> entities = new ArrayList<>();
        try (FileReader reader = new FileReader("src/main/resources/db/hrs.json"); BufferedReader bufferedReader = new BufferedReader(reader)) {
            String jsonData = bufferedReader.lines().collect(Collectors.joining());
            entities = new Gson().fromJson(jsonData, hrListType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }


    public HRDao(String path) {
        super(path);
    }

    public HRDao() {
        super();
    }

    @Override
    public List<ATMEntity> getALL() {
        return null;
    }

    @Override
    public void writerALL(List<ATMEntity> dataList) {

    }
}
