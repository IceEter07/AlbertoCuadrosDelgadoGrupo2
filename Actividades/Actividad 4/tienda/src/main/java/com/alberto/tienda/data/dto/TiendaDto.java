package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TiendaDto {
    private Integer id;
    private String rfc;
    private String nombre;
    private String descripcion;
    private Integer rating;

    public TiendaDto(){

    }
}
