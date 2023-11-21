package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioRol;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.UserInformation.EmailDto;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.repository.UsuarioRolRepository;
import com.alberto.tienda.utils.Constantes;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioRolRepository usuarioRolRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public RespuestaGenerica getUsuarios(){
        List<Usuario> users = usuarioRepository.findAll();
        if (users.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE);
        }
        RespuestaGenerica respuesta  = new RespuestaGenerica();

        for(Usuario user: users){
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setId(user.getId());
            usuarioDto.setNombre(user.getNombre());
            usuarioDto.setApPat(user.getApPat());
            usuarioDto.setApMat(user.getApMat());
            usuarioDto.setTelefono(user.getTelefono());
            usuarioDto.setEmail(user.getEmail());
            usuarioDto.setPass(user.getPass());

            respuesta.getDatos().add(usuarioDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);

        return respuesta;
    }

    public RespuestaGenerica getUsuario(Integer idUsuario) {

        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        RespuestaGenerica respuesta = new RespuestaGenerica();
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setId(user.getId());
            usuarioDto.setNombre(user.getNombre());
            usuarioDto.setApPat(user.getApPat());
            usuarioDto.setApMat(user.getApMat());
            usuarioDto.setTelefono(user.getTelefono());
            usuarioDto.setEmail(user.getEmail());
            usuarioDto.setPass(user.getPass());

            respuesta.getDatos().add(usuarioDto);
            respuesta.setExito(true);
            respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }

    public RespuestaGenerica guardarUsuario(@Valid UsuarioDto usuarioDto){
        RespuestaGenerica respuesta = new RespuestaGenerica();
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApPat(usuarioDto.getApPat());
        usuario.setApMat(usuarioDto.getApMat());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPass(passwordEncoder.encode(usuarioDto.getPass()));

        //Comprobar que el usuario No este registrado.
        List<Usuario> findEmail = usuarioRepository.findByEmail(usuarioDto.getEmail());
        if(findEmail.isEmpty()){
            usuario = usuarioRepository.save(usuario);
            usuarioDto.setId(usuario.getId());
            respuesta.setExito(true);
            respuesta.getDatos().add(usuarioDto);
            respuesta.setMensaje(Constantes.MENSAJE_USUARIO_REGISTRADO_EXISTOSAMENTE+usuarioDto.getId());
        }
        else{
            throw new BadRequestException(Constantes.MENSAJE_EMAIL_YA_REGISTRADO);
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

        return respuesta;
    }

    public RespuestaGenerica actualizarEmail(@Valid EmailDto emailDto){
        RespuestaGenerica respuesta = new RespuestaGenerica();

        Usuario findEmail = usuarioRepository.findById(emailDto.getId())
                .orElseThrow(()-> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        findEmail.setEmail(emailDto.getEmail());
        usuarioRepository.save(findEmail);
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_PETICION_EXITOSA);
        return respuesta;
    }
}
