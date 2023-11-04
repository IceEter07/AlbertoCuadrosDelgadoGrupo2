package com.alberto.tienda.repository;

import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TiendaRepository extends JpaRepository<Tienda, Integer> {
    List<Tienda> findByRfc(String rfc);
    List<Tienda> findByIdUsuario(Usuario idUsuario);
    List<Tienda> findByNombre (String nombre);
}
