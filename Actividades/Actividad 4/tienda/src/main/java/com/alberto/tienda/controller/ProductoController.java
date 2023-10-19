package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.ProductoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@Validated
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/obtenerProductos")
    public ResponseEntity<RespuestaGenerica> getProducts(){
        RespuestaGenerica respuesta = productoService.getProductos();
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

    @GetMapping("/obtenerProductosTienda/{idTienda}")
    public ResponseEntity<RespuestaGenerica> getProductsByShop(@PathVariable Integer idTienda){
        RespuestaGenerica respuesta = productoService.getProductosPorTienda(idTienda);
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

    @GetMapping("/obtenerProductosCategoria/{idCategoria}")
    public ResponseEntity<RespuestaGenerica> getProductsByCat(@PathVariable Integer idCategoria){
        RespuestaGenerica respuesta = productoService.getProductosPorCategoria(idCategoria);
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

    @PostMapping("/guardarProductos")
    public ResponseEntity<RespuestaGenerica> saveProduct(@Valid @RequestBody ProductoDto productoDto) {
        RespuestaGenerica respuesta = productoService.guardarProducto(productoDto);
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
