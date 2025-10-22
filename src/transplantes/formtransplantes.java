/*

    Nombre: FormTransplantes
    Fecha: 21/04/2019
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: diagnostico
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Método que arma el formulario para el abm de
	             transplantes recibidos

*/

// definición del paquete
package transplantes;

// importamos las librerías
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
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
import seguridad.Seguridad;
import dbApi.Utilidades;
import dbApi.ComboSiNo;
import dbApi.Mensaje;
import dbApi.calculaFechas;
import dbApi.fuentes;
import pacientes.formpacientes;
import java.util.Date;
import vertical.formVertical;

// definición de la clase
public class formtransplantes extends JDialog {

	// definimos el serial id
	private static final long serialVersionUID = -315485615814103181L;

	// definición de las varibles de clase
	private JTextField tId;                        // clave del registro
	private JDateChooser dFecha;                   // fecha del transplante
	private JTextField tUsuario;                   // nombre del usuario
	private JTable tTransplantes;                  // tabla con los transplantes
	public int Protocolo;                          // protocolo del paciente
	private transplantes Cirugias;                 // clase de la base de datos
	private Utilidades Herramientas;               // funciones de fecha
	private ComboSiNo cPositivo;                   // positivo para chagas
	private organos cOrgano;                       // combo de órganos

	// constructor de la clase cuando es llamado 
	// desde el formulario de pacientes
	public formtransplantes(formpacientes padre) {
			
		// asignamos el protocolo
		this.Protocolo = padre.getProtocolo();

		// configuramos la interfaz
		this.setupUi();

	}

	// constructor de la clase cuando es llamado 
	// desde el formulario de vertical
	public formtransplantes(formVertical padre){

		// obtenemos el protocolo
		this.Protocolo = padre.getProtocolo();

		// configuramos la interfaz
		this.setupUi();

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método llamado desde el constructor que configura 
	 * el formulario 
	 */
	protected void setupUi(){

		// lo fijamos como modal
		setModal(true);

		// instanciamos la fuente
		fuentes Fuente = new fuentes();

		// instanciamos las clases
		this.Cirugias = new transplantes();
		this.Herramientas = new Utilidades();

		// fijamos las propiedades
		this.setSize(680, 300);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Organos Transplantados");

        // fijamos el ícono
		Toolkit miPantalla = Toolkit.getDefaultToolkit();		
		setIconImage(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")).getImage());

        // centramos la ventana
        Dimension tamanioPantalla = miPantalla.getScreenSize();

        // ahora lo convertimos a alto y ancho
        int ancho = tamanioPantalla.width;
        int alto = tamanioPantalla.height;

        // y ahora lo movemos para centrarlo
        setLocation((ancho-680)/2, (alto-300)/2);

		// fijamos el layout
		JPanel Principal = new JPanel();
		Principal.setLayout(new BoxLayout(Principal, BoxLayout.PAGE_AXIS));		

		// presenta el título
		JLabel lTitulo = new JLabel("Transplantes del paciente");
		lTitulo.setFont(Fuente.Normal);
		Principal.add(lTitulo);

		// agregamos un panel para los controles
        JPanel Fila1 = new JPanel();
        Fila1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

		// presentamos la clave
		this.tId = new JTextField();
		this.tId.setPreferredSize(new Dimension(70, 30));        				
		this.tId.setFont(Fuente.Normal);
		this.tId.setToolTipText("Clave del registro");
		this.tId.setEditable(false);
		Fila1.add(this.tId);

		// presenta el select del órgano
		this.cOrgano = new organos();
		this.cOrgano.setPreferredSize(new Dimension(220, 30));        				
		this.cOrgano.setFont(Fuente.Normal);
		this.cOrgano.setToolTipText("Seleccione el órgano de la lista");
		Fila1.add(this.cOrgano);

		// pide si el órgano es positivo
		this.cPositivo = new ComboSiNo();
		this.cPositivo.setPreferredSize(new Dimension(100, 30));        				
		this.cPositivo.setFont(Fuente.Normal);
		this.cPositivo.setToolTipText("Indique si el órgano era positivo para Chagas");
		Fila1.add(this.cPositivo);

        // calculamos las fechas
        calculaFechas Periodo = new calculaFechas();
        Date inicio = Periodo.sumarFecha (-50, "Anios");
        Date fin = Periodo.sumarFecha (-1, "Dias");
        
		// pide la fecha del transplante
		this.dFecha= new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
		this.dFecha.setPreferredSize(new Dimension(120, 30));        				
		this.dFecha.setFont(Fuente.Normal);
        this.dFecha.setMinSelectableDate (inicio);
        this.dFecha.setMaxSelectableDate (fin);
		this.dFecha.setToolTipText("Indique la fecha del transplante");
		Fila1.add(this.dFecha);

		// presenta el usuario
		this.tUsuario = new JTextField();
		this.tUsuario.setPreferredSize(new Dimension(90, 30));        				
		this.tUsuario.setFont(Fuente.Normal);
		this.tUsuario.setToolTipText("Usuario que ingresó el registro");
		this.tUsuario.setEditable(false);
		Fila1.add(this.tUsuario);

		// fijamos el usuario actual
		this.tUsuario.setText(Seguridad.Usuario);

		// presenta el botón grabar
		JButton btnGrabar = new JButton();
		btnGrabar.setPreferredSize(new Dimension(30, 30));        				
		btnGrabar.setFont(Fuente.Normal);
		btnGrabar.setToolTipText("Pulse para grabar el registro");
		btnGrabar.setIcon(new ImageIcon(getClass().getResource("/recursos/agregar.png")));
		btnGrabar.addActionListener(e -> validaTransplante());
		Fila1.add(btnGrabar);

		// agregamos la fila al contenedor
		Principal.add(Fila1);

		// define el scroll
		JScrollPane scrollTransplantes = new JScrollPane();
		Principal.add(scrollTransplantes);

		// define la tabla
		this.tTransplantes = new JTable();
		this.tTransplantes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"Id",
				"Organo",
				"Pos.",
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
				false, false, false, false, false, false, false
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// establecemos el ancho de las columnas
		this.tTransplantes.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tTransplantes.getColumnModel().getColumn(0).setMinWidth(0);
		this.tTransplantes.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.tTransplantes.getColumn("Pos.").setPreferredWidth(40);
        this.tTransplantes.getColumn("Pos.").setMaxWidth(40);
		this.tTransplantes.getColumn("Fecha").setPreferredWidth(100);
        this.tTransplantes.getColumn("Fecha").setMaxWidth(100);
        this.tTransplantes.getColumn("Usuario").setPreferredWidth(100);
        this.tTransplantes.getColumn("Usuario").setMaxWidth(100);
		this.tTransplantes.getColumn("Ed.").setPreferredWidth(35);
        this.tTransplantes.getColumn("Ed.").setMaxWidth(35);
        this.tTransplantes.getColumn("El.").setPreferredWidth(35);
        this.tTransplantes.getColumn("El.").setMaxWidth(35);

        // establecemos el tooltip
        this.tTransplantes.setToolTipText("Pulse para editar / borrar");

        // fijamos el alto de las filas
        this.tTransplantes.setRowHeight(25);

		// fijamos la fuente
		this.tTransplantes.setFont(Fuente.Normal);

		// agrega la tabla al scroll
		scrollTransplantes.setViewportView(this.tTransplantes);

		// agregamos el evento de la tabla
		this.tTransplantes.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tTransplantesMouseClicked(evt);
			}
		});

		// cargamos los transplantes
		this.cargaTransplantes();

		// agregamos al contenedor principal
		add(Principal);

		// mostramos el formulario
		this.setVisible(true);

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método público que carga los transplantes del paciente
	 */
	public void cargaTransplantes(){

		// obtenemos la nómina
		ResultSet Nomina = this.Cirugias.nominaTransplantes(this.Protocolo);

		// sobrecargamos el renderer de la tabla
		this.tTransplantes.setDefaultRenderer(Object.class, new RendererTabla());

		// obtenemos el modelo de la tabla
		DefaultTableModel modeloTabla = (DefaultTableModel) this.tTransplantes.getModel();

		// hacemos la tabla se pueda ordenar
		this.tTransplantes.setRowSorter(new TableRowSorter<>(modeloTabla));

		// limpiamos la tabla
		modeloTabla.setRowCount(0);

		// definimos el objeto de las filas
		Object[] fila = new Object[7];

		try {

			// definimos los íconos
			ImageIcon iEditar = new ImageIcon(getClass().getResource("/recursos/editar.png"));
			ImageIcon iBorrar = new ImageIcon(getClass().getResource("/recursos/borrar.png"));

			// iniciamos un bucle recorriendo el vector
			while (Nomina.next()) {

				// fijamos los valores de la fila
				fila[0] = Nomina.getInt("id");
				fila[1] = Nomina.getString("organo");
				fila[2] = Nomina.getString("positivo");
				fila[3] = Nomina.getString("fecha");
				fila[4] = Nomina.getString("usuario");
				fila[5] = new JLabel(iEditar);
				fila[6] = new JLabel(iBorrar);

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
	 * Método que verifica los datos del formulario antes
	 * de enviarlo al servidor
	 */
	private void validaTransplante(){

		// si está insertando
		if (this.tId.getText().equals("")){
			this.Cirugias.setId(0);
		} else {
			this.Cirugias.setId(Integer.parseInt(this.tId.getText()));
		}

		// asignamos el protocolo
		this.Cirugias.setProtocolo(this.Protocolo);

		// si no seleccionó el órgano
		int idorgano = this.cOrgano.getId();
		if (idorgano == 0){

			// presenta el mensaje
            JOptionPane.showMessageDialog(this,
                        "Seleccione de la lista Organo Transplantado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
			this.cOrgano.requestFocus();
			return;

		// si seleccionó
		} else {

			// asignamos en la clase
			this.Cirugias.setIdOrgano(idorgano);
		}

		// si no indicó si era positivo
		if (this.cPositivo.getSelectedItem().toString().equals("")){

			// presenta el mensaje
            JOptionPane.showMessageDialog(this,
						"Indique si el órgano era\n" +
						"positivo para Chagas",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
			this.cPositivo.requestFocus();
			return;

		// si seleccionó 
		} else {

			// asigna en la clase
			this.Cirugias.setPositivo(this.cPositivo.getSelectedItem().toString());

		}

		// si no ingresó la fecha
		if (this.Herramientas.fechaJDate(this.dFecha) == null){

			// presenta el mensaje
            JOptionPane.showMessageDialog(this,
                        "Indique la fecha del transplante",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
			this.dFecha.requestFocus();
			return;

		// si seleccionó 
		} else {

			// asigna en la clase
			this.Cirugias.setFechaTransplante(this.Herramientas.fechaJDate(this.dFecha));

		}

		// fijamos la fecha de alta
		this.Cirugias.setFecha(this.Herramientas.FechaActual());

		// grabamos el registro
		int id = this.Cirugias.grabaTransplante();
		if (id != 0){

			// presenta el mensaje y limpia el formulario
			new Mensaje("Transplante grabado ...");
			this.limpiaFormulario();
			this.cargaTransplantes();
			
		}

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método que limpia el formulario luego de una
	 * eliminación o grabación
	 */
	private void limpiaFormulario(){

		// limpiamos los campos
		this.tId.setText("");
		this.cOrgano.setValor(0, "");
		this.cPositivo.setSelectedItem("");
		this.dFecha.setDate(null);
		this.tUsuario.setText(Seguridad.Usuario);

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param clave - entero con la clave del registro
	 * Método que recibe como parámetro la clave de un
	 * registro y lo presenta en el formulario
	 */
	private void verTransplante(int clave){

		// obtenemos el registro
		this.Cirugias.getDatosTransplante(clave);

		// asignamos en el formulario
		this.tId.setText(Integer.toString(this.Cirugias.getId()));

		// seleccionamos el órgano
		this.cOrgano.setValor(this.Cirugias.getIdOrgano(), this.Cirugias.getOrgano());

		// asignamos si era positivo
		this.cPositivo.setSelectedItem(this.Cirugias.getPositivo());

		// fijamos la fecha
		this.dFecha.setDate(this.Herramientas.StringToDate(this.Cirugias.getFechaTransplante()));

		// finalmente el usuario
		this.tUsuario.setText(this.Cirugias.getUsuario());

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param clave - entero con la clave del registro
	 * Método que recibe como parámetro la clave de un
	 * registro y luego de pedir confirmación ejecuta la
	 * consulta de eliminación
	 */
	private void borraTransplante(int clave){

		// pide confirmación
		int respuesta = JOptionPane.showOptionDialog(this,
		                            "Está seguro que desea eliminar el registro?",
									"Transplantes del Paciente",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, null, null);

		// si confirmó
		if (respuesta == JOptionPane.YES_OPTION) {

			// eliminamos el registro
			this.Cirugias.borraTransplante(clave);

			// limpiamos el formulario
			this.limpiaFormulario();

			// recargamos la grilla
			this.cargaTransplantes();

		}

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método llamado al pulsar sobre la grilla de transplantes
	 */
	protected void tTransplantesMouseClicked(MouseEvent evt){

        // obtenemos el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel)tTransplantes.getModel();

        // obtenemos la fila y columna pulsados
        int fila = tTransplantes.rowAtPoint(evt.getPoint());
        int columna = tTransplantes.columnAtPoint(evt.getPoint());

        // como tenemos la tabla ordenada nos aseguramos de convertir
        // la fila pulsada (vista) a la fila de datos (modelo)
        int indice = this.tTransplantes.convertRowIndexToModel (fila);

        // si está dentro de los límites de la tabla
        if ((fila > -1) && (columna > -1)){

            // obtenemos la id indec del registro
            int id = (Integer) modeloTabla.getValueAt(indice, 0);

            // según la columna pulsada
            if (columna == 5){
            	this.verTransplante(id);
            } else if (columna == 6){
            	this.borraTransplante(id);
            }

		}

	}

}
