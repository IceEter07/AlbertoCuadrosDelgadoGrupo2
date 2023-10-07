package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.ResenasDto;
import com.alberto.tienda.service.ResenasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resena")
public class ResenaController {

    @Autowired
    ResenasService resenasService;

    @GetMapping("obtenerResenas")
    public List<ResenasDto> getComments(){
        return resenasService.obtenerResenas();
    }

    @PostMapping("guardarResena")
    public ResenasDto saveComment(@RequestBody ResenasDto dto){
        return resenasService.guardarResena(dto);
    }
}
