package com.alberto.tienda.service;

import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.UsuarioTienda;
import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.repository.TiendaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.repository.UsuarioTiendaRepository;
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
    UsuarioTiendaRepository usuarioTiendaRepository;

    public TiendaDto guardarTienda(TiendaDto tiendaDto){
        Tienda nuevaTienda = new Tienda();
        Usuario idUsuario = usuarioRepository.getReferenceById(tiendaDto.getIdUsuario());
        nuevaTienda.setIdUsuario(idUsuario);
        nuevaTienda.setRfc(tiendaDto.getRfc());
        nuevaTienda.setNombre(tiendaDto.getNombre());
        nuevaTienda.setDescripcion(tiendaDto.getDescripcion());
        nuevaTienda.setRating(tiendaDto.getRating());
        nuevaTienda = tiendaRepository.save(nuevaTienda);
        tiendaDto.setId(nuevaTienda.getIdTienda());

        //Codigo que servía para actualizar la tabla de enlace (Actualmente se optó por eliminarla)
//        //for (UsuarioTiendaDto userShopDto: tiendaDto.getUsuario_tienda()){
//            UsuarioTienda nuevoUsuarioTienda = new UsuarioTienda();
//            Usuario userBd = usuarioRepository.getReferenceById(tiendaDto.getIdUsuario());
//            nuevoUsuarioTienda.setIdUsuario(userBd);
//            nuevoUsuarioTienda.setIdTienda(nuevaTienda);
//
//            usuarioTiendaRepository.save(nuevoUsuarioTienda);
//        //}

        //Nota: en la petición se obervan los campos de la tabla usuarios_tienda

        return tiendaDto;
    }

    public List<TiendaDto> getTiendas(){
        List<TiendaDto> listaTiendas = new ArrayList<>();

        for (Tienda shop: tiendaRepository.findAll()){
            TiendaDto tiendaDto = new TiendaDto();
            tiendaDto.setId(shop.getIdTienda());
            tiendaDto.setRfc(shop.getRfc());
            tiendaDto.setNombre(shop.getNombre());
            tiendaDto.setDescripcion(shop.getDescripcion());
            tiendaDto.setRating(shop.getRating());

            listaTiendas.add(tiendaDto);
        }
        return listaTiendas;
    }
}
