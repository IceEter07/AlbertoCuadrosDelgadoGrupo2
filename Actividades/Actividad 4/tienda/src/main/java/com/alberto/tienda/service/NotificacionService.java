package com.alberto.tienda.service;

import com.alberto.tienda.data.Notificacion;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.NotificacionDto;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.NotificacionRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    @Autowired
    NotificacionRepository notificacionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public NotificacionDto guardarNotificacion(@Valid NotificacionDto notificacionDto){
        Notificacion nuevaNotificacion = new Notificacion();
        //Usuario user = usuarioRepository.getReferenceById(notificacionDto.getIdUsuario());
        Usuario user = usuarioRepository.findById(notificacionDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        nuevaNotificacion.setIdUsuario(user);
        nuevaNotificacion.setMensaje(notificacionDto.getMensaje());
        nuevaNotificacion.setFecha(new Date());
        notificacionRepository.save(nuevaNotificacion);
        //Obtener la fecha y el ID de la notificación.
        notificacionDto.setFecha(nuevaNotificacion.getFecha());
        notificacionDto.setId(nuevaNotificacion.getId());
        return notificacionDto;
    }

    //Obtener notificaciones por cada usuario.
    //Se deja comentado porque aun no sabemos como validar al 100% cuando no existe el usuario
    public List<NotificacionDto> getNotificacionPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));

        List<NotificacionDto> listaNotificaciones = new ArrayList<>();

        for(Notificacion notify: notificacionRepository.findByIdUsuario(user)){
            NotificacionDto notificacionDto = new NotificacionDto();
            notificacionDto.setId(notify.getId());
            notificacionDto.setIdUsuario(idUsuario);
            notificacionDto.setMensaje(notify.getMensaje());
            notificacionDto.setFecha(notify.getFecha());

            listaNotificaciones.add(notificacionDto);
        }
        return  listaNotificaciones;
    }

    //Obtener todas las notificaciones
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
