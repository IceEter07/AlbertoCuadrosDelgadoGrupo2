package com.alberto.tienda.service;


import com.alberto.tienda.data.Notificacion;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.NotificacionDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.repository.NotificacionRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {
    @Mock
    private NotificacionRepository notificacionRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacion;
    private NotificacionDto notificacionDto;
    private Usuario usuario;

    @BeforeEach
    void setUp(){
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Jose");
        usuario.setApPat("Pimentel");
        usuario.setApMat("Castillo");
        usuario.setTelefono("4381096547");
        usuario.setEmail("jose@example.com");
        usuario.setPass("Hola1234.");

        notificacion = new Notificacion();
        notificacion.setId(1);
        notificacion.setIdUsuario(usuario);
        notificacion.setMensaje("Nueva notificacion");
        notificacion.setFecha(new Date());

        notificacionDto = new NotificacionDto();
        notificacionDto.setIdUsuario(usuario.getId());
        notificacionDto.setMensaje("Nueva notificacion");
    }

    @Test
    void saveNotifyShouldReturnNotificacionDto(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(notificacionRepository.save(any(Notificacion.class))).thenAnswer(invocation ->{
            Notificacion notify = invocation.getArgument(0);
            notify.setId(1);
            return notify;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = notificacionService.guardarNotificacion(notificacionDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar la interaccion con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(notificacionRepository).save(any(Notificacion.class));
    }

    @Test
    void getNoticationsShouldReturnListOfNotifications(){
        when(notificacionRepository.findAll()).thenReturn(Collections.singletonList(notificacion));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = notificacionService.getNotificaciones();

        //Compararar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar la interaccion con los mocks
        verify(notificacionRepository).findAll();
    }

    @Test
    void getNotificationsByUserShouldReturnListOfNotificactions(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(notificacionRepository.findByIdUsuario(any(Usuario.class))).thenReturn(Collections.singletonList(notificacion));

        //Metodo que se va a comparar
        RespuestaGenerica respuesta = notificacionService.getNotificacionPorUsuario(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar la interaccion con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(notificacionRepository).findByIdUsuario(any(Usuario.class));


    }
}
