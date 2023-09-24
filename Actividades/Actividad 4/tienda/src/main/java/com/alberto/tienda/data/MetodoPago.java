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

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "numero_tarjeta", length = 50)
    private String numTarjeta;

    @Column(name = "vencimiento_mes", length = 2)
    private String vencimiento_mes;

    @Column(name = "vencimiento_ano", length = 2)
    private String vencimiento_ano;

    @Column(name = "cvv", length = 4)
    private String cvv;

    @Column(name = "is_active", length = 2)
    private String isActive;

}
