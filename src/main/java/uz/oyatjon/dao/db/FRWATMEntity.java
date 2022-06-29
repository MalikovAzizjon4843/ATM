package uz.oyatjon.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.oyatjon.configs.AppConfig;
import uz.oyatjon.models.atm.ATMEntity;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FRWATMEntity extends FRWBase<ATMEntity> {
    private static FRWATMEntity frwATM;

    public static FRWATMEntity getInstance() {
        if (Objects.isNull(frwATM)) {
            frwATM = new FRWATMEntity();
        }
        return frwATM;
    }


    public FRWATMEntity() {
        super(AppConfig.get("db.atm.path"));
    }

    @Override
    public List<ATMEntity> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<ATMEntity>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<ATMEntity> dataList) {
        try (FileWriter fileWriter = new FileWriter(path, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(ATMEntity ATM) {
        List<ATMEntity> list = getAll();
        list.add(ATM);
        writeAll(list);
    }
}
