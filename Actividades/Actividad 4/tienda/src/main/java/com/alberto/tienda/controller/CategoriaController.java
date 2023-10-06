package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CategoriaDto;
import com.alberto.tienda.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categoria")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/obtenerCategoria")
    public List<CategoriaDto> getCategory(){
        return categoriaService.getCategorias();
    }

    @PostMapping("/guardarCategoria")
    public CategoriaDto saveCategories(@RequestBody CategoriaDto dto){
        return categoriaService.guardarCategoria(dto);
    }
}
