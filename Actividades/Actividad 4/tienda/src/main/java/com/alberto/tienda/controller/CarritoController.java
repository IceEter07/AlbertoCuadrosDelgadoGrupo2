package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.data.dto.ProductoAddDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
@Validated
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @GetMapping("/obtenerCarritoUsuario/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getCarDetails(@Valid @PathVariable Integer idUsuario){
        RespuestaGenerica respuesta = carritoService.getCarritoPorUsuario(idUsuario);
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

    @PostMapping("/guardarCarrito")
    public ResponseEntity<RespuestaGenerica> saveCar(@Valid @RequestBody CarritoDto carritoDto){
        RespuestaGenerica respuesta = carritoService.guardarCarrito(carritoDto);
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
