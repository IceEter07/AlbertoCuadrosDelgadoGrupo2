package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.PedidoDto;
import com.alberto.tienda.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@Validated
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/obtenerPedidos/{idUsuario}")
    public List<PedidoDto> getOrderByUser(@PathVariable Integer idUsuario){

        return pedidoService.getPedidosPorUsuario(idUsuario);
    }

    @GetMapping("/obtenerPedidosActivos/{idUsuario}")
    public List<PedidoDto> getOrderByUserAndStatus(@PathVariable Integer idUsuario){

        return pedidoService.getPedidosPorUsuarioYEstadoActivo(idUsuario);
    }

    @PostMapping("/guardarPedido")
    public PedidoDto saveOrder(@Valid @RequestBody PedidoDto dto){
        return pedidoService.savePedido(dto);
    }

    //EndPoint creado para experimentar la actualización de un campo.
    @PutMapping("/actualizarEstadoPedido/{idPedido}")
    public String updateOrderEstatus(@Valid @PathVariable Integer idPedido){
        return pedidoService.putEstadoPedido(idPedido);
    }
}
