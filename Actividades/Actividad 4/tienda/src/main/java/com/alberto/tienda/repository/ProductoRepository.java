package com.alberto.tienda.repository;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByidTienda(Tienda idTienda);
    List<Producto> findByidCategoria(Categoria idCategoria);
}
