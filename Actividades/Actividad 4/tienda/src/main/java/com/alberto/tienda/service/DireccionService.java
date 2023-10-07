package com.alberto.tienda.service;

import com.alberto.tienda.data.Direccion;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.repository.DireccionRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DireccionService {
    @Autowired
    DireccionRepository direccionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public DireccionDto guardarDireccion(DireccionDto direccionDto){
        Direccion nuevaDireccion = new Direccion();
        Usuario user = buscarUsuarioPorId(direccionDto.getIdUsuario());
        nuevaDireccion.setIdUsuario(user);
        nuevaDireccion.setPais(direccionDto.getPais());
        nuevaDireccion.setEstado(direccionDto.getEstado());
        nuevaDireccion.setMunicipio(direccionDto.getMunicipio());
        nuevaDireccion.setColonia(direccionDto.getColonia());
        nuevaDireccion.setCalle(direccionDto.getCalle());
        nuevaDireccion.setNumExt(direccionDto.getNumeroExt());
        nuevaDireccion.setNumInt(direccionDto.getNumeroInt());
        direccionRepository.save(nuevaDireccion);
        direccionDto.setId(nuevaDireccion.getId());

        return direccionDto;
    }

    private Usuario buscarUsuarioPorId(int idUsuario){
        Usuario user = usuarioRepository.getReferenceById(idUsuario);
        return user;
    }
    
    
    public List<DireccionDto> getDirecciones(){
        List<DireccionDto> listaDirecciones = new ArrayList<>();
        
        for(Direccion address: direccionRepository.findAll()){
            DireccionDto direccionDto = new DireccionDto();
            direccionDto.setId(address.getId());
            //Id del usuario (FK)
            Usuario idUsuario = address.getIdUsuario();
            direccionDto.setIdUsuario(idUsuario.getId());
            direccionDto.setPais(address.getPais());
            direccionDto.setEstado(address.getEstado());
            direccionDto.setMunicipio(address.getMunicipio());
            direccionDto.setColonia(address.getColonia());
            direccionDto.setCalle(address.getCalle());
            direccionDto.setNumeroExt(address.getNumExt());
            direccionDto.setNumeroInt(address.getNumInt());

            listaDirecciones.add(direccionDto);
        }
        return listaDirecciones;
    }
}
