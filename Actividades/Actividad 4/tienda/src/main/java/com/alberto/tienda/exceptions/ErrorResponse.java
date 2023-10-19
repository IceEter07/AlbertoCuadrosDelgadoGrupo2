package com.alberto.tienda.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int codigo;
    private String estado;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.codigo = httpStatus.value();
        this.estado = httpStatus.name();
        this.message = message;
    }
}
