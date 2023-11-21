package com.alberto.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alberto.tienda.data.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    List<Usuario> findByEmail(String email);
    Optional<Usuario> findByIdAndEmail(Integer id, String email);
}
