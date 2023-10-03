package com.alberto.tienda.service;

import com.alberto.tienda.data.Tienda;
import com.alberto.tienda.data.dto.TiendaDto;
import com.alberto.tienda.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TiendaService {

    @Autowired
    TiendaRepository tiendaRepository;

    public TiendaDto guardarTienda(TiendaDto tiendaDto){
        Tienda nuevaTienda = new Tienda();
        nuevaTienda.setRfc(tiendaDto.getRfc());
        nuevaTienda.setNombre(tiendaDto.getNombre());
        nuevaTienda.setDescripcion(tiendaDto.getDescripcion());
        nuevaTienda.setRating(tiendaDto.getRating());
        tiendaRepository.save(nuevaTienda);
        tiendaDto.setId(nuevaTienda.getIdTienda());
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
