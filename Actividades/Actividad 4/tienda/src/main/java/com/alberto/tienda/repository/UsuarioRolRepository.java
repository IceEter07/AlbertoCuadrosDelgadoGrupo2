package com.alberto.tienda.repository;

import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Integer> {
}
