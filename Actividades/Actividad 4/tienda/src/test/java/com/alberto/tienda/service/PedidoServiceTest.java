package com.alberto.tienda.service;

import com.alberto.tienda.data.*;
import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.data.dto.PedidoDto;
import com.alberto.tienda.data.dto.ProductoAddDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.repository.*;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @Mock
    PedidoRepository pedidoRepository;
    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    DireccionRepository direccionRepository;
    @Mock
    MetodoPagoRepository metodoPagoRepository;
    @Mock
    ProductoRepository productoRepository;
    @Mock
    CarritoRepository carritoRepository;
    @Mock
    DetalleCarritoRepository detalleCarritoRepository;
    @Mock
    DetallePedidoRepository detallePedidoRepository;

    @InjectMocks
    PedidoService pedidoService;

    private Usuario usuario;
    private Pedido pedido;
    private Direccion direccion;
    private MetodoPago metodoPago;
    private Producto producto;
    private Carrito carrito;
    private DetalleCarrito detalleCarrito;
    private DetallePedido detallePedido;
    private PedidoDto pedidoDto;

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

        Tienda tienda = new Tienda();
        tienda.setIdTienda(1);
        tienda.setIdUsuario(usuario);
        tienda.setRfc("SUFA091264GA1");
        tienda.setNombre("Microsoft");
        tienda.setDescripcion("Ejemplo de descripcion");
        tienda.setRating(5);

        Categoria categoria = new Categoria();
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

        metodoPago = new MetodoPago();
        metodoPago.setIdPago(1);
        metodoPago.setIdUsuario(usuario);
        metodoPago.setNombre("tarjeta de debito");
        metodoPago.setDescripcion("Ejemplo de descripcion");

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

        //Datos que se usan en la logica del service de pedido
        //En esta prueba se dan datos "erronos" debido a que Pedido solo copia lo que esta en la tabla de "detalle_carrito"
        carrito = new Carrito();
        carrito.setIdCarrito(1);
        carrito.setIdUsuario(usuario);
        carrito.setTotal(300F);
        carrito.setEstado(true);

        detalleCarrito = new DetalleCarrito();
        detalleCarrito.setIdDetalleCarrito(1);
        detalleCarrito.setIdCarrito(carrito);
        detalleCarrito.setIdProducto(producto);
        detalleCarrito.setCantidad(5);
        detalleCarrito.setPrecio(producto.getPrecioVenta());
        detalleCarrito.setTotal(300F);

        pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setIdUsuario(usuario);
        pedido.setIdDireccion(direccion);
        pedido.setIdMetodoPago(metodoPago);
        pedido.setFechaPedido(new Date());
        pedido.setImpuesto(detalleCarrito.getTotal()*0.15F);
        pedido.setTotal(detalleCarrito.getTotal());
        pedido.setEstado(true);

        pedidoDto = new PedidoDto();
        pedidoDto.setIdUsuario(1);
        pedidoDto.setIdDireccion(1);
        pedidoDto.setIdPago(1);

        detallePedido = new DetallePedido();
        detallePedido.setIdDetallePedido(1);
        detallePedido.setIdProducto(producto);
        detallePedido.setIdPedido(pedido);
        detallePedido.setCantidad(detalleCarrito.getCantidad());
        detallePedido.setPrecio(detalleCarrito.getPrecio());
        detallePedido.setTotal(detalleCarrito.getTotal());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void saveOrderShouldReturnPedidoDto(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(direccionRepository.findByIdAndIdUsuario(any(Integer.class), any(Usuario.class))).thenReturn(Collections.singletonList(direccion));
        when(metodoPagoRepository.findByIdPagoAndIdUsuario(any(Integer.class), any(Usuario.class))).thenReturn(Collections.singletonList(metodoPago));
        when(carritoRepository.findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class))).thenReturn(Collections.singletonList(carrito));
        when(detalleCarritoRepository.findByIdCarrito(any(Carrito.class))).thenReturn(Collections.singletonList(detalleCarrito));
        when(productoRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(producto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation->{
            Producto product = invocation.getArgument(0);
            product.setStock(producto.getStock()-detalleCarrito.getCantidad());
            return product;
        });
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation->{
            Pedido order = invocation.getArgument(0);
            order.setIdPedido(1);
            return order;
        });
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenAnswer(invocation->{
            DetallePedido orderDetails = invocation.getArgument(0);
            return orderDetails;
        });
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation->{
            Carrito car = invocation.getArgument(0);
            car.setEstado(true);
            return car;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = pedidoService.savePedido(pedidoDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_PETICION_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones de los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(direccionRepository).findByIdAndIdUsuario(any(Integer.class), any(Usuario.class));
        verify(metodoPagoRepository).findByIdPagoAndIdUsuario(any(Integer.class), any(Usuario.class));
        verify(carritoRepository).findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class));
        verify(detalleCarritoRepository).findByIdCarrito(any(Carrito.class));
        verify(productoRepository).findById(any(Integer.class));
        verify(productoRepository).save(any(Producto.class));
        verify(pedidoRepository).save(any(Pedido.class));
        verify(detallePedidoRepository).save(any(DetallePedido.class));
        verify(carritoRepository).save(any(Carrito.class));
    }
    @Test
    void getOrdersByUser(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(pedidoRepository.findByIdUsuario(any(Usuario.class))).thenReturn(Collections.singletonList(pedido));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = pedidoService.getPedidosPorUsuario(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar interaccion con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(pedidoRepository).findByIdUsuario(any(Usuario.class));
    }
    @Test
    void getOrdersByUserAndActiveState(){
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(usuario));
        when(pedidoRepository.findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class))).thenReturn(Collections.singletonList(pedido));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = pedidoService.getPedidosPorUsuarioYEstadoActivo(usuario.getId());

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar interacciones con los mocks
        verify(usuarioRepository).findById(any(Integer.class));
        verify(pedidoRepository).findByIdUsuarioAndEstado(any(Usuario.class), any(Boolean.class));

    }

    //Prueba de "prueba" para el metodo de actualizar estado del pedido
    @Test
    void putStateOrderShouldReturnMessage(){
        //when(pedidoRepository.findByIdPedidoAndEstado(any(Integer.class), any(Boolean.class))).thenReturn(Collections.singletonList(pedido));
        when(pedidoRepository.findByIdPedidoAndEstado(any(Integer.class), any(Boolean.class))).thenReturn(Collections.singletonList(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation->{
            Pedido order = invocation.getArgument(0);
            order.setEstado(false);
            return order;
        });

        //Metodo a probar
        RespuestaGenerica respuesta = pedidoService.putEstadoPedido(pedido.getIdPedido()); //ID 1

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertTrue(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_PETICION_EXITOSA, respuesta.getMensaje());

        //Verificar interacciones de los mocks
        verify(pedidoRepository).findByIdPedidoAndEstado(any(Integer.class), any(Boolean.class));
        verify(pedidoRepository).save(any(Pedido.class));
    }

}
