package com.alberto.tienda.repository;

import com.alberto.tienda.data.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByNombre(String nombre);
}
