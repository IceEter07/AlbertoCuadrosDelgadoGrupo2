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

public class TiendaDtoTest {

    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidShop(){
        TiendaDto tiendaDto = new TiendaDto();

        tiendaDto.setId(1);
        tiendaDto.setIdUsuario(1);
        tiendaDto.setRfc("CUDA78462OP2");
        tiendaDto.setNombre("Microsoft");
        tiendaDto.setDescripcion("Ejemplo de descripcion");
        tiendaDto.setRating(5);

        Set<ConstraintViolation<TiendaDto>> violations = validator.validate(tiendaDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidShop(){
        TiendaDto tiendaDto = new TiendaDto();

        Set<ConstraintViolation<TiendaDto>> violations = validator.validate(tiendaDto);

        assertEquals(5, violations.size());
    }
}
