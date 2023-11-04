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

public class PedidoDtoTest {
    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidOrder(){
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setId(1);
        pedidoDto.setIdUsuario(1);
        pedidoDto.setIdDireccion(1);
        pedidoDto.setIdPago(1);
        pedidoDto.setFecha(new Date());
        pedidoDto.setImpuesto(1567.99F);
        pedidoDto.setTotal(11098.99F);

        Set<ConstraintViolation<PedidoDto>> violations = validator.validate(pedidoDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidOrder(){
        PedidoDto pedidoDto = new PedidoDto();

        Set<ConstraintViolation<PedidoDto>> violations = validator.validate(pedidoDto);

        assertEquals(3, violations.size());

    }
}
