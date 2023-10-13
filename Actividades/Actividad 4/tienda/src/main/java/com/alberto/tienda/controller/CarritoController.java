package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.data.dto.ProductoAddDto;
import com.alberto.tienda.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
@Validated
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @GetMapping("/obtenerCarrito/{idUsuario}")
    public List<CarritoDto> getCarDetails(@PathVariable Integer idUsuario){
        return carritoService.getCarritoPorUsuario(idUsuario);
    }

    @PostMapping("/guardarCarrito")
    public CarritoDto saveCar(@Valid @RequestBody CarritoDto dto){
        return carritoService.guardarCarrito(dto);
    }
}
