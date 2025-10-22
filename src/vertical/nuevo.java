/*

    Nombre: Nuevo
    Fecha: 24/04/2019
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: diagnostico
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Método que define el formulario para un paciente nuevo
                 vertical, solicitando el número de documento de la 
                 madre y del hijo

*/

// declaración del paquete
package vertical;

// importamos las librerías
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import dbApi.Mensaje;
import dbApi.fuentes;
import pacientes.pacientes;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class nuevo extends JDialog {

    // declaramsos las variables
    private formvertical Padre;               // el formulario padre
    private JTextField tDocMadre;             // el documento de la madre
    private JTextField tDocPaciente;          // el documento del paciente
    private pacientes Pacientes;              // objeto de la base de datos

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param padre el formulario padre
     * Constructor de la clase, instanciamos los objetos e 
     * inicializamos las variables
     */
    public nuevo(formvertical padre){

        // fijamos como modal
        setModal(true);

        // fijamos el padre
        this.Padre = padre;

        // definimos la fuente
        fuentes Fuente = new fuentes();

        // instanciamos la clase de pacientes
        this.Pacientes = new pacientes();

		// fijamos las propiedades
		this.setSize(350, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Paciente Vertical");
        
        // fijamos el ícono
		Toolkit miPantalla = Toolkit.getDefaultToolkit();		
        setIconImage(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")).getImage());

        // centramos la ventana
        Dimension tamanioPantalla = miPantalla.getScreenSize();

        // ahora lo convertimos a alto y ancho
        int ancho = tamanioPantalla.width;
        int alto = tamanioPantalla.height;

        // y ahora lo movemos para centrarlo
        setLocation((ancho-350)/2, (alto-200)/2);

		// fijamos el layout
		JPanel Principal = new JPanel();
		Principal.setLayout(new BoxLayout(Principal, BoxLayout.PAGE_AXIS));		

        // presenta el título
        JLabel lTitulo = new JLabel("Alta de Nuevo Paciente");
        lTitulo.setFont(Fuente.Negrita);
        Principal.add(lTitulo);

        // definimos la primer fila
        JPanel Fila1 = new JPanel();
        Fila1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

        // pedimos el documento de la madre
        JLabel lDocMadre = new JLabel("Documento de la Madre: ");
        lDocMadre.setFont(Fuente.Normal);
        Fila1.add(lDocMadre);
        this.tDocMadre = new JTextField();
        this.tDocMadre.setFont(Fuente.Normal);
        this.tDocMadre.setPreferredSize(new Dimension(150, 30));
        this.tDocMadre.setToolTipText("Número de documento de la madre");
        Fila1.add(this.tDocMadre);

        // agregamos la fila
        Principal.add(Fila1);

        // agregamos la segunda fila
        JPanel Fila2 = new JPanel();
        Fila2.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

        // pedimos el documento del paciente 
        JLabel lDocPaciente = new JLabel("Documento del paciente: ");
        lDocPaciente.setFont(Fuente.Normal);
        Fila2.add(lDocPaciente);
        this.tDocPaciente = new JTextField();
        this.tDocPaciente.setFont(Fuente.Normal);
        this.tDocPaciente.setPreferredSize(new Dimension(150, 30));
        this.tDocPaciente.setToolTipText("Número de documento del paciente");
        Fila2.add(this.tDocPaciente);

        // agregamos la segunda fila
        Principal.add(Fila2);

        // agregamos la fila de los botones
        JPanel Fila3 = new JPanel();
        Fila3.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 5));

        // el botón aceptar
        JButton btnGrabar = new JButton("Grabar");
        btnGrabar.setFont(Fuente.Normal);
        btnGrabar.setIcon(new ImageIcon(getClass().getResource("/recursos/confirmar.png")));        
        btnGrabar.setToolTipText("Pulse para grabar el registro");
        btnGrabar.setPreferredSize(new Dimension(120, 30));
        Fila3.add(btnGrabar);

        // fijamos el listener
        btnGrabar.addActionListener(e -> verificaPaciente());

        // el botón cancelar 
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(Fuente.Normal);
        btnCancelar.setIcon(new ImageIcon(getClass().getResource("/recursos/borrar.png")));        
        btnCancelar.setToolTipText("Pulse para reiniciar el formulario");
        btnCancelar.setPreferredSize(new Dimension(120, 30));        
        Fila3.add(btnCancelar);

        // fijamos el listener
        btnCancelar.addActionListener(e -> cancelaPaciente());        

        // agregamos la fila 
        Principal.add(Fila3);

        // agregamos el panel principal
        add(Principal);

        // mostramos el formulario
        this.setVisible(true);

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar el botón aceptar que verifica
     * los datos del formulario
     */
    private void verificaPaciente(){

        // declaración de variables
        String docmadre = "";
        String docpaciente = "";

        // verificamos el documento de la madre
        docmadre = this.tDocMadre.getText();
        if (docmadre.equals("")){

            // presenta el mensaje y retorna
            new Mensaje("Ingrese el documento de la madre");
            return; 

        }

        // verificamos el documento del hijo
        docpaciente = this.tDocPaciente.getText();
        if (docpaciente.equals("")){

            // presenta el mensaje y retorna
            new Mensaje("Ingrese el documento del paciente");
            return;

        }

        // buscamos la madre
        int idmadre = this.Pacientes.getProtocoloPaciente(docmadre);
        if (idmadre == 0){

            // presenta el mensaje
            new Mensaje("No encuentro el protocolo de la madre");
            return;

        }

        // buscamos al hijo
        int idpaciente = this.Pacientes.getProtocoloPaciente(docpaciente);

        // si lo encontró lo presenta 
        if (idpaciente != 0){

            // lo presenta
            this.Padre.getDatosPaciente(idpaciente);

            // carga los datos de la madre
            this.Padre.cargaDatosMadre(idmadre);
            
        // si no lo encontró 
        } else {

            // limpiamos el formulario 
            this.Padre.limpiaFormulario();

            // cargamos la ficha de la madre
            this.Padre.cargaDatosMadre(idmadre);

            // fijamos el documento 
            this.Padre.tDocumento.setText(docpaciente);

        }

        // cerramos el formulario
        this.dispose();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar el botón cancelar que simplemente
     * elimina el formulario
     */
    private void cancelaPaciente(){

        // cerramos el formulario
        this.dispose();

    }

}
