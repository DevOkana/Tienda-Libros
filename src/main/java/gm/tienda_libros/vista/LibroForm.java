package gm.tienda_libros.vista;

import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    private JTextField idTexto;
    private DefaultTableModel tablaModeloLibros;//Modelo de la tabla

    private boolean modificar = false;

    @Autowired
    public LibroForm(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;

        iniciarForma();//Llamar al método para iniciar la pantalla
        agregarButton.addActionListener(e -> agregarLibro());//Agregar un evento al botón agregar
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
                modificar = true;
                agregarButton.setForeground(Color.RED);
                //En caso de que desees que no se pueda agregar el mismo libro seleccionado
                //agregarButton.setEnabled(false);
            }

        });
        modificarButton.addActionListener(e -> {
            modificarLibro();
            agregarButton.setForeground(Color.BLACK);
            //En caso de que desees que no se pueda agregar el mismo libro seleccionado
            //agregarButton.setEnabled(true);
        });

        eliminarButton.addActionListener(e -> {

            if(idTexto.getText().isEmpty()){
                mostrarMensaje("Selecciona el libro a eliminar", 2);
                return;
            }
            int id = Integer.parseInt(idTexto.getText());
            Libro libro = libroServicio.obtenerLibro(id);
            if(libro != null){
                libroServicio.eliminarLibro(libro);
                mostrarMensaje("Libro eliminado correctamente", 1);
                listarLibros();
                limpiarFormulario();
            }



        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (libroTexto.getText().isEmpty() && autorTexto.getText().isEmpty() &&
                        precioTexto.getText().isEmpty() && cantidadTexto.getText().isEmpty()) {
                    agregarButton.setForeground(Color.BLACK);
                }
            }
        });

    }

    private void modificarLibro() {
        if(!idTexto.getText().isEmpty()){
            int id = Integer.parseInt(idTexto.getText());
            var libro = libroServicio.obtenerLibro(id);
            if(libro != null){
                if(libroTexto.getText().isEmpty()){
                    mostrarMensaje("Proporciona los datos del libro a modificar", 2);
                    libroTexto.requestFocusInWindow();
                    return;
                }

                Libro modificarLibro = new Libro(id,libroTexto.getText(),autorTexto.getText(),Double.parseDouble(precioTexto.getText()), Integer.parseInt(cantidadTexto.getText()));
                libroServicio.agregarLibro(modificarLibro);

                mostrarMensaje("Libro modificado correctamente", 1);
                listarLibros();
                limpiarFormulario();
                return;
            }
        }
        mostrarMensaje("Debes de seleccionar un libro parar modificarlo", 2);
    }

    private void cargarLibroSeleccionado() {
        // El índice de las columnas inician en 0
        int id = tablaLibros.getSelectedRow();
        if(id != -1){
            String idLibro = tablaLibros.getModel().getValueAt(id, 0).toString();
            idTexto.setText(idLibro);
            libroTexto.setText(tablaLibros.getModel().getValueAt(id, 1).toString());
            autorTexto.setText(tablaLibros.getModel().getValueAt(id, 2).toString());
            precioTexto.setText(String.valueOf(tablaLibros.getModel().getValueAt(id, 1).toString()));
            cantidadTexto.setText(String.valueOf(tablaLibros.getModel().getValueAt(id, 4).toString()));

        }
    }

    private void agregarLibro() {
        if (modificar) {
            if(ConfirmarMensaje("¿Está seguro de que desea agregar el mismo libro dos veces?", "Agregar Libro Existente", JOptionPane.YES_NO_OPTION)){
                modificar = false;
                agregarLibro();
            }
            return;
        }

        //Leer los datos del formulario
        if(libroTexto.getText().isEmpty() || autorTexto.getText().isEmpty() || precioTexto.getText().isEmpty()){
            mostrarMensaje("Los campos no pueden estar vacíos", 1);
            return;
        }
        var libro = new Libro(null,libroTexto.getText(),autorTexto.getText(),Double.parseDouble(precioTexto.getText()), Integer.parseInt(cantidadTexto.getText()));
        libroServicio.agregarLibro(libro);
        mostrarMensaje("Libro agregado correctamente", 1);
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
        int tipo = switch (tipoMensaje) {
            case 1 -> JOptionPane.INFORMATION_MESSAGE;
            case 2 -> JOptionPane.WARNING_MESSAGE;
            case 3 -> JOptionPane.ERROR_MESSAGE;
            default -> {
                mensaje = "Error Desconocido";
                yield JOptionPane.ERROR_MESSAGE;
            }
        };
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje", tipo);
    }

    private Boolean ConfirmarMensaje(String mensaje, String titulo, int tipoPanel){
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                mensaje,
                titulo,
                tipoPanel
        );
        if (respuesta == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
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
        idTexto = new JTextField();
        idTexto.setVisible(false);
        // TODO: place custom component creation code here
        this.tablaModeloLibros = new DefaultTableModel(0,5);//Crear el modelo de la tabla
        String[] columnas = {"Id", "Nombre", "Autor", "Precio", "Cantidad"};
        this.tablaModeloLibros.setColumnIdentifiers(columnas);//Asignar los nombres de las columnas
        //Instance la tabla
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
