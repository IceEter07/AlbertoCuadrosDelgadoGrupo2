package com.alberto.tienda.data.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.util.Set;

public class NotificacionDtoTest {

    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidNotify(){
        NotificacionDto notificacionDto = new NotificacionDto();
        notificacionDto.setId(1);
        notificacionDto.setIdUsuario(1);
        notificacionDto.setMensaje("Nueva notificación");
        notificacionDto.setFecha(new Date());

        Set<ConstraintViolation<NotificacionDto>> violations = validator.validate(notificacionDto);

        assertTrue(violations.isEmpty());
    }


    @Test
    public void testInvalidNotify(){
        NotificacionDto notificacionDto = new NotificacionDto();

        Set<ConstraintViolation<NotificacionDto>> violations = validator.validate(notificacionDto);

        assertEquals(2, violations.size());
    }



}
