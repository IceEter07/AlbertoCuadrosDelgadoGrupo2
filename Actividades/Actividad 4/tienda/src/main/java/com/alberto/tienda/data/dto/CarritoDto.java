package com.alberto.tienda.data.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Getter
@Setter
@Validated
public class CarritoDto {
    private Integer id;
    @NotNull(message = "Debe ingresar un usuario")
    @Positive(message = "Debe ingresar un usuario valido")
    private int idUsuario;
    private Float total;
    @Valid
    private List<ProductoAddDto> productos;

    public  CarritoDto(){

    }
}
