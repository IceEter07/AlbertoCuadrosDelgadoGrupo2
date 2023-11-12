package com.alberto.tienda.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

//Clase para filtrar los token JWT en las solicitudes HTTP
public class JwtTokenFilter extends OncePerRequestFilter {
    //Almacenar la clave secreta para verificar el token JWT
    private String secret;

    //Constructor que inicializa el filtro con una clave secreta
    public JwtTokenFilter(String s){
        this.secret = s;
    }

    //Override del metodo OncePerRequestFilter para filtrar las solicitudes
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException{

        //Obtener el encabezado de la autorización de la solicitud HTTP
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Verifica si el encabezado de autorización no es nulo y sí comienza con "Bearer "
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            //Extrae el token JWT del encabezado
            String token = tokenHeader.split(" ")[1].trim();

            try{
                //Convierte la clave secreta a bytes usando UTF-8
                byte[] secretKeysBytes = secret.getBytes(StandardCharsets.UTF_8);

                //Parsea y valida el token JWT
                Jws<Claims> claims = Jwts.parserBuilder()
                        .setSigningKey(secretKeysBytes)
                        .build()
                        .parseClaimsJws(token);

                //Obtiene el username (subject) del cuerpo del token.
                String username = claims.getBody().getSubject();

                //Si el username no es null y no hay una auth actual establece una nueva auth con el username
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username, null, Collections.emptyList());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            catch (Exception e){
                //En caso de error (token inválido) se imprimen los errores correspondientes
                e.printStackTrace();
                SecurityContextHolder.clearContext();
            }
        }

        //Continua con el resto de la cadena de filtros
        chain.doFilter(request, response);


    }
}
