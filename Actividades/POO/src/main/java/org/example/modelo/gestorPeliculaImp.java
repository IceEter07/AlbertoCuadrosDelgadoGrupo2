package org.example.modelo;

import java.util.ArrayList;
import java.util.List;

public class gestorPeliculaImp implements gestorPeliculaDao {

    private List<pelicula> peliculas = new ArrayList<>();
    @Override
    public void agregarPelicula(pelicula peli) {
        peliculas.add(peli);
    }

    @Override
    public void eliminarPelicula(int id) {
        System.out.println("\nEliminando pelicula... " + id);

        // NOTA: En este método no se utilizó un foreach porque marcaba un error al momento de ejecutar el método remove
        // Comparar el id mandado como parametro con el id de cada elemento de la lista
        boolean estado = false;
        for (int i = 0; i < peliculas.size(); i++) {
            pelicula peli = peliculas.get(i);
            if (peli.getId() == id){
                peliculas.remove(i);
                System.out.println("\tLa pelicula ha sido eliminada con éxito");
                estado = true; // Variable para saber si se elimino la pelicula con éxito
                break;
            }
        }
        if (!estado)
            System.out.println("\tLa pelicula que se quiere eliminar no existe");

        // Función lambda que hace lo anterior pero en una sola línea (funciona solo con el if).
            // peliculas.removeIf(peli -> peli.getId() == id);

    }

    @Override
    public void obtenerPeliculas() {
        System.out.println("\nLista de peliculas:");
        for (pelicula peli : peliculas){
            System.out.println("\t" + peli.getNombre()); //Llama al método obtener nombre
        }
    }

    @Override
    public void obtenerPeliculasDisponibles() {
        System.out.println("\nLista de peliculas disponibles:");
        for (pelicula peli : peliculas){
            if (peli.getDisponible())
                System.out.println("\t" + peli.getNombre()); //Llama al método obtener nombre
        }
    }

    @Override
    public void obtenerPeliculasNoDisponibles() {
        System.out.println("\nLista de peliculas no disponibles:");
        for (pelicula peli : peliculas){
            if (!peli.getDisponible())
                System.out.println("\t" + peli.getNombre()); //Llama al método obtener nombre
        }
    }

    @Override
    public void marcarPeliculaComoDisponible(int id) {
        System.out.println("\nCambiando la disponibilidad de la pelicula... " + id);
        boolean encontrado = false;
        for (pelicula peli : peliculas) {
            if (peli.getId() == id) {
                encontrado = true; //Variable para saber si encontro la pelicula
                if (!peli.getDisponible()) {
                    peli.setDisponible(true);
                    System.out.println("\tLa pelicula " + peli.getNombre() + " ha sido marcada como disponible");
                    break;
                }
                else{
                    System.out.println("\tLa pelicula " + peli.getNombre() + " ya esta marcada como disponible");
                    break;
                }
            }
        }
        if(!encontrado)
            System.out.println("\tLa pelicula no existe");
    }
}