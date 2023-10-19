package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Validated
public class PedidoDto {
    private Integer id;
    @NotNull(message = "Debe ingresar un usuario")
    @Positive(message = "Debe ingresar un usuario valido")
    private int idUsuario;
    @NotNull(message = "Debe ingresar una dirección")
    @Positive(message = "Debe ingresar una dirección válida")
    private int idDireccion;
    @NotNull(message = "Debe ingresar un método de pago")
    @Positive(message = "Debe ingresar un método de pago valido")
    private int idPago;
    private Date fecha;
    private Float impuesto;
    private Float total;

    public PedidoDto(){

    }
}
