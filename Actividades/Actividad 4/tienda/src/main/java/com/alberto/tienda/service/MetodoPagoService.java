package com.alberto.tienda.service;

import com.alberto.tienda.data.MetodoPago;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.MetodoPagoDto;
import com.alberto.tienda.repository.MetodoPagoRepository;
import com.alberto.tienda.repository.UsuarioRepository;
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

    public MetodoPagoDto guardarMetodoPago(MetodoPagoDto metodoPagoDto){
        MetodoPago nuevoMetodoPago = new MetodoPago();
        Usuario user = buscarUsuarioPorId(metodoPagoDto.getIdUsuario());
        nuevoMetodoPago.setIdUsuario(user);
        nuevoMetodoPago.setNombre(metodoPagoDto.getNombre());
        nuevoMetodoPago.setDescripcion(metodoPagoDto.getDescripcion());
        metodoPagoRepository.save(nuevoMetodoPago);

        metodoPagoDto.setId(nuevoMetodoPago.getIdPago());
        return metodoPagoDto;

    }

    private Usuario buscarUsuarioPorId(int idUsuario){
        Usuario user = usuarioRepository.getReferenceById(idUsuario);
        return user;
    }

    public List<MetodoPagoDto> getMetodosPago(){
        List<MetodoPagoDto> listaMetodosPago = new ArrayList<>();

        for(MetodoPago payMethod: metodoPagoRepository.findAll()){
            MetodoPagoDto metodoPagoDto = new MetodoPagoDto();
            metodoPagoDto.setId(payMethod.getIdPago());
            //Id del usuario (FK)
            Usuario idUsuario = payMethod.getIdUsuario();
            metodoPagoDto.setIdUsuario(idUsuario.getId());
            metodoPagoDto.setNombre(payMethod.getNombre());
            metodoPagoDto.setDescripcion(payMethod.getDescripcion());

            listaMetodosPago.add(metodoPagoDto);
        }
        return listaMetodosPago;
    }
}
