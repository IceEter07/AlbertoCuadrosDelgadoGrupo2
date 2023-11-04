package com.alberto.tienda.data.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarritoDtoTest {
    Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCar(){
        CarritoDto carritoDto = new CarritoDto();
        carritoDto.setId(1);
        carritoDto.setIdUsuario(1);
        carritoDto.setTotal(100F);

        List<ProductoAddDto> productos = new ArrayList<>();
        ProductoAddDto productoAddDto = new ProductoAddDto();
        productoAddDto.setIdProducto(1);
        productoAddDto.setCantidad(10);
        productoAddDto.setPrecioUnitario(23.99F);
        productoAddDto.setTotal(productoAddDto.getCantidad()*productoAddDto.getPrecioUnitario());

        ProductoAddDto productoAddDto2 = new ProductoAddDto();
        productoAddDto2.setIdProducto(1);
        productoAddDto2.setCantidad(10);
        productoAddDto2.setPrecioUnitario(23.99F);
        productoAddDto2.setTotal(productoAddDto.getCantidad()*productoAddDto.getPrecioUnitario());

        productos.add(productoAddDto);
        productos.add(productoAddDto2);
        carritoDto.setProductos(productos);

        Set<ConstraintViolation<CarritoDto>> violations = validator.validate(carritoDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidCar(){
        CarritoDto carritoDto = new CarritoDto();

        Set<ConstraintViolation<CarritoDto>> violations = validator.validate(carritoDto);

        assertEquals(1, violations.size());
    }
}
