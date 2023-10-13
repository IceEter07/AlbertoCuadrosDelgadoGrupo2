package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class RolDto {
    private Integer id;
    @NotNull(message = "Debe ingresar un nombre de rol")
    @Size(message = "Se excedió el número de caracteres en el nombre del rol (max 10)", max = 10)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Solo ingrese datos alfabéticos")
    private String nombre;

    public RolDto(){

    }
}
