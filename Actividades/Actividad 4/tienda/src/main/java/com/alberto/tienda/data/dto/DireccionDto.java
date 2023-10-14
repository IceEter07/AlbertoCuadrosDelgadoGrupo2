package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionDto {
    private Integer id;
    private int IdUsuario;
    private String pais;
    private String estado;
    private String municipio;
    private String colonia;
    private String calle;
    private Integer numeroExt;
    private Integer numeroInt;

    public DireccionDto(){

    }
}
