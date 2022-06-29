package uz.oyatjon.exceptions;

import lombok.Getter;
import uz.oyatjon.enums.http.HttpStatus;


@Getter
public class APIException extends Exception {
    private final Integer code;

    private APIException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public APIException(String message, HttpStatus status) {
        this(message, status.getCode());
    }
}
