package com.alberto.tienda.service;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.dto.CategoriaDto;
import com.alberto.tienda.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public CategoriaDto guardarCategoria(CategoriaDto categoriaDto){
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre(categoriaDto.getNombre());
        nuevaCategoria.setDescripcion(categoriaDto.getDescripcion());
        categoriaRepository.save(nuevaCategoria);
        categoriaDto.setId(nuevaCategoria.getIdCategoria());

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
