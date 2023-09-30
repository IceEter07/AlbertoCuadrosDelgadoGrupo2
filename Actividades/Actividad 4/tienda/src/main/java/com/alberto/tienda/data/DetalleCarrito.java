package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_carrito", nullable = false)
    private Integer idDetalleCarrito;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productos_id_producto", nullable = false)
    private Producto idProducto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrito_id_carrito", nullable = false)
    private Carrito idCarrito;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", nullable = false, precision = 11, scale = 2)
    private BigDecimal precio;

    @Column(name = "costo_envio", nullable = false, precision = 11, scale = 2)
    private BigDecimal costoEnvio;

    @Column(name = "descuento", nullable = false, precision = 11, scale = 2)
    private BigDecimal descuento;
}
