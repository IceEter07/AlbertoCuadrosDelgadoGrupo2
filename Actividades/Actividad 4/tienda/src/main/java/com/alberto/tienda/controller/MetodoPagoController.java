package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.MetodoPagoDto;
import com.alberto.tienda.service.MetodoPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodoPago")
@Validated
public class MetodoPagoController {

    @Autowired
    MetodoPagoService metodoPagoService;

    @GetMapping("/obtenerMetodosPago")
    public List<MetodoPagoDto> getPayMethods(){
        return metodoPagoService.getMetodosPago();
    }
    @GetMapping("/obtenerMetodosPagoUsuario/{idUsuario}")
    public List<MetodoPagoDto> getPayMethodsByUser(@PathVariable Integer idUsuario){
        return metodoPagoService.getMetodosPagoPorUsuario(idUsuario);
    }

    @PostMapping("/guardarMetodoPago")
    public MetodoPagoDto savePayMethod(@Valid @RequestBody MetodoPagoDto dto){
        return  metodoPagoService.guardarMetodoPago(dto);
    }
}
