package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id_categoria", nullable = false)
    private Categoria idCategoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tiendas_id_tienda", nullable = false)
    private Tienda idTienda;

    @Column(name = "codigo", nullable = false, length = 15)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "precio_venta", nullable = false)
    private Float precioVenta;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "descripcion", nullable = false)
    private  String descripcion;
}
