/*

    Nombre: FormTransfusiones
    Fecha: 19/09/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: diagnostico
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Método que arma el formulario para el abm
	             de transfusiones del paciente

 */

// definición del paquete
package transfusiones;

// importamos las librerías
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import com.toedter.calendar.JDateChooser;
import dbApi.RendererTabla;
import dbApi.Utilidades;
import dbApi.calculaFechas;
import dbApi.fuentes;
import instituciones.instituciones;
import localidades.localidades;
import pacientes.formpacientes;
import provincias.provincias;
import seguridad.Seguridad;
import java.util.Date;

// definición de la clase
public class formtransfusiones extends JDialog {

	// definimos el serial id
	private static final long serialVersionUID = 4793119109639734311L;

	// definición de las variables de clase
	private JTextField tId;                 // clave del registro
	private JDateChooser dFecha;            // fecha de la transfusión
	private JTextField tMotivo;             // motivo de la transfusión
	private provincias cProvincia;          // combo de la provincia
	private localidades cLocalidad;         // combo de la localidad
	private instituciones cInstitucion;     // institución de la transfusión
	private JTextField tUsuario;            // nombre del usuario
	private JTable tTransfusion;            // tabla con los datos
	private int Protocolo;                  // protocolo del paciente
	private Utilidades Herramientas;        // funciones de fecha
	private transfusiones Hematologia;      // funciones de la base de datos
	private formpacientes Padre;            // formulario padre

	// constructor de la clase
	public formtransfusiones(formpacientes padre) {

		// lo fijamos como modal
		setModal(true);
		
		// instanciamos las fuentes
		fuentes Fuente = new fuentes();
		
		// inicializamos las variables
		this.Padre = padre;
		this.Protocolo = Padre.getProtocolo();
		this.Herramientas = new Utilidades();
		this.Hematologia = new transfusiones();

		// fijamos las propiedades
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Transfusiones Recibidas");
		this.setSize(900, 325);

        // fijamos el ícono
		Toolkit miPantalla = Toolkit.getDefaultToolkit();		
		setIconImage(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")).getImage());

        // centramos la ventana
        Dimension tamanioPantalla = miPantalla.getScreenSize();

        // ahora lo convertimos a alto y ancho
        int ancho = tamanioPantalla.width;
        int alto = tamanioPantalla.height;

        // y ahora lo movemos para centrarlo
        setLocation((ancho-900)/2, (alto-325)/2);

		// fijamos el layout
		JPanel Principal = new JPanel();
		Principal.setLayout(new BoxLayout(Principal, BoxLayout.PAGE_AXIS));		

		// presenta el título
		JLabel lTitulo = new JLabel("Transfusiones del Paciente");
		lTitulo.setFont(Fuente.Normal);
		Principal.add(lTitulo);

		// agregamos un panel para los controles
        JPanel Fila1 = new JPanel();
        Fila1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

		// presenta la id
		JLabel lId = new JLabel("ID");
		lId.setFont(Fuente.Normal);
		Fila1.add(lId);
		this.tId = new JTextField();
		this.tId.setPreferredSize(new Dimension(70, 30));        
		this.tId.setFont(Fuente.Normal);
		this.tId.setToolTipText("Número de protocolo");
		this.tId.setEditable(false);
		Fila1.add(this.tId);

        // calculamos las fechas
        calculaFechas Periodo = new calculaFechas();
        Date inicio = Periodo.sumarFecha (-50, "Anios");
        Date fin = Periodo.sumarFecha (-1, "Dias");
        
		// pide la fecha
		JLabel lFecha = new JLabel("Fecha");
		lFecha.setFont(Fuente.Normal);
		Fila1.add(lFecha);
		this.dFecha = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
		this.dFecha.setPreferredSize(new Dimension(120, 30));        		
		this.dFecha.setFont(Fuente.Normal);
        this.dFecha.setMinSelectableDate (inicio);
        this.dFecha.setMaxSelectableDate (fin);
		this.dFecha.setToolTipText("Indique la fecha de la transfusión");
		Fila1.add(this.dFecha);

		// pide el motivo
		JLabel lMotivo = new JLabel("Motivo");
		lMotivo.setFont(Fuente.Normal);
		Fila1.add(lMotivo);
		this.tMotivo = new JTextField();
		this.tMotivo.setPreferredSize(new Dimension(150, 30));        		
		this.tMotivo.setFont(Fuente.Normal);
		this.tMotivo.setToolTipText("Motivo de la transfusión");
		Fila1.add(this.tMotivo);

		// presenta el usuario
		JLabel lUsuario = new JLabel("Usuario");
		lUsuario.setFont(Fuente.Normal);
		Fila1.add(lUsuario);
		this.tUsuario = new JTextField();
		this.tUsuario.setPreferredSize(new Dimension(90, 30));        
		this.tUsuario.setFont(Fuente.Normal);
		this.tUsuario.setToolTipText("Usuario que ingresó el registro");
		this.tUsuario.setEditable(false);
		Fila1.add(this.tUsuario);

		// fijamos el nombre
		this.tUsuario.setText(Seguridad.Usuario);

		// presenta el botón grabar
		JButton btnGrabar = new JButton();
		btnGrabar.setPreferredSize(new Dimension(30, 30));        
		btnGrabar.setFont(Fuente.Normal);
		btnGrabar.setToolTipText("Pulse para grabar el registro");
		btnGrabar.setIcon(new ImageIcon(getClass().getResource("/recursos/agregar.png")));
		btnGrabar.addActionListener(e -> validaTransfusion());
		Fila1.add(btnGrabar);

		// agregamos la primer fila
		Principal.add(Fila1);

		// definimos la segunda fila
        JPanel Fila2 = new JPanel();
        Fila2.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

		// presenta la provincia
        JLabel lProvincia = new JLabel("Provincia:");
        lProvincia.setFont(Fuente.Normal);
        Fila2.add(lProvincia);
        this.cProvincia = new provincias();
        this.cProvincia.setFont(Fuente.Normal);
        this.cProvincia.setToolTipText("Seleccione la provincia de nacimiento");
        this.cProvincia.setPreferredSize(new Dimension(200, 30));
        Fila2.add(this.cProvincia);

		// cargamos las provincias
		this.cProvincia.cargaDiccionario(1);

        // agregamos el listener de cambio
        this.cProvincia.addItemListener(new cambiaProvincia());

		// presenta la localidad
		JLabel lLocalidad = new JLabel("Localidad");
		lLocalidad.setFont(Fuente.Normal);
		Fila2.add(lLocalidad);
		this.cLocalidad = new localidades();
		this.cLocalidad.setPreferredSize(new Dimension(200, 30));        		
		this.cLocalidad.setFont(Fuente.Normal);
		this.cLocalidad.setToolTipText("Localidad de la institución");
		Fila2.add(this.cLocalidad);

        // agregamos el listener de cambio
        this.cLocalidad.addItemListener(new cambiaLocalidad());

		// presenta la institución
		JLabel lInstitucion = new JLabel("Institucion");
		lInstitucion.setFont(Fuente.Normal);
		Fila2.add(lInstitucion);
		this.cInstitucion = new instituciones();
		this.cInstitucion.setPreferredSize(new Dimension(250, 30));        		
		this.cInstitucion.setFont(Fuente.Normal);
		this.cInstitucion.setToolTipText("Institución donde se realizó la transfusión");
		Fila2.add(this.cInstitucion);

		// agregamos la segunda fila
		Principal.add(Fila2);

		// define el scroll
		JScrollPane scrollTabla = new JScrollPane();
		Principal.add(scrollTabla);

		// define la tabla
		this.tTransfusion = new JTable();
		this.tTransfusion.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null, "" }, },
				new String[] { "ID",
							   "Fecha",
							   "Motivo",
							   "Institucion",
							   "Localidad",
							   "Usuario",
							   "Ed.",
							   "El." }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Integer.class,
					String.class,
					String.class,
					String.class,
					String.class,
					String.class,
					Object.class,
					Object.class };

			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// establecemos el ancho de las columnas
		this.tTransfusion.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tTransfusion.getColumnModel().getColumn(0).setMinWidth(0);
		this.tTransfusion.getColumnModel().getColumn(0).setPreferredWidth(0);
		this.tTransfusion.getColumn("Fecha").setPreferredWidth(90);
		this.tTransfusion.getColumn("Fecha").setMaxWidth(90);
		this.tTransfusion.getColumn("Motivo").setPreferredWidth(160);
		this.tTransfusion.getColumn("Motivo").setMaxWidth(160);
		this.tTransfusion.getColumn("Localidad").setPreferredWidth(150);
		this.tTransfusion.getColumn("Localidad").setMaxWidth(150);
		this.tTransfusion.getColumn("Usuario").setPreferredWidth(90);
		this.tTransfusion.getColumn("Usuario").setMaxWidth(90);
		this.tTransfusion.getColumn("Ed.").setPreferredWidth(35);
		this.tTransfusion.getColumn("Ed.").setMaxWidth(35);
		this.tTransfusion.getColumn("El.").setPreferredWidth(35);
		this.tTransfusion.getColumn("El.").setMaxWidth(35);

		// establecemos el tooltip
		this.tTransfusion.setToolTipText("Pulse para editar / borrar");

		// fijamos el alto de las filas
		this.tTransfusion.setRowHeight(25);

		// establecemos la fuente
		this.tTransfusion.setFont(Fuente.Normal);

		// agregamos la tabla al scroll
		scrollTabla.setViewportView(this.tTransfusion);

		// agregamos el evento de la tabla
		this.tTransfusion.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tTransfusionMouseClicked(evt);
			}
		});

		// cargamos la grilla
		this.cargaTransfusiones();

		// agregamos el panel
		add(Principal);

		// mostramos el formulario
		this.setVisible(true);

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método público que carga la grilla con las transfusiones
	 * recibidas por el paciente
	 */
	public void cargaTransfusiones() {

		// obtenemos la nómina
		ResultSet Nomina = this.Hematologia.nominaTransfusiones(this.Protocolo);

		// sobrecargamos el renderer de la tabla
		this.tTransfusion.setDefaultRenderer(Object.class, new RendererTabla());

		// obtenemos el modelo de la tabla
		DefaultTableModel modeloTabla = (DefaultTableModel) this.tTransfusion.getModel();

		// hacemos la tabla se pueda ordenar
		this.tTransfusion.setRowSorter(new TableRowSorter<>(modeloTabla));

		// limpiamos la tabla
		modeloTabla.setRowCount(0);

		// definimos el objeto de las filas
		Object[] fila = new Object[8];

		try {

			// definimos los íconos
			ImageIcon iEditar = new ImageIcon(getClass().getResource("/recursos/editar.png"));
			ImageIcon iBorrar = new ImageIcon(getClass().getResource("/recursos/borrar.png"));

			// iniciamos un bucle recorriendo el vector
			while (Nomina.next()) {

				// fijamos los valores de la fila
				fila[0] = Nomina.getInt("id");
				fila[1] = Nomina.getString("fecha");
				fila[2] = Nomina.getString("motivo");
				fila[3] = Nomina.getString("institucion");
				fila[4] = Nomina.getString("localidad");
				fila[5] = Nomina.getString("usuario");
				fila[6] = new JLabel(iEditar);
				fila[7] = new JLabel(iBorrar);

				// lo agregamos
				modeloTabla.addRow(fila);

			}

		// si hubo un error
		} catch (SQLException ex) {

			// presenta el mensaje
			ex.printStackTrace();			

		}

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método que valida el formulario antes de enviarlo
	 * al servidor
	 */
	private void validaTransfusion(){

		// si está insertando
		if (this.tId.getText().isEmpty()){
			this.Hematologia.setId(0);
		} else {
			this.Hematologia.setId(Integer.parseInt(this.tId.getText()));
		}

		// el protocolo
		this.Hematologia.setProtocolo(this.Protocolo);

		// verifica se halla ingresado la fecha
		if (this.Herramientas.fechaJDate(this.dFecha) == null){

            // presenta el mensaje
            JOptionPane.showMessageDialog(this,
                        "Indique la fecha de la transfusión",
                        "Error",
						JOptionPane.ERROR_MESSAGE);
			this.dFecha.requestFocus();
			return;

		// si completó 
		} else {

			// asignamos en la clase
			this.Hematologia.setFechaTransfusion(this.Herramientas.fechaJDate(this.dFecha));

		}

		// verifica se halla indicado el motivo
		if (this.tMotivo.getText().isEmpty()){

            // presenta el mensaje
            JOptionPane.showMessageDialog(this,
                        "Indique el motivo de la transfusión",
                        "Error",
						JOptionPane.ERROR_MESSAGE);
			this.tMotivo.requestFocus();
			return;

		// si ingresó 
		} else {

			// asigna en la clase
			this.Hematologia.setMotivo(this.tMotivo.getText());

		}

		// la institución y la localidad la permite en blanco
		this.Hematologia.setIdInstitucion(this.cInstitucion.getId());

		// grabamos el registro
		this.Hematologia.grabaTransfusion();

		// limpiamos el formulario
		this.limpiaFormulario();

		// recargamos la grilla
		this.cargaTransfusiones();

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param clave entero con la clave del registro
	 * Mètodo que recibe como parámetro la clave del registro
	 * y lo muestra en el formulario de datos
	 */
	private void verTransfusion(int clave){

		// obtenemos el registro
		this.Hematologia.getDatosTransfusion(clave);

		// cargamos el formulario
		this.tId.setText(Integer.toString(this.Hematologia.getId()));
		this.dFecha.setDate(this.Herramientas.StringToDate(this.Hematologia.getFechaTransfusion()));
		this.tMotivo.setText(this.Hematologia.getMotivo());
		this.cProvincia.setValor(this.Hematologia.getCodProv(), this.Hematologia.getProvincia());
		this.cLocalidad.setValor(this.Hematologia.getCodLoc(), this.Hematologia.getLocalidad());
		this.cInstitucion.setValor(this.Hematologia.getIdInstitucion(), this.Hematologia.getInstitucion());
		this.cLocalidad.setValor("", "");
		this.cProvincia.setValor("", "");
		this.tUsuario.setText(this.Hematologia.getUsuario());

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param clave - entero con la clave del registro
	 * Mètodo que luego de pedir confirmación ejecuta la
	 * consulta de eliminación
	 */
	private void borraTransfusion(int clave){

		// pide confirmación
		int respuesta = JOptionPane.showOptionDialog(this,
		                            "Está seguro que desea eliminar el registro?",
									"Transfusiones del Paciente",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, null, null);

		// si confirmó
		if (respuesta == JOptionPane.YES_OPTION) {

			// eliminamos el registro
			this.Hematologia.borraTransfusion(clave);

			// limpiamos el formulario
			this.limpiaFormulario();

			// recargamos la grilla
			this.cargaTransfusiones();

		}

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método que limpia el formulario luego de ejecutar
	 * una edición o una eliminación
	 */
	private void limpiaFormulario(){

		// limpiamos los campos
		this.tId.setText("");
		this.dFecha.setDate(null);
		this.tMotivo.setText("");
		this.cProvincia.setValor("", "");
		this.cLocalidad.setValor("", "");
		this.cInstitucion.setValor(0, "");
		this.tUsuario.setText(Seguridad.Usuario);

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método llamado al pulsar sobre la grilla de transfusiones
	 */
	protected void tTransfusionMouseClicked(MouseEvent evt){

        // obtenemos el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel)tTransfusion.getModel();

        // obtenemos la fila y columna pulsados
        int fila = tTransfusion.rowAtPoint(evt.getPoint());
        int columna = tTransfusion.columnAtPoint(evt.getPoint());

        // como tenemos la tabla ordenada nos aseguramos de convertir
        // la fila pulsada (vista) a la fila de datos (modelo)
        int indice = this.tTransfusion.convertRowIndexToModel (fila);

        // si está dentro de los límites de la tabla
        if ((fila > -1) && (columna > -1)){

            // obtenemos la id indec del registro
            int id = (Integer) modeloTabla.getValueAt(indice, 0);

            // según la columna pulsada
            if (columna == 6){
            	this.verTransfusion(id);
            } else if (columna == 7){
            	this.borraTransfusion(id);
            }

		}

	}

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Clase que actualiza el combo de localidad 
     */
    class cambiaProvincia implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                cLocalidad.cargaDiccionario(cProvincia.getCodigo());
            }
        }       
    }    

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Clase que actualiza el combo de institución
     */
    class cambiaLocalidad implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                cInstitucion.cargaDiccionario(cLocalidad.getCodigo());
            }
        }       
    }    


}
