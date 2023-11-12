package com.alberto.tienda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Bean para configurar la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenFilter jwtTokenFilter) throws Exception{
        http
                //Desactiva el CSRF. útil para las API REST que no usan cookies.
                //Parece que se usa para que se pueda acceder a la API desde otros hosts.
                .csrf(AbstractHttpConfigurer::disable)
                //Configura la gestión de sesiones para que sea sin estado
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Configura las reglas de autorización de solitudes HTTP
                .authorizeHttpRequests(requests ->requests
                    //Permite todas las solicitudes a "auth/login" sin autenticación
                        .requestMatchers("/auth/login").permitAll()
                        //Todas las demás solicitudes requieren autenticación
                        .anyRequest().authenticated()
                )
                //Agrega el filtro JwtTokenFilter antes del filtro de autenticación por username y password
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //Construye y devuelve la cadena de filtros de seguridad configurada
        return  http.build();
    }

    //Bean para crear una intancia de JwtTokenFilter
    @Bean
    public JwtTokenFilter jwtTokenFilter(@Value("${jwt.secret}") String secret){
        //Inyecta la clave secreta (definida en las propiedades de la aplicación) en el filtro JWT
        return new JwtTokenFilter(secret);
    }
}
