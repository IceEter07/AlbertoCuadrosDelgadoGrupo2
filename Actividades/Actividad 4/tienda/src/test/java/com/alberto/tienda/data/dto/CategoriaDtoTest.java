package com.alberto.tienda.data.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoriaDtoTest {
    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCategory(){
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1);
        categoriaDto.setNombre("Articulos del hogar");
        categoriaDto.setDescripcion("Ejemplo de descripcion");

        Set<ConstraintViolation<CategoriaDto>> violations = validator.validate(categoriaDto);

        assertTrue(violations.isEmpty());
    }


    @Test
    public void testInvalidCategory(){
        CategoriaDto categoriaDto = new CategoriaDto();

        Set<ConstraintViolation<CategoriaDto>> violations = validator.validate(categoriaDto);

        assertEquals(3, violations.size());
    }
}
