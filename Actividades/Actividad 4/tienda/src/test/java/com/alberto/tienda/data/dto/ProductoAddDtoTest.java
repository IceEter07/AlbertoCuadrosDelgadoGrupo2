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

public class ProductoAddDtoTest {
    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAddProduct(){
        ProductoAddDto productoAddDto = new ProductoAddDto();
        productoAddDto.setIdProducto(1);
        productoAddDto.setCantidad(10);
        productoAddDto.setPrecioUnitario(50.99F);
        productoAddDto.setTotal(productoAddDto.getCantidad()*productoAddDto.getPrecioUnitario());

        Set<ConstraintViolation<ProductoAddDto>> violations = validator.validate(productoAddDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidAddProduct(){
        ProductoAddDto productoAddDto = new ProductoAddDto();

        Set<ConstraintViolation<ProductoAddDto>> violations = validator.validate(productoAddDto);

        assertEquals(2, violations.size());

    }
}
