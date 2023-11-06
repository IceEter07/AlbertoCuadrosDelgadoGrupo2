package com.alberto.tienda.service;

import com.alberto.tienda.data.*;
import com.alberto.tienda.data.dto.ResenaDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.ResenaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {
    @Mock
    private ResenaRepository resenaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ResenaService resenaService;

    private Usuario usuario;
    private Producto producto;
    private Categoria categoria;
    private Tienda tienda;
    private Resena resena;
    private ResenaDto resenaDto;

    @BeforeEach
    void setUp(){
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

        categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNombre("tecnologia");
        categoria.setDescripcion("ejemplo de descripcion");

        producto = new Producto();
        producto.setIdProducto(1);
        producto.setIdCategoria(categoria);
        producto.setIdTienda(tienda);
        producto.setCodigo("1234567890");
        producto.setNombre("Pinol");
        producto.setPrecioVenta(29.99F);
        producto.setStock(50);
        producto.setDescripcion("Ejemplo de descripcion");

        resena = new Resena();
        resena.setIdResena(1);
        resena.setIdUsuario(usuario);
        resena.setIdProducto(producto);
        resena.setComentario("Ejemplo de comentario");
        resena.setFecha(new Date());

        resenaDto = new ResenaDto();
        resenaDto.setIdUsuario(1);
        resenaDto.setIdProducto(1);
        resenaDto.setComentario("Ejemplo de comentario");
        resenaDto.setFecha(new Date());
    }
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void saveReviewShouldReturnResenaDto(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(productoRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(producto));
        when(resenaRepository.save(any(Resena.class))).thenAnswer(invocation ->{
            Resena review = invocation.getArgument(0);
            review.setIdResena(1);
            return review;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = resenaService.guardarResena(resenaDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar la interaccion con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(productoRepository).findById(any(Integer.class));
        verify(resenaRepository).save(any(Resena.class));
    }

    @Test
    void getReviewsByUserShouldReturnListOfReviews(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(resenaRepository.findByIdUsuario(any(Usuario.class))).thenReturn(Collections.singletonList(resena));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = resenaService.obtenerResenasPorUsuario(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar la interaccion con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(resenaRepository).findByIdUsuario(any(Usuario.class));
    }

    @Test
    void getReviewsByProductShouldReturnListOfReviews(){
        when(productoRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(producto));
        when(resenaRepository.findByIdProducto(any(Producto.class))).thenReturn(Collections.singletonList(resena));


        RespuestaGenerica respuesta = resenaService.obtenerResenasPorProducto(producto.getIdProducto());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar la interaccion con los mocks
        verify(productoRepository).findById(any(Integer.class));
        verify(resenaRepository).findByIdProducto(any(Producto.class));
    }
}
