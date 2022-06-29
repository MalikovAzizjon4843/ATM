package uz.oyatjon.enums.card;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CardType {
    UZCARD("8600"),
    HUMO("9860"),
    MASTER_CARD("4853"),
    VISA("4735"),
    COBAGE("6330"),
    UNION_PAY("6262");
    private final String code;
}
