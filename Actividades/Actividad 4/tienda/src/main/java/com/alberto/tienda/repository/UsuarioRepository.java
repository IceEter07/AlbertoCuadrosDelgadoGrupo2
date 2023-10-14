package com.alberto.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alberto.tienda.data.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
}
