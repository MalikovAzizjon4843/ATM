package uz.oyatjon.exceptions;


public class APIRuntimeException extends RuntimeException {
    private final Integer code;

    public APIRuntimeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public APIRuntimeException(String message) {
        super(message);
        this.code = 200;
    }
}
