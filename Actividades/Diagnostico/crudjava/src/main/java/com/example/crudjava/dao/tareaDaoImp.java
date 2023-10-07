package com.example.crudjava.dao;

import com.example.crudjava.exception.taskException;
import com.example.crudjava.models.tareas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Encargado de realizar los cambios de los datos del microservicio
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class tareaDaoImp implements tareaDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity getTareas() {
        try{
            String query = "From tareas";
            List<tareas> tareas = entityManager.createQuery(query).getResultList();
            //Se retorna una respuesta y se manda el cuerpo con la consulta realizada
            return ResponseEntity.ok(tareas);
        } catch (taskException ex){
            throw new taskException("501", HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error en la consulta");
        }
    }

    //Registrar tarea
    @Override
    public ResponseEntity registrarTarea(tareas task) {
        if (task.getDescripcion().equals("") || task.getDescripcion() == null){
            throw new taskException("400", HttpStatus.BAD_REQUEST, "La descripción es requerida");
        }
        if (task.getEstatus().equals("") || task.getEstatus() == null){
            throw new taskException("400", HttpStatus.BAD_REQUEST, "El estado de la tarea es requerido");
        }
        if (task.getId() != null){
            throw new taskException("400", HttpStatus.BAD_REQUEST, "No puedes ingresar un ID");
        }
        entityManager.persist(task);
        return new ResponseEntity("La tarea fue registrada con éxito", HttpStatus.CREATED);
    }

    //Eliminar tarea
    @Override
    public ResponseEntity eliminarTarea(Long id) {
        tareas task = entityManager.find(tareas.class, id);
        if(task == null){
            throw new taskException("404", HttpStatus.NOT_FOUND, "La tarea que se quiere eliminar no existe");
        }

        try{
        entityManager.remove(task);
        return new ResponseEntity("La tarea fue eliminada con éxito", HttpStatus.OK);
        }

        //El catch no funciona como se desea. Aunque de momento se dejará por cualquier cosa y para su posterior modificación
        catch (taskException ex){
            throw new taskException("400", HttpStatus.BAD_REQUEST, "Los datos que se introducidos no son válidos");
        }
    }

    //Actualizar tarea
    @Override
    public ResponseEntity actualizarTarea(Long id, tareas task) {
        //Buscar sí existe el registro que se quiere actualizar
        tareas query = entityManager.find(tareas.class, id);

        if(query == null){
        throw new taskException("404", HttpStatus.NOT_FOUND, "La tarea que se quiere actualizar no existe");
        }
        if (task.getDescripcion().equals("") || task.getDescripcion() == null){
            throw new taskException("400", HttpStatus.BAD_REQUEST, "La descripción es requerida");
        }
        if (task.getEstatus().equals("") || task.getEstatus() == null){
            throw new taskException("400", HttpStatus.BAD_REQUEST, "El estado de la tarea es requerido");
        }
        if (task.getId() != null){
            throw new taskException("400", HttpStatus.BAD_REQUEST, "No puedes ingresar un ID");
        }

        //Fue necesario especificar los valores que se querían actualizar incluyendo el ID, porque de no mandar dicho ID
        //En lugar de actualizar un campo se creaba uno nuevo
        task.setId(id);
        task.setDescripcion(task.getDescripcion());
        task.setEstatus(task.getEstatus());
        entityManager.merge(task);
        return new ResponseEntity("La tarea fue actualizada con éxito", HttpStatus.OK);
    }
}
