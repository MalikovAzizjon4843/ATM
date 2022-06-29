package uz.oyatjon.models.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oyatjon.enums.atm.Status;
import uz.oyatjon.enums.card.CardType;
import uz.oyatjon.models.Auditable;

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
