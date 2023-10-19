package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.DireccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direccion")
@Validated
public class DireccionController {

    @Autowired
    DireccionService direccionService;

    @GetMapping("/obtenerDirecciones")
    public ResponseEntity<RespuestaGenerica> getAddress(){
        RespuestaGenerica respuesta = direccionService.getDirecciones();
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status = HttpStatus.NOT_FOUND;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

    @GetMapping("/obtenerDireccionUsuario/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getAdrresByUser(@PathVariable Integer idUsuario){
        RespuestaGenerica respuesta = direccionService.getDireccionPorUsuario(idUsuario);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status = HttpStatus.NOT_FOUND;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

    @PostMapping("/guardarDireccion")
    public ResponseEntity<RespuestaGenerica> saveAddress(@Valid @RequestBody DireccionDto direccionDto){
        RespuestaGenerica respuesta = direccionService.guardarDireccion(direccionDto);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status = HttpStatus.NOT_FOUND;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

}
