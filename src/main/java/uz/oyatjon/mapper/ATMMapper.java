package uz.oyatjon.mapper;

import uz.oyatjon.dto.atm.ATMDto;
import uz.oyatjon.models.atm.ATMEntity;

import java.util.Objects;

public class ATMMapper extends BaseMapper<ATMEntity, ATMDto> {

    private static ATMMapper mapper;

    public static ATMMapper getInstance() {
        if (Objects.isNull(mapper)) {
            mapper = new ATMMapper();
        }
        return mapper;
    }
    @Override
    ATMEntity to(ATMDto atmDto) {
        return null;
    }

    @Override
    ATMDto from(ATMEntity atmEntity) {
        return null;
    }
}
