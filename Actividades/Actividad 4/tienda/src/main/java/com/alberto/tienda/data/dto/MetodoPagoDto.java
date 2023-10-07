package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetodoPagoDto {
    private Integer id;
    private int idUsuario;
    private String nombre;
    private String descripcion;

    public MetodoPagoDto(){

    }
}
