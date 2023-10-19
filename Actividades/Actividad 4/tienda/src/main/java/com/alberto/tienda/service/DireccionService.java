package com.alberto.tienda.service;

import com.alberto.tienda.data.Direccion;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.DireccionRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import jakarta.validation.Valid;
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

    public RespuestaGenerica guardarDireccion(@Valid DireccionDto direccionDto){
        Usuario user = usuarioRepository.findById(direccionDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        Direccion nuevaDireccion = new Direccion();
        RespuestaGenerica respuesta = new RespuestaGenerica();

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

        respuesta.getDatos().add(direccionDto);
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);

        return respuesta;
    }

    public RespuestaGenerica getDirecciones(){
        List<Direccion> direccionnes = direccionRepository.findAll();
        if (direccionnes.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_SIN_HISTORIAL_DE_DIRECCIONES);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();
        //List<DireccionDto> listaDirecciones = new ArrayList<>();
        
        for(Direccion address: direccionnes){
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

            respuesta.getDatos().add(direccionDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }

    public RespuestaGenerica getDireccionPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        //List<DireccionDto> listaDirecciones = new ArrayList<>();
        RespuestaGenerica respuesta = new RespuestaGenerica();

        List<Direccion> direccion = direccionRepository.findByIdUsuario(user);
        if (direccion.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_USUARIO_SIN_DIRECCION);
        }

        for(Direccion address: direccion){
            DireccionDto direccionDto = new DireccionDto();
            direccionDto.setId(address.getId());
            //Id del usuario (FK)
            Usuario idUser= address.getIdUsuario();
            direccionDto.setIdUsuario(idUser.getId());
            direccionDto.setPais(address.getPais());
            direccionDto.setEstado(address.getEstado());
            direccionDto.setMunicipio(address.getMunicipio());
            direccionDto.setColonia(address.getColonia());
            direccionDto.setCalle(address.getCalle());
            direccionDto.setNumeroExt(address.getNumExt());
            direccionDto.setNumeroInt(address.getNumInt());

            respuesta.getDatos().add(direccionDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }
}
