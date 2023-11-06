package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.ProductoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.ProductoService;
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
public class ProductoControllerTest {
    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private RespuestaGenerica respuesta;
    private ProductoDto productoDto;

    @BeforeEach
    void setUp(){
        productoDto = new ProductoDto();
        productoDto.setIdCategoria(1);
        productoDto.setIdTienda(1);
        productoDto.setCodigo("1234567890");
        productoDto.setNombre("Pinol");
        productoDto.setPrecio(29.99F);
        productoDto.setNumeroProductos(50);
        productoDto.setDescripcion("Ejemplo de descripcion");
    }

    @Test
    void saveProductShouldRetunrHttpStatusOk(){
        when(productoService.guardarProducto(any(ProductoDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(productoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        RespuestaGenerica respuesta = productoController.saveProduct(productoDto).getBody();
        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        verify(productoService).guardarProducto(any(ProductoDto.class));
    }

    @Test
    void getAllProductShouldRetunrHttpStatusOk(){
        when(productoService.getProductos()).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(productoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = productoController.getProducts().getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(productoService).getProductos();
    }

    @Test
    void getProductByShopShouldRetunrHttpStatusOk(){
        when(productoService.getProductosPorTienda(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(productoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = productoController.getProductsByShop(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(productoService).getProductosPorTienda(any(Integer.class));
    }

    @Test
    void getProductByCategoryShouldRetunrHttpStatusOk(){
        when(productoService.getProductosPorCategoria(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(productoDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });
        RespuestaGenerica respuesta = productoController.getProductsByCat(1).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(productoService).getProductosPorCategoria(any(Integer.class));
    }


}
