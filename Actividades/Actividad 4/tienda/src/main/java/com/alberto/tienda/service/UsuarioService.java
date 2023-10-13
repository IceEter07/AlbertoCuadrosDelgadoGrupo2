package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioRol;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.repository.UsuarioRolRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioRolRepository usuarioRolRepository;

    @Autowired
    RolRepository rolRepository;

    public List<UsuarioDto> getUsuarios(){
        List<UsuarioDto> listaUsuarios = new ArrayList<>();

        for(Usuario user: usuarioRepository.findAll()){
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setId(user.getId());
            usuarioDto.setNombre(user.getNombre());
            usuarioDto.setApPat(user.getApPat());
            usuarioDto.setApMat(user.getApMat());
            usuarioDto.setTelefono(user.getTelefono());
            usuarioDto.setEmail(user.getEmail());
            usuarioDto.setPass(user.getPass());

            listaUsuarios.add(usuarioDto);
        }
        return listaUsuarios;
    }

    @Transactional
    public UsuarioDto guardarUsuario(@Valid UsuarioDto usuarioDto){

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApPat(usuarioDto.getApPat());
        usuario.setApMat(usuarioDto.getApMat());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPass(usuarioDto.getPass());

        //Comprobar que el usuario No este registrado.
        List<Usuario> findEmail = usuarioRepository.findByEmail(usuarioDto.getEmail());
        if(findEmail.isEmpty()){
            usuario = usuarioRepository.save(usuario);
            usuarioDto.setId(usuario.getId());
        }
        else{
            throw new BadRequestException("El email ya fue registrado");
        }

        //Asignar el rol al usuario automaticamente
        //Buscar si existe el rol "comprador", sí no esta se crea.
        List<Rol> rolBD = rolRepository.findByNombre("comprador");
        if (rolBD.isEmpty()){
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("comprador");
            rolRepository.save(nuevoRol);
        }

        Rol rolBd = rolRepository.findByNombre("comprador").get(0);
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setIdRol(rolBd);
        usuarioRol.setIdUsuario(usuario);
        usuarioRolRepository.save(usuarioRol);

        return usuarioDto;
    }
}
