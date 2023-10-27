package com.alberto.tienda.data.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResenaDtoTest {
    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidComment(){
        ResenaDto resenaDto = new ResenaDto();
        resenaDto.setId(1);
        resenaDto.setIdUsuario(1);
        resenaDto.setIdProducto(1);
        resenaDto.setComentario("Ejemplo de comentario");
        resenaDto.setFecha(new Date());

        Set<ConstraintViolation<ResenaDto>> violations = validator.validate(resenaDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidComment(){
        ResenaDto resenaDto = new ResenaDto();

        Set<ConstraintViolation<ResenaDto>> violations = validator.validate(resenaDto);

        assertEquals(3, violations.size());
    }
}
