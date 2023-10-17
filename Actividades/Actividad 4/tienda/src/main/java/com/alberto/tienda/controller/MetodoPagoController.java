package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.MetodoPagoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.MetodoPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodoPago")
@Validated
public class MetodoPagoController {

    @Autowired
    MetodoPagoService metodoPagoService;

    @GetMapping("/obtenerMetodoPagoUsuario/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getPayMethodsByUser(@PathVariable Integer idUsuario){
        RespuestaGenerica respuesta = metodoPagoService.getMetodosPagoPorUsuario(idUsuario);
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

    @PostMapping("/guardarMetodoPago")
    public ResponseEntity<RespuestaGenerica> savePayMethod(@Valid @RequestBody MetodoPagoDto metodoPagoDto){
        RespuestaGenerica respuesta = metodoPagoService.guardarMetodoPago(metodoPagoDto);
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
