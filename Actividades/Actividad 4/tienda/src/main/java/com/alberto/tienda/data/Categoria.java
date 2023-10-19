package com.alberto.tienda.data;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Integer idCategoria;

    @Column(name = "nombre", nullable = false, length = 45, unique = true)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}
