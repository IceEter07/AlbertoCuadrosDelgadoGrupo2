package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.PedidoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.PedidoService;
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
public class PedidoControllerTest {
    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private RespuestaGenerica respuesta;
    private PedidoDto pedidoDto;

    @BeforeEach
    void setUp(){
        pedidoDto = new PedidoDto();
        pedidoDto.setIdUsuario(1);
        pedidoDto.setIdDireccion(1);
        pedidoDto.setIdPago(1);
    }

    @Test
    void saveOrderShouldReturnHttpStatusOk(){
        when(pedidoService.savePedido(any(PedidoDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(pedidoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        RespuestaGenerica respuesta = pedidoController.saveOrder(pedidoDto).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        verify(pedidoService).savePedido(any(PedidoDto.class));
    }

    @Test
    void getOrderHistoryByUserShouldReturnHttpStatusOk(){
        when(pedidoService.getPedidosPorUsuario(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(pedidoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = pedidoController.getOrderByUser(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(pedidoService).getPedidosPorUsuario(any(Integer.class));
    }

    @Test
    void getActiveOrderByUserShouldReturnHttpStatusOk(){
        when(pedidoService.getPedidosPorUsuarioYEstadoActivo(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(pedidoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = pedidoController.getOrderByUserAndStatus(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(pedidoService).getPedidosPorUsuarioYEstadoActivo(any(Integer.class));
    }

    @Test
    void putOrderStateByUserShouldReturnHttpStatusOk(){
        when(pedidoService.putEstadoPedido(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(pedidoDto));
            respuesta.setMensaje(Constantes.MENSAJE_PETICION_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = pedidoController.updateOrderEstatus(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_PETICION_EXITOSA, respuesta.getMensaje());

        verify(pedidoService).putEstadoPedido(any(Integer.class));
    }

}
