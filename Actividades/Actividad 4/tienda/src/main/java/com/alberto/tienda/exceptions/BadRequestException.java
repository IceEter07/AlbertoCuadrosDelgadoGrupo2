package com.alberto.tienda.exceptions;


public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg){
        super(msg);
    }
}
