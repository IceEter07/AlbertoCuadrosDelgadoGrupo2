package com.alberto.tienda.repository;

import com.alberto.tienda.data.Notificacion;
import com.alberto.tienda.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByIdUsuario(Usuario idUsuario);
}
