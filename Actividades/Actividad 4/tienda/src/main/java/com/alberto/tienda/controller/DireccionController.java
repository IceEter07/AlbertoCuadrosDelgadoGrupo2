package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direccion")
public class DireccionController {

    @Autowired
    DireccionService direccionService;

    @GetMapping("/obtenerDireccion")
    public List<DireccionDto> getAddress(){
        return direccionService.getDirecciones();
    }

    @PostMapping("/guardarDireccion")
    public DireccionDto saveAddress(@RequestBody DireccionDto dto){
        return direccionService.guardarDireccion(dto);
    }

}
