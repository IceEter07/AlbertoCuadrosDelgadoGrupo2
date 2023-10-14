package com.alberto.tienda.controller;


import com.alberto.tienda.data.dto.NotificacionDto;
import com.alberto.tienda.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/obtenerNotificaciones")
    public List<NotificacionDto> getNotification(){
        return notificacionService.getNotificaciones();
    }

    @PostMapping("/guardarNotificacion")
    public NotificacionDto saveNotification(@RequestBody NotificacionDto dto){
        return notificacionService.guardarNotificacion(dto);
    }
}
