package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tienda")
public class TiendaController {

    @Autowired
    TiendaService tiendaService;

    @GetMapping("/obtenerTiendas")
    public List<TiendaDto> getShop(){
        return tiendaService.getTiendas();
    }

    @PostMapping("/guardarTienda")
    public TiendaDto saveShop(@RequestBody TiendaDto dto){
        return tiendaService.guardarTienda(dto);
    }


}
