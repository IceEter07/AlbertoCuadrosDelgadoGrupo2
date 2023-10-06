package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDto {
    private Integer id;
    private String nombre;
    private String descripcion;

    public CategoriaDto(){

    }
}
