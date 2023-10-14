package com.alberto.tienda.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TiendaDto {
    private Integer id;
    private Integer idUsuario;
    private String rfc;
    private String nombre;
    private String descripcion;
    private Integer rating;
    //private List<UsuarioTiendaDto> usuario_tienda
    public TiendaDto(){

    }
}
