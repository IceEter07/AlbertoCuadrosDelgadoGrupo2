package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.service.UsuarioService;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private RespuestaGenerica respuesta;
    private UsuarioDto usuarioDto;


    @BeforeEach
    void setUp(){
        usuarioDto = new UsuarioDto();
        usuarioDto.setNombre("Juan");
        usuarioDto.setApPat("Ramos");
        usuarioDto.setApMat("Flores");
        usuarioDto.setTelefono("4381096548");
        usuarioDto.setEmail("jose2@example.com");
        usuarioDto.setPass("Hola1234.");
    }

    @Test
    void saveUserShouldReturnHttpStatusOk(){
//        when(usuarioController.guardarUsuario(any(UsuarioDto.class))).thenAnswer(invocation->{
//            return invocation.<RespuestaGenerica>getArgument(0);
//        });
        when(usuarioService.guardarUsuario(any(UsuarioDto.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(usuarioDto));
            respuesta.setMensaje(Constantes.MENSAJE_USUARIO_REGISTRADO_EXISTOSAMENTE+1);
            return respuesta;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = usuarioController.guardarUsuario(usuarioDto).getBody();

        //Comprobar los resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(200, respuesta.getCodigo());
        assertEquals(Constantes.MENSAJE_USUARIO_REGISTRADO_EXISTOSAMENTE+1, respuesta.getMensaje());

        //Verificar interaccion de los mocks
        verify(usuarioService).guardarUsuario(any(UsuarioDto.class));
    }

    @Test
    void getAllUsersShouldReturnHttpStatusOk(){
        when(usuarioService.getUsuarios()).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(usuarioDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = usuarioController.getAllUsers().getBody();

        //Comprobar los resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertEquals(200, respuesta.getCodigo());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar interaccion de los mocks
        verify(usuarioService).getUsuarios();
    }

    @Test
    void getUserByIdShouldReturnHttpStatusOk(){
        when(usuarioService.getUsuario(any(Integer.class))).thenAnswer(invocation->{
            respuesta = new RespuestaGenerica();
            respuesta.setExito(true);
            respuesta.setDatos(Collections.singletonList(usuarioDto));
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
            return respuesta;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = usuarioController.getUser(1).getBody();

        //Comprobar los resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertEquals(200, respuesta.getCodigo());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones de los mocks
        verify(usuarioService).getUsuario(any(Integer.class));
    }
}
