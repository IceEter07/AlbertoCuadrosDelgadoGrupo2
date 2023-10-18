package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.PedidoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@Validated
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/obtenerHistorialPedidos/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getOrderByUser(@PathVariable Integer idUsuario){
        RespuestaGenerica respuesta = pedidoService.getPedidosPorUsuario(idUsuario);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status= HttpStatus.BAD_REQUEST;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

    @GetMapping("/obtenerPedidosActivos/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getOrderByUserAndStatus(@PathVariable Integer idUsuario){
        RespuestaGenerica respuesta = pedidoService.getPedidosPorUsuarioYEstadoActivo(idUsuario);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status= HttpStatus.BAD_REQUEST;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

    @PostMapping("/guardarPedido")
    public ResponseEntity<RespuestaGenerica> saveOrder(@Valid @RequestBody PedidoDto pedidoDto){
        RespuestaGenerica respuesta = pedidoService.savePedido(pedidoDto);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status= HttpStatus.BAD_REQUEST;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

    //EndPoint creado para experimentar la actualización de un campo.
    @PutMapping("/actualizarEstadoPedido/{idPedido}")
    public ResponseEntity<RespuestaGenerica> updateOrderEstatus(@Valid @PathVariable Integer idPedido){
        RespuestaGenerica respuesta = pedidoService.putEstadoPedido(idPedido);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status= HttpStatus.BAD_REQUEST;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }
}
