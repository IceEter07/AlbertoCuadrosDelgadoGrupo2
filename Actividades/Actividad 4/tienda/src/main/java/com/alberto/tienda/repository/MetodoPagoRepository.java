package com.alberto.tienda.repository;

import com.alberto.tienda.data.Direccion;
import com.alberto.tienda.data.MetodoPago;
import com.alberto.tienda.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    List<MetodoPago> findByIdUsuario(Usuario idUsuario);

    List<MetodoPago> findByIdPagoAndIdUsuario(Integer idPago, Usuario idUsuario);


}
