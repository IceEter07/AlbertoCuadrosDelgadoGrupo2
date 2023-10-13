package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.UsuarioDto;
import com.alberto.tienda.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/obtenerUsuario")
    public List<UsuarioDto> getAllUsers(){
        return usuarioService.getUsuarios();
    }
    @PostMapping("/guardarUsuario")
    public UsuarioDto guardarUsuario(@Valid @RequestBody UsuarioDto dto){
        return usuarioService.guardarUsuario(dto);
    }
}
