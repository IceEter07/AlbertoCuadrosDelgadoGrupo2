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

public class ProductoDtoTest {
    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidProduct(){
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(1);
        productoDto.setIdCategoria(1);
        productoDto.setIdTienda(1);
        productoDto.setCodigo("ASDF1236DF3");
        productoDto.setNombre("Fabuloso");
        productoDto.setPrecio(50.99F);
        productoDto.setNumeroProductos(400);
        productoDto.setDescripcion("Ejemplo de descripcion");

        Set<ConstraintViolation<ProductoDto>> violations = validator.validate(productoDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidProduct(){
        ProductoDto productoDto = new ProductoDto();

        Set<ConstraintViolation<ProductoDto>> violations = validator.validate(productoDto);

        assertEquals(7, violations.size());
    }
}
