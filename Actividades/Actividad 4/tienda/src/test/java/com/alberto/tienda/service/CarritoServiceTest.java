package com.alberto.tienda.service;

import com.alberto.tienda.data.*;
import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.data.dto.ProductoAddDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.repository.CarritoRepository;
import com.alberto.tienda.repository.DetalleCarritoRepository;
import com.alberto.tienda.repository.ProductoRepository;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {
    @Mock
    private CarritoRepository carritoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private DetalleCarritoRepository detalleCarritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private Usuario usuario;
    private Producto producto;
    private Categoria categoria;
    private Tienda tienda;
    private Carrito carrito;
    private CarritoDto carritoDto;
    private DetalleCarrito detalleCarrito;
    private ProductoAddDto productoAddDto;

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


        //Datos que se usan en la logica del service
        productoAddDto = new ProductoAddDto();
        productoAddDto.setIdProducto(1);
        productoAddDto.setCantidad(5);

        carrito = new Carrito();
        carrito.setIdCarrito(1);
        carrito.setIdUsuario(usuario);
        carrito.setTotal(productoAddDto.getCantidad()*producto.getPrecioVenta());
        carrito.setEstado(true);

        carritoDto = new CarritoDto();
        carritoDto.setIdUsuario(1);
        carritoDto.setProductos(List.of(productoAddDto));

        detalleCarrito = new DetalleCarrito();
        detalleCarrito.setIdDetalleCarrito(1);
        detalleCarrito.setIdCarrito(carrito);
        detalleCarrito.setIdProducto(producto);
        detalleCarrito.setCantidad(productoAddDto.getCantidad());
        detalleCarrito.setPrecio(producto.getPrecioVenta());
        detalleCarrito.setTotal(producto.getPrecioVenta()*productoAddDto.getCantidad());
    }

    //Cuando no existe un carrito activo
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void saveCarShouldReturnCarritoDto(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(carritoRepository.findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class))).thenReturn(Collections.emptyList());
        when(productoRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(producto)); //2
        when(carritoRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation->{
            Carrito car = invocation.getArgument(0);
            car.setIdCarrito(1);
            return car;
        });
        when(detalleCarritoRepository.save(any(DetalleCarrito.class))).thenAnswer(invocation->{
            DetalleCarrito carDetails = invocation.getArgument(0);
            carDetails.setIdDetalleCarrito(1);
            return carDetails;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = carritoService.guardarCarrito(carritoDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar las interacciones de los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(carritoRepository).findById(any(Integer.class));
        verify(carritoRepository).findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class));
        verify(productoRepository, times(2)).findById(any(Integer.class));
        verify(carritoRepository).save(any(Carrito.class));
        verify(detalleCarritoRepository).save(any(DetalleCarrito.class));
    }

    //Cuando existe un carrito activo (Situación: se quiere actualizar los datos de un producto en cuestión en la tabla "detalle_carrito")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void saveExistingCarShouldReturnCarritoDto(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(carritoRepository.findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class))).thenReturn(Collections.singletonList(carrito));
        when(productoRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(producto)); //1 invocacion en esta caso
        when(detalleCarritoRepository.findByIdCarritoAndIdProducto(any(Carrito.class), any(Producto.class))).thenReturn(Collections.singletonList(detalleCarrito));
        when(detalleCarritoRepository.save(any(DetalleCarrito.class))).thenAnswer(invocation->{
            DetalleCarrito carDetails = invocation.getArgument(0);
            //carDetails.setIdDetalleCarrito(1);
            //Como hay una coincidencia se actualizan los campos cantidad y total del producto
            carDetails.setCantidad(productoAddDto.getCantidad());
            carDetails.setTotal(producto.getPrecioVenta()*carDetails.getCantidad());
            return carDetails;
        });
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation->{
            Carrito car = invocation.getArgument(0);
            car.setIdCarrito(1);
            return car;
        });
        //Metodo que se va a probar
        RespuestaGenerica respuesta = carritoService.guardarCarrito(carritoDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CARRITO_PRODUCTOS_INSERTADOS_EXITOSAMENTE, respuesta.getMensaje());

        //Verificar las interacciones de los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(carritoRepository).findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class));
        verify(productoRepository).findById(any(Integer.class));
        verify(carritoRepository).save(any(Carrito.class));
        verify(detalleCarritoRepository).findByIdCarritoAndIdProducto(any(Carrito.class), any(Producto.class));
        verify(detalleCarritoRepository).save(any(DetalleCarrito.class));
    }

    @Test
    void getCarByUserShouldReturnListOfCarDetails(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(carritoRepository.findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class))).thenReturn(Collections.singletonList(carrito));
        when(detalleCarritoRepository.findByIdCarrito(any(Carrito.class))).thenReturn(Collections.singletonList(detalleCarrito));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = carritoService.getCarritoPorUsuario(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones de los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(carritoRepository).findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class));
        verify(detalleCarritoRepository).findByIdCarrito(any(Carrito.class));
    }
}
