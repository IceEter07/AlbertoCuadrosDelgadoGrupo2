package com.alberto.tienda.repository;

import com.alberto.tienda.data.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
