package com.alberto.tienda.data.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RolDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidRol(){
        RolDto rolDto = new RolDto();
        rolDto.setId(1);
        rolDto.setNombre("cliente");

        Set<ConstraintViolation<RolDto>> violations = validator.validate(rolDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidRol(){
        RolDto rolDto = new RolDto();

        Set<ConstraintViolation<RolDto>> violations = validator.validate(rolDto);
        assertEquals(1, violations.size());
    }
}
