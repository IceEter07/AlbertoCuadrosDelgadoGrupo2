package com.alberto.tienda.service;

import com.alberto.tienda.data.MetodoPago;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.MetodoPagoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.MetodoPagoRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetodoPagoService {
    @Autowired
    MetodoPagoRepository metodoPagoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public RespuestaGenerica guardarMetodoPago(@Valid MetodoPagoDto metodoPagoDto){
        Usuario user = usuarioRepository.findById(metodoPagoDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));
        RespuestaGenerica respuesta = new RespuestaGenerica();
        MetodoPago nuevoMetodoPago = new MetodoPago();

        nuevoMetodoPago.setIdUsuario(user);
        nuevoMetodoPago.setNombre(metodoPagoDto.getNombre());
        nuevoMetodoPago.setDescripcion(metodoPagoDto.getDescripcion());
        metodoPagoRepository.save(nuevoMetodoPago);
        metodoPagoDto.setId(nuevoMetodoPago.getIdPago());
        respuesta.getDatos().add(metodoPagoDto);
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
        return respuesta;

    }

    public RespuestaGenerica getMetodosPagoPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        List<MetodoPago> metodosPago = metodoPagoRepository.findByIdUsuario(user);
        if (metodosPago.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_SIN_HISTORIAL_DE_METODOS_DE_PAGO);
        }

        //List<MetodoPagoDto> listaMetodosPago = new ArrayList<>();
        RespuestaGenerica respuesta = new RespuestaGenerica();


        for(MetodoPago payMethod: metodosPago){
            MetodoPagoDto metodoPagoDto = new MetodoPagoDto();
            metodoPagoDto.setId(payMethod.getIdPago());
            //Id del usuario (FK)
            metodoPagoDto.setIdUsuario(idUsuario);
            metodoPagoDto.setNombre(payMethod.getNombre());
            metodoPagoDto.setDescripcion(payMethod.getDescripcion());

            respuesta.getDatos().add(metodoPagoDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }
}
