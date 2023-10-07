package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CarritoDto {
    private Integer id;
    private int idUsuario;
    private Float total;
    private List<ProductoAddDto> productos;

    public  CarritoDto(){

    }
}
