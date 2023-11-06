package com.alberto.tienda.service;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.ProductoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.repository.CategoriaRepository;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.TiendaRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    @Mock
    ProductoRepository productoRepository;
    @Mock
    CategoriaRepository categoriaRepository;
    @Mock
    TiendaRepository tiendaRepository;

    @InjectMocks
    ProductoService productoService;

    private Producto producto;
    private ProductoDto productoDto;
    private Categoria categoria;
    private Tienda tienda;
    private Usuario usuario;

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

        productoDto = new ProductoDto();
        productoDto.setIdCategoria(categoria.getIdCategoria());
        productoDto.setIdTienda(tienda.getIdTienda());
        productoDto.setCodigo("1234567890");
        productoDto.setNombre("Pinol");
        productoDto.setPrecio(29.99F);
        productoDto.setNumeroProductos(50);
        productoDto.setDescripcion("Ejemplo de descripcion");
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void saveProductShouldReturnProductoDto(){
        when(categoriaRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(categoria));
        when(tiendaRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(tienda));
        when(productoRepository.findByIdTiendaAndCodigo(any(Tienda.class),any(String.class))).thenReturn(Collections.emptyList());
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation ->{
            Producto product = invocation.getArgument(0);
            product.setIdProducto(1);
            return product;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = productoService.guardarProducto(productoDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar las interacciones con los mocks
        verify(categoriaRepository).findById(any(Integer.class));
        verify(tiendaRepository).findById(any(Integer.class));
        verify(productoRepository).findByIdTiendaAndCodigo(any(Tienda.class), any(String.class));
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void getAllProductoShouldReturnListOfProducts(){
        when(productoRepository.findAll()).thenReturn(Collections.singletonList(producto));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = productoService.getProductos();

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones de los mocks
        verify(productoRepository).findAll();
    }

    @Test
    void getProductsByTiendaShouldReturnListOfProducts(){
        when(tiendaRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(tienda));
        when(productoRepository.findByIdTienda(any(Tienda.class))).thenReturn(Collections.singletonList(producto));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = productoService.getProductosPorTienda(tienda.getIdTienda());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones con los mocks
        verify(tiendaRepository).findById(any(Integer.class));
        verify(productoRepository).findByIdTienda(any(Tienda.class));
    }

    @Test
    void getProductsByCategoryShouldReturnListOfProducts(){
        when(categoriaRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(categoria));
        when(productoRepository.findByIdCategoria(any(Categoria.class))).thenReturn(Collections.singletonList(producto));

        //Comparar resultados
        RespuestaGenerica respuesta = productoService.getProductosPorCategoria(categoria.getIdCategoria());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones de los mocks
        verify(categoriaRepository).findById(any(Integer.class));
        verify(productoRepository).findByIdCategoria(any(Categoria.class));

    }
}
