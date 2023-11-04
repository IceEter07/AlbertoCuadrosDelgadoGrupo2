package com.alberto.tienda.repository;

import com.alberto.tienda.data.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    List<Rol> findByNombre(String nombre);
}
