package com.alberto.tienda.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PedidoDto {
    private Integer id;
    private int idUsuario;
    private int idDireccion;
    private int idPago;
    private Date fecha;
    private BigDecimal impuesto;
    private BigDecimal total;
    private String Estado;

    public PedidoDto(){

    }
}
