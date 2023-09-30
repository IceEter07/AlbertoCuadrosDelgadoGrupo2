package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.MetodoPagoDto;
import com.alberto.tienda.service.MetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodoPago")
public class MetodoPagoController {

    @Autowired
    MetodoPagoService metodoPagoService;

    @GetMapping("/obtenerMetodosPago")
    public List<MetodoPagoDto> getPayMethods(){
        return metodoPagoService.getMetodosPago();
    }

    @PostMapping("/guardarMetodoPago")
    public MetodoPagoDto savePayMethod(@RequestBody MetodoPagoDto dto){
        return  metodoPagoService.guardarMetodoPago(dto);
    }
}
