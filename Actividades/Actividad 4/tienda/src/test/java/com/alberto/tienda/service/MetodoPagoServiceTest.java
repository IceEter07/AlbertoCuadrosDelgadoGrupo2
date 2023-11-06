package com.alberto.tienda.service;

import com.alberto.tienda.data.MetodoPago;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.MetodoPagoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.repository.MetodoPagoRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MetodoPagoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @InjectMocks
    private MetodoPagoService metodoPagoService;

    private Usuario usuario;
    private MetodoPago metodoPago;
    private MetodoPagoDto metodoPagoDto;

    @BeforeEach
    void setUp(){
        //Colocar los datos del mock
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Jose");
        usuario.setApPat("Pimentel");
        usuario.setApMat("Castillo");
        usuario.setTelefono("4381096547");
        usuario.setEmail("jose@example.com");
        usuario.setPass("Hola1234.");

        metodoPago = new MetodoPago();
        metodoPago.setIdPago(1);
        metodoPago.setIdUsuario(usuario);
        metodoPago.setNombre("tarjeta de debito");
        metodoPago.setDescripcion("Ejemplo de descripcion");

        metodoPagoDto = new MetodoPagoDto();
        metodoPagoDto.setIdUsuario(1);
        metodoPagoDto.setNombre("tarjeta de debito");
        metodoPagoDto.setDescripcion("Ejemplo de descripcion");
    }

    @Test
    void savePayMethodShouldReturnMetodoPagoDto(){
        //Establecer lo que el mock hará
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(metodoPagoRepository.save(any(MetodoPago.class))).thenAnswer(invocation ->{
            MetodoPago metodoPago = invocation.getArgument(0);
            metodoPago.setIdPago(1);
            return metodoPago;
        });

        //Llamada al metodo que se va a probar
        RespuestaGenerica respuesta = metodoPagoService.guardarMetodoPago(metodoPagoDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar la interaccion con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(metodoPagoRepository).save(any(MetodoPago.class));
    }

    @Test
    void getPayMethodByIdShouldReturnListOfPayMethods(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(metodoPagoRepository.findByIdUsuario(any(Usuario.class))).thenReturn(Collections.singletonList(metodoPago));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = metodoPagoService.getMetodosPagoPorUsuario(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(metodoPagoRepository).findByIdUsuario(any(Usuario.class));

    }
}
