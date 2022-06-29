package com.example.models.atm;

import com.example.enums.atm.CassetteStatus;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cassette {
    private String currencyValue;
    private CassetteStatus status;
    private Integer currencyCount;
}
