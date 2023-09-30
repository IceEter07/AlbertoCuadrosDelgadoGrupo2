package com.alberto.tienda.repository;

import com.alberto.tienda.data.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiendaRepository extends JpaRepository<Tienda, Integer> {
}
