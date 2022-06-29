package uz.oyatjon.enums.auth;

public enum UserStatus {
    ACTIVE(0),
    NON_ACTIVE(-1),
    BLOCKED(-2);

    private final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
