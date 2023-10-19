package com.alberto.tienda.service;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.dto.ProductoDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.CategoriaRepository;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.TiendaRepository;
import com.alberto.tienda.utils.Constantes;
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

    public RespuestaGenerica guardarProducto(@Valid ProductoDto productoDto) {
        //Guardar el Id de la categoria (FK)
        Categoria category = categoriaRepository.findById(productoDto.getIdCategoria())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_CATEGORIA_NO_EXISTENTE + productoDto.getIdCategoria()));

        //Guardar el ID de la tiena (FK)
        Tienda shop = tiendaRepository.findById(productoDto.getIdTienda())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_TIENDA_NO_EXISTENTE + productoDto.getIdTienda()));

        Producto nuevoProducto = new Producto();
        RespuestaGenerica respuesta = new RespuestaGenerica();

        //Validar que el producto NO exista
        //Sí existe, se actualizan los campos precio y stock (a este último se le suman las nuevas existencias).
        List<Producto> findProductoTiendaYCodigo = productoRepository.findByIdTiendaAndCodigo(shop, productoDto.getCodigo());
        if (!(findProductoTiendaYCodigo.isEmpty())) {
            throw new BadRequestException(Constantes.MENSAJE_PRODUCTO_TIENDA_YA_REGISTRADO+shop.getIdTienda()+Constantes.MENSAJE_PRODUCTO_CODIGO_YA_REGISTRADO+productoDto.getCodigo());

            //El bloque de codigo comentado puede ser utilizado para actualizar productos.
            //De momento se deja comentado para su futura implementacion
//            Producto actualizarProducto = findProductoNombreYCodigo.get(0);
//
//            actualizarProducto.setPrecioVenta(productoDto.getPrecio());
//            actualizarProducto.setStock(productoDto.getNumeroProductos() + actualizarProducto.getStock());
//            productoRepository.save(actualizarProducto);
//            productoDto.setId(actualizarProducto.getIdProducto());
//            //Mantener actualizado el stock
//            productoDto.setPrecio(actualizarProducto.getPrecioVenta());
//            productoDto.setNombre(actualizarProducto.getNombre());
//            productoDto.setNumeroProductos(actualizarProducto.getStock());
//            respuesta.setMensaje(Constantes.MENSAJE_PRODUCTO_ACTUALIZADO_EXITOSAMENTE);
        } else {

            nuevoProducto.setIdCategoria(category);
            nuevoProducto.setIdTienda(shop);
            nuevoProducto.setCodigo(productoDto.getCodigo());
            nuevoProducto.setNombre(productoDto.getNombre());
            nuevoProducto.setPrecioVenta(productoDto.getPrecio());
            nuevoProducto.setStock(productoDto.getNumeroProductos());
            nuevoProducto.setDescripcion(productoDto.getDescripcion());
            productoRepository.save(nuevoProducto);
            productoDto.setId(nuevoProducto.getIdProducto());
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);

            respuesta.getDatos().add(productoDto);
            respuesta.setExito(true);
        }
        return respuesta;
    }

    public RespuestaGenerica getProductos(){
        List<Producto> findProductos = productoRepository.findAll();
        if (findProductos.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_PRODUCTOS_SIN_HISTORIAL);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Producto product : findProductos){
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

            respuesta.getDatos().add(productoDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }

    public RespuestaGenerica getProductosPorTienda(Integer idTienda){
        Tienda shop = tiendaRepository.findById(idTienda)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_TIENDA_NO_EXISTENTE+idTienda));

        List<Producto> findProductoTienda = productoRepository.findByIdTienda(shop);
        if (findProductoTienda.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_PRODUCTOS_TIENDA_NO_EXISTENTES+idTienda);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Producto product : findProductoTienda){
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

            respuesta.getDatos().add(productoDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }

    public RespuestaGenerica getProductosPorCategoria(Integer idCategoria){
        Categoria cat = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_CATEGORIA_NO_EXISTENTE+idCategoria));

        List<Producto> findProductoCategoria = productoRepository.findByIdCategoria(cat);
        if (findProductoCategoria.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_PRODUCTOS_CATEGORIA_NO_EXISTENTES+idCategoria);
        }
        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Producto product : findProductoCategoria){
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

            respuesta.getDatos().add(productoDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }
}
