package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.NotificacionDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.NotificacionService;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificacionControllerTest {
    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private NotificacionController notificacionController;

    private RespuestaGenerica respuesta;
    private NotificacionDto notificacionDto;

    @BeforeEach
    void setUp(){
        notificacionDto = new NotificacionDto();
        notificacionDto.setIdUsuario(1);
        notificacionDto.setMensaje("Nueva notificacion");
    }

    @Test
    void saveNotificationShouldReturnHttpStatusOk(){
        when(notificacionService.guardarNotificacion(any(NotificacionDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(notificacionDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        RespuestaGenerica respuesta = notificacionController.saveNotification(notificacionDto).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        verify(notificacionService).guardarNotificacion(any(NotificacionDto.class));
    }

    @Test
    void getNotificationsShouldReturnHttpStatusOk(){
        when(notificacionService.getNotificaciones()).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(notificacionDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = notificacionController.getNotification().getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(notificacionService).getNotificaciones();
    }

    @Test
    void getNotificationsByUserShouldReturnHttpStatusOk(){
        when(notificacionService.getNotificacionPorUsuario(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(notificacionDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = notificacionController.getNotificationByUser(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(notificacionService).getNotificacionPorUsuario(any(Integer.class));
    }

}
