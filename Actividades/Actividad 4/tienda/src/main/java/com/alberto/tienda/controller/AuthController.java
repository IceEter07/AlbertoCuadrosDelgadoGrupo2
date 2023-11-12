package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CredencialesDto;
import com.alberto.tienda.service.JwtService;
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

    //Constructor para inyectat JwtService
    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    //Petición para manejar el login
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody CredencialesDto credencialesDto){

        //Comprobar que las credenciales coincidan con las configuradas
        if (configuredUsername.equals(credencialesDto.getUsuario()) && configuredPassword.equals(credencialesDto.getContrasena())){
            //Genera el token JWT
        String token = jwtService.generateToken(credencialesDto.getUsuario());

        //Crea un mapa para la respuesta con el token JWT
            Map<String, String> response = new HashMap<>();
            response.put("access_token", token);

            //Devuelve un HTTP OK con el token
            return ResponseEntity.ok(response);
        }
        //Devuelve un HTTP 400 si las credencialas son erroneas
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
