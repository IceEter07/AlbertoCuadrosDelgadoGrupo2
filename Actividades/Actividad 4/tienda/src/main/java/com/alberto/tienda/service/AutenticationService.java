package com.alberto.tienda.service;

import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.CredencialesDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutenticationService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public RespuestaGenerica getTokenUser(CredencialesDto credencialesDto){
        RespuestaGenerica respuesta = new RespuestaGenerica();
        //Verificar que el user exista en la BD
        List<Usuario> usuario = usuarioRepository.findByEmail(credencialesDto.getEmail());
        if (usuario.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_EMAIL_PASS_INCORRECTOS);
        }
        //Validar que la contraseña es correcta
        Usuario dataUser = usuario.get(0);
        boolean matchPasswords = passwordEncoder.matches(credencialesDto.getContrasena(), dataUser.getPass());
        if (matchPasswords){
            String token = jwtService.generateToken(credencialesDto.getEmail());
            respuesta.setMensaje(Constantes.MENSAJE_TOKEN_GENERADO);
            respuesta.getDatos().add(token);
            respuesta.setExito(true);
        }
        else{
            respuesta.setExito(false);
            respuesta.setMensaje(Constantes.MENSAJE_EMAIL_PASS_INCORRECTOS);
        }
        return respuesta;
    }
}
