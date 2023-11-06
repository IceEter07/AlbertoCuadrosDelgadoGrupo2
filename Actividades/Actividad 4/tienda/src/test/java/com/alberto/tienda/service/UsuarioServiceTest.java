package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioRol;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.RolDto;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.repository.UsuarioRolRepository;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private UsuarioRolRepository usuarioRolRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Rol rol;
    private UsuarioRol usuarioRol;
    private UsuarioDto usuarioDto;

    @BeforeEach
    void setUp(){
        //Colocar los datos del muck
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Jose");
        usuario.setApPat("Pimentel");
        usuario.setApMat("Castillo");
        usuario.setTelefono("4381096547");
        usuario.setEmail("jose@example.com");
        usuario.setPass("Hola1234.");

        usuarioDto = new UsuarioDto();
        usuarioDto.setNombre("Juan");
        usuarioDto.setApPat("Ramos");
        usuarioDto.setApMat("Flores");
        usuarioDto.setTelefono("4381096548");
        usuarioDto.setEmail("jose2@example.com");
        usuarioDto.setPass("Hola1234.");

        rol = new Rol();
        rol.setId(1);
        rol.setNombre("compradror");

        usuarioRol = new UsuarioRol();
        usuarioRol.setIdUsuarioRol(1);
        usuarioRol.setIdUsuario(usuario);
        usuarioRol.setIdRol(rol);

    }

    @Test
    void getUsersShouldReturnListOfUsers(){
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));

        RespuestaGenerica respuesta = usuarioService.getUsuarios();

        //Comprobar los resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(usuarioRepository).findAll();
    }

    @Test
    void getUserByIdShouldReturnOneUser(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));

        RespuestaGenerica respuesta = usuarioService.getUsuario(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(usuarioRepository).findById(usuario.getId());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void saveUserShouldReturnUsuarioDto(){
        //Configurar lo que el el mock regresará cuando se guarda el usuario
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
                    Usuario usuario = invocation.getArgument(0);
                    usuario.setId(1); //Simular la generiación del ID
                    return usuario;
                });

        when(rolRepository.findByNombre("comprador")).thenReturn(Collections.singletonList(rol));

        when(usuarioRolRepository.save(any(UsuarioRol.class))).thenAnswer(invocation -> {
            UsuarioRol userRol = invocation.getArgument(0);
            userRol.setIdUsuarioRol(1); //Simular la generiación del ID
            return userRol;
        });


        //Llamada al metodo que se va a probar
        RespuestaGenerica respuesta = usuarioService.guardarUsuario(usuarioDto);

        //Comparar resultados
        assertTrue(respuesta.isExito());
        assertNotNull(respuesta.getDatos());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_USUARIO_REGISTRADO_EXISTOSAMENTE+1, respuesta.getMensaje());

        //Verificar la interacción de los Mocks
        //El save del Rol nunca se va a ejecuar porque estpy diciendole a Mockito que el rol ya existe :).
        //Por lo tanto en esta prueba no se coloca
        verify(usuarioRepository).save(any(Usuario.class));
        verify(rolRepository, times(2)).findByNombre(any(String.class));
        verify(usuarioRolRepository).save(any(UsuarioRol.class));

    }
}
