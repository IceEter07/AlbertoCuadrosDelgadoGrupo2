package com.alberto.tienda.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
public class TiendaDto {
    private Integer id;
    @NotNull(message = "Debe ingresar un usuario")
    @Positive(message = "Debe ingresar un usuario valido")
    private Integer idUsuario;
    @NotBlank(message = "Debe ingresar un RFC")
    @Size(message = "El número de caracteres en el RFC no es el correcto", max = 13, min = 13)
    private String rfc;
    @NotNull(message = "Debe ingresar el nombre de la empresa")
    @Size(message = "Se excedió el número de caracteres en el nombre de la empresa (max 45)", max = 45)
    private String nombre;
    @NotNull(message = "Debe ingresar una descripción de la empresa")
    @Size(message = "Se excedió el número de caracteres en el nombre del país (max 255)", max = 255)
    private String descripcion;
    @NotNull(message = "Debe ingresar el rating")
    @Positive(message = "Debe ingresar un rating valido")
    private Integer rating;
    //private List<UsuarioTiendaDto> usuario_tienda
    public TiendaDto(){

    }
}
