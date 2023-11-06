package com.alberto.tienda.service;

import com.alberto.tienda.data.Categoria;
import com.alberto.tienda.data.dto.CategoriaDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.repository.CategoriaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;
    private CategoriaDto categoriaDto;

    @BeforeEach
    void setUp(){
        categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNombre("tecnologia");
        categoria.setDescripcion("Ejemplo de descripcion");

        categoriaDto = new CategoriaDto();
        categoriaDto.setNombre("tecnologia");
        categoriaDto.setDescripcion("Ejemplo de descripcion");
    }

    @Test
    void saveCategoryShouldReturnCategoriaDto(){
        when(categoriaRepository.findByNombre(any(String.class))).thenReturn(Collections.emptyList());
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation ->{
            Categoria category = invocation.getArgument(0);
            category.setIdCategoria(1);
            return category;
        });

        //Metodo que se va a probar
        RespuestaGenerica respuesta = categoriaService.guardarCategoria(categoriaDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar las interacciones de los mocks
        verify(categoriaRepository).findByNombre(any(String.class));
        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void getCategoriesShouldReturnListOfCategories(){
        when(categoriaRepository.findAll()).thenReturn(Collections.singletonList(categoria));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = categoriaService.getCategorias();

        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        //Verificar las interacciones con los mocks
        verify(categoriaRepository).findAll();
    }
}
