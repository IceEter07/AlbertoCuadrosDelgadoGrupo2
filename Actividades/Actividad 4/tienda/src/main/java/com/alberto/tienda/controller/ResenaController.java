package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.ResenaDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.ResenasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resena")
@Validated
public class ResenaController {

    @Autowired
    ResenasService resenasService;

    @GetMapping("/obtenerResenasProducto/{idProducto}")
    public ResponseEntity<RespuestaGenerica> getCommentsProducts(@PathVariable Integer idProducto){
        RespuestaGenerica respuesta = resenasService.obtenerResenasPorProducto(idProducto);
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

    @GetMapping("/obtenerResenasUsuario/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getCommentsByUser(@PathVariable Integer idUsuario){
        RespuestaGenerica respuesta = resenasService.obtenerResenasPorUsuario(idUsuario);
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

    @PostMapping("/guardarResena")
    public ResponseEntity<RespuestaGenerica> saveComment(@Valid @RequestBody ResenaDto resenaDto){
        RespuestaGenerica respuesta = resenasService.guardarResena(resenaDto);
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
