package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter

@Entity
@Table (name = "detalle_carrito")
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

    @Column(name = "precio", nullable = false)
    private Float precio;

    @Column(name = "total", nullable = false)
    private Float total;
}
