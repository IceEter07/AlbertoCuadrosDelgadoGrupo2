package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.DireccionService;
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
public class DireccionControllerTest {
    @Mock
    private DireccionService direccionService;

    @InjectMocks
    private DireccionController direccionController;

    private RespuestaGenerica respuesta;
    private DireccionDto direccionDto;

    @BeforeEach
    void setUp(){
        direccionDto = new DireccionDto();
        direccionDto.setIdUsuario(1);
        direccionDto.setPais("Mexico");
        direccionDto.setEstado("Guanajuato");
        direccionDto.setMunicipio("Valle de Santiago");
        direccionDto.setColonia("Los doctores");
        direccionDto.setCalle("Manzanillo");
        direccionDto.setNumeroExt(1);
        direccionDto.setNumeroInt(1);
    }

    @Test
    void saveAddressShouldReturnHttpStatusOk(){
        when(direccionService.guardarDireccion(any(DireccionDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(direccionDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        RespuestaGenerica respuesta = direccionController.saveAddress(direccionDto).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        verify(direccionService).guardarDireccion(any(DireccionDto.class));
    }

    @Test
    void getAllAdrressShouldReturnHttpStatusOk(){
        when(direccionService.getDirecciones()).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(direccionDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = direccionController.getAddress().getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(direccionService).getDirecciones();
    }

    @Test
    void getAdrressByUserShouldReturnHttpStatusOk(){
        when(direccionService.getDireccionPorUsuario(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(direccionDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = direccionController.getAdrresByUser(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(direccionService).getDireccionPorUsuario(any(Integer.class));
    }


}
