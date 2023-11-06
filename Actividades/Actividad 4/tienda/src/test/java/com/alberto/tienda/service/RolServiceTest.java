package com.alberto.tienda.service;

import com.alberto.tienda.data.Rol;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.data.dto.RolDto;
import com.alberto.tienda.repository.RolRepository;
import com.alberto.tienda.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RolServiceTest {
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rol;
    private RolDto rolDto;

    @BeforeEach
    void setUp(){
        //Colocar los datos del mock
        rol = new Rol();
        rol.setId(1);
        rol.setNombre("comprador");

        rolDto = new RolDto();
        rolDto.setNombre("comprador");
    }

    @Test
    void saveRolShouldReturnRolDto(){
        when(rolRepository.save(any(Rol.class))).thenAnswer(invocation ->{
            Rol rol = invocation.getArgument(0);
            rol.setId(1);
            return rol;
        });

        when(rolRepository.findByNombre(any(String.class))).thenReturn(Collections.emptyList());

        //Metodo que se va a probar
        RespuestaGenerica respuesta = rolService.guardarRol(rolDto);

        //Comparar resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE, respuesta.getMensaje());

        //Verificar las interacción con los mocks
        verify(rolRepository).findByNombre(any(String.class));
        verify(rolRepository).save(any(Rol.class));
    }

    @Test
    void getRolesShouldReturnListOfRoles(){
        when(rolRepository.findAll()).thenReturn(Collections.singletonList(rol));

        //Metodo que se va a probar
        RespuestaGenerica respuesta = rolService.getRoles();

        //Comprobar los resultados
        assertNotNull(respuesta);
        assertTrue(respuesta.isExito());
        assertFalse(respuesta.getDatos().isEmpty());
        assertEquals(Constantes.MENSAJE_CONSULTA_EXITOSA, respuesta.getMensaje());

        verify(rolRepository).findAll();
    }
}
