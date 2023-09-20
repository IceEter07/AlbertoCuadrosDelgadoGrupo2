package com.example.crudjava.controllers;

import com.example.crudjava.dao.errorDao;
import com.example.crudjava.exception.taskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class adviceController {
    @ExceptionHandler(value = taskException.class)
    public ResponseEntity<errorDao> taskExceptionHandler(taskException ex){
        errorDao error = errorDao.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getHttpStatus());

    }
}
