package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResenasDto {
    private Integer id;
    private int idUsuario;
    private int idProducto;
    private String comentario;
    private Date fecha;

    public ResenasDto(){

    }

}
