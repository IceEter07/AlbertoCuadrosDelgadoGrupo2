package com.example.crudjava;

import com.example.crudjava.dao.tareaDao;
import com.example.crudjava.models.tareas;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class taskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EntityManager entityManager;

    @MockBean
    private tareaDao taskDao;

    @Test
    void taskRegisterTest() throws Exception {
        tareas task = new tareas();
        task.setDescripcion("Realizando pruebas unitarias");
        task.setEstatus("Pendiente");

        //Al menos para la prueba es necesario nombrar la clave del JSON igual que con el método get que se utilizará.
            //En este caso, la prueba fallaba al poner la clave "estado" porque spring no podía mapear la propiedad.
            //Por otro lado, al poner estatus, sprin pudo mapear la propiedad y que por ende la prueba pasara.
        //En el entorno real, esto sería erroneo, ya que la columa de la bd se llama "estado", por lo que fallará sí
        // mandamos un nombre (clave) distinto.

        String JSON = "{\"descripcion\": \"Realizando pruebas unitarias\", \"estatus\": \"Pendiente\"}";

        mockMvc.perform(post("/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON))
                .andExpect(status().isOk());

        //Verificar que el método se ejecuta una sola vez con los parametros adecuados
        ArgumentCaptor<tareas> captor = ArgumentCaptor.forClass(tareas.class);
        verify(taskDao, times(1)).registrarTarea(captor.capture());

        tareas capturado = captor.getValue();
        assertEquals("Realizando pruebas unitarias", capturado.getDescripcion());
        assertEquals("Pendiente", capturado.getEstatus());
    }

    @Test
    void taskEditarTest() throws Exception {
        tareas task = new tareas();
        task.setId(1L);

        task.setDescripcion("Realizando pruebas unitarias (editar)");
        task.setEstatus("En progreso");

        String JSON = "{\"descripcion\": \"Editando desde un test\", \"estatus\": \"Completo\"}";

        //Simular el comportamiento del método (No resulto por lo que se comentó)
        //when(taskDao.actualizarTarea(anyLong(), any(tareas.class)));
        //doNothing().when(taskDao.actualizarTarea(anyLong(), any(tareas.class)));

        mockMvc.perform(put("/tareas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON))
                .andExpect(status().isOk());

        //Verificar que el método se ejecuta una sola vez con los parametros adecuados
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<tareas> taskCaptor = ArgumentCaptor.forClass(tareas.class);
        verify(taskDao, times(1)).actualizarTarea(idCaptor.capture(), taskCaptor.capture());

        //Obtener el valor capturado
        Long idCapturado = idCaptor.getValue();

        //Verificar el ID
        assertEquals(1L, idCapturado);

        //Obtener el valor capturado del body enviado (descripcion y estatus)
        tareas taskCapturada = taskCaptor.getValue();

        //Verifiicar los datos capturados con los datos reemplazantes (JSON enviado)
        assertEquals("Editando desde un test", taskCapturada.getDescripcion());
        assertEquals("Completo", taskCapturada.getEstatus());
    }

}
