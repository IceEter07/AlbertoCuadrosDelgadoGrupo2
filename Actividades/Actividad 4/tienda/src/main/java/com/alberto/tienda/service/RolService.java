package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.RolDto;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.utils.Constantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;


    //Estoy en conflicto por la tabla roles. No se si es necesario que
    //El usuario obtenga la lista de roles o que tambien pueda crear roles específicos.

    //De momento voy a crear los metodos get y findAll solo para prácticar
    public RespuestaGenerica guardarRol(@Valid RolDto rolDto){
        RespuestaGenerica respuesta = new RespuestaGenerica();
        Rol nuevoRol = new Rol();
        nuevoRol.setNombre(rolDto.getNombre());
        List<Rol> findRol = rolRepository.findByNombre(rolDto.getNombre());
        //Comprobar que el rol no exista
        if (findRol.isEmpty()){
            rolRepository.save(nuevoRol);
            rolDto.setId(nuevoRol.getId());
            respuesta.getDatos().add(rolDto);
            respuesta.setExito(true);
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
        }
        else {
            throw new BadRequestException(Constantes.MENSAJE_ROL_YA_REGISTRADO);
        }
        return respuesta;
    }

    public RespuestaGenerica getRoles(){
        List<Rol> roles = rolRepository.findAll();
        if (roles.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_ROL_NO_EXISTENTE);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();


        for(Rol rol: roles){
            RolDto rolDto = new RolDto();
            rolDto.setId(rol.getId());
            rolDto.setNombre(rol.getNombre());
            respuesta.getDatos().add(rolDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }
}
