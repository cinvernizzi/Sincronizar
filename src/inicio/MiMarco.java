/**
 * 
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 24/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase llamada luego de acreditarse el usuario que define el 
 * tabulador con los formularios de la aplicación
 * 
 */

// definición del paquete
package inicio;

// importamos las librerías
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.Dimension;
import java.awt.Toolkit;
import dbApi.fuentes;
import pacientes.formpacientes;
import vertical.formvertical;

// definimos la clase
public class MiMarco extends JFrame{

    // definimos las variables
    private formpacientes formPacientes;
    private formvertical formVertical;
    private JTabbedPane tabulador;

	// constructor de la clase
    public MiMarco(){

        // intentamos mejorar el renderizado de fuentes, esto
        // debería ser la primera línea de la aplicación
        System.setProperty("awt.useSystemAAFontSettings", "lcd");
        System.setProperty("swing.aatext", "true");

        // fijamos el laf
        FlatIntelliJLaf.setup();

        // fijamos el evento por defecto al cerrar
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // fijamos el tamaño
        setSize(1000, 600);

        // podemos redimensionar
        setResizable(true);

        // instanciamos las fuentes
        fuentes Fuente = new fuentes();
        
        // fijamos el título
        setTitle("Trazabilidad de Muestras");

        // centramos la ventana
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = miPantalla.getScreenSize();

        // ahora lo convertimos a alto y ancho
        int ancho = tamanioPantalla.width;
        int alto = tamanioPantalla.height;

        // y ahora lo movemos para centrarlo
        setLocation((ancho-1000)/2, (alto-600)/2);

        // fijamos el ícono
        setIconImage(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")).getImage());        

        // aquí agregamos el menú de opciones
        JMenuBar menu = new JMenuBar();
        menu.setFont(Fuente.Negrita);

        // definimos el menú de pacientes y sus opciones
        JMenu mPacientes = new JMenu("Pacientes");
        mPacientes.setFont(Fuente.Normal);
        JMenuItem mBuscaPaciente = new JMenuItem("Buscar", new ImageIcon(getClass().getResource("/recursos/buscar.png")));
        mBuscaPaciente.setToolTipText("Busca un paciente en la base");
        mBuscaPaciente.addActionListener(e -> buscaPaciente());        
        mPacientes.add(mBuscaPaciente);        
        JMenuItem mNuevoPaciente = new JMenuItem("Nuevo", new ImageIcon(getClass().getResource("/recursos/agregar.png")));
        mNuevoPaciente.setToolTipText("Ingresa un nuevo paciente en la base");
        mNuevoPaciente.addActionListener(e -> nuevoPaciente());        
        mPacientes.add(mNuevoPaciente);
        mPacientes.addSeparator();
        JMenuItem mEnfermedadesPaciente = new JMenuItem("Enfermedades", new ImageIcon(getClass().getResource("/recursos/capsula.png")));
        mEnfermedadesPaciente.setToolTipText("Declara las enfermedades del paciente");
        mEnfermedadesPaciente.addActionListener(e -> enfermedadesPaciente());        
        mPacientes.add(mEnfermedadesPaciente);
        JMenuItem mTransfusionesPaciente = new JMenuItem("Transfusiones", new ImageIcon(getClass().getResource("/recursos/blood.png")));
        mTransfusionesPaciente.setToolTipText("Declara las transfusiones recibidas");
        mTransfusionesPaciente.addActionListener(e -> transfusionesPaciente());
        mPacientes.add(mTransfusionesPaciente);
        JMenuItem mTransplantesPaciente = new JMenuItem("Transplantes", new ImageIcon(getClass().getResource("/recursos/transplante.png")));
        mTransplantesPaciente.setToolTipText("Declara los transplantes recibidos");
        mTransplantesPaciente.addActionListener(e -> transplantesPaciente());
        mPacientes.add(mTransplantesPaciente);
        mPacientes.addSeparator();
        JMenuItem mSalir = new JMenuItem("Salir", new ImageIcon(getClass().getResource("/recursos/salida.png")));
        mSalir.setToolTipText("Abandona la aplicación");
        mSalir.addActionListener(e -> Salir());        
        mPacientes.add(mSalir);

        // definimos el menú de vertical y sus opciones
        JMenu mVertical = new JMenu("Vertical");
        mVertical.setFont(Fuente.Normal);
        JMenuItem mBuscaVertical = new JMenuItem("Buscar", new ImageIcon(getClass().getResource("/recursos/buscar.png")));
        mBuscaVertical.setToolTipText("Busca un paciente vertical");
        mBuscaVertical.addActionListener(e -> buscaVertical());        
        mVertical.add(mBuscaVertical);        
        JMenuItem mNuevoVertical = new JMenuItem("Nuevo", new ImageIcon(getClass().getResource("/recursos/agregar.png")));
        mNuevoVertical.setToolTipText("Ingresa un nuevo paciente vertical");
        mNuevoVertical.addActionListener(e -> nuevoVertical());        
        mVertical.add(mNuevoVertical);
        mVertical.addSeparator();
        JMenuItem mEnfermedadesVertical = new JMenuItem("Enfermedades", new ImageIcon(getClass().getResource("/recursos/capsula.png")));
        mEnfermedadesVertical.setToolTipText("Declara las enfermedades del paciente");
        mEnfermedadesVertical.addActionListener(e -> enfermedadesVertical());        
        mVertical.add(mEnfermedadesVertical);
        JMenuItem mTransfusionesVertical = new JMenuItem("Transfusiones", new ImageIcon(getClass().getResource("/recursos/blood.png")));
        mTransfusionesVertical.setToolTipText("Declara las transfusiones recibidas");
        mTransfusionesVertical.addActionListener(e -> transfusionesVertical());
        mVertical.add(mTransfusionesVertical);
        JMenuItem mTransplantesVertical = new JMenuItem("Transplantes", new ImageIcon(getClass().getResource("/recursos/transplante.png")));
        mTransplantesVertical.setToolTipText("Declara los transplantes recibidos");
        mTransplantesVertical.addActionListener(e -> transplantesVertical());
        mVertical.add(mTransplantesVertical);
        mVertical.addSeparator();
        JMenuItem mPartoVertical = new JMenuItem("Parto", new ImageIcon(getClass().getResource("/recursos/vertical.png")));
        mPartoVertical.setToolTipText("Ingresa los datos del parto");
        mPartoVertical.addActionListener(e -> partoVertical());
        mVertical.add(mPartoVertical);

        // ahora agregamos los menú a la barra
        menu.add(mPacientes);
        menu.add(mVertical);

        // agregamos la barra al formulario
        setJMenuBar(menu);

        // creamos el tabulador
        this.tabulador = new JTabbedPane();
        this.tabulador.setFont(Fuente.Normal);

        // creamos el panel del primer tabulador
        // y lo asociamos al formulario de pacientes
        JPanel tPacientes = new JPanel();  
        this.formPacientes = new formpacientes(tPacientes);

        // creamos el panel del segundo tabulador
        JPanel tVertical = new JPanel();
        this.formVertical = new formvertical(tVertical, this.formPacientes);

        // creamos el panel del tercer tabulador
        JPanel tMuestras = new JPanel();

        // agregamos los paneles al tabulador
        this.tabulador.addTab("Pacientes", new ImageIcon(getClass().getResource("/recursos/pacientes.png")), tPacientes, "Datos de Pacientes Adultos");
        this.tabulador.addTab("Vertical", new ImageIcon(getClass().getResource("/recursos/vertical.png")), tVertical, "Datos de Pacientes Verticales");
        this.tabulador.addTab("Muestras", new ImageIcon(getClass().getResource("/recursos/blood.png")), tMuestras, "Muestras Pendientes");

        // agregamos el tabulador a la ventana
        add(this.tabulador);

        // mostramos el formulario
        this.setVisible(true);

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar la opción buscar paciente del 
     * menú que llama el procedimiento de la clase
     */
    private void buscaPaciente(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(0);

        // llamamos el método 
        this.formPacientes.buscaPaciente();
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar la opción buscar paciente del 
     * menú que llama el procedimiento de la clase
     */
    private void nuevoPaciente(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(0);

        // llamamos el método 
        this.formPacientes.nuevoPaciente();
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar la opción enfermedades del 
     * menú de pacientes adultos
     */
    private void enfermedadesPaciente(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(0);

        // llamamos al método
        this.formPacientes.verEnfermedades();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar la opción transfusiones del 
     * menú de pacientes adultos
     */
    private void transfusionesPaciente(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(0);

        // llamamos al método
        this.formPacientes.verTransfusiones();

    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar la opción enfermedades del 
     * menú de pacientes adultos
     */
    private void transplantesPaciente(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(0);

        // llamamos al método
        this.formPacientes.verTransplantes();
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde la búsqueda de pacientes verticales
     */
    private void buscaVertical(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(1);

        // llamamos al método
        this.formVertical.buscaPaciente();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el alta de pacientes verticales
     */
    private void nuevoVertical(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(1);

        // llamamos al método 
        this.formVertical.nuevoPaciente();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el menú vertical que abre el formulario 
     * para la declaración de enfermedades
     */
    private void enfermedadesVertical(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(1);

        // llamamos al método
        this.formVertical.verEnfermedades();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el menú vertical que instancia el 
     * formulario de declaración de transfusiones
     */
    private void transfusionesVertical(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(1);

        // llamamos al método
        this.formVertical.verTransfusiones();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el menú vertical que presenta el 
     * formulario con los datos de los transplantes
     */
    private void transplantesVertical(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(1);

        // lamamos al método
        this.formVertical.verTransplantes();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el menú vertical que abre el layer
     * con el formulario de datos del parto
     */
    private void partoVertical(){

        // fijamos el tabulador
        this.tabulador.setSelectedIndex(1);

        // cargamos el formulario
        this.formVertical.partoVertical();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar la opción salir que 
     * simplemente cierra la aplicación
     */
    private void Salir(){

        // abandona la ejecución
        System.exit(0);

    }

}
