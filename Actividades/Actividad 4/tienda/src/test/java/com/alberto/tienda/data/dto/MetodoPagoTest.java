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

public class MetodoPagoTest {
    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidMetodoPago(){
        MetodoPagoDto metodoPagoDto = new MetodoPagoDto();
        metodoPagoDto.setId(1);
        metodoPagoDto.setIdUsuario(1);
        metodoPagoDto.setNombre("Tarjeta de debito");
        metodoPagoDto.setDescripcion("Tarjeta de ahorro");

        Set<ConstraintViolation<MetodoPagoDto>> violations = validator.validate(metodoPagoDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidMetodoPago(){
        MetodoPagoDto metodoPagoDto = new MetodoPagoDto();

        Set<ConstraintViolation<MetodoPagoDto>> violations = validator.validate(metodoPagoDto);
        assertEquals(3, violations.size());
    }

}