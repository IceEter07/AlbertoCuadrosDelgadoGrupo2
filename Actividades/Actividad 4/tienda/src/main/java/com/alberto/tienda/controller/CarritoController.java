package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @GetMapping("/obtenerCarritos")
    public List<CarritoDto> getCars(){
        return carritoService.getCarrito();
    }

    @PostMapping("/guardarCarrito")
    public CarritoDto saveCar(@RequestBody CarritoDto dto){
        return carritoService.guardarCarrito(dto);
    }
}
