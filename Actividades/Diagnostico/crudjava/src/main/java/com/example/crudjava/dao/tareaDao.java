package com.example.crudjava.dao;

import com.example.crudjava.models.tareas;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface tareaDao {
//    List<tareas> getTareas();
    ResponseEntity getTareas();

    ResponseEntity registrarTarea(tareas task);

    ResponseEntity eliminarTarea(Long id);

    ResponseEntity actualizarTarea(Long id, tareas task);

}
