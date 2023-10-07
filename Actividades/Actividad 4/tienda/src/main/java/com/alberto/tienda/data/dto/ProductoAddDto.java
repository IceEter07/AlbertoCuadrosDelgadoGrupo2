package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoAddDto {
    private int idProducto;
    private int cantidad;
    private double precioUnitario;
    private double total;

    public ProductoAddDto(){

    }
}
