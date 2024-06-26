package gm.tienda_libros.vista;

import gm.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class LibroForm extends JFrame {

    LibroServicio libroServicio;//Servicio de libros
    private JPanel panel;//Panel principal
    private JTable tablaLibros;//Tabla para mostrar los libros

    private DefaultTableModel tablaModeloLibros;//Modelo de la tabla

    @Autowired
    public LibroForm(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;
        iniciarForma();
    }

    private void iniciarForma() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        //Traer los datos de la pantalla para posicionar el componente
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int width = dimension.width;
        int height = dimension.height;
        setLocation(width / 2 - getWidth() / 2, height / 2 - getHeight() / 2);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloLibros = new DefaultTableModel(0,5);//Crear el modelo de la tabla
        String[] columnas = {"Id", "Nombre", "Autor", "Precio", "Cantidad"};
        this.tablaModeloLibros.setColumnIdentifiers(columnas);//Asignar los nombres de las columnas
        //Instanciar la tabla
        this.tablaLibros = new JTable(tablaModeloLibros);

    }
}
