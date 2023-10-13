package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.ResenasDto;
import com.alberto.tienda.service.ResenasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resena")
@Validated
public class ResenaController {

    @Autowired
    ResenasService resenasService;

    @GetMapping("obtenerResenas")
    public List<ResenasDto> getComments(){
        return resenasService.obtenerResenas();
    }

    @GetMapping("obtenerResenasUsuario")
    public List<ResenasDto> getCommentsByUser(@PathVariable Integer idUsuario){
        return resenasService.obtenerResenasPorUsuario(idUsuario);
    }

    @PostMapping("guardarResena")
    public ResenasDto saveComment(@Valid @RequestBody ResenasDto dto){
        return resenasService.guardarResena(dto);
    }
}
