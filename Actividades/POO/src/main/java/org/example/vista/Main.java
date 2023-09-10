package org.example.vista;


import org.example.modelo.gestorPeliculaImp;
import org.example.modelo.pelicula;

public class Main {
    public static void main(String[] args) {

        //Se hace la instacia a la clase gestorPeliculaImp (la implementación de la interfaz)
        gestorPeliculaImp gestorPelicula = new gestorPeliculaImp();

        //Agregar peliculas
            //Pelicula 1
            pelicula peli1 = new pelicula(1, "Titanic", true);
            gestorPelicula.agregarPelicula(peli1);
            //Pelicula 2
            pelicula peli2 = new pelicula(2, "Spiderman 1", true);
            gestorPelicula.agregarPelicula(peli2);
            //Pelicula 3
            pelicula peli3 = new pelicula(3, "Avengers", false);
            gestorPelicula.agregarPelicula(peli3);
            //Pelicula 4
            pelicula peli4 = new pelicula(4, "Black panter", false);
            gestorPelicula.agregarPelicula(peli4);

        //Obtener la lista de peliculas
            gestorPelicula.obtenerPeliculas();

        // Obtener la lista de peliculas disponibles
            gestorPelicula.obtenerPeliculasDisponibles();

        // Obtener la lista de peliculas NO disponibles
            gestorPelicula.obtenerPeliculasNoDisponibles();

        // Eliminar peliculas
            // Pelicula 1
            gestorPelicula.eliminarPelicula(1);
            //Pelicula que no existe
            gestorPelicula.eliminarPelicula(10);
            // Comprobar que fueron eliminadas con éxito
            gestorPelicula.obtenerPeliculas();

        // Marcar pelicula como disponibles
            //Pelicula 1: Titanic
            gestorPelicula.marcarPeliculaComoDisponible(1);
            //Pelicula 3: Avengers
            gestorPelicula.marcarPeliculaComoDisponible(3);

    }
}