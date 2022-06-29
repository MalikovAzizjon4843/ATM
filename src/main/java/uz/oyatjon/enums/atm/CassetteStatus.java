package uz.oyatjon.enums.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CassetteStatus {
    BLOCKED(-1),
    ACTIVE(0);
    private final int code;
}
