/*

    Nombre: FormBuscar
    Fecha: 16/09/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: Sitracha
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Método que arma el formulario con la grilla de resultados
	             en la búsqueda de pacientes

 */

// definición del paquete
package pacientes;

// importamos las librerías
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import dbApi.RendererTabla;
import dbApi.fuentes;
import vertical.formvertical;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

// declaración de la clase
public class formbuscar extends JDialog {

	// definimos las variables de clase
	private JTable tPacientes;
	private formpacientes Padre;

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param padre el contenedor del padre
	 * Constructor de la clase llamado desde el formulario de 
	 * pacientes adultos
	 */
	public formbuscar(formpacientes padre) {

		// lo fijamos como modal
		setModal(true);

		// seteamos el padre
		this.Padre = padre;

		// configuramos la interfaz
		this.initUi();

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param padre el formulario padre
	 * Constructor de la clase llamado desde el formulario de 
	 * pacientes vertical
	 */
	public formbuscar(formvertical padre){

		// fijamos como modal
		setModal(true);

		// seteamos el padre
		this.Padre = padre;

		// configuramos la interfaz
		this.initUi();

	}
	
	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * Método llamado desde el constructor que configura la 
	 * interfaz
	 */
	private void initUi(){

		// instanciamos las fuentes
		fuentes Fuente = new fuentes();

		// definimos las propiedades
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Pacientes Encontrados");
		this.setBounds(150, 100, 945, 435);
		this.setLayout(null);

        // fijamos el ícono
		setIconImage(new ImageIcon(getClass().getResource("/recursos/Logo.jpg")).getImage());

		// definimos el scroll
		JScrollPane scrollPacientes = new JScrollPane();
		scrollPacientes.setBounds(10,10,915,380);
		this.add(scrollPacientes);

		// definimos la tabla de resultados
		this.tPacientes = new JTable();
		this.tPacientes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Protocolo",
				"Nombre",
				"Documento",
				"Residencia",
				"Motivo",
				"Trat.",
				"Alta",
				"Sel."
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				Integer.class,
				String.class,
				String.class,
				String.class,
				String.class,
				String.class,
				String.class,
				Object.class
			};
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// fijamos el tooltip
		this.tPacientes.setToolTipText("Pulse para seleccionar el paciente");

		// definimos la fuente
		this.tPacientes.setFont(Fuente.Normal);

		// fijamos el ancho de las columnas
		this.tPacientes.getColumn("Protocolo").setMaxWidth(70);
		this.tPacientes.getColumn("Trat.").setMaxWidth(35);
		this.tPacientes.getColumn("Alta").setMaxWidth(85);
		this.tPacientes.getColumn("Sel.").setMaxWidth(30);

		// agregamos la tabla al scroll
		scrollPacientes.setViewportView(this.tPacientes);

        // fijamos el evento click
        this.tPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tPacientesMouseClicked(evt);
            }
        });

	}

	/**
	 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
	 * @param nomina - vector con los registros
	 * Método que recibe como parámetro un vector y agrega los
	 * registros en la tabla
	 */
	public void cargaPacientes (ResultSet nomina){

        // sobrecargamos el renderer de la tabla
        this.tPacientes.setDefaultRenderer(Object.class, new RendererTabla());

        // obtenemos el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel)this.tPacientes.getModel();

    	// hacemos la tabla se pueda ordenar
		this.tPacientes.setRowSorter (new TableRowSorter<>(modeloTabla));

        // limpiamos la tabla
        modeloTabla.setRowCount(0);

        // definimos el objeto de las filas
        Object [] fila = new Object[8];

		// creamos la imagen
		ImageIcon iEditar = new ImageIcon(getClass().getResource("/recursos/editar.png"));

        try {

            // iniciamos un bucle recorriendo el vector
            while (nomina.next()){

                // fijamos los valores de la fila
                fila[0] = nomina.getInt("protocolo");
                fila[1] = nomina.getString("nombre");
				fila[2] = nomina.getString("documento");
				fila[3] = nomina.getString("localidad");
				fila[4] = nomina.getString("motivo");
				fila[5] = nomina.getString("tratamiento");
				fila[6] = nomina.getString("alta");
                fila[7] = new JLabel(iEditar);

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
	 * Método llamado al pulsar sobre la grilla de pacientes
	 */
	private void tPacientesMouseClicked(MouseEvent evt) {

        // obtenemos el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tPacientes.getModel();

        // obtenemos la fila y columna pulsados
        int fila = this.tPacientes.rowAtPoint(evt.getPoint());
        int columna = this.tPacientes.columnAtPoint(evt.getPoint());

        // como tenemos la tabla ordenada nos aseguramos de convertir
        // la fila pulsada (vista) a la fila de datos (modelo)
        int indice = this.tPacientes.convertRowIndexToModel (fila);

        // si pulsó en seleccionar
        if ((fila > -1) && (columna == 7)) {

			// obtenemos el protocolo
			int protocolo = (Integer) modeloTabla.getValueAt(indice, 0);

			// cargamos el registro
			this.Padre.getDatosPaciente(protocolo);

			// cerramos el formulario
			this.dispose();

		}

	}

}
