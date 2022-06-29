package uz.oyatjon.models.atm;

import lombok.*;
import uz.oyatjon.enums.atm.CassetteStatus;



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
