package gm.tienda_libros.servicio;

import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LibroServicio implements ILibroServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Override
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }
    @Override
    public Libro obtenerLibro(int idLibro) {
        return libroRepositorio.findById(idLibro).orElse(null);
    }
    @Override
    public void agregarLibro(Libro libro) {
        libroRepositorio.save(libro);
    }
    @Override
    public void eliminarLibro(Libro libro) {
        libroRepositorio.delete(libro);
    }
}
