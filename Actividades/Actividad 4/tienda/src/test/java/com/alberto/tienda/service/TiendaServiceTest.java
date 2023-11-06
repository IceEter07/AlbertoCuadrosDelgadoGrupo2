package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioRol;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.repository.TiendaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.repository.UsuarioRolRepository;
import com.alberto.tienda.utils.Constantes;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TiendaServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private TiendaRepository tiendaRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private UsuarioRolRepository usuarioRolRepository;

    @InjectMocks
    private TiendaService tiendaService;

    private Usuario usuario;
    private Rol rol;
    private Tienda tienda;
    private TiendaDto tiendaDto;
    private UsuarioRol usuarioRol;

    @BeforeEach
    void setUp(){
        //Colocar datos del muck
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Jose");
        usuario.setApPat("Pimentel");
        usuario.setApMat("Castillo");
        usuario.setTelefono("4381096547");
        usuario.setEmail("jose@example.com");
        usuario.setPass("Hola1234.");

        tienda = new Tienda();
        tienda.setIdTienda(1);
        tienda.setIdUsuario(usuario);
        tienda.setRfc("SUFA091264GA1");
        tienda.setNombre("Microsoft");
        tienda.setDescripcion("Ejemplo de descripcion");
        tienda.setRating(5);

        tiendaDto = new TiendaDto();
        tiendaDto.setIdUsuario(usuario.getId());
        tiendaDto.setRfc("SUFA091264GA1");
        tiendaDto.setNombre("Microsoft");
        tiendaDto.setDescripcion("Ejemplo de descripcion");
        tiendaDto.setRating(5);

        rol = new Rol();
        rol.setId(1);
        rol.setNombre("comprador");

        usuarioRol = new UsuarioRol();
        usuarioRol.setIdUsuarioRol(1);
        usuarioRol.setIdRol(rol);
        usuarioRol.setIdUsuario(usuario);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void saveShopShouldReturnTiendaDto(){
        //Establecer lo que el mock hará
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(tiendaRepository.findByRfc(any(String.class))).thenReturn(Collections.emptyList());
        when(tiendaRepository.findByNombre(any(String.class))).thenReturn(Collections.emptyList());

        when(rolRepository.findByNombre("vendedor")).thenReturn(Collections.singletonList(rol));
        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(invocation ->{
            Tienda tienda = invocation.getArgument(0);
            tienda.setIdTienda(1);
            return tienda;
        });

        when(usuarioRolRepository.save(any(UsuarioRol.class))).thenAnswer(invocation -> {
            return invocation.<UsuarioRol>getArgument(0);
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = tiendaService.guardarTienda(tiendaDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar la interaccion con los Mock
        verify(usuarioRepository).findById(any(Integer.class));
        verify(tiendaRepository).findByNombre(any(String.class));
        verify(tiendaRepository).findByRfc(any(String.class));
        verify(rolRepository, times(2)).findByNombre(any(String.class));
        verify(tiendaRepository).save(any(Tienda.class));
    }

    @Test
    void getShopsShouldReturnListOfShops(){
        when(tiendaRepository.findAll()).thenReturn(Collections.singletonList(tienda));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = tiendaService.getTiendas();

        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones de los mock
        verify(tiendaRepository).findAll();
    }

    @Test
    void getShopsByIdShouldReturnTiendaDto(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(tiendaRepository.findByIdUsuario(any(Usuario.class))).thenReturn(Collections.singletonList(tienda));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = tiendaService.getTiendasPorUsuario(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones entre los mock
        verify(usuarioRepository).findById(any(Integer.class));
        verify(tiendaRepository).findByIdUsuario(any(Usuario.class));

    }
}
