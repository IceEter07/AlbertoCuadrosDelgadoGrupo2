package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.service.TiendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tienda")
@Validated
public class TiendaController {

    @Autowired
    TiendaService tiendaService;

    @GetMapping("/obtenerTiendas")
    public ResponseEntity<RespuestaGenerica> getShop(){
        RespuestaGenerica respuesta = tiendaService.getTiendas();
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

    @GetMapping("/obtenerTiendasUsuario/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getShopByUser(@PathVariable Integer idUsuario){
        RespuestaGenerica respuesta = tiendaService.getTiendasPorUsuario(idUsuario);
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

    @PostMapping("/guardarTienda")
    public ResponseEntity<RespuestaGenerica> saveShop(@Valid @RequestBody TiendaDto tiendaDto){
        RespuestaGenerica respuesta = tiendaService.guardarTienda(tiendaDto);
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
