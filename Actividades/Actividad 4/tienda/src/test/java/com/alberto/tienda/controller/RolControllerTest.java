package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.RolDto;
import com.alberto.tienda.service.RolService;
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
public class RolControllerTest {
    @Mock
    private RolService rolService;

    @InjectMocks
    private RolController rolController;

    private RespuestaGenerica respuesta;
    private RolDto rolDto;

    @BeforeEach
    void setUp(){
        rolDto = new RolDto();
        rolDto.setNombre("comprador");
    }

    @Test
    void saveRolShouldReturnHttpStatusOk(){
        when(rolService.guardarRol(any(RolDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(rolDto));
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
            return respuesta;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = rolController.saveRol(rolDto).getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar interacciones con los mocks
        verify(rolService).guardarRol(any(RolDto.class));
    }

    @Test
    void getRolesShouldReturnHttpStatusOk(){
        when(rolService.getRoles()).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(rolDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        RespuestaGenerica respuesta = rolController.getRol().getBody();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(rolService).getRoles();
    }
}
