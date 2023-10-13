package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioRol;
import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.repository.TiendaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.repository.UsuarioRolRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TiendaService {

    @Autowired
    TiendaRepository tiendaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    UsuarioRolRepository usuarioRolRepository;

    public TiendaDto guardarTienda(@Valid TiendaDto tiendaDto){
        Tienda nuevaTienda = new Tienda();
        Usuario idUsuario = usuarioRepository.findById(tiendaDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        nuevaTienda.setIdUsuario(idUsuario);
        //Comprobar que el RFC no haya sido registrado
        List<Tienda> findRfc = tiendaRepository.findByRfc(tiendaDto.getRfc());
        if (findRfc.isEmpty()){
            nuevaTienda.setRfc(tiendaDto.getRfc());
            nuevaTienda.setNombre(tiendaDto.getNombre());
            nuevaTienda.setDescripcion(tiendaDto.getDescripcion());
            nuevaTienda.setRating(tiendaDto.getRating());
            nuevaTienda = tiendaRepository.save(nuevaTienda);
            tiendaDto.setId(nuevaTienda.getIdTienda());
        }
        else {
            throw new BadRequestException("El RFC ya fue registrado");
        }

        // Añadir un nuevo rol al usuario
        //Comprobar que el rol exista en la BD. Sí no existe se crea.
        List<Rol> rolBD = rolRepository.findByNombre("vendedor");
        if (rolBD.isEmpty()){
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("vendedor");
            rolRepository.save(nuevoRol);
        }

        //Actualizar la tabla usuario_rol
        Rol rolBd = rolRepository.findByNombre("vendedor").get(0);
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setIdRol(rolBd);
        usuarioRol.setIdUsuario(nuevaTienda.getIdUsuario());
        usuarioRolRepository.save(usuarioRol);

        return tiendaDto;
    }

    public List<TiendaDto> getTiendas(){
        List<TiendaDto> listaTiendas = new ArrayList<>();

        for (Tienda shop: tiendaRepository.findAll()){
            TiendaDto tiendaDto = new TiendaDto();
            tiendaDto.setId(shop.getIdTienda());
            tiendaDto.setIdUsuario(shop.getIdUsuario().getId());
            tiendaDto.setRfc(shop.getRfc());
            tiendaDto.setNombre(shop.getNombre());
            tiendaDto.setDescripcion(shop.getDescripcion());
            tiendaDto.setRating(shop.getRating());

            listaTiendas.add(tiendaDto);
        }
        return listaTiendas;
    }

    public List<TiendaDto> getTiendasPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));

        List<TiendaDto> listaTiendas = new ArrayList<>();

        for (Tienda shop: tiendaRepository.findByIdUsuario(user)){
            TiendaDto tiendaDto = new TiendaDto();
            tiendaDto.setId(shop.getIdTienda());
            tiendaDto.setIdUsuario(shop.getIdUsuario().getId());
            tiendaDto.setRfc(shop.getRfc());
            tiendaDto.setNombre(shop.getNombre());
            tiendaDto.setDescripcion(shop.getDescripcion());
            tiendaDto.setRating(shop.getRating());

            listaTiendas.add(tiendaDto);
        }
        return listaTiendas;
    }
}
