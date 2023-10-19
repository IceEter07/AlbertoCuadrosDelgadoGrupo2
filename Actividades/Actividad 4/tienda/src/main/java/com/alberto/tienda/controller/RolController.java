package com.alberto.tienda.controller;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.RolDto;
import com.alberto.tienda.service.RolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol")
@Validated
public class RolController {
    @Autowired
    RolService rolService;

//    @GetMapping("/obtenerRoles")
//    public List<RolDto> getRol(){
//        return rolService.getRoles();
//    }
    @GetMapping("/obtenerRoles")
    public ResponseEntity<RespuestaGenerica> getRol(){
        RespuestaGenerica respuesta = rolService.getRoles();
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }else{
            status = HttpStatus.BAD_REQUEST;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }

//    @PostMapping("/guardarRol")
//    public RolDto saveRol(@Valid @RequestBody RolDto dto){
//        return rolService.guardarRol(dto);
//    }

    @PostMapping("/guardarRol")
    public ResponseEntity<RespuestaGenerica> saveRol(@Valid @RequestBody RolDto rolDto){
        RespuestaGenerica respuesta = rolService.guardarRol(rolDto);
        HttpStatus status = null;
        if (respuesta.isExito()){
            status = HttpStatus.OK;
            respuesta.setCodigo(status.value());
        }
        else{
            status = HttpStatus.NOT_FOUND;
            respuesta.setCodigo(status.value());
        }
        return new ResponseEntity<>(respuesta, status);
    }
}
