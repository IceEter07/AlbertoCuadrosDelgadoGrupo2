package com.alberto.tienda.service;

import com.alberto.tienda.data.MetodoPago;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.MetodoPagoDto;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.MetodoPagoRepository;
import com.alberto.tienda.repository.UsuarioRepository;
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

    public MetodoPagoDto guardarMetodoPago(@Valid MetodoPagoDto metodoPagoDto){
        MetodoPago nuevoMetodoPago = new MetodoPago();
        Usuario user = usuarioRepository.findById(metodoPagoDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        nuevoMetodoPago.setIdUsuario(user);
        nuevoMetodoPago.setNombre(metodoPagoDto.getNombre());
        nuevoMetodoPago.setDescripcion(metodoPagoDto.getDescripcion());
        metodoPagoRepository.save(nuevoMetodoPago);

        metodoPagoDto.setId(nuevoMetodoPago.getIdPago());
        return metodoPagoDto;

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

    public List<MetodoPagoDto> getMetodosPagoPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));

        List<MetodoPagoDto> listaMetodosPago = new ArrayList<>();

        for(MetodoPago payMethod: metodoPagoRepository.findByIdUsuario(user)){
            MetodoPagoDto metodoPagoDto = new MetodoPagoDto();
            metodoPagoDto.setId(payMethod.getIdPago());
            //Id del usuario (FK)
            metodoPagoDto.setIdUsuario(idUsuario);
            metodoPagoDto.setNombre(payMethod.getNombre());
            metodoPagoDto.setDescripcion(payMethod.getDescripcion());

            listaMetodosPago.add(metodoPagoDto);
        }
        return listaMetodosPago;
    }
}
