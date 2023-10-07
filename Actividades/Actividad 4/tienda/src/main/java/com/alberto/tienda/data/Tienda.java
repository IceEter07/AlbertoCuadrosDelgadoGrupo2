package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Entity
@Table(name = "tiendas")
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tienda", nullable = false)
    private Integer idTienda;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(name = "rfc_empresa", nullable = false, unique = true, length = 15)
    private String rfc;

    @Column(name = "nombre_empresa",nullable = false ,length = 45)
    private String nombre;

    @Column(name = "descripcion_empresa", nullable = false)
    private String descripcion;

    @Column(name = "rating")
    private Integer rating;
}
