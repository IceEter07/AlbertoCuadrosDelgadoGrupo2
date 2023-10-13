package com.alberto.tienda.controller;

import com.alberto.tienda.data.dto.ProductoDto;
import com.alberto.tienda.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductoDto> getProducts(){
        return productoService.getProductos();
    }

    @GetMapping("/obtenerProductosTienda/{idTienda}")
    public List<ProductoDto> getProductsByShop(@PathVariable Integer idTienda){
        return productoService.getProductosPorTienda(idTienda);
    }

    @GetMapping("/obtenerProductosCategoria/{idCategoria}")
    public List<ProductoDto> getProductsByCat(@PathVariable Integer idCategoria){
        return productoService.getProductosPorCategoria(idCategoria);
    }

    @PostMapping("/guardarProductos")
    public ProductoDto saveProduct(@Valid @RequestBody ProductoDto dto){
        return productoService.guardarProducto(dto);
    }
}
