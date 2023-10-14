package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Entity
@Table(name = "metodos_pago")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago", nullable = false)
    private Integer idPago;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(name = "nombre",length = 45, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 45, nullable = false)
    private String descripcion;

}
