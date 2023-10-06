package com.alberto.tienda.service;

import com.alberto.tienda.data.Direccion;
import com.alberto.tienda.data.MetodoPago;
import com.alberto.tienda.data.Pedido;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.PedidoDto;
import com.alberto.tienda.repository.DireccionRepository;
import com.alberto.tienda.repository.MetodoPagoRepository;
import com.alberto.tienda.repository.PedidoRepository;
import com.alberto.tienda.repository.UsuarioRepository;
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

    public PedidoDto savePedido(PedidoDto pedidoDto){
        Pedido nuevoPedido = new Pedido();
        //Usuario (FK)
        Usuario user = buscarUsuarioPorId(pedidoDto.getIdUsuario());
        nuevoPedido.setIdUsuario(user);
        //Dirección (FK)
        Direccion address = buscarDireccionPorId(pedidoDto.getIdDireccion());
        nuevoPedido.setIdDireccion(address);
        //MetodoPago (FK)
        MetodoPago payMethod = buscarPagoPorId(pedidoDto.getIdPago());
        nuevoPedido.setIdMetodoPago(payMethod);
        nuevoPedido.setFechaPedido(new Date());
        nuevoPedido.setImpuesto(BigDecimal.valueOf(0.16));
        nuevoPedido.setTotal(pedidoDto.getTotal());
        nuevoPedido.setEstado(pedidoDto.getEstado());

        pedidoRepository.save(nuevoPedido);
        pedidoDto.setId(nuevoPedido.getIdPedido());
        pedidoDto.setFecha(nuevoPedido.getFechaPedido());
        pedidoDto.setImpuesto(nuevoPedido.getImpuesto());

        return pedidoDto;


    }

    private Usuario buscarUsuarioPorId(int idUsuario){
        Usuario user = usuarioRepository.getReferenceById(idUsuario);
        return user;
    }

    private Direccion buscarDireccionPorId(int idDireccion){
        Direccion address = direccionRepository.getReferenceById(idDireccion);
        return address;
    }

    private MetodoPago buscarPagoPorId(int idPago){
        MetodoPago payMethod = metodoPagoRepository.getReferenceById(idPago);
        return payMethod;
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
            pedidoDto.setEstado(pedido.getEstado());

            listaPedidos.add(pedidoDto);
        }
        return listaPedidos;
    }
}