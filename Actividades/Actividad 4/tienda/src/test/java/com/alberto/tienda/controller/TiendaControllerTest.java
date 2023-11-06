package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.service.TiendaService;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TiendaControllerTest{
    @Mock
    private TiendaService tiendaService;

    @InjectMocks
    private TiendaController tiendaController;

    private RespuestaGenerica respuesta;
    private TiendaDto tiendaDto;

    @BeforeEach
    void setUp(){
        tiendaDto = new TiendaDto();
        tiendaDto.setIdUsuario(1);
        tiendaDto.setRfc("SUFA091264GA1");
        tiendaDto.setNombre("Microsoft");
        tiendaDto.setDescripcion("Ejemplo de descripcion");
        tiendaDto.setRating(5);
    }

    @Test
    void saveShopShouldReturnHttpStatusOk(){
        when(tiendaService.guardarTienda(any(TiendaDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(tiendaDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        RespuestaGenerica respuesta = tiendaController.saveShop(tiendaDto).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        verify(tiendaService).guardarTienda(any(TiendaDto.class));
    }

    @Test
    void getAllShopsShouldReturnHttpStatusOk(){
        when(tiendaService.getTiendas()).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(tiendaDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = tiendaController.getShop().getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(tiendaService).getTiendas();
    }

    @Test
    void getShopsByUserShouldReturnHttpStatusOk(){
        when(tiendaService.getTiendasPorUsuario(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(tiendaDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = tiendaController.getShopByUser(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(tiendaService).getTiendasPorUsuario(any(Integer.class));
    }
}
