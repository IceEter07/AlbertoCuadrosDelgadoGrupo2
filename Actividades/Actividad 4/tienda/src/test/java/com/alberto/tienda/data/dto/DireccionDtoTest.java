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

public class DireccionDtoTest {

    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAddress(){
        DireccionDto direccionDto = new DireccionDto();
        direccionDto.setId(1);
        direccionDto.setIdUsuario(1);
        direccionDto.setPais("Mexico");
        direccionDto.setEstado("Guanajuato");
        direccionDto.setMunicipio("Valle de Santiago");
        direccionDto.setColonia("Paredones");
        direccionDto.setCalle("Universidad");
        direccionDto.setNumeroExt(10);
        direccionDto.setNumeroInt(0);

        Set<ConstraintViolation<DireccionDto>> violations = validator.validate(direccionDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidAddress(){
        DireccionDto direccionDto = new DireccionDto();

        Set<ConstraintViolation<DireccionDto>> violations = validator.validate(direccionDto);

        assertEquals(8, violations.size());
    }


}
