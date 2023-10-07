package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.ProductoDto;
import com.alberto.tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/obtenerProductos")
    public List<ProductoDto> getProducts(){
        return productoService.getProductos();
    }

    @PostMapping("/guardarProductos")
    public ProductoDto saveProduct(@RequestBody ProductoDto dto){
        return productoService.guardarProducto(dto);
    }
}
