package gm.tienda_libros.servicio;

import gm.tienda_libros.modelo.Libro;

import java.util.List;

public interface ILibroServicio {
    List<Libro> listarLibros();
    Libro obtenerLibro(int idLibro);
    void agregarLibro(Libro libro);
    void eliminarLibro(Libro libro);
}
