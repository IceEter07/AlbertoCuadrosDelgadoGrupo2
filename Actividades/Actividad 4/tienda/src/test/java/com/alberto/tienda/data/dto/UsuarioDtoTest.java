package com.alberto.tienda.data.dto;

import com.alberto.tienda.data.UsuarioRol;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDtoTest {

    //Inicalizar el validator para los DTO que contengan @Validate
    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUser(){
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1);
        usuarioDto.setNombre("Alberto");
        usuarioDto.setApPat("Cuadros");
        usuarioDto.setApMat("Delgado");
        usuarioDto.setTelefono("4391034567");
        usuarioDto.setEmail("hola1234@gmail.com");
        usuarioDto.setPass("Hola123.");

        Set<ConstraintViolation<UsuarioDto>> violations = validator.validate(usuarioDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidUser(){
        UsuarioDto usuarioDto = new UsuarioDto();

        Set<ConstraintViolation<UsuarioDto>> violations = validator.validate(usuarioDto);

        assertEquals(5, violations.size());
    }

}
