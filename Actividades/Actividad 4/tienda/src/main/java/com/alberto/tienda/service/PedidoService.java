package com.alberto.tienda.service;

import com.alberto.tienda.data.*;
import com.alberto.tienda.data.dto.PedidoDto;
import com.alberto.tienda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public PedidoDto savePedido(PedidoDto pedidoDto){
        //PRIMERA PARTE: llenar la tabla de pedidos
        Pedido nuevoPedido = new Pedido();
        //Obtener el ID del usuario (FK)
        Usuario user = usuarioRepository.getReferenceById(pedidoDto.getIdUsuario());
        nuevoPedido.setIdUsuario(user);
        //Obtener el ID de la dirección (FK)
        Direccion address = direccionRepository.getReferenceById(pedidoDto.getIdDireccion());
        nuevoPedido.setIdDireccion(address);
        //Obtener el ID del MétodoPago (FK)
        MetodoPago payMethod = metodoPagoRepository.getReferenceById(pedidoDto.getIdPago());
        nuevoPedido.setIdMetodoPago(payMethod);


        float impuesto = 0.16F;
        float totalCompraNeta = 0.0F;

        //Obtener el carrito del usuario y que este activo. El .get(0) especifica que solo se traiga la primera coincidencia
        Carrito car = carritoRepository.findByIdUsuarioAndEstado(user, true).get(0);
        //Obtener el detalle del carrito
        List<DetalleCarrito> productos = detalleCarritoRepository.findByIdCarrito(car);

        // Calcular el total de la compra iterando en los articulos guardados en el carrito
        for (DetalleCarrito productoBd: productos){
            // Sumar el precio de los productos para calcular el total
            totalCompraNeta += productoBd.getPrecio() * productoBd.getCantidad();
        }

        nuevoPedido.setFechaPedido(new Date());
        //Asignar los valores calculados
        nuevoPedido.setImpuesto(totalCompraNeta * impuesto);
        nuevoPedido.setTotal(totalCompraNeta + impuesto);
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

        //Retroalimentación al usuario
        pedidoDto.setId(nuevoPedido.getIdPedido());
        pedidoDto.setFecha(nuevoPedido.getFechaPedido());
        pedidoDto.setImpuesto(nuevoPedido.getImpuesto());
        pedidoDto.setTotal(nuevoPedido.getTotal());

        return pedidoDto;


    }

    public List<PedidoDto> getPedidos(){
        List<PedidoDto> listaPedidos = new ArrayList<>();

        for (Pedido pedido: pedidoRepository.findAll()){
            PedidoDto pedidoDto = new PedidoDto();

            pedidoDto.setId(pedido.getIdPedido());
            //ID del usario (FK)
            Usuario idUsuario = pedido.getIdUsuario();
            pedidoDto.setIdUsuario(idUsuario.getId());
            //ID de la dirección (FK)
            Direccion idDireccion = pedido.getIdDireccion();
            pedidoDto.setIdDireccion(idDireccion.getId());
            //ID del usario (FK)
            MetodoPago idPago = pedido.getIdMetodoPago();
            pedidoDto.setIdPago(idPago.getIdPago());
            pedidoDto.setFecha(pedido.getFechaPedido());
            pedidoDto.setImpuesto(pedido.getImpuesto());
            pedidoDto.setTotal(pedido.getTotal());

            listaPedidos.add(pedidoDto);
        }
        return listaPedidos;
    }
}