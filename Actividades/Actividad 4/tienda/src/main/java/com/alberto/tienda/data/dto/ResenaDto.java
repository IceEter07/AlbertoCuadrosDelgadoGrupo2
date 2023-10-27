package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Getter
@Setter
@Validated
public class ResenaDto {
    private Integer id;
    @NotNull(message = "Debe ingresar un usuario")
    @Positive(message = "Debe ingresar un usuario valido")
    private int idUsuario;
    @NotNull(message = "Debe ingresar un producto válido")
    @Positive(message = "Debe ingresar un usuario válido")
    private int idProducto;
    @NotBlank(message = "Debe ingresar un país")
    @Size(message = "Se excedió el número de caracteres del comentario (max 255)", max = 255)
    private String comentario;
    private Date fecha;

    public ResenaDto(){

    }

}
