package com.alberto.tienda.repository;

import com.alberto.tienda.data.Carrito;
import com.alberto.tienda.data.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Integer> {
    //Obtener el carrito mediante el ID proporcionado

    //NOTA: El metodo FIND "PERSONALIZADO" debe concordar con el nombre del atributo de la entidad
    List<DetalleCarrito> findByIdCarrito(Carrito idCarrito);
}