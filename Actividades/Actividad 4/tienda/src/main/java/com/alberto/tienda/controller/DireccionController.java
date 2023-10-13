package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.DireccionDto;
import com.alberto.tienda.service.DireccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direccion")
@Validated
public class DireccionController {

    @Autowired
    DireccionService direccionService;

    @GetMapping("/obtenerDireccion")
    public List<DireccionDto> getAddress(){
        return direccionService.getDirecciones();
    }

    @GetMapping("/obtenerDireccionUsuario/{idUsuario}")
    public List<DireccionDto> getAdrresByUser(@PathVariable Integer idUsuario){
        return direccionService.getDireccionPorUsuario(idUsuario);
    }

    @PostMapping("/guardarDireccion")
    public DireccionDto saveAddress(@Valid @RequestBody DireccionDto dto){
        return direccionService.guardarDireccion(dto);
    }

}
