package com.alberto.tienda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    //Este es el método que maneja la excepción de validación.
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        //Se obtiene el primer error de validación encontrado en la excepción
        FieldError fieldError = ex.getBindingResult().getFieldError();
        // Se obtiene el mensaje de error predeterminado asociado al error de validación. El mensaje quedó definido en los DTO.
        String errorMessage = fieldError.getDefaultMessage();
        //Se retorna el error a traves de la clse ErrorResponse
        return new ErrorResponse(errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex){
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequestException(BadRequestException ex){
        return new ErrorResponse(ex.getMessage());
    }
}
