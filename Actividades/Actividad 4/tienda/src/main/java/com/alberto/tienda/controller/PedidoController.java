package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.PedidoDto;
import com.alberto.tienda.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/obtenerPedidos")
    public List<PedidoDto> getPedido(){
        return pedidoService.getPedidos();
    }

    @PostMapping("/guardarPedido")
    public PedidoDto savePedido(@RequestBody PedidoDto dto){
        return pedidoService.savePedido(dto);
    }
}
