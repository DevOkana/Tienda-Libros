package gm.tienda_libros.vista;

import gm.tienda_libros.modelo.Libro;
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
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField cantidadTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;

    private DefaultTableModel tablaModeloLibros;//Modelo de la tabla

    @Autowired
    public LibroForm(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;

        iniciarForma();//Llamar al método para iniciar la pantalla
        agregarButton.addActionListener(e -> agregarLibro());//Agregar un evento al botón agregar
    }

    private void agregarLibro() {
        //Leer los datos del formulario
        if(libroTexto.getText().isEmpty() || autorTexto.getText().isEmpty() || precioTexto.getText().isEmpty()){
            mostrarMensaje("Los campos no pueden estar vacíos", 1);
            return;
        }
        var libro = new Libro(libroTexto.getText(),autorTexto.getText(),Double.parseDouble(precioTexto.getText()));
        libroServicio.agregarLibro(libro);
        mostrarMensaje("Libro agregado correctamente", 3);
        listarLibros();
        //Limpiar los campos de texto
        limpiarFormulario();
    }

    private void limpiarFormulario(){
        //Limpiar los campos de texto
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        cantidadTexto.setText("");
    }

    private void mostrarMensaje(String mensaje, int tipoMensaje){
        int tipo = 0;
        switch (tipoMensaje){
            case 1:
                tipo = JOptionPane.INFORMATION_MESSAGE;
                break;
            case 2:
                tipo = JOptionPane.WARNING_MESSAGE;
                break;
            case 3:
                tipo = JOptionPane.PLAIN_MESSAGE;
                break;
            case 4:
                tipo = JOptionPane.ERROR_MESSAGE;
                break;
            default:
                mensaje = "Error Desconocido";
                tipo = JOptionPane.ERROR_MESSAGE;
                break;
        }
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje", tipo);
    }
    private void iniciarForma() {
        setContentPane(panel);
        setTitle("Control sobre las venta");
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
        listarLibros();//Llamar al método para listar los libros
    }
    private void listarLibros(){
        this.tablaModeloLibros.setRowCount(0);//Limpiar la tabla
        var libros = libroServicio.listarLibros();//Obtener los libros
        libros.forEach(libro -> {
            Object[] datos = {libro.getIdLibro(),libro.getNombreLibro(),libro.getAutorLibro(),libro.getPrecioLibro(),libro.getCantidadLibro()};
            this.tablaModeloLibros.addRow(datos);
        });

    }
}
