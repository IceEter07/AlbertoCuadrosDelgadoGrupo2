package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class ProductoAddDto {
    @NotNull(message = "Debe ingresar un id de producto válido")
    @Positive(message = "Debe ingresar un id de producto válido")
    private int idProducto;
    @NotNull(message = "Debe ingresar una cantidad")
    @Positive(message = "Debe ingresar una cantidad válida")
    private int cantidad;
    private double precioUnitario;
    private double total;

    public ProductoAddDto(){

    }
}
