package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.CategoriaDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@Validated
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/obtenerCategorias")
    public ResponseEntity<RespuestaGenerica> getCategory(){
        RespuestaGenerica respuesta = categoriaService.getCategorias();
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status= HttpStatus.BAD_REQUEST;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

    @PostMapping("/guardarCategoria")
    public ResponseEntity<RespuestaGenerica> saveCategories(@Valid @RequestBody CategoriaDto categoriaDto){
        RespuestaGenerica respuesta = categoriaService.guardarCategoria(categoriaDto);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status= HttpStatus.BAD_REQUEST;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }
}
