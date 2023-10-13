package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class DireccionDto {
    private Integer id;
    @NotNull(message = "Debe ingresar un usuario")
    @Positive(message = "Debe ingresar un usuario valido")
    private int IdUsuario;
    @NotBlank(message = "Debe ingresar un país")
    @Size(message = "Se excedió el número de caracteres en el nombre del país (max 25)", max = 25)
    @Pattern(regexp = "^[A-Za-z]+$", message = "El nombre del país debe contener solo caracteres alfabéticos")
    private String pais;
    @NotBlank(message = "Debe ingresar un estado")
    @Size(message = "Se excedió el número de caracteres en el nombre del estado (max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z]+$", message = "El nombre del estado debe contener solo caracteres alfabéticos")
    private String estado;
    @NotBlank(message = "Debe ingresar un municipio")
    @Size(message = "Se excedió el número de caracteres en el nombre del muncipio (max 50)", max = 50)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El nombre del municipio debe contener solo caracteres alfabéticos")
    private String municipio;
    @NotBlank(message = "Debe ingresar una colonia")
    @Size(message = "Se excedió el número de caracteres en el nombre de la colonia (max 50)", max = 50)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El nombre de la colonia debe contener solo caracteres alfabéticos")
    private String colonia;
    @NotBlank(message = "Debe ingresar una calle")
    @Size(message = "Se excedió el número de caracteres en el nombre de la calle(max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El nombre de la calle debe contener solo caracteres alfabéticos")
    private String calle;
    @NotNull(message = "Debe ingresar un número exterior")
    @Positive(message = "Debe ingresar un número válido")
    private Integer numeroExt;
    @NotNull(message = "Debe ingresar un número interior")
    @Positive(message = "Debe ingresar un número interior válido")
    private Integer numeroInt;

    public DireccionDto(){

    }
}
