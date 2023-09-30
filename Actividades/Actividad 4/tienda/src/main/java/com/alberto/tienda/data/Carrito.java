package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter

@Entity
@Table(name = "carrito")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito", nullable = false)
    private Integer idCarrito;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(name = "total", nullable = false, precision = 11, scale = 2)
    private BigDecimal total;

    @Column(name = "estado_carrito", nullable = false, length = 20)
    private String estado;
}
