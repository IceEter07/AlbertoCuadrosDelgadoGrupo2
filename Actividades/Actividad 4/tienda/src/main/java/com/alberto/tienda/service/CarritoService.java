package com.alberto.tienda.service;

import com.alberto.tienda.data.Carrito;
import com.alberto.tienda.data.DetalleCarrito;
import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.data.dto.ProductoAddDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.CarritoRepository;
import com.alberto.tienda.repository.DetalleCarritoRepository;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
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

    public RespuestaGenerica guardarCarrito(@Valid CarritoDto carritoDto){
        // PRIMERA PARTE: rellenar la tabla carrito
        Usuario user = usuarioRepository.findById(carritoDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        //Buscar el carrito
        List<Carrito> findCarrito = carritoRepository.findByIdUsuarioAndEstado(user, true);
        Carrito nuevoCarrito = new Carrito();
        RespuestaGenerica respuesta = new RespuestaGenerica();

        if (findCarrito.isEmpty()){
            //nuevoCarrito.setTotal(carritoDto.getTotal());
            float totalCompra  = 0.0F;

            // Calcular el total de la compra iterando en la lista de productos proporcionada
            for (ProductoAddDto productoJson: carritoDto.getProductos()){
                Producto productoBd = productoRepository.findById(productoJson.getIdProducto())
                        .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_PRODUCTO_NO_EXISTENTE + productoJson.getIdProducto()));
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
            //Asignar ID del usuario
            nuevoCarrito.setIdUsuario(user);
            carritoRepository.save(nuevoCarrito);


            // SEGUNDA PARTE: Rellenar la tabla intermedia "detalle_carrito"
            List<ProductoAddDto> listaProductos = new ArrayList<>();
            for (ProductoAddDto productoJson: carritoDto.getProductos()){
                Producto productoBd = productoRepository.findById(productoJson.getIdProducto())
                        .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_PRODUCTO_NO_EXISTENTE + productoJson.getIdProducto()));
                DetalleCarrito detalleCarrito = new DetalleCarrito();

                Carrito idCarrito = carritoRepository.findById(nuevoCarrito.getIdCarrito())
                        .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_CARRITO_NO_EXISTENTE + nuevoCarrito.getIdCarrito()));
                detalleCarrito.setIdCarrito(idCarrito);
                detalleCarrito.setIdProducto(productoBd);
                //Guardar los datos calculados
                detalleCarrito.setCantidad(productoJson.getCantidad());
                detalleCarrito.setPrecio(productoBd.getPrecioVenta());
                detalleCarrito.setTotal((float) (productoJson.getCantidad() * productoJson.getPrecioUnitario()));

                //Actualizar el JSON para mantener retroalimentado al usuario
                productoJson.setCantidad(productoBd.getStock());
                productoJson.setPrecioUnitario(productoBd.getPrecioVenta());
                listaProductos.add(productoJson);
                //Se guardan los datos en la BD
                detalleCarritoRepository.save(detalleCarrito);
            }
            carritoDto.setId(nuevoCarrito.getIdCarrito());
            carritoDto.setTotal(nuevoCarrito.getTotal());
            carritoDto.setProductos(listaProductos);

            respuesta.getDatos().add(carritoDto);
            respuesta.setExito(true);
            respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
        }
        else{
            throw new BadRequestException(Constantes.MENSAJE_CARRITO_YA_REGISTRADO+user.getId());
        }

        //El siguiente bloque de codigo puede ser utilizado para una actualización de carrito.
        //Lo estoy comentando porque creo que no es necesario en el endpoint de guardar un carrito.
//        else{
//            //Actualizar la tabla detalle_carrito y el total de la tabla carrito.
//            float nuevoTotalCompra  = 0.0F;
//
//            Carrito carrito = findCarrito.get(0);
//            List<ProductoAddDto> listaProductos = new ArrayList<>();
//
//            // Calcular el nuevo total de la compra iterando en la lista de productos proporcionada
//            for (ProductoAddDto productoJson: carritoDto.getProductos()){
//                Producto productoBd = productoRepository.findById(productoJson.getIdProducto())
//                        .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_PRODUCTO_NO_EXISTENTE + productoJson.getIdProducto()));
//
//                // Calcular el NUEVO TOTAL del carrito
//                nuevoTotalCompra += productoBd.getPrecioVenta() * productoJson.getCantidad();
//
//                //Buscar producto por ID de carrito e ID de producto. Sí existe se actualizan los campos.
//                //DetalleCarrito actualizarProducto = detalleCarritoRepository.findByIdCarritoAndIdProducto(carrito, productoBd).get(0);
//                List<DetalleCarrito> producto = detalleCarritoRepository.findByIdCarritoAndIdProducto(carrito, productoBd);
//                if (producto.isEmpty()){
//                    DetalleCarrito detalleCarrito = new DetalleCarrito();
//                    detalleCarrito.setIdCarrito(carrito);
//                    detalleCarrito.setIdProducto(productoBd);
//                    detalleCarrito.setCantidad(productoJson.getCantidad());
//                    detalleCarrito.setPrecio(productoBd.getPrecioVenta());
//                    detalleCarrito.setTotal(productoBd.getPrecioVenta() * productoJson.getCantidad());
//                    detalleCarritoRepository.save(detalleCarrito);
//                }
//                else{
//                    DetalleCarrito actualizarProducto = producto.get(0);
//                    //Insertar la NUEVA cantidad.
//                    //En este caso en concreto se sobreescribe la cantidad, para que el usuario pueda mandar una cantidad mayor o igual.
//                    actualizarProducto.setCantidad(productoJson.getCantidad());
//                    //Actualizar los datos calculados en la tabla detalle_carrito
//                    actualizarProducto.setTotal((float) (productoJson.getCantidad() * productoJson.getPrecioUnitario()));
//                    detalleCarritoRepository.save(actualizarProducto);
//                }
//
//                //Actualizar el JSON para mantener retroalimentado al usuario
//                carritoDto.setId(carrito.getIdCarrito());
//                carritoDto.setIdUsuario(user.getId());
//
//                productoJson.setIdProducto(productoBd.getIdProducto());
//                productoJson.setPrecioUnitario(productoBd.getPrecioVenta());
//                productoJson.setTotal(productoBd.getPrecioVenta() * productoJson.getCantidad());
//                listaProductos.add(productoJson);
//
//                carritoDto.setProductos(listaProductos);
//
//            }
//
//            //Actualizar el campo total de la BD y del JSON
//            carrito.setTotal(nuevoTotalCompra);
//            carritoRepository.save(carrito);
//            carritoDto.setTotal(carrito.getTotal());
//
//            respuesta.getDatos().add(carritoDto);
//            respuesta.setExito(true);
//            respuesta.setMensaje(Constantes.MENSAJE_CARRITO_ACTUALIZADO_EXITOSAMENTE);
//        }
        return respuesta;
    }

    public RespuestaGenerica getCarritoPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        List<Carrito> findCarrito = carritoRepository.findByIdUsuarioAndEstado(user, true);
        if (findCarrito.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_CARRITO_NO_EXISTENTE_PARA_USUARIO + idUsuario);
        }

        List<DetalleCarrito> productsCar = detalleCarritoRepository.findByIdCarrito(findCarrito.get(0));

        if (productsCar.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_CARRITO_SIN_PRODUCTO);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

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
        respuesta.getDatos().add(carritoDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }
}