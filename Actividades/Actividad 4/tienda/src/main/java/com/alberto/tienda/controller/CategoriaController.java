package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CategoriaDto;
import com.alberto.tienda.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@Validated
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/obtenerCategoria")
    public List<CategoriaDto> getCategory(){
        return categoriaService.getCategorias();
    }

    @PostMapping("/guardarCategoria")
    public CategoriaDto saveCategories(@Valid @RequestBody CategoriaDto dto){
        return categoriaService.guardarCategoria(dto);
    }
}
