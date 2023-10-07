package com.alberto.tienda.repository;

import com.alberto.tienda.data.Carrito;
import com.alberto.tienda.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    //Metodo personalizado para bucar el carrito por medio del ID del usuario y del campo isActive
    List<Carrito> findByIdUsuarioAndEstado(Usuario idUsuario, Boolean estado);
}
