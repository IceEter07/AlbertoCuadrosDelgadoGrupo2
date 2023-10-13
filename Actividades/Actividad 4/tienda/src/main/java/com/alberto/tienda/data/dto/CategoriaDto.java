package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDto {
    private Integer id;
    @NotBlank(message = "Debe ingresar el nombre de una categoría")
    @Size(message = "Se excedió el número de caracteres en el nombre de la categoría (max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "El nombre de la categoría no cumple con el formato establecido. No se aceptan caracteres especiales.")
    private String nombre;
    @Size(message = "Se excedió el número de caracteres la descripción de la categoría (max 255)", max = 255)
    @NotBlank(message = "Debe colocar una breve descripción")
    private String descripcion;

    public CategoriaDto(){

    }
}
