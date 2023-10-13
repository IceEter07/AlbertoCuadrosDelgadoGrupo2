package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.service.TiendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tienda")
@Validated
public class TiendaController {

    @Autowired
    TiendaService tiendaService;

    @GetMapping("/obtenerTiendas")
    public List<TiendaDto> getShop(){
        return tiendaService.getTiendas();
    }

    @GetMapping("/obtenerTiendasUsuario/{idUsuario}")
    public List<TiendaDto> getShopByUser(@PathVariable Integer idUsuario){
        return tiendaService.getTiendasPorUsuario(idUsuario);
    }

    @PostMapping("/guardarTienda")
    public TiendaDto saveShop(@Valid @RequestBody TiendaDto dto){
        return tiendaService.guardarTienda(dto);
    }


}
