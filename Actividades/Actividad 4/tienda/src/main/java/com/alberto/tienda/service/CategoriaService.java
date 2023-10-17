package com.alberto.tienda.service;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.dto.CategoriaDto;
import com.alberto.tienda.exceptions.BadRequestException;
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

    public CategoriaDto guardarCategoria(@Valid CategoriaDto categoriaDto){
        Categoria nuevaCategoria = new Categoria();
        List<Categoria> findCategoria = categoriaRepository.findByNombre(categoriaDto.getNombre());
        if (findCategoria.isEmpty()){
            nuevaCategoria.setNombre(categoriaDto.getNombre());
            nuevaCategoria.setDescripcion(categoriaDto.getDescripcion());
            categoriaRepository.save(nuevaCategoria);
            categoriaDto.setId(nuevaCategoria.getIdCategoria());
        }
        else{
            throw new BadRequestException(Constantes.MENSAJE_CATEGORIA_YA_REGISTRADA);
        }

        return  categoriaDto;
    }

    public List<CategoriaDto> getCategorias(){
        List<CategoriaDto> listaCategorias = new ArrayList<>();

        for (Categoria category: categoriaRepository.findAll()){
            CategoriaDto categoriaDto = new CategoriaDto();
            categoriaDto.setId(category.getIdCategoria());
            categoriaDto.setNombre(category.getNombre());
            categoriaDto.setDescripcion(category.getDescripcion());

            listaCategorias.add(categoriaDto);
        }
        return listaCategorias;
    }
}
