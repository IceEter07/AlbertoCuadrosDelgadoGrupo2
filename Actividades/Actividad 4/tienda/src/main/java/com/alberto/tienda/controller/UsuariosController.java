package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@Validated
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

//    @GetMapping("/obtenerUsuario")
//    public List<UsuarioDto> getAllUsers(){
//        return usuarioService.getUsuarios();
//    }
    @GetMapping("/obtenerUsuarios")
    public ResponseEntity<RespuestaGenerica> getAllUsers(){
        RespuestaGenerica respuesta = usuarioService.getUsuarios();
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status = HttpStatus.NOT_FOUND;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

    @GetMapping("/obtenerUsuario/{idUsuario}")
    public ResponseEntity<RespuestaGenerica> getUser(@PathVariable Integer idUsuario){

        RespuestaGenerica respuesta = usuarioService.getUsuario(idUsuario);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status = HttpStatus.NOT_FOUND;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }


    @PostMapping("/guardarUsuario")
    public ResponseEntity<RespuestaGenerica> guardarUsuario(@Valid @RequestBody UsuarioDto usuarioDto){
        RespuestaGenerica respuestaGenerica = usuarioService.guardarUsuario(usuarioDto);
        HttpStatus status = null;
        if (respuestaGenerica.isExito()){
            status = HttpStatus.OK;
            respuestaGenerica.setCodigo(status.value());
        }
        else {
            status = HttpStatus.BAD_REQUEST;
            respuestaGenerica.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuestaGenerica, status);
    }


}
