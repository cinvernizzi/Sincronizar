/*

    Nombre: Mensaje
    Fecha: 15/05/2019
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
    Comentarios: Método que recibe en el constructor un texto
                 a presentar y lo muestra en un diálogo
                 emergente y luego cierra automáticamente

 */

// declaracion del paquete
package dbApi;

// importamos las librerías
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

// definición de la clase
public class Mensaje extends javax.swing.JDialog {

    // define el serial id
    private static final long serialVersionUID = 1L;

	// declaracion de variables
    private final JLabel tMensaje;
    private final Timer Temporizador;

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param mensaje el mensaje a presentar
     * Creamos el formulario
     */
    public Mensaje(String mensaje) {

        // definimos las fuentes
        fuentes Fuentes = new fuentes();
        
        // fijamos las propiedades
        this.setAlwaysOnTop(true);
        this.setAutoRequestFocus(false);
        this.setUndecorated(true);
        this.setResizable(false);
        this.setBounds(900, 50, 200, 50);

        // declaramos el label del mensaje
        this.tMensaje = new JLabel(mensaje);
        this.tMensaje.setBounds(0, 0, 190, 45);
        this.tMensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.tMensaje.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        this.tMensaje.setFont(Fuentes.Normal);
        this.add(this.tMensaje);

        // mostramos el formulario
        this.setVisible(true);

        // instanciamos el temporizador
        Temporizador = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {

                // cerramos el mensaje
                cerrarMensaje();

            }});

        // iniciamos el timer
        Temporizador.start();

    }

    // método protegido que destruye el formulario
    private void cerrarMensaje(){

        // detiene el temporizador
        Temporizador.stop();

        // destruye el formulario
        this.dispose();

    }

}
