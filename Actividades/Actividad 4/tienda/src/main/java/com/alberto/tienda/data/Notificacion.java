package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @Column(name = "id_notificacion", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fecha", nullable = false)
    private Date fecha;
}
