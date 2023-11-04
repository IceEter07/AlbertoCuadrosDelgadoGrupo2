package com.alberto.tienda.repository;

import com.alberto.tienda.data.Direccion;
import com.alberto.tienda.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findByIdUsuario (Usuario idUsuario);
    List<Direccion> findByIdAndIdUsuario (Integer id,Usuario idUsuario);

}
