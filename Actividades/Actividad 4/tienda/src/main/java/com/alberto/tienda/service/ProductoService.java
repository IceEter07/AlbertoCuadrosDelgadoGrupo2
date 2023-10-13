package com.alberto.tienda.service;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.dto.ProductoDto;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.CategoriaRepository;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.TiendaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    TiendaRepository tiendaRepository;

    public ProductoDto guardarProducto(@Valid ProductoDto productoDto){
        Producto nuevoProducto = new Producto();
        //Guardar el Id de la categoria (FK)
        Categoria category = categoriaRepository.findById(productoDto.getIdCategoria())
                .orElseThrow(() -> new EntityNotFoundException("La categoría no existe"));
        nuevoProducto.setIdCategoria(category);
        //Guardar el ID de la tiena (FK)
        Tienda shop = tiendaRepository.findById(productoDto.getIdTienda())
                .orElseThrow(() -> new EntityNotFoundException("La tienda no existe"));
        nuevoProducto.setIdTienda(shop);
        nuevoProducto.setCodigo(productoDto.getCodigo());
        nuevoProducto.setNombre(productoDto.getNombre());
        nuevoProducto.setPrecioVenta(productoDto.getPrecio());
        nuevoProducto.setStock(productoDto.getNumeroProductos());
        nuevoProducto.setDescripcion(productoDto.getDescripcion());

        productoRepository.save(nuevoProducto);
        productoDto.setId(nuevoProducto.getIdProducto());

        return productoDto;
    }

    public List<ProductoDto> getProductos(){
        List<ProductoDto> listaProductos = new ArrayList<>();

        for (Producto product : productoRepository.findAll()){
            ProductoDto productoDto = new ProductoDto();
            productoDto.setId(product.getIdProducto());
            //Id de la categoria (FK)
            Categoria idCategoria = product.getIdCategoria();
            productoDto.setIdCategoria(idCategoria.getIdCategoria());
            //ID de la tienda (FK)
            Tienda idTienda = product.getIdTienda();
            productoDto.setIdTienda(idTienda.getIdTienda());
            productoDto.setCodigo(product.getCodigo());
            productoDto.setNombre(product.getNombre());
            productoDto.setPrecio(product.getPrecioVenta());
            productoDto.setNumeroProductos(product.getStock());
            productoDto.setDescripcion(product.getDescripcion());

            listaProductos.add(productoDto);
        }
        return listaProductos;
    }

    public List<ProductoDto> getProductosPorTienda(Integer idTienda){
        Tienda shop = tiendaRepository.findById(idTienda)
                .orElseThrow(() -> new EntityNotFoundException("La tienda no existe"));
        List<ProductoDto> listaProductos = new ArrayList<>();

        for (Producto product : productoRepository.findByidTienda(shop)){
            ProductoDto productoDto = new ProductoDto();
            productoDto.setId(product.getIdProducto());
            //Id de la categoria (FK)
            Categoria idCategoria = product.getIdCategoria();
            productoDto.setIdCategoria(idCategoria.getIdCategoria());
            //ID de la tienda (FK)
            productoDto.setIdTienda(idTienda);
            productoDto.setCodigo(product.getCodigo());
            productoDto.setNombre(product.getNombre());
            productoDto.setPrecio(product.getPrecioVenta());
            productoDto.setNumeroProductos(product.getStock());
            productoDto.setDescripcion(product.getDescripcion());

            listaProductos.add(productoDto);
        }
        return listaProductos;
    }

    public List<ProductoDto> getProductosPorCategoria(Integer idCategoria){
        Categoria cat = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new EntityNotFoundException("La categoría no existe"));
        List<ProductoDto> listaProductos = new ArrayList<>();

        for (Producto product : productoRepository.findByidCategoria(cat)){
            ProductoDto productoDto = new ProductoDto();
            productoDto.setId(product.getIdProducto());
            //Id de la categoria (FK)
            productoDto.setIdCategoria(idCategoria);
            //ID de la tienda (FK)
            Tienda idTienda = product.getIdTienda();
            productoDto.setIdTienda(idTienda.getIdTienda());
            productoDto.setCodigo(product.getCodigo());
            productoDto.setNombre(product.getNombre());
            productoDto.setPrecio(product.getPrecioVenta());
            productoDto.setNumeroProductos(product.getStock());
            productoDto.setDescripcion(product.getDescripcion());

            listaProductos.add(productoDto);
        }
        return listaProductos;
    }
}
