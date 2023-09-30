package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Entity
@Table(name = "usuarios_tiendas")
public class UsuarioTienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuarios_tiendas", nullable = false)
    private Integer idUsuarioTienda;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id_usuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tiendas_id_tienda", nullable = false)
    private Tienda idTienda;
}
