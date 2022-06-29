package com.example.models.card;

import com.example.enums.atm.Status;
import com.example.models.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.enums.card.CardType;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cards extends Auditable {
    private String pan;
    private String expiry;
    private String password;
    private CardType type;
    private Status status;
    private BigDecimal balance;
    private String bankId;
    private String holderId;
    private  String number;


}
