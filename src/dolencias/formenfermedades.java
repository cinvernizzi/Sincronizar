/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 18/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que arma el formulario para el abm de enfermedades
 * 
 */

// definimos el paquete
package dolencias;

// importamos las librerías
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import com.toedter.calendar.JDateChooser;
import dbApi.Mensaje;
import dbApi.RendererTabla;
import dbApi.Utilidades;
import dbApi.calculaFechas;
import dbApi.fuentes;
import seguridad.Seguridad;
import vertical.formVertical;
import pacientes.formpacientes;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class formenfermedades extends JDialog{

    // declaramos las variables
	private JTextField tId;                     // clave del registro
	private enfermedades Dolencias;             // combo de las enfermedades
	private JDateChooser dFecha;                // fecha de la enfermedad
	private JTextField tUsuario;                // nombre del usuario
	private JTable tEnfermedades;               // tabla con las enfermedades
	private int Protocolo;                      // protocolo del paciente
	private dolencias Sufridas;                 // clase de las enfermedades del paciente
	private Utilidades Herramientas;            // herramientas para las fechas

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param padre el formulario de pacientes
	 * Constructor invocado cuando es llamado desde el formulario
	 * de pacientes adultos
	 */
	public formenfermedades(formpacientes padre) {

		// inicializamos las variables
		this.Protocolo = padre.getProtocolo();

		// configuramos la interfaz
		this.setupUi();

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param padre el formulario de vertical
	 * Constructor que es invocado cuando es llamado desde 
	 * el formulario de chagas vertical
	 */
	public formenfermedades(formVertical padre){

		// asignamos el protocolo
		this.Protocolo = padre.getProtocolo();

		// configuramos la interfaz
		this.setupUi();
		
	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método que configura la interfaz
	 */
	protected void setupUi(){

		// lo fijamos modal
		setModal(true);

        // instanciamos las fuentes
        fuentes Fuente = new fuentes();

        // fijamos el ícono
		Toolkit miPantalla = Toolkit.getDefaultToolkit();		
		setIconImage(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")).getImage());

		this.Sufridas = new dolencias();
		this.Herramientas = new Utilidades();

		// fijamos las propiedades
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Enfermedades del Paciente");
		this.setSize(590, 380);

        // centramos la ventana
        Dimension tamanioPantalla = miPantalla.getScreenSize();

        // ahora lo convertimos a alto y ancho
        int ancho = tamanioPantalla.width;
        int alto = tamanioPantalla.height;

        // y ahora lo movemos para centrarlo
        setLocation((ancho-590)/2, (alto-380)/2);


		// definimos un panel principal 
		JPanel Principal = new JPanel();

		// definimos el layout
		Principal.setLayout(new BoxLayout(Principal, BoxLayout.PAGE_AXIS));		

		// define el título
		JLabel lTitulo = new JLabel("Enfermedades del Paciente");
		lTitulo.setFont(Fuente.Negrita);
        lTitulo.setPreferredSize(new Dimension(100, 30));        
		Principal.add(lTitulo);

		// agregamos un panel para los controles
        JPanel Fila1 = new JPanel();
        Fila1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

		// presenta la id
		this.tId = new JTextField();
        this.tId.setPreferredSize(new Dimension(75, 30));
		this.tId.setFont(Fuente.Normal);
		this.tId.setToolTipText("Protocolo del paciente");
		this.tId.setEditable(false);
		Fila1.add(this.tId);

		// presenta el combo con las enfermedades
		this.Dolencias = new enfermedades();
        this.Dolencias.setPreferredSize(new Dimension(220, 30));
		this.Dolencias.setFont(Fuente.Normal);
		this.Dolencias.setToolTipText("Seleccione la enfermedad de la lista");
		Fila1.add(this.Dolencias);

        // calculamos las fechas
        calculaFechas Periodo = new calculaFechas();
        Date inicio = Periodo.sumarFecha (-50, "Anios");
        Date fin = Periodo.sumarFecha (-1, "Dias");
        
		// pide la fecha de la enfermedad
		this.dFecha = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        this.dFecha.setPreferredSize(new Dimension(110, 30));        
		this.dFecha.setToolTipText("Indique la fecha de la enfermedad");
        this.dFecha.setMinSelectableDate (inicio);
        this.dFecha.setMaxSelectableDate (fin);
		this.dFecha.setFont(Fuente.Normal);
		Fila1.add(this.dFecha);

		// presenta el usuario
		this.tUsuario = new JTextField();
        this.tUsuario.setPreferredSize(new Dimension(90, 30));        
		this.tUsuario.setToolTipText("Usuario que ingresó el registro");
		this.tUsuario.setEditable(false);
		this.tUsuario.setFont(Fuente.Normal);
		Fila1.add(this.tUsuario);

		// agregamos el usuario actual
		this.tUsuario.setText(Seguridad.Usuario);

		// presenta el botón grabar
		JButton btnGrabar = new JButton();
        btnGrabar.setPreferredSize(new Dimension(30, 30));
		btnGrabar.setIcon(new ImageIcon(getClass().getResource("/recursos/agregar.png")));
		btnGrabar.setToolTipText("Pulse para agregar la enfermedad");
		btnGrabar.setFont(Fuente.Normal);
		Fila1.add(btnGrabar);

        // agregamos el listener
        btnGrabar.addActionListener(e -> validaEnfermedad());

		// agregamos la fina al contenedor
		Principal.add(Fila1);
		
		// agrega el scroll
		JScrollPane scrollEnfermedades = new JScrollPane();
        scrollEnfermedades.setPreferredSize(new Dimension(560, 265));
		Principal.add(scrollEnfermedades);

		// agrega la tabla
		this.tEnfermedades = new JTable();
		this.tEnfermedades.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"ID",
				"Enfermedad",
				"Fecha",
				"Usuario",
				"Ed.",
				"El."
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				Integer.class,
				String.class,
				String.class,
				String.class,
				Object.class,
				Object.class
			};
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// establecemos el ancho de las columnas
		this.tEnfermedades.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tEnfermedades.getColumnModel().getColumn(0).setMinWidth(0);
		this.tEnfermedades.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.tEnfermedades.getColumn("Fecha").setPreferredWidth(100);
        this.tEnfermedades.getColumn("Fecha").setMaxWidth(100);
        this.tEnfermedades.getColumn("Usuario").setPreferredWidth(100);
        this.tEnfermedades.getColumn("Usuario").setMaxWidth(100);
		this.tEnfermedades.getColumn("Ed.").setPreferredWidth(35);
        this.tEnfermedades.getColumn("Ed.").setMaxWidth(35);
        this.tEnfermedades.getColumn("El.").setPreferredWidth(35);
        this.tEnfermedades.getColumn("El.").setMaxWidth(35);

        // establecemos el tooltip
        this.tEnfermedades.setToolTipText("Pulse para editar / borrar");

        // fijamos el alto de las filas
        this.tEnfermedades.setRowHeight(30);

		// fijamos la fuente
		this.tEnfermedades.setFont(Fuente.Normal);

		// agregamos la tabla al scroll
		scrollEnfermedades.setViewportView(this.tEnfermedades);

        // fijamos el evento click
        this.tEnfermedades.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tEnfermedadesMouseClicked(evt);
            }
        });

		// cargamos la grilla de enfermedades
		this.grillaEnfermedades();

		// agregamos el contenedor
		add(Principal);

        // mostramos el formulario
        this.setVisible(true);
        
    }

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método pùblico que carga la grilla con las enfermedades
	 * del paciente
	 */
	public void grillaEnfermedades(){

		// obtenemos la nómina
		ResultSet Nomina = this.Sufridas.nominaEnfermedades(this.Protocolo);

        // sobrecargamos el renderer de la tabla
        this.tEnfermedades.setDefaultRenderer(Object.class, new RendererTabla());

        // obtenemos el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel)this.tEnfermedades.getModel();

    	// hacemos la tabla se pueda ordenar
		this.tEnfermedades.setRowSorter (new TableRowSorter<>(modeloTabla));

        // limpiamos la tabla
        modeloTabla.setRowCount(0);

        // definimos el objeto de las filas
        Object [] fila = new Object[6];

		// definimos los íconos
		ImageIcon iGrabar = new ImageIcon(getClass().getResource("/recursos/save.png"));
		ImageIcon iBorrar = new ImageIcon(getClass().getResource("/recursos/borrar.png"));

        try {

            // iniciamos un bucle recorriendo el vector
            while (Nomina.next()){

                // fijamos los valores de la fila
                fila[0] = Nomina.getInt("id");
                fila[1] = Nomina.getString("enfermedad");
                fila[2] = Nomina.getString("fecha");
				fila[3] = Nomina.getString("usuario");
                fila[4] = new JLabel(iGrabar);
				fila[5] = new JLabel(iBorrar);

                // lo agregamos
                modeloTabla.addRow(fila);

            }

        // si hubo un error
        } catch (SQLException ex){

            // presenta el mensaje
            ex.printStackTrace();			

        }

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método llamado al pulsar el botón agregar que valida los 
	 * datos del formulario y ejecuta la consulta en la base 
	 * de datos local
	 */
	private void validaEnfermedad(){

		// si existe una clave
		if (this.tId.getText().equals("")){
			this.Sufridas.setId(0);
		} else {
			this.Sufridas.setId(Integer.parseInt(this.tId.getText()));
		}

		// asignamos el protocolo
		this.Sufridas.setProtocolo(this.Protocolo);

		// verificamos que halla seleccionado una enfermedad
		int idEnfermedad = this.Dolencias.getId();
		if (idEnfermedad == 0){

			// presenta el mensaje
            JOptionPane.showMessageDialog(this,
                        "Seleccione de la lista la enfermedad",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
			return;

		// si seleccionó
		} else {

			// asignamos en la clase
			this.Sufridas.setIdEnfermedad(idEnfermedad);

		}

		// si no indicó la fecha de inicio del tratamiento
		if (this.Herramientas.fechaJDate(this.dFecha)  == null){

			// presenta el mensaje
            JOptionPane.showMessageDialog(this,
                        "Indique la fecha de inicio de la enfermedad",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
			this.dFecha.requestFocus();
			return;

		// si indicó
		} else {

			// asignamos en la clase
			this.Sufridas.setFecha(this.Herramientas.fechaJDate(this.dFecha));

		}

		// terminamos de asignar
		this.Sufridas.setFechaAlta(this.Herramientas.FechaActual());

		// grabamos el registro y recargamos la grilla
		int resultado = this.Sufridas.grabaEnfermedad();
		if (resultado != 0){
			this.limpiaFormulario();
			this.grillaEnfermedades();
			new Mensaje("Registro grabado ...");
		} 

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param evt el evento del mouse
	 * Método llamado al pulsar sobre la grilla de enfermedades
	 */
	private void tEnfermedadesMouseClicked(java.awt.event.MouseEvent evt) {

        // obtenemos el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tEnfermedades.getModel();

        // obtenemos la fila y columna pulsados
        int fila = this.tEnfermedades.rowAtPoint(evt.getPoint());
        int columna = this.tEnfermedades.columnAtPoint(evt.getPoint());

        // como tenemos la tabla ordenada nos aseguramos de convertir
        // la fila pulsada (vista) a la fila de datos (modelo)
        int indice = this.tEnfermedades.convertRowIndexToModel (fila);

		// obtenemos el protocolo
		int clave = (Integer) modeloTabla.getValueAt(indice, 0);

        // si está dentro de los límites de la tabla
        if ((fila > -1) && (columna > -1)) {

            // si pulsó en editar
            if (columna == 4){

				// editamos el registro
				this.verEnfermedad(clave);

			// si pulsó en eliminar
			} else if (columna == 5){

				// eliminamos el registro
				this.borraEnfermedad(clave);

			}

		}

	}
	
	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param clave la clave del registro
	 * Método llamado cuando el usuario pulsa sobre la grilla 
	 * y pretende editar el registro, recibe como parámetro 
	 * la clave del mismo y lo carga en el formulario
	 */
	private void verEnfermedad(int clave){

		// obtenemos el registro
		this.Sufridas.getDatosEnfermedad(clave);

		// ahora actualizamos en el formulario
		this.tId.setText(Integer.toString(this.Sufridas.getId()));
		this.Dolencias.setValor(this.Sufridas.getId(), this.Sufridas.getEnfermedad());
		this.dFecha.setDate(this.Herramientas.StringToDate(this.Sufridas.getFecha()));
		this.tUsuario.setText(this.Sufridas.getUsuario());

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param clave la clave del registro
	 * Método llamado cuando el usuario pulsa sobre el botón 
	 * eliminar de la grilla que recibe como parámetro la 
	 * clave del registro y lo elimina
	 */
	private void borraEnfermedad(int clave){

		// pide confirmación
		int respuesta = JOptionPane.showOptionDialog(this,
		                            "Está seguro que desea eliminar el registro?",
									"Enfermedades del Paciente",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, null, null);

		// si confirmó
		if (respuesta == JOptionPane.YES_OPTION) {

			// eliminamos el registro
			this.Sufridas.borraEnfermedad(clave);

			// limpiamos el formulario
			this.limpiaFormulario();

			// recargamos la grilla
			this.grillaEnfermedades();

		}

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método llamado luego de grabar o eliminar que limpia
	 * el formulario de datos
	 */
	private void limpiaFormulario(){

		// inicializamos los campos
		this.tId.setText("");
		this.Dolencias.setValor(0, "");
		this.dFecha.setDate(null);

	}

}
