package uz.oyatjon.enums.branch;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum BranchStatus {
    BLOCKED(-1),
    ACTIVE(0);

    private final int code;
}
