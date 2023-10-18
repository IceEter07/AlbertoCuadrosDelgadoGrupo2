package com.alberto.tienda.service;

import com.alberto.tienda.data.*;
import com.alberto.tienda.data.dto.PedidoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.*;
import com.alberto.tienda.utils.Constantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DireccionRepository direccionRepository;

    @Autowired
    MetodoPagoRepository metodoPagoRepository;

    @Autowired
    DetalleCarritoRepository detalleCarritoRepository;

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    ProductoRepository productoRepository;

    public RespuestaGenerica savePedido(@Valid PedidoDto pedidoDto){
        //PRIMERA PARTE: llenar la tabla de pedidos
        Pedido nuevoPedido = new Pedido();
        RespuestaGenerica respuesta = new RespuestaGenerica();
        //Obtener el ID del usuario (FK)
        Usuario user = usuarioRepository.findById(pedidoDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));
        nuevoPedido.setIdUsuario(user);
        //Obtener el ID de la dirección (FK)
        List<Direccion> address = direccionRepository.findByIdAndIdUsuario(pedidoDto.getIdDireccion(), user);
        if (address.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_USUARIO_DIRECCION_NO_REGISTRADA);
        }
        nuevoPedido.setIdDireccion(address.get(0));
        //Obtener el ID del MétodoPago (FK)
        List<MetodoPago> payMethod = metodoPagoRepository.findByIdPagoAndIdUsuario(pedidoDto.getIdPago(), user);
        if (payMethod.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_USUARIO_METODO_PAGO_NO_REGISTRADO);
        }
        nuevoPedido.setIdMetodoPago(payMethod.get(0));


        float impuesto = 0.16F;
        float totalCompraNeta = 0.0F;

        //Obtener el carrito del usuario y que este activo. El .get(0) especifica que solo se traiga la primera coincidencia
        List<Carrito> car = carritoRepository.findByIdUsuarioAndEstado(user, true);
        if (car.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_CARRITO_NO_EXISTENTE_PARA_USUARIO+pedidoDto.getIdUsuario());
        }
        //Obtener el detalle del carrito
        List<DetalleCarrito> productos = detalleCarritoRepository.findByIdCarrito(car.get(0));

        // Calcular el total de la compra iterando en los artículos guardados en el carrito (tabla "detalle_carrito")
        for (DetalleCarrito productoBd: productos){
            //Obtener el ID de los productos
            Producto idProducto = productoBd.getIdProducto();
            //Validar que exista el producto.
            Producto productoStock = productoRepository.findById(idProducto.getIdProducto())
                    .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_PRODUCTO_NO_EXISTENTE + idProducto.getIdProducto()));
            //Validar que exista suficiente stock de determinado producto
            if (productoStock.getStock() < productoBd.getCantidad()){
                throw new BadRequestException(Constantes.MENSAJE_CANTIDAD_PRODUCTO_EXCESIVA + idProducto.getIdProducto());
            }else{
                //Actualizar el stock de los productos en la BD.
                productoStock.setStock(productoStock.getStock() - productoBd.getCantidad());
                productoRepository.save(productoStock);
            }
            // Sumar el precio de los productos para calcular el total
            totalCompraNeta += productoBd.getPrecio() * productoBd.getCantidad();

        }

        nuevoPedido.setFechaPedido(new Date());
        //Asignar los valores calculados
        nuevoPedido.setImpuesto(totalCompraNeta * impuesto);
        //Nota: Fue necesario quitar el impuesto en el cálculo del total porque carece de sentido que en el
        //carrito ya se haya dado un precio y resulte que el pedido hay otro.
        nuevoPedido.setTotal(totalCompraNeta);
        nuevoPedido.setEstado(true);
        pedidoRepository.save(nuevoPedido);

        // SEGUNDA PARTE: rellenar la tabla de detalles_pedido
        for (DetalleCarrito productoBd: productos){
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setIdProducto(productoBd.getIdProducto());
            detallePedido.setIdPedido(nuevoPedido);
            detallePedido.setCantidad(productoBd.getCantidad());
            detallePedido.setPrecio(productoBd.getPrecio());
            detallePedido.setTotal(productoBd.getCantidad() * productoBd.getPrecio());
            detallePedidoRepository.save(detallePedido);
        }

        //Actualizar el estado del carrito
        Carrito carrito = car.get(0);
        carrito.setEstado(false);
        carritoRepository.save(carrito);

        //Retroalimentación al usuario
        pedidoDto.setId(nuevoPedido.getIdPedido());
        pedidoDto.setFecha(nuevoPedido.getFechaPedido());
        pedidoDto.setImpuesto(nuevoPedido.getImpuesto());
        pedidoDto.setTotal(nuevoPedido.getTotal());

        respuesta.getDatos().add(pedidoDto);
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_PETICION_EXITOSA);
        return respuesta;


    }

    public RespuestaGenerica getPedidosPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));
        List<Pedido> order = pedidoRepository.findByIdUsuario(user);
        if (order.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_PEDIDO_SIN_HISTORIAL);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Pedido pedido: order){
            PedidoDto pedidoDto = new PedidoDto();
            pedidoDto.setId(pedido.getIdPedido());
            //ID del usario (FK)
            pedidoDto.setIdUsuario(idUsuario);
            //ID de la dirección (FK)
            Direccion idDireccion = pedido.getIdDireccion();
            pedidoDto.setIdDireccion(idDireccion.getId());
            //ID del usario (FK)
            MetodoPago idPago = pedido.getIdMetodoPago();
            pedidoDto.setIdPago(idPago.getIdPago());
            pedidoDto.setFecha(pedido.getFechaPedido());
            pedidoDto.setImpuesto(pedido.getImpuesto());
            pedidoDto.setTotal(pedido.getTotal());

            respuesta.getDatos().add(pedidoDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }

    public RespuestaGenerica getPedidosPorUsuarioYEstadoActivo(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));
        List<Pedido> order = pedidoRepository.findByIdUsuarioAndEstado(user, true);
        if (order.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_PEDIDO_SIN_ACTIVOS);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Pedido pedido: order){
            PedidoDto pedidoDto = new PedidoDto();
            pedidoDto.setId(pedido.getIdPedido());
            //ID del usario (FK)
            pedidoDto.setIdUsuario(idUsuario);
            //ID de la dirección (FK)
            Direccion idDireccion = pedido.getIdDireccion();
            pedidoDto.setIdDireccion(idDireccion.getId());
            //ID del usario (FK)
            MetodoPago idPago = pedido.getIdMetodoPago();
            pedidoDto.setIdPago(idPago.getIdPago());
            pedidoDto.setFecha(pedido.getFechaPedido());
            pedidoDto.setImpuesto(pedido.getImpuesto());
            pedidoDto.setTotal(pedido.getTotal());

            respuesta.getDatos().add(pedidoDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }

    //Intento de método de actualizar el estado del pedido
    //NOTA: solo se creó el método para experimentar.
    public RespuestaGenerica putEstadoPedido(Integer idPedido){
        List<Pedido> order = pedidoRepository.findByIdPedidoAndEstado(idPedido, true);
        if (order.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_PEDIDO_NO_EXISTENTE);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();
        Pedido pedido = order.get(0);
        pedido.setEstado(false);
        pedidoRepository.save(pedido);

        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_PETICION_EXITOSA);

        return respuesta;
    }
}