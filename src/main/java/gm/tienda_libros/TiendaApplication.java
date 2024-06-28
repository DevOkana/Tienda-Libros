package gm.tienda_libros;

import gm.tienda_libros.vista.LibroForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class TiendaApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = new SpringApplicationBuilder(TiendaApplication.class).headless(false).web(WebApplicationType.NONE).run(args);
        //Ejecutar el formulario
        EventQueue.invokeLater(() -> {
            try {
                context.getBean(LibroForm.class).setVisible(true);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
