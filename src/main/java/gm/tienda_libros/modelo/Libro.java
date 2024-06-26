package gm.tienda_libros.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Creacion de de la tabla libros
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Generar un numero de identidad
    Integer idLibro;
    String nombreLibro;
    String autorLibro;
    Double precioLibro;
    Integer cantidadLibro;

    public Libro(int i,String text, String text1, double v, int c) {
        this.idLibro = i;
        this.nombreLibro = text;
        this.autorLibro = text1;
        this.precioLibro = v;
        this.cantidadLibro = c;
    }
}
