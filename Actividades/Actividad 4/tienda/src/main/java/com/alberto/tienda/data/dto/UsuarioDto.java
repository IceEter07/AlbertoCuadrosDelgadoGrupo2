package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UsuarioDto implements Serializable {
    Integer id;
    String nombre;
    String apPat;
    String apMat;
    String telefono;
    String email;
    String pass;

    public UsuarioDto(){

    }

}
