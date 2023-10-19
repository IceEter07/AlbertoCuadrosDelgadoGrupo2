package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@Validated
public class ProductoDto {
    private Integer id;
    @NotNull(message = "Debe ingresar una categoría")
    @Positive(message = "Debe ingresar una categoría válida")
    private int idCategoria;
    @NotNull(message = "Debe ingresar una tienda")
    @Positive(message = "Debe ingresar una tienda válida")
    private int idTienda;
    @NotBlank(message = "Debe ingresar un código")
    @Size(message = "Se excedió el número de caracteres del código de barras (max 15)", max = 15)
    private String codigo;
    @NotBlank(message = "Debe ingresar el nombre del producto")
    @Size(message = "Se excedió el número de caracteres del nombre del producto(max 45)", max = 45)
    private String nombre;
    @NotNull(message = "Debe ingresar un precio para el producto")
    @Positive(message = "Debe ingresar una cantidad válida para precio")
    private Float precio;
    @NotNull(message = "Debe ingresar una cantidad de stock")
    @Positive(message = "Debe ingresar una cantidad de stock válida")
    private Integer numeroProductos;
    @NotBlank(message = "Debe proporcionar una descripción de producto")
    @Size(message = "Se excedió el número de caracteres en la descripción del producto(max 255)", max = 255)
    private String descripcion;


    public ProductoDto(){

    }


}
