/*

    Nombre: FormIngreso
    Fecha: 24/09/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: UploadSitracha
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Clase que implementa el formulario de ingreso
                 y verifica las credenciales de acceso del 
                 usuario

 */

// definición del paquete
package seguridad;

// importamos las librerías
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.formdev.flatlaf.FlatIntelliJLaf;
import dbApi.Mensaje;
import dbApi.fuentes;
import inicio.MiMarco;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class formingreso extends JFrame{

    // definición de variables
    private Seguridad Seguridad;            // clase de verificación
    private JTextField tUsuario;            // nombre de usuario
    private JTextField tPassword;           // contraseña de acceso

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos la clase de 
     * seguridad e inicializamos las variables
     */
    public formingreso(){

        // intentamos mejorar el renderizado de fuentes, esto
        // debería ser la primera línea de la aplicación
        System.setProperty("awt.useSystemAAFontSettings", "lcd");
        System.setProperty("swing.aatext", "true");

        // instanciamos la clase 
        this.Seguridad = new Seguridad();

        // fijamos el laf
        FlatIntelliJLaf.setup();

        // fijamos el tamaño
        setSize(450, 250);

        // podemos redimensionar
        setResizable(false);

        // instanciamos las fuentes
        fuentes Fuente = new fuentes();
        
        // fijamos el título
        setTitle("Ingreso al Sistema");

        // centramos la ventana
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = miPantalla.getScreenSize();

        // ahora lo convertimos a alto y ancho
        int ancho = tamanioPantalla.width;
        int alto = tamanioPantalla.height;

        // y ahora lo movemos para centrarlo
        setLocation((ancho-450)/2, (alto-250)/2);

        // fijamos el ícono
        setIconImage(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")).getImage());

        // definimos el layout 
        JPanel Contenedor = new JPanel();
        Contenedor.setLayout(new BorderLayout(5,5));

        // presentamos al norte el título
        JLabel lTitulo = new JLabel("Trazabilidad de Pacientes");
        lTitulo.setFont(Fuente.Negrita);
        Contenedor.add(lTitulo, BorderLayout.NORTH);

        // al oeste presentamos el logo
        JLabel gLogo = new JLabel();
        gLogo.setIcon(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")));
        Contenedor.add(gLogo, BorderLayout.WEST);

        // en el centro definimos un nuevo panel
        JPanel Acceso = new JPanel();
        Acceso.setLayout(new BoxLayout(Acceso, BoxLayout.PAGE_AXIS));

        // agregamos un panel para la primer fila
        JPanel Fila1 = new JPanel();
        Fila1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

        // presenta el nombre de usuario
        JLabel lUsuario = new JLabel("Usuario:");
        lUsuario.setPreferredSize(new Dimension(90, 30));
        lUsuario.setFont(Fuente.Negrita);
        Fila1.add(lUsuario);
        this.tUsuario = new JTextField();
        this.tUsuario.setFont(Fuente.Normal);
        this.tUsuario.setPreferredSize(new Dimension(120, 30));
        this.tUsuario.setToolTipText("Su nombre de usuario para la base");
        Fila1.add(this.tUsuario);

        // agregamos el listener
        this.tUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                tUsuarioKeyPressed(evt);
            }
        });

        // agregamos un panel para la segunda fila 
        JPanel Fila2 = new JPanel();
        Fila2.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

        // presenta la contraseña
        JLabel lContrasenia = new JLabel("Contraseña: ");
        lContrasenia.setPreferredSize(new Dimension(90, 30));
        lContrasenia.setFont(Fuente.Negrita);
        Fila2.add(lContrasenia);
        this.tPassword = new JPasswordField();
        this.tPassword.setFont(Fuente.Normal);
        this.tPassword.setPreferredSize(new Dimension(120, 30));
        this.tPassword.setToolTipText("Su contraseña de acceso");
        Fila2.add(this.tPassword);

        // agregamos el listener
        this.tPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                tPasswordKeyPressed(evt);
            }
        });

        // agregamos un panel para la fila de los botones
        JPanel Fila3 = new JPanel();
        Fila3.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 5));

        // agregamos el botón ingresar
        JButton btnIngreso = new JButton("Ingresar");
        btnIngreso.setIcon(new ImageIcon(getClass().getResource("/recursos/confirmar.png")));
        btnIngreso.setFont(Fuente.Normal);
        btnIngreso.setToolTipText("Ingresa al Sistema");
        btnIngreso.setPreferredSize(new Dimension(120, 30));
        Fila3.add(btnIngreso);

        // fijamos el listener
        btnIngreso.addActionListener(e -> validaUsuario());

        // agregamos las filas al contenedor de la botonera
        Acceso.add(Fila1);
        Acceso.add(Fila2);
        Acceso.add(Fila3);

        // agregamos la botonera
        Contenedor.add(Acceso, BorderLayout.CENTER);

        // agregamos el contenedor
        add(Contenedor);

        // mostramos el formulario 
        this.setVisible(true);

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar el botón ingresar que verifica
     * los datos del formulario
     */
    private void validaUsuario(){

        // si no ingresó el usuario
        if (this.tUsuario.getText().equals("")){

            // presenta el mensaje y retorna
            new Mensaje("Ingrese su nombre de usuario");
            return;
            
        }

        // si no ingresó la contraseña
        if (this.tPassword.getText().equals("")){

            // presenta el mensaje y retorna
            new Mensaje("Debe ingresar su contraseña");
            return;

        }

        // verificamos las credenciales
        if (Seguridad.validaAcceso(this.tUsuario.getText(), this.tPassword.getText())){

            // instanciamos el formulario
            new MiMarco();

            // destruimos este formulario 
            this.dispose();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param evt el evento de teclado
     * Método llamado al pulsar sobre el campo de usuario, si 
     * se pulsó enter cambia el foco
     */
    private void tUsuarioKeyPressed(KeyEvent evt) {

        // si pulsó enter
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.tPassword.requestFocus();
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param evt evento del teclado
     * Método llamado al pulsar una tecla sobre el campo 
     * contraseña, si se pulsó enter verifica el ingreso
     */
    private void tPasswordKeyPressed(java.awt.event.KeyEvent evt) {

        // si pulsó enter 
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.validaUsuario();
        }

    }

}
