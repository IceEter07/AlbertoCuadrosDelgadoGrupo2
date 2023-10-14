package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificacionDto {
    private Integer id;
    private int idUsuario;
    private String mensaje;
    private Date fecha;

    public NotificacionDto(){

    }
}