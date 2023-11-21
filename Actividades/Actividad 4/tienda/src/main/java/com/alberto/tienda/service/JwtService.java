package com.alberto.tienda.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    //Inyección del valor de la clave secreta para la firma de tokens JWT
    @Value("${jwt.secret}")
    private String secret;

    //Método para generar un token JWT para un nombre de usuario
    public String generateToken(String email){
        //Tiempo de expiración en milisegundos
        long expirationTimeInMilis = 3600000;

        //Covertir las claves secreta en bytes usando UTF-8
        byte[] secretKeyBytes = secret.getBytes(StandardCharsets.UTF_8);

        //Contruir el token JWT

        return Jwts.builder()
                .setSubject(email) //Establece el username como sujeto del token
                .setIssuedAt(new Date()) //Hora de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMilis)) //Fecha de expiracion del token
                .signWith(SignatureAlgorithm.HS512, secretKeyBytes) //Firma el token con el SHA-512 y la clave secreta
                .compact(); //Compacta el JWT en un String
    }
}
