package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CarritoDto {
    private Integer id;
    private int idUsuario;
    private Float total;

    public  CarritoDto(){

    }
}