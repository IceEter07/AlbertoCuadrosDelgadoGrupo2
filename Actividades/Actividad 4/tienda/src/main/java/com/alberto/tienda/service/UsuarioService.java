package com.alberto.tienda.service;

import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

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

    public UsuarioDto guardarUsuario(UsuarioDto dto){
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApPat(dto.getApPat());
        usuario.setApMat(dto.getApMat());
        usuario.setTelefono(dto.getTelefono());
        usuario.setEmail(dto.getEmail());
        usuario.setPass(dto.getPass());
        usuario = usuarioRepository.save(usuario);
        dto.setId(usuario.getId());

        return dto;
    }
}
