package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoDto {
    private Integer id;
    private int idCategoria;
    private String codigo;
    private String nombre;
    private BigDecimal precio;
    private Integer numeroProductos;
    private String descripcion;


    public ProductoDto(){

    }


}
