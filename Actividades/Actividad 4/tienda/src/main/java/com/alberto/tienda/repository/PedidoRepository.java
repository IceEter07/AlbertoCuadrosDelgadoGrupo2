package com.alberto.tienda.repository;

import com.alberto.tienda.data.Pedido;
import com.alberto.tienda.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByIdUsuario(Usuario idUsuario);
    List<Pedido> findByIdUsuarioAndEstado(Usuario idUsuario, Boolean estado);
    List<Pedido> findByIdPedidoAndEstado(Integer idPedido, Boolean estado);
}
