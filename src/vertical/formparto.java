/*

    Nombre: FormParto
    Fecha: 05/02/2020
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: diagnostico
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
    Comentarios: Clase que implementa el formulario de datos del parto
                 de los pacientes congénitos

 */

// definimos el paquete
package vertical;

// importamos las librerías
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import dbApi.ComboSiNo;
import dbApi.Mensaje;
import seguridad.Seguridad;
import dbApi.Utilidades;
import dbApi.fuentes;
import instituciones.instituciones;
import localidades.localidades;
import provincias.provincias;

// definición de la clase
public class formparto extends JDialog {

	// declaramos el serial id
	private static final long serialVersionUID = 1L;

	// declaración de variables
	private JTextField tId;                           // clave del registro
	private JTextField tSivila;                       // clave del sivila
	private JDateChooser dReportado;                  // fecha de denuncia al sivila
	private JComboBox<Object> cParto;                 // tipo de parto
	private JSpinner sPeso;                           // peso al nacer
	private JComboBox<Object> cPrematuro;             // si fue prematuro
	public instituciones cInstitucion;                // combo de la institución
	private JTextArea tComentarios;                   // comentarios del usuario
	private JTextField tUsuario;                      // nombre del usuario
	private JTextField tAlta;                         // fecha de alta del registro
	private provincias cProvincia;                    // combo de provincias
	private localidades cLocalidad;                   // combo de localidades

	// variables que públicas a las que accede el formulario padre
	private int Protocolo;               // protocolo del paciente
	private int IdMadre;                 // protocolo de la madre
	private parto Parto;                // objeto de la base de datos
	private Utilidades Herramientas;    // funciones de fecha
	
	// constructor del formulario
	public formparto(formVertical padre) {

        // setea como modal
        setModal(true);

		// inicializamos las variables
		this.Protocolo = padre.getProtocolo();
		this.IdMadre = padre.getIdMadre();

		// instanciamos los objetos
		this.Parto = new parto();
		this.Herramientas = new Utilidades();

		// configuramos el formulario
		this.initForm();

	}

    /**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método que configura el formulario
	 */
	protected void initForm(){

		// definimos la fuente
		fuentes Fuente = new fuentes();

		// posicionamos el formulario
		this.setSize(645, 365);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Datos del Parto");

        // fijamos el ícono
		Toolkit miPantalla = Toolkit.getDefaultToolkit();		
		setIconImage(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")).getImage());

        // centramos la ventana
        Dimension tamanioPantalla = miPantalla.getScreenSize();

        // ahora lo convertimos a alto y ancho
        int ancho = tamanioPantalla.width;
        int alto = tamanioPantalla.height;

        // y ahora lo movemos para centrarlo
        setLocation((ancho-645)/2, (alto-365)/2);

		// fijamos el layout
		JPanel Principal = new JPanel();
		Principal.setLayout(new BoxLayout(Principal, BoxLayout.PAGE_AXIS));		

		// presenta el título
		JLabel lTitulo = new JLabel("Datos del Parto");
		lTitulo.setFont(Fuente.Normal);
		Principal.add(lTitulo);

		// agregamos un panel para los controles
        JPanel Fila1 = new JPanel();
        Fila1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

		// presenta la id
		JLabel lId = new JLabel("Id:");
		lId.setFont(Fuente.Normal);
		Fila1.add(lId);
		this.tId = new JTextField();
		this.tId.setPreferredSize(new Dimension(50,30));        
		this.tId.setFont(Fuente.Normal);
		this.tId.setToolTipText("Clave del registro");
		this.tId.setEditable(false);
		Fila1.add(this.tId);

		// pide la clave del sivila
		JLabel lSivila = new JLabel("Sivila:");
		lSivila.setFont(Fuente.Normal);
		Fila1.add(lSivila);
		this.tSivila = new JTextField();
		this.tSivila.setPreferredSize(new Dimension(100, 30));        
		this.tSivila.setFont(Fuente.Normal);
		this.tSivila.setToolTipText("Clave de la denuncia al Sivila");
		Fila1.add(tSivila);

		// pide la fecha de reportado
		JLabel lReportado = new JLabel("Reportado:");
		lReportado.setFont(Fuente.Normal);
		Fila1.add(lReportado);
        this.dReportado = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
		this.dReportado.setPreferredSize(new Dimension(120, 30));        		
        this.dReportado.setFont(Fuente.Normal);
		this.dReportado.setToolTipText("Indique la fecha de denuncia al Sivila");
		Fila1.add(this.dReportado);

		// pide el tipo de parto
		JLabel lParto = new JLabel("Parto:");
		lParto.setFont(Fuente.Normal);
		Fila1.add(lParto);
		this.cParto = new JComboBox<>();
		this.cParto.setPreferredSize(new Dimension(140, 30));        		
		this.cParto.setFont(Fuente.Normal);
		this.cParto.setToolTipText("Seleccione de la lista el tipo de parto");
		this.cParto.setEditable(true);
		Fila1.add(this.cParto);

		// agrega los elementos
		this.elementosParto();

		// pide el peso al nacer
		JLabel lPeso = new JLabel("Peso:");
		lPeso.setFont(Fuente.Normal);
		Fila1.add(lPeso);
		this.sPeso = new JSpinner();
		this.tId.setPreferredSize(new Dimension(75, 30));        		
		this.sPeso.setFont(Fuente.Normal);
		this.sPeso.setToolTipText("Peso al nacer en gramos");
		Fila1.add(this.sPeso);

		// pregunta si fue prematuro
		JLabel lPrematuro = new JLabel("Prematuro:");
		Fila1.add(lPrematuro);
		this.cPrematuro = new ComboSiNo();
		this.cPrematuro.setPreferredSize(new Dimension(90, 30));        		
		this.cPrematuro.setFont(Fuente.Normal);
		this.cPrematuro.setToolTipText("Indique si fue o no prematuro");
		this.cPrematuro.setEditable(true);
		Fila1.add(this.cPrematuro);

		// presenta el select de provincia
        JLabel lProvincia = new JLabel("Provincia:");
        lProvincia.setFont(Fuente.Normal);
        Fila1.add(lProvincia);
        this.cProvincia = new provincias();
        this.cProvincia.setFont(Fuente.Normal);
        this.cProvincia.setToolTipText("Seleccione la provincia de la institucion");
        this.cProvincia.setPreferredSize(new Dimension(250, 30));
        Fila1.add(this.cProvincia);

		// presenta el select de localidad 
        JLabel lLocalidad = new JLabel("Localidad:");
        lLocalidad.setFont(Fuente.Normal);
        Fila1.add(lLocalidad);
        this.cLocalidad = new localidades();
        this.cLocalidad.setFont(Fuente.Normal);
        this.cLocalidad.setToolTipText("Seleccione la localidad de la institución");
        this.cLocalidad.setPreferredSize(new Dimension(300, 30));
        Fila1.add(this.cLocalidad);

 		// presenta la institución de nacimiento
		JLabel lInstitucion = new JLabel("Institución:");
		lInstitucion.setFont(Fuente.Normal);
		Fila1.add(lInstitucion);
		this.cInstitucion = new instituciones();
		this.cInstitucion.setPreferredSize(new Dimension(440, 30));        		
		this.cInstitucion.setFont(Fuente.Normal);
		this.cInstitucion.setToolTipText("Institución de nacimiento");
		Fila1.add(this.cInstitucion);

		// el usuario
		JLabel lUsuario = new JLabel("Usuario:");
		lUsuario.setFont(Fuente.Normal);
		Fila1.add(lUsuario);
		this.tUsuario = new JTextField();
		this.tUsuario.setPreferredSize(new Dimension(90, 30));        
		this.tUsuario.setFont(Fuente.Normal);
		this.tUsuario.setToolTipText("Usuario que ingresó el registro");
		this.tUsuario.setEditable(false);
		Fila1.add(this.tUsuario);

		// por defecto fijamos el usuario actual
		this.tUsuario.setText(Seguridad.Usuario);
		
		// la fecha de alta
		JLabel lAlta = new JLabel("Alta:");
		lAlta.setFont(Fuente.Normal);
		Fila1.add(lAlta);
		this.tAlta = new JTextField();
		this.tAlta.setPreferredSize(new Dimension(90, 30));        		
		this.tAlta.setFont(Fuente.Normal);
		this.tAlta.setToolTipText("Fecha de alta del registro");
		this.tAlta.setEditable(false);
		Fila1.add(this.tAlta);

		// por defecto fijamos la fecha actual
		this.tAlta.setText(this.Herramientas.FechaActual());
		
		// el botón grabar
		JButton btnGrabar = new JButton("Grabar");
		btnGrabar.setPreferredSize(new Dimension(100, 30));        		
		btnGrabar.setFont(Fuente.Normal);
		btnGrabar.setToolTipText("Pulse para grabar el registro");
		btnGrabar.setIcon(new ImageIcon(getClass().getResource("/recursos/save.png")));
		btnGrabar.addActionListener(e -> validaParto());		
		Fila1.add(btnGrabar);

		// el botón cancelar
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setPreferredSize(new Dimension(100, 30));        		
		btnCancelar.setFont(Fuente.Normal);
		btnCancelar.setToolTipText("Reinicia el formulario");
		btnCancelar.setIcon(new ImageIcon(getClass().getResource("/recursos/borrar.png")));
		btnCancelar.addActionListener(e -> cancelaParto());		
		Fila1.add(btnCancelar);

		// agregamos la fila al contenedor
		Principal.add(Fila1);

		// los comentarios
		JLabel lComentarios = new JLabel("Comentarios");
		lComentarios.setFont(Fuente.Normal);
		Principal.add(lComentarios);

		// el scrollpane de los comentarios
		JScrollPane scrollComentarios = new JScrollPane();
		scrollComentarios.setPreferredSize(new Dimension(450, 145));        		
		Principal.add(scrollComentarios);

        // fijamos la barra lateral solo si es necesaria 
        // y nunca la barra inferior (horizontal)
        scrollComentarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollComentarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// el área de texto
		this.tComentarios = new JTextArea();
		this.tComentarios.setFont(Fuente.Normal);
		this.tComentarios.setToolTipText("Comentarios del usuario");

        // establecemos que haga word wrap
        this.tComentarios.setLineWrap(true);
        this.tComentarios.setWrapStyleWord(true);

		// agregamos el viewport
		scrollComentarios.setViewportView(this.tComentarios);

		// cargamos el registro 
		if (this.Protocolo != 0){
			this.getDatosParto(this.Protocolo);
		}

		// agregamos el layer
		add(Principal);

		// lo mostramos
		this.setVisible(true);

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método que agrega los elementos al combo de tipo de parto
	 */
	protected void elementosParto(){

		// agregamos los elementos
		this.cParto.addItem("");
		this.cParto.addItem("Normal");
		this.cParto.addItem("Cesárea");
		this.cParto.addItem("No Sabe");

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param int protocolo - protocolo del paciente
	 * Método público llamado desde el padre que recibe el
	 * protocolo del paciente, y presenta los datos del
	 * registro en caso de existir
	 */
	public void getDatosParto(int protocolo){

		// obtenemos el registro
		this.Parto.getDatosParto(protocolo);

		// si hay un registro (siempre tiene que haber)
		if (this.Parto.getId() != 0){

			// cargamos el resultado
			this.IdMadre = this.Parto.getMadre();
			this.tId.setText(Integer.toString(this.Parto.getId()));
			this.tSivila.setText(this.Parto.getSivila());
			if (this.Parto.getReportado() != null){
				this.dReportado.setDate(this.Herramientas.StringToDate(this.Parto.getReportado()));
			}
			this.cParto.setSelectedItem(this.Parto.getParto());
			this.sPeso.setValue(this.Parto.getPeso());
			this.cPrematuro.setSelectedItem(this.Parto.getPrematuro());
			this.cInstitucion.setValor(this.Parto.getIdInstitucion(), this.Parto.getInstitucion());
			this.tComentarios.setText(this.Parto.getComentarios());
			this.tUsuario.setText(this.Parto.getUsuario());
			this.tAlta.setText(this.Parto.getFecha());

			// fijamos el foco
			this.tSivila.requestFocus();

		}

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método llamado al pulsar el botón grabar que valida
	 * los datos del formulario antes de enviarlos al
	 * servidor
	 */
	protected void validaParto(){

		// si está insertando
		if (this.tId.getText().equals("")){
			this.Parto.setId(0);
		} else {
			this.Parto.setId(Integer.parseInt(this.tId.getText()));
		}

		// fijamos el protocolo y el de la madre
		this.Parto.setProtocolo(this.Protocolo);
		this.Parto.setMadre(this.IdMadre);

		// si indicó clave de sivila
		if (!this.tSivila.getText().isEmpty()){

			// verifica se indique la fecha
			if (this.Herramientas.fechaJDate(this.dReportado) == null){

				// presenta el mensaje y retorna
				JOptionPane.showMessageDialog(this,
							"Debe indicar una fecha",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				this.dReportado.requestFocus();
				return;

			}

		// si declaró 
		} else {

			// asignamos 
			this.Parto.setReportado(this.Herramientas.fechaJDate(this.dReportado));

		}

		// si no indicó el tipo de parto
		if (this.cParto.getSelectedItem().toString().isEmpty()){

			// presenta el mensaje y retorna
			JOptionPane.showMessageDialog(this,
						"Indique el tipo de parto",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			this.cParto.requestFocus();
			return;

		// si indicó
		} else {

			// asignamos en la clase
			this.Parto.setParto(this.cParto.getSelectedItem().toString());

		}

		// si no indicó el peso al nacer
		if ((Integer) this.sPeso.getValue() == 0 ){

			// presenta el mensaje y retorna
			JOptionPane.showMessageDialog(this,
						"Indique el peso al nacer en gramos",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			this.sPeso.requestFocus();
			return;

		// si asignó
		} else {

			// asignamos en la clase
			this.Parto.setPeso(Integer.parseInt(this.sPeso.getValue().toString()));

		}

		// si no indicó si fue prematuro
		if (this.cPrematuro.getSelectedItem().toString().isEmpty()){

			// presenta el mensaje y retorna
			JOptionPane.showMessageDialog(this,
						"Indique si fue prematuro",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			this.cPrematuro.requestFocus();
			return;

		// si seleccionó
		} else {

			// asigna en la clase
			this.Parto.setPrematuro(this.cPrematuro.getSelectedItem().toString());

		}

		// si no indicó la institución
		if (this.cInstitucion.getId() == 0){

			// presenta el mensaje y retorna
			JOptionPane.showMessageDialog(this,
						"Ingrese parte del nombre de la Insitución\n" +
						"y luego pulse el botón Buscar",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			this.cInstitucion.requestFocus();
			return;

		// si seleccionó 
		} else {

			// fijamos en la clase
			this.Parto.setIdInstitucion(this.cInstitucion.getId());

		}

		// grabamos el registro
		int id = this.Parto.grabaParto();

		// si grabó correctamente
		if (id != 0){

			// presenta el mensaje y asigna la clave
			new Mensaje("Registro grabado ... ");
			this.tId.setText(Integer.toString(id));

		}

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método llamado al pulsar el botón cancelar que
	 * según el caso recarga el registro o limpia el
	 * formulario
	 */
	protected void cancelaParto(){

		// si está insertando
		if (this.tId.getText().isEmpty()){
			this.getDatosParto(this.Protocolo);
		} else {
			this.limpiaParto();
		}
	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método que limpia el formulario de datos
	 */
	protected void limpiaParto(){

		// limpiamos el formulario
		this.tSivila.setText("");
		this.dReportado.setCalendar(null);
		this.cParto.setSelectedItem("");
		this.sPeso.setValue(0);
		this.cPrematuro.setSelectedItem("");
		this.cProvincia.setValor(this.Parto.getCodProv(), this.Parto.getProvincia());
		this.cLocalidad.setValor(this.Parto.getCodloc(), this.Parto.getLocalidad());

		this.cInstitucion.setValor(0, "");
		this.tComentarios.setText("");

		// fijamos el foco
		this.tSivila.requestFocus();

	}

}
