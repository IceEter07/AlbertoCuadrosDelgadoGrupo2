package com.alberto.tienda.controller;


import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.NotificacionDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacion")
@Validated
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/obtenerNotificaciones")
    public ResponseEntity<RespuestaGenerica> getNotification(){
        RespuestaGenerica respuesta = notificacionService.getNotificaciones();
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

    @GetMapping("/obtenerNotificacionUsuario/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getNotificationByUser(@PathVariable Integer idUsuario){
        RespuestaGenerica respuesta = notificacionService.getNotificacionPorUsuario(idUsuario);
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

//    @PostMapping("/guardarNotificacion")
//    public NotificacionDto saveNotification(@Valid @RequestBody NotificacionDto dto){
//        return notificacionService.guardarNotificacion(dto);
//    }

    @PostMapping("/guardarNotificacion")
    public ResponseEntity<RespuestaGenerica> saveNotification(@Valid @RequestBody NotificacionDto notificacionDto){
        RespuestaGenerica respuesta = notificacionService.guardarNotificacion(notificacionDto);
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
