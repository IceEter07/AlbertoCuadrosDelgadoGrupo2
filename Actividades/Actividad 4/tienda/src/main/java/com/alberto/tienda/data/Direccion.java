package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Entity
@Table(name = "direccion")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(name = "pais", length = 25)
    private String pais;

    @Column(name = "estado", length = 45)
    private String estado;

    @Column(name = "municipio", length = 50)
    private String municipio;

    @Column(name = "colonia", length = 50)
    private String colonia;

    @Column(name = "calle", length = 45)
    private String calle;

    @Column(name = "num_ext")
    private Integer numExt;

    @Column(name = "num_int")
    private Integer numInt;
}
