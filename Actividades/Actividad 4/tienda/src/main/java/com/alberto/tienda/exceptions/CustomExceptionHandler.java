package com.alberto.tienda.exceptions;

import com.alberto.tienda.utils.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    //Este es el método que maneja la excepción de validación.
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        //Se obtiene el primer error de validación encontrado en la excepción
        FieldError fieldError = ex.getBindingResult().getFieldError();
        // Se obtiene el mensaje de error predeterminado asociado al error de validación. El mensaje quedó definido en los DTO.
        String errorMessage = fieldError.getDefaultMessage();
        //Se retorna el error a traves de la clse ErrorResponse
        return new ResponseEntity<>(new ErrorResponse(status, errorMessage), status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();

        return new ResponseEntity<>(new ErrorResponse(status, message), status);
    }


    //Esta excepcion permite lanzar un error cuando se manda un parametro distinto al que se esperaba. No es necesario invocarla en otra clase
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMismatchException(MethodArgumentTypeMismatchException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String tipoRequerido = ex.getRequiredType().getSimpleName();
        String tipoIntroducido = ex.getParameter().getParameterName();
        String message = Constantes.MENSAJE_EXCEPCION_TIPO_PARAMETRO_INCORRECTO + tipoIntroducido +Constantes.MENSAJE_EXCEPCION_TIPO_PARAMETRO_INCORRECTO_COMPLEMENTO+tipoRequerido;

        return new ResponseEntity<>(new ErrorResponse(status, message), status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorResponse(status,ex.getMessage()), status);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return  new ResponseEntity<>(new ErrorResponse(status, ex.getMessage()), status);
    }
}
