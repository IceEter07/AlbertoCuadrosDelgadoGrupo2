package com.alberto.tienda.service;

import com.alberto.tienda.data.Direccion;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.repository.DireccionRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DireccionServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionService direccionService;

    private Usuario usuario;
    private Direccion direccion;
    private DireccionDto direccionDto;

    @BeforeEach
    void setUp(){
        //Datos del mock

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Jose");
        usuario.setApPat("Pimentel");
        usuario.setApMat("Castillo");
        usuario.setTelefono("4381096547");
        usuario.setEmail("jose@example.com");
        usuario.setPass("Hola1234.");

        direccion = new Direccion();
        direccion.setId(1);
        direccion.setIdUsuario(usuario);
        direccion.setPais("Mexico");
        direccion.setEstado("Guanajuato");
        direccion.setMunicipio("Valle de Santiago");
        direccion.setColonia("Los doctores");
        direccion.setCalle("Manzanillo");
        direccion.setNumExt(1);
        direccion.setNumInt(1);

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
    void saveAddressShouldReturnDireccionDto(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(direccionRepository.save(any(Direccion.class))).thenAnswer(invocation ->{
            Direccion direccion = invocation.getArgument(0);
            direccion.setId(1);
            return direccion;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = direccionService.guardarDireccion(direccionDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar interacción con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(direccionRepository).save(any(Direccion.class));
    }

    @Test
    void getAdressShouldReturnListOfAdress(){
        when(direccionRepository.findAll()).thenReturn(Collections.singletonList(direccion));

        RespuestaGenerica respuesta = direccionService.getDirecciones();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones de los mock
        verify(direccionRepository).findAll();
    }

    @Test
    void getAddressByIdShouldReturnDireccionDto(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(direccionRepository.findByIdUsuario(any(Usuario.class))).thenReturn(Collections.singletonList(direccion));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = direccionService.getDireccionPorUsuario(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones con los mock
        verify(usuarioRepository).findById(any(Integer.class));
        verify(direccionRepository).findByIdUsuario(any(Usuario.class));
    }
}
