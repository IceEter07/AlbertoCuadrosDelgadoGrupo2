package org.example.modelo;

import java.util.List;

public interface gestorPeliculaDao {
    void agregarPelicula(pelicula peli);
    void eliminarPelicula(int id);
    void obtenerPeliculas();
    void obtenerPeliculasDisponibles();
    void obtenerPeliculasNoDisponibles();
    void marcarPeliculaComoDisponible(int id);
}
