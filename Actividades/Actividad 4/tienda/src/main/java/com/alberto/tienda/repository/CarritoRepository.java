package com.alberto.tienda.repository;

import com.alberto.tienda.data.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
}
