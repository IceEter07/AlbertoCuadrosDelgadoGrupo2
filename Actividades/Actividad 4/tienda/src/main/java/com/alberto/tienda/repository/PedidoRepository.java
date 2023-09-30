package com.alberto.tienda.repository;

import com.alberto.tienda.data.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
