package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.data.dto.ProductoAddDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.CarritoService;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarritoControllerTest {
    @Mock
    private CarritoService carritoService;
    @InjectMocks
    private CarritoController carritoController;

    private RespuestaGenerica respuesta;
    private CarritoDto carritoDto;

    @BeforeEach
    void setUp(){
        ProductoAddDto productoAddDto = new ProductoAddDto();
        productoAddDto = new ProductoAddDto();
        productoAddDto.setIdProducto(1);
        productoAddDto.setCantidad(5);

        carritoDto = new CarritoDto();
        carritoDto.setIdUsuario(1);
        carritoDto.setProductos(List.of(productoAddDto));
    }

    @Test
    void saveCarShouldReturnHttpStatusOk(){
        when(carritoService.guardarCarrito(any(CarritoDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(carritoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        RespuestaGenerica respuesta = carritoController.saveCar(carritoDto).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        verify(carritoService).guardarCarrito(any(CarritoDto.class));
    }

    @Test
    void getCarByUserShouldReturnHttpStatusOk(){
        when(carritoService.getCarritoPorUsuario(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(carritoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = carritoController.getCarDetails(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(carritoService).getCarritoPorUsuario(any(Integer.class));
    }
}
