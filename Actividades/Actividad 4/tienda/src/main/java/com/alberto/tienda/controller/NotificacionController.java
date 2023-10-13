package com.alberto.tienda.controller;


import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.NotificacionDto;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@Validated
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/obtenerNotificaciones")
    public List<NotificacionDto> getNotification(){
        return notificacionService.getNotificaciones();
    }

    @GetMapping("/obtenerNotificacionUsuario/{idUsuario}")
    public List<NotificacionDto> getNotificationByUser(@PathVariable Integer idUsuario){
        return notificacionService.getNotificacionPorUsuario(idUsuario);
    }

    @PostMapping("/guardarNotificacion")
    public NotificacionDto saveNotification(@Valid @RequestBody NotificacionDto dto){
        return notificacionService.guardarNotificacion(dto);
    }
}
