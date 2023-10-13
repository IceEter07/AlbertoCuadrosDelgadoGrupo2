package com.alberto.tienda.service;

import com.alberto.tienda.data.Carrito;
import com.alberto.tienda.data.DetalleCarrito;
import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.data.dto.ProductoAddDto;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.CarritoRepository;
import com.alberto.tienda.repository.DetalleCarritoRepository;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import jakarta.validation.Valid;
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

    public CarritoDto guardarCarrito(@Valid CarritoDto carritoDto){
        // PRIMERA PARTE: rellenar la tabla carrito
        Carrito nuevoCarrito = new Carrito();
        Usuario user = usuarioRepository.findById(carritoDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        nuevoCarrito.setIdUsuario(user);
        //nuevoCarrito.setTotal(carritoDto.getTotal());
        float totalCompra  = 0.0F;

        // Calcular el total de la compra iterando en la lista de productos proporcionada
        for (ProductoAddDto productoJson: carritoDto.getProductos()){
            Producto productoBd = productoRepository.findById(productoJson.getIdProducto())
                    .orElseThrow(() -> new EntityNotFoundException("El producto con id " + productoJson.getIdProducto()+ " no existe."));
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
            Producto productoBd = productoRepository.findById(productoJson.getIdProducto())
                    .orElseThrow(() -> new EntityNotFoundException("El producto con id " + productoJson.getIdProducto() + " no existe."));
            DetalleCarrito detalleCarrito = new DetalleCarrito();

            Carrito idCarrito = carritoRepository.findById(nuevoCarrito.getIdCarrito())
                    .orElseThrow(() -> new EntityNotFoundException("El carrito con id " + nuevoCarrito.getIdCarrito() + " no existe."));
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

    public List<CarritoDto> getCarritoPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));

        List<Carrito> findCarrito = carritoRepository.findByIdUsuarioAndEstado(user, true);
        if (findCarrito.isEmpty()){
            throw new EntityNotFoundException("No existe ningún carrito activo para el usuario " + idUsuario);
        }

        List<DetalleCarrito> productsCar = detalleCarritoRepository.findByIdCarrito(findCarrito.get(0));

        List<CarritoDto> listaCarrito = new ArrayList<>();
        List<ProductoAddDto> listaProductos = new ArrayList<>();
//        CarritoDto carritoDto = new CarritoDto();
//        carritoDto.setIdUsuario(user.getId());

        //Obtener detalles generales del carrito
        for(Carrito car: findCarrito){
            CarritoDto carritoDto = new CarritoDto();
            carritoDto.setId(car.getIdCarrito());
            //Id usuario (FK)
            carritoDto.setIdUsuario(idUsuario);
            carritoDto.setTotal(car.getTotal());

            //Obtener productos
            for(DetalleCarrito carDetails: productsCar){
                ProductoAddDto productoAddDto = new ProductoAddDto();
                productoAddDto.setIdProducto(carDetails.getIdProducto().getIdProducto());
                productoAddDto.setPrecioUnitario(carDetails.getPrecio());
                productoAddDto.setCantidad(carDetails.getCantidad());
                productoAddDto.setTotal(carDetails.getTotal());
                //Asignar los productos a la lista
                listaProductos.add(productoAddDto);
                //Asignar la lista al campo de comprasDto
                carritoDto.setProductos(listaProductos);
            }
        listaCarrito.add(carritoDto);
        }
        return listaCarrito;
    }
}