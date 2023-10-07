package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioRol;
import com.alberto.tienda.data.dto.RolAddDto;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.repository.UsuarioRolRepository;
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

    public UsuarioDto guardarUsuario(UsuarioDto usuarioDto){
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApPat(usuarioDto.getApPat());
        usuario.setApMat(usuarioDto.getApMat());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPass(usuarioDto.getPass());

        usuario = usuarioRepository.save(usuario);
        usuarioDto.setId(usuario.getId());

        //Asignar el rol al usuario automaticamente
        Rol rolBd = rolRepository.getReferenceById(1);
        UsuarioRol usuarioRol = new UsuarioRol();
        // Hacer que el rol sea el 1 por defecto (que sea comprador)
        usuarioRol.setIdRol(rolBd);
        usuarioRol.setIdUsuario(usuario);
        usuarioRolRepository.save(usuarioRol);

        return usuarioDto;
    }

    private Rol buscarRolPorId(int idRol){
        Rol rol = rolRepository.getReferenceById(idRol);
        return rol;
    }
}
