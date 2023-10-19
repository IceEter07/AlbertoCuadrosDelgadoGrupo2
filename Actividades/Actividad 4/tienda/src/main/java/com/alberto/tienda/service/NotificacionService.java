package com.alberto.tienda.service;

import com.alberto.tienda.data.Notificacion;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.NotificacionDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.NotificacionRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public RespuestaGenerica guardarNotificacion(@Valid NotificacionDto notificacionDto){
        //Usuario user = usuarioRepository.getReferenceById(notificacionDto.getIdUsuario());
        Usuario user = usuarioRepository.findById(notificacionDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        RespuestaGenerica respuesta = new RespuestaGenerica();

        Notificacion nuevaNotificacion = new Notificacion();
        //Usuario getIdUser = usuarioRepository.getReferenceById(notificacionDto.getIdUsuario());

        nuevaNotificacion.setIdUsuario(user);
        nuevaNotificacion.setMensaje(notificacionDto.getMensaje());
        nuevaNotificacion.setFecha(new Date());
        notificacionRepository.save(nuevaNotificacion);
        respuesta.setExito(true);
        respuesta.getDatos().add(notificacionDto);
        respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);

        //Obtener la fecha y el ID de la notificación.
        notificacionDto.setFecha(nuevaNotificacion.getFecha());
        notificacionDto.setId(nuevaNotificacion.getId());
        return respuesta;
    }

    //Obtener notificaciones por cada usuario.
    public RespuestaGenerica getNotificacionPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        List<Notificacion> notificaciones = notificacionRepository.findByIdUsuario(user);
        if (notificaciones.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_NOTIFICACION_NO_EXISTENTE);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

        for(Notificacion notify: notificaciones){
            NotificacionDto notificacionDto = new NotificacionDto();
            notificacionDto.setId(notify.getId());
            notificacionDto.setIdUsuario(idUsuario);
            notificacionDto.setMensaje(notify.getMensaje());
            notificacionDto.setFecha(notify.getFecha());

            respuesta.getDatos().add(notificacionDto);
        }

        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);

        return respuesta;
    }

    //Obtener todas las notificaciones
    public RespuestaGenerica getNotificaciones(){
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        if (notificaciones.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_NOTIFICACION_NO_EXISTENTE);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

        for(Notificacion notify: notificacionRepository.findAll()){
            NotificacionDto notificacionDto = new NotificacionDto();
            notificacionDto.setId(notify.getId());
            //Obtener el ID del usuario (FK)
            Usuario idUsuario = notify.getIdUsuario();
            notificacionDto.setIdUsuario(idUsuario.getId());
            notificacionDto.setMensaje(notify.getMensaje());
            notificacionDto.setFecha(notify.getFecha());

            respuesta.getDatos().add(notificacionDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return  respuesta;
    }
}
