package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.MetodoPagoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.MetodoPagoService;
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
public class MetodoPagoControllerTest {
    @Mock
    private MetodoPagoService metodoPagoService;

    @InjectMocks
    private MetodoPagoController metodoPagoController;

    private RespuestaGenerica respuesta;
    private MetodoPagoDto metodoPagoDto;

    @BeforeEach
    void setUp(){
        metodoPagoDto = new MetodoPagoDto();
        metodoPagoDto.setIdUsuario(1);
        metodoPagoDto.setNombre("tarjeta de debito");
        metodoPagoDto.setDescripcion("Ejemplo de descripcion");
    }

    @Test
    void savePayMethodShouldReturnHttpStatusOk(){
        when(metodoPagoService.guardarMetodoPago(any(MetodoPagoDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(metodoPagoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        RespuestaGenerica respuesta = metodoPagoController.savePayMethod(metodoPagoDto).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        verify(metodoPagoService).guardarMetodoPago(any(MetodoPagoDto.class));
    }

    @Test
    void getPayMethodsByUserShouldReturnHttpStatusOk(){
        when(metodoPagoService.getMetodosPagoPorUsuario(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(metodoPagoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = metodoPagoController.getPayMethodsByUser(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(metodoPagoService).getMetodosPagoPorUsuario(any(Integer.class));
    }

}
