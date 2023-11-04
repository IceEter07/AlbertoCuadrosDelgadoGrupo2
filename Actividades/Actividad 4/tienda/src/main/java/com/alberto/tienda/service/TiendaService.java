package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioRol;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.repository.TiendaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.repository.UsuarioRolRepository;
import com.alberto.tienda.utils.Constantes;
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

    public RespuestaGenerica guardarTienda(@Valid TiendaDto tiendaDto){
        Usuario idUsuario = usuarioRepository.findById(tiendaDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));
        //Comprobar que el RFC no haya sido registrado
        List<Tienda> findRfc = tiendaRepository.findByRfc(tiendaDto.getRfc());
        List<Tienda> findNombre = tiendaRepository.findByNombre(tiendaDto.getNombre());

        if (!(findRfc.isEmpty())){
            throw new BadRequestException(Constantes.MENSAJE_RFC_YA_REGISTRADO);
        }

        if (!(findNombre.isEmpty())){
            throw  new BadRequestException(Constantes.MENSAJE_NOMBRE_TIENDA_YA_REGISTRADO);
        }

        Tienda nuevaTienda = new Tienda();
        RespuestaGenerica respuesta = new RespuestaGenerica();

        nuevaTienda.setIdUsuario(idUsuario);
        nuevaTienda.setRfc(tiendaDto.getRfc());
        nuevaTienda.setNombre(tiendaDto.getNombre());
        nuevaTienda.setDescripcion(tiendaDto.getDescripcion());
        nuevaTienda.setRating(tiendaDto.getRating());
        nuevaTienda = tiendaRepository.save(nuevaTienda);
        tiendaDto.setId(nuevaTienda.getIdTienda());

        // Añadir un nuevo rol al usuario
        //Comprobar que el rol exista en la BD. Sí no existe se crea.
        List<Rol> rolBD = rolRepository.findByNombre("vendedor");
        if (rolBD.isEmpty()){
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("vendedor");
            rolRepository.save(nuevoRol);
        }

        //Actualizar la tabla usuario_rol

        //Obtener ID del rol
        Rol rolBd = rolRepository.findByNombre("vendedor").get(0);
        //Validar que el usuario aún NO tenga un rol de "vendedor"
        List<UsuarioRol> findUsuarioRol = usuarioRolRepository.findByIdUsuarioAndIdRol(idUsuario, rolBd);
        if (findUsuarioRol.isEmpty()){
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setIdRol(rolBd);
            usuarioRol.setIdUsuario(nuevaTienda.getIdUsuario());
            usuarioRolRepository.save(usuarioRol);
        }

        respuesta.getDatos().add(tiendaDto);
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
        return respuesta;
    }

    public RespuestaGenerica getTiendas(){
        List<Tienda> tiendas = tiendaRepository.findAll();
        if (tiendas.isEmpty())
        {
            throw new EntityNotFoundException(Constantes.MENSAJE_SIN_HISTORIAL_DE_TIENDAS);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Tienda shop: tiendas){
            TiendaDto tiendaDto = new TiendaDto();
            tiendaDto.setId(shop.getIdTienda());
            tiendaDto.setIdUsuario(shop.getIdUsuario().getId());
            tiendaDto.setRfc(shop.getRfc());
            tiendaDto.setNombre(shop.getNombre());
            tiendaDto.setDescripcion(shop.getDescripcion());
            tiendaDto.setRating(shop.getRating());

            respuesta.getDatos().add(tiendaDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }

    public RespuestaGenerica getTiendasPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        List<Tienda> tiendas = tiendaRepository.findByIdUsuario(user);
        if (tiendas.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_SIN_HISTORIAL_DE_TIENDAS);
        }
        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Tienda shop: tiendas){
            TiendaDto tiendaDto = new TiendaDto();
            tiendaDto.setId(shop.getIdTienda());
            tiendaDto.setIdUsuario(shop.getIdUsuario().getId());
            tiendaDto.setRfc(shop.getRfc());
            tiendaDto.setNombre(shop.getNombre());
            tiendaDto.setDescripcion(shop.getDescripcion());
            tiendaDto.setRating(shop.getRating());

            respuesta.getDatos().add(tiendaDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }
}
