package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarritoDto {
    private Integer id;
    private int idUsuario;
    private BigDecimal total;
    private String estado;

    public  CarritoDto(){

    }
}
