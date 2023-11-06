package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CategoriaDto;
import com.alberto.tienda.data.dto.ResenaDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.ResenaService;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResenaControllerTest {
    @Mock
    private ResenaService resenaService;
    @InjectMocks
    private ResenaController resenaController;

    private RespuestaGenerica respuesta;
    private ResenaDto resenaDto;

    @BeforeEach
    void setUp(){
        resenaDto = new ResenaDto();
        resenaDto.setIdUsuario(1);
        resenaDto.setIdProducto(1);
        resenaDto.setComentario("Ejemplo de comentario");
        resenaDto.setFecha(new Date());
    }

    @Test
    void saveReviewShouldReturnHttpStatusOk(){
        when(resenaService.guardarResena(any(ResenaDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(resenaDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        RespuestaGenerica respuesta = resenaController.saveComment(resenaDto).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        verify(resenaService).guardarResena(any(ResenaDto.class));
    }

    @Test
    void getReviewsByProductShouldReturnHttpStatusOk(){
        when(resenaService.obtenerResenasPorProducto(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(resenaDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = resenaController.getCommentsProducts(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(resenaService).obtenerResenasPorProducto(any(Integer.class));
    }

    @Test
    void getReviewsByUserShouldReturnHttpStatusOk(){
        when(resenaService.obtenerResenasPorUsuario(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(resenaDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = resenaController.getCommentsByUser(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(resenaService).obtenerResenasPorUsuario(any(Integer.class));
    }

}
