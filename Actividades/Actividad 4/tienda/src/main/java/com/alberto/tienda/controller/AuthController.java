package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CredencialesDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.AutenticationService;
import com.alberto.tienda.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    //Inyección de valores de propiedades para el username y la password
    @Value("${myapp.username}")
    private String configuredUsername;

    @Value("${myapp.password}")
    private String configuredPassword;

    //Inyección automática de la dependencia JwtService
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private AutenticationService autenticationService;

    //Constructor para inyectat JwtService
    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    //Petición para manejar el login
    @PostMapping("/auth/login")
    public ResponseEntity<RespuestaGenerica> authenticate(@Valid @RequestBody CredencialesDto credencialesDto){
        RespuestaGenerica respuesta = autenticationService.getTokenUser(credencialesDto);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status = HttpStatus.BAD_REQUEST;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }
}
