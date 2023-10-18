package com.alberto.tienda.service;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.dto.CategoriaDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.exceptions.BadRequestException;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.CategoriaRepository;
import com.alberto.tienda.utils.Constantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public RespuestaGenerica guardarCategoria(@Valid CategoriaDto categoriaDto){
        List<Categoria> findCategoria = categoriaRepository.findByNombre(categoriaDto.getNombre());

        RespuestaGenerica respuesta = new RespuestaGenerica();
        Categoria nuevaCategoria = new Categoria();

        if (!(findCategoria.isEmpty())){
            throw new BadRequestException(Constantes.MENSAJE_CATEGORIA_YA_REGISTRADA);
        }

        nuevaCategoria.setNombre(categoriaDto.getNombre());
        nuevaCategoria.setDescripcion(categoriaDto.getDescripcion());
        categoriaRepository.save(nuevaCategoria);
        categoriaDto.setId(nuevaCategoria.getIdCategoria());
        respuesta.getDatos().add(categoriaDto);
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);

        return  respuesta;
    }

    public RespuestaGenerica getCategorias(){
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_CATEGORIAS_SIN_HISTORIAL);
        }

        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Categoria category: categorias){
            CategoriaDto categoriaDto = new CategoriaDto();
            categoriaDto.setId(category.getIdCategoria());
            categoriaDto.setNombre(category.getNombre());
            categoriaDto.setDescripcion(category.getDescripcion());

            respuesta.getDatos().add(categoriaDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return respuesta;
    }
}
