package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.dto.RolDto;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.repository.RolRepository;
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
    public RolDto guardarRol(@Valid RolDto rolDto){
        Rol nuevoRol = new Rol();
        nuevoRol.setNombre(rolDto.getNombre());
        List<Rol> findRol = rolRepository.findByNombre(rolDto.getNombre());
        //Comprobar que el rol no exista
        if (findRol.isEmpty()){
            rolRepository.save(nuevoRol);
            rolDto.setId(nuevoRol.getId());
        }
        else {
            throw new BadRequestException("El rol ya esta registrado");
        }
        return rolDto;
    }

    public List<RolDto> getRoles(){
        List<RolDto> listaRoles = new ArrayList<>();

        for(Rol rol: rolRepository.findAll()){
            RolDto rolDto = new RolDto();
            rolDto.setId(rol.getId());
            rolDto.setNombre(rol.getNombre());

            listaRoles.add(rolDto);
        }
        return listaRoles;
    }
}
