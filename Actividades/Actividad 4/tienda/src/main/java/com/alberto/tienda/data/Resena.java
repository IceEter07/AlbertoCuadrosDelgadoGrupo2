package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "resenas")
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resenas", nullable = false)
    private Integer idResena;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id_usuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productos_id_producto", nullable = false)
    private Producto idProducto;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha")
    private Date fecha;
}
