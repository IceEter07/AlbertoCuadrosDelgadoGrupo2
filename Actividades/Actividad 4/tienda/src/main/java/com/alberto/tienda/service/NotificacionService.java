package com.alberto.tienda.service;

import com.alberto.tienda.data.Notificacion;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.NotificacionDto;
import com.alberto.tienda.repository.NotificacionRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    NotificacionRepository notificacionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public NotificacionDto guardarNotificacion(NotificacionDto notificacionDto){
        Notificacion nuevaNotificacion = new Notificacion();
        Usuario user = buscarUsuarioPorId(notificacionDto.getIdUsuario());
        nuevaNotificacion.setIdUsuario(user);
        nuevaNotificacion.setMensaje(notificacionDto.getMensaje());
        nuevaNotificacion.setFecha(new Date());
        notificacionRepository.save(nuevaNotificacion);
        //Obtener la fecha y el ID de la notificación.
        notificacionDto.setFecha(nuevaNotificacion.getFecha());
        notificacionDto.setId(nuevaNotificacion.getId());
        return notificacionDto;
    }

    private Usuario buscarUsuarioPorId(int idUsuario){
        Usuario user = usuarioRepository.getReferenceById(idUsuario);
        return user;
    }

    public List<NotificacionDto> getNotificaciones(){
        List<NotificacionDto> listaNotificaciones = new ArrayList<>();

        for(Notificacion notify: notificacionRepository.findAll()){
            NotificacionDto notificacionDto = new NotificacionDto();
            notificacionDto.setId(notify.getId());
            //Obtener el ID del usuario (FK)
            Usuario idUsuario = notify.getIdUsuario();
            notificacionDto.setIdUsuario(idUsuario.getId());
            notificacionDto.setMensaje(notify.getMensaje());
            notificacionDto.setFecha(notify.getFecha());

            listaNotificaciones.add(notificacionDto);
        }
        return  listaNotificaciones;
    }
}
