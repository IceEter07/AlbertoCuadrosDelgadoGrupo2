package com.alberto.tienda.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    @GetMapping("/getusuario")
    public String getuser(){
        return "hola";
    }
}
