package com.alberto.tienda.repository;

import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Resena;
import com.alberto.tienda.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    List<Resena> findByIdUsuario(Usuario idUsuario);

    List<Resena> findByIdProducto(Producto idProducto);
}
