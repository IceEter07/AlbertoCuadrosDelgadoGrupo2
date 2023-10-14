package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter

@Entity
@Table(name = "detalles_pedidos")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalles_pedido", nullable = false)
    private Integer idDetallePedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productos_id_producto", nullable = false)
    private Producto idProducto;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedidos_id_pedido", nullable = false)
    private Pedido idPedido;

    @Column(name = "cantidad", nullable = false)
    private  Integer cantidad;

    @Column(name = "precio", nullable = false, precision = 11, scale = 2)
    private Float precio;

    @Column(name = "total", nullable = false)
    private Float total;
}
