package uz.oyatjon.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.oyatjon.configs.AppConfig;
import uz.oyatjon.models.branch.Branch;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public final class FRWBranch extends FRWBase<Branch> {

    private static FRWBranch frwBranch;

    public static FRWBranch getInstance() {
        if (Objects.isNull(frwBranch)) {
            frwBranch = new FRWBranch();
        }
        return frwBranch;
    }

    public FRWBranch() {
        super(AppConfig.get("db.branch.path"));
    }

    @Override
    public List<Branch> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<Branch>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<Branch> dataList) {
        try (FileWriter fileWriter = new FileWriter(path, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(Branch branch) {
        List<Branch> all = getAll();
        all.add(branch);
        writeAll(all);
    }
}
