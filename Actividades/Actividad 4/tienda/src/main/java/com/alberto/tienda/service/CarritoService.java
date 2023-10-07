package com.alberto.tienda.service;

import com.alberto.tienda.data.Carrito;
import com.alberto.tienda.data.DetalleCarrito;
import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.data.dto.ProductoAddDto;
import com.alberto.tienda.repository.CarritoRepository;
import com.alberto.tienda.repository.DetalleCarritoRepository;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {
    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    DetalleCarritoRepository detalleCarritoRepository;

    public CarritoDto guardarCarrito(CarritoDto carritoDto){
        // PRIMERA PARTE: rellenar la tabla carrito
        Carrito nuevoCarrito = new Carrito();
        Usuario user = usuarioRepository.getReferenceById(carritoDto.getIdUsuario());
        nuevoCarrito.setIdUsuario(user);
        //nuevoCarrito.setTotal(carritoDto.getTotal());
        float totalCompra  = 0.0F;

        // Calcular el total de la compra iterando en la lista de productos proporcionada
        for (ProductoAddDto productoJson: carritoDto.getProductos()){
            Producto productoBd = productoRepository.getReferenceById(productoJson.getIdProducto());
            // Calcular el total de la compra
            totalCompra += productoBd.getPrecioVenta() * productoJson.getCantidad();
            // Actualizar el Json para mantener informado al usuario
            productoJson.setPrecioUnitario(productoBd.getPrecioVenta());
            productoJson.setTotal(productoBd.getPrecioVenta() * productoJson.getCantidad());
        }
        // El carrito se encuentra activo
        nuevoCarrito.setEstado(true);
        // Asignar el total calculado
        nuevoCarrito.setTotal(totalCompra);
        carritoRepository.save(nuevoCarrito);

        // SEGUNDA PARTE: Rellenar la tabla intermedia "detalle_carrito"

        for (ProductoAddDto productoJson: carritoDto.getProductos()){
            Producto productoBd = productoRepository.getReferenceById(productoJson.getIdProducto());
            DetalleCarrito detalleCarrito = new DetalleCarrito();

            Carrito idCarrito = carritoRepository.getReferenceById(nuevoCarrito.getIdCarrito());
            detalleCarrito.setIdCarrito(idCarrito);
            detalleCarrito.setIdProducto(productoBd);
            //Guardar los datos calculados
            detalleCarrito.setCantidad(productoJson.getCantidad());
            detalleCarrito.setPrecio(productoBd.getPrecioVenta());
            detalleCarrito.setTotal((float) (productoJson.getCantidad() * productoJson.getPrecioUnitario()));
             //Se guardan los datos en la BD

            detalleCarritoRepository.save(detalleCarrito);
        }
        carritoDto.setId(nuevoCarrito.getIdCarrito());
        carritoDto.setTotal(nuevoCarrito.getTotal());

        return carritoDto;
    }

    public List<CarritoDto> getCarritos(){
        List<CarritoDto> listaCarritos = new ArrayList<>();

        for(Carrito car: carritoRepository.findAll()){
            CarritoDto carritoDto = new CarritoDto();
            carritoDto.setId(car.getIdCarrito());
            //Id usuario (FK)
            Usuario idUsuario = car.getIdUsuario();
            carritoDto.setIdUsuario(idUsuario.getId());
            carritoDto.setTotal(car.getTotal());

            listaCarritos.add(carritoDto);
        }
        return listaCarritos;
    }
}
