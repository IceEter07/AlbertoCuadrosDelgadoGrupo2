package com.example.crudjava.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

//Esta clase ayudará a que las excepciones sean más fáciles de manipular
@EqualsAndHashCode(callSuper = true)
@Data
public class taskException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;

    public taskException(String code, HttpStatus httpStatus, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
