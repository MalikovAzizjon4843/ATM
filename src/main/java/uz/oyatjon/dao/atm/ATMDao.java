package uz.oyatjon.dao.atm;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import uz.oyatjon.configs.AppConfig;
import uz.oyatjon.models.atm.ATMEntity;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter

public class ATMDao extends BaseDao<ATMEntity> {
    private static ATMDao instance;

    public static ATMDao getInstance(){
        if (Objects.isNull(instance)){
            return new ATMDao();
        }
        return instance;
    }
    public ATMDao(){
        super(AppConfig.get("db.atm.path"));
    }


    @Override
    public List<ATMEntity> getALL() {
        if (BaseList.isEmpty()){
            try(FileReader fileReader = new FileReader(path);
                BufferedReader bufferedReader = new BufferedReader(fileReader)){

                String JsonData = bufferedReader.lines().collect(Collectors.joining());
                BaseList = gson.fromJson(JsonData,new TypeToken<List<ATMEntity>>(){}.getType());

            }catch (IOException e){
                Print.println(Color.RED,"File I/O exception");
                e.printStackTrace();
            }
        }
        return BaseList;
    }

    @Override
    public void writerALL(List<ATMEntity> dataList) {
        try(FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

            String JsonData = gson.toJson(dataList);
            bufferedWriter.write(JsonData);

        }catch (IOException e){
            Print.println(Color.RED,"File I/O exception");
            e.printStackTrace();
        }
    }

    public void writerALL(ATMEntity AtmEntity){
        writerALL(Collections.singletonList(AtmEntity));
    }
}
