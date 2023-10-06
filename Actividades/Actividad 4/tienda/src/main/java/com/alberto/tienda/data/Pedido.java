package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id_usuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "direccion_id_direccion", nullable = false)
    private Direccion idDireccion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metodos_pago_id_pago", nullable = false)
    private MetodoPago idMetodoPago;

    @Column(name = "fecha_pedido", nullable = false)
    private Date fechaPedido;

    @Column(name = "impuesto", nullable = false, precision = 4, scale = 2)
    private BigDecimal impuesto;

    @Column(name = "total", nullable = false, precision = 11, scale = 2)
    private BigDecimal total;

    @Column(name = "isActive", nullable = false, length = 20)
    private String estado;
}
