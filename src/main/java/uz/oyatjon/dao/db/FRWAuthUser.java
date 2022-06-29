package uz.oyatjon.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.oyatjon.configs.AppConfig;
import uz.oyatjon.models.auth.AuthUser;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FRWAuthUser extends FRWBase<AuthUser> {

    private static FRWAuthUser frwAuthUser;

    public static FRWAuthUser getInstance() {
        if (Objects.isNull(frwAuthUser)) {
            frwAuthUser = new FRWAuthUser();
        }
        return frwAuthUser;
    }


    public FRWAuthUser() {
        super(AppConfig.get("db.users.path"));
    }

    @Override
    public List<AuthUser> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<AuthUser>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<AuthUser> dataList) {
        try (FileWriter fileWriter = new FileWriter(path, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(AuthUser user){
        List<AuthUser> list=getAll();
        list.add(user);
        writeAll(list);
    }
}
