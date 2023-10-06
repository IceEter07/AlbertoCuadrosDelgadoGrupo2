package com.alberto.tienda.service;

import com.alberto.tienda.data.Carrito;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.CarritoDto;
import com.alberto.tienda.repository.CarritoRepository;
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

    public CarritoDto guardarCarrito(CarritoDto carritoDto){
        Carrito nuevoCarrito = new Carrito();
        Usuario user = buscarUsuarioPorId(carritoDto.getIdUsuario());
        nuevoCarrito.setIdUsuario(user);
        nuevoCarrito.setTotal(carritoDto.getTotal());
        nuevoCarrito.setEstado(true);

        carritoRepository.save(nuevoCarrito);
        carritoDto.setId(nuevoCarrito.getIdCarrito());
        return carritoDto;
    }

    private Usuario buscarUsuarioPorId(int idUsuario){
        Usuario user = usuarioRepository.getReferenceById(idUsuario);
        return user;
    }

    public List<CarritoDto> getCarrito(){
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
