package com.alberto.tienda.service;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.dto.ProductoDto;
import com.alberto.tienda.repository.CategoriaRepository;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.TiendaRepository;
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

    public ProductoDto guardarProducto(ProductoDto productoDto){
        Producto nuevoProducto = new Producto();
        //Guardar el Id de la categoria (FK)
        Categoria category = buscarCategoriaPorId(productoDto.getIdCategoria());
        nuevoProducto.setIdCategoria(category);
        //Guardar el ID de la tiena (FK)
        Tienda shop = buscarTiendaPorId(productoDto.getIdTienda());
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

    private Categoria buscarCategoriaPorId(int idCategoria){
        Categoria category = categoriaRepository.getReferenceById(idCategoria);
        return category;
    }

    private Tienda buscarTiendaPorId(int idTienda){
        Tienda shop = tiendaRepository.getReferenceById(idTienda);
        return shop;
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
            productoDto.setPrecio(product.getPrecioVenta());
            productoDto.setNumeroProductos(productoDto.getNumeroProductos());
            productoDto.setDescripcion(product.getDescripcion());

            listaProductos.add(productoDto);
        }
        return listaProductos;
    }
}
