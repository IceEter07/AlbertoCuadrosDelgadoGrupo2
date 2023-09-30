package com.alberto.tienda.repository;

import com.alberto.tienda.data.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
