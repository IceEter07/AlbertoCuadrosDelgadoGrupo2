package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class MetodoPagoDto {
    private Integer id;
    @NotNull(message = "Debe ingresar un usuario")
    @Positive(message = "Debe ingresar un usuario valido")
    private int idUsuario;
    @NotNull(message = "Debe ingresar el nombre del método de pago")
    @Size(message = "Se excedió el número de caracteres en el nombre del método de pago (max 45)", max = 45)
    private String nombre;
    @NotNull(message = "Debe ingresar una descripción del método de pago")
    @Size(message = "Se excedió el número de caracteres en el nombre de la descripción (max 45)", max = 45)
    private String descripcion;

    public MetodoPagoDto(){

    }
}
