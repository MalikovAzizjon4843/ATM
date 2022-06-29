package com.example.mapper;

import com.example.dto.atm.ATMDto;
import com.example.models.atm.ATMEntity;

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
