/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 14/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que define y configura el formulario de pacientes
 * adultos y los eventos del mismo
 * 
 */

// definición del paquete
package pacientes;

// importamos las librerías
import com.toedter.calendar.JDateChooser;
import dbApi.ComboSiNo;
import dbApi.Mensaje;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import dbApi.fuentes;
import derivacion.derivacion;
import documentos.documentos;
import dolencias.formenfermedades;
import estados.estados;
import dbApi.Utilidades;
import dbApi.calculaFechas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import localidades.localidades;
import motivos.motivos;
import ocupaciones.ocupaciones;
import paises.paises;
import provincias.provincias;
import seguridad.Seguridad;
import sexos.sexos;
import transfusiones.formtransfusiones;
import transplantes.formtransplantes;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class formpacientes{

    protected documentos TipoDoc;
    protected sexos Sexo;
    protected estados Estado;
    protected paises Nacionalidad;
    protected provincias ProvNacimiento;
    protected localidades LocNacimiento;
    protected ocupaciones Ocupacion;
    protected motivos Motivo;
    protected derivacion Derivacion;
    protected JTextField tId;
    protected JTextField tHistoria;
    protected JTextField tApellido;
    protected JTextField tNombre;
    public JTextField tDocumento;
    protected JSpinner sHijos;
    protected JTextField tDireccion;
    protected JSpinner sEdad;
    protected JTextField tTelefono;
    protected JTextField tCelular;
    protected JTextField tMail;
    protected paises PaisResidencia;
    protected JTextField tUsuario;
    protected JTextField tAlta;
    protected Utilidades Herramientas;
    protected JTextField tProfesional;
    protected JTextField tMatricula;
    protected ComboSiNo cTratamiento;
    protected JTextField tObraSocial;
    protected ComboSiNo cMadrePositiva;
    protected provincias ProvResidencia;
    protected localidades LocResidencia;
    protected paises NacMadre;
    protected provincias ProvMadre;
    protected localidades LocMadre;
    protected JTextArea Comentarios;
    protected JDateChooser dNacimiento;
    protected pacientes Pacientes;

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param Contenedor el panel del tabulador
     * Constructor de la clase, recibe como parámetro el panel 
     * del tabulador que contendrá el formulario de pacientes
     */
    public formpacientes(JPanel Contenedor){

        // instanciamos las clases
        this.Herramientas = new Utilidades();
        this.Pacientes = new pacientes();

        // definimos el layout
        Contenedor.setLayout(new BoxLayout(Contenedor, BoxLayout.PAGE_AXIS));
        
        // creamos las fuentes
        fuentes Fuente = new fuentes();
        
        // ahora creamos un contenedor para la primer fila
        JPanel Fila1 = new JPanel();
        Fila1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
        
        // presenta la clave del registro
        JLabel lId = new JLabel("Id:");
        lId.setFont(Fuente.Normal);
        Fila1.add(lId);
        this.tId = new JTextField();
        this.tId.setFont(Fuente.Normal);
        this.tId.setToolTipText("Clave del Registro");
        this.tId.setPreferredSize(new Dimension(50, 30));
        this.tId.setEditable(false);
        Fila1.add(tId);

        // la historia clínica 
        JLabel lHistoria = new JLabel("Historia Clínica:");
        lHistoria.setFont(Fuente.Normal);
        Fila1.add(lHistoria);
        this.tHistoria = new JTextField();
        this.tHistoria.setFont(Fuente.Normal);
        this.tHistoria.setToolTipText("Número de Historia Clínica");
        this.tId.setPreferredSize(new Dimension(80, 30));        
        Fila1.add(tHistoria);

        // el apellido
        JLabel lApellido = new JLabel("Apellido:");
        lApellido.setFont(Fuente.Normal);
        Fila1.add(lApellido);
        this.tApellido = new JTextField();
        this.tApellido.setFont(Fuente.Normal);
        this.tApellido.setPreferredSize(new Dimension(150, 30));
        this.tApellido.setToolTipText("Apellido del paciente");
        Fila1.add(tApellido);

         // el nombre 
        JLabel lNombre = new JLabel("Nombre:");
        lNombre.setFont(Fuente.Normal);
        Fila1.add(lNombre);
        this.tNombre = new JTextField();
        this.tNombre.setFont(Fuente.Normal);
        this.tNombre.setPreferredSize(new Dimension(150, 30));
        this.tNombre.setToolTipText("Nombre del paciente");
        Fila1.add(tNombre);

        // el tipo de documento y el número
        JLabel lDocumento = new JLabel("Documento:");
        lDocumento.setFont(Fuente.Normal);
        Fila1.add(lDocumento);
        this.TipoDoc = new documentos();
        this.TipoDoc.setFont(Fuente.Normal);
        this.TipoDoc.setToolTipText("Seleccione el tipo de documento");
        this.TipoDoc.setPreferredSize(new Dimension(90, 30));
        Fila1.add(this.TipoDoc);
        this.tDocumento = new JTextField();
        this.tDocumento.setFont(Fuente.Normal);
        this.tDocumento.setToolTipText("Número de documento del paciente");
        this.tDocumento.setPreferredSize(new Dimension(110, 30));
        Fila1.add(this.tDocumento);
            
        // agregamos la fila
        Contenedor.add(Fila1);

        // ahora definimos la segunda fila
        JPanel Fila2 = new JPanel();
        Fila2.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
            
        // obtenemos los limites inferior y superior
        calculaFechas Periodo = new calculaFechas();
        Date limiteInferior = Periodo.sumarFecha (-90, "Anios");
        Date limiteSuperior = Periodo.sumarFecha (-1, "Dias");
        
        // la fecha de nacimiento
        JLabel lNacimiento = new JLabel("Fecha Nacimiento:");
        lNacimiento.setFont(Fuente.Normal);
        Fila2.add(lNacimiento);
        this.dNacimiento = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        this.dNacimiento.setFont(Fuente.Normal);
        this.dNacimiento.setToolTipText("Indique la fecha de nacimiento");
        this.dNacimiento.setMinSelectableDate (limiteInferior);
        this.dNacimiento.setMaxSelectableDate (limiteSuperior);
        this.dNacimiento.setPreferredSize(new Dimension(120, 30));        
        Fila2.add(this.dNacimiento);
        
        // la edad
        JLabel lEdad = new JLabel("Edad:");
        lEdad.setFont(Fuente.Normal);
        Fila2.add(lEdad);
        this.sEdad = new JSpinner();
        this.sEdad.setFont(Fuente.Normal);
        this.sEdad.setToolTipText("Edad en años del paciente");
        this.sEdad.setPreferredSize(new Dimension(70, 30));
        Fila2.add(this.sEdad);
        
        // el sexo
        JLabel lSexo = new JLabel("Sexo:");
        lSexo.setFont(Fuente.Normal);
        Fila2.add(lSexo);
        this.Sexo = new sexos();
        this.Sexo.setFont(Fuente.Normal);
        this.Sexo.setToolTipText("Seleccione el sexo de la lista");
        this.Sexo.setPreferredSize(new Dimension(120, 30));
        Fila2.add(this.Sexo);
        
        // el estado civil
        JLabel lEstado = new JLabel("Estado Civil:");
        lEstado.setFont(Fuente.Normal);
        Fila2.add(lEstado);
        this.Estado = new estados();
        this.Estado.setFont(Fuente.Normal);
        this.Estado.setToolTipText("Seleccione el estado civil");
        this.Estado.setPreferredSize(new Dimension(130, 30));
        Fila2.add(this.Estado);
        
        // el número de hijos
        JLabel lHijos = new JLabel("Hijos:");
        lHijos.setFont(Fuente.Normal);
        Fila2.add(lHijos);
        this.sHijos = new JSpinner();
        this.sHijos.setFont(Fuente.Normal);
        this.sHijos.setToolTipText("Indique el número de hijos");
        this.sHijos.setPreferredSize(new Dimension(50, 30));
        Fila2.add(this.sHijos);
        
        // agregamos al contenedor
        Contenedor.add(Fila2);
        
        // ahora definimos la segunda fila
        JPanel Fila3 = new JPanel();
        Fila3.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

        // la dirección postal
        JLabel lDireccion = new JLabel("Dirección:");
        lDireccion.setFont(Fuente.Normal);
        Fila3.add(lDireccion);
        this.tDireccion = new JTextField();
        this.tDireccion.setFont(Fuente.Normal);
        this.tDireccion.setToolTipText("Dirección postal del paciente");
        this.tDireccion.setPreferredSize(new Dimension(300, 30));
        Fila3.add(this.tDireccion);
        
        // el número de teléfono
        JLabel lTelefono = new JLabel("Teléfono:");
        lTelefono.setFont(Fuente.Normal);
        Fila3.add(lTelefono);
        this.tTelefono = new JTextField();
        this.tTelefono.setFont(Fuente.Normal);
        this.tTelefono.setToolTipText("Número de teléfono con prefijos");
        this.tTelefono.setPreferredSize(new Dimension(110, 30));
        Fila3.add(this.tTelefono);
        
        // el celular
        JLabel lCelular = new JLabel("Celular:");
        lCelular.setFont(Fuente.Normal);
        Fila3.add(lCelular);
        this.tCelular = new JTextField();
        this.tCelular.setFont(Fuente.Normal);
        this.tCelular.setToolTipText("Número de móvil con prefijos");
        this.tCelular.setPreferredSize(new Dimension(110, 30));
        Fila3.add(this.tCelular);
        
        // el mail
        JLabel lMail = new JLabel("Mail:");
        lMail.setFont(Fuente.Normal);
        Fila3.add(lMail);
        this.tMail = new JTextField();
        this.tMail.setFont(Fuente.Normal);
        this.tMail.setToolTipText("Dirección de correo electrónico");
        this.tMail.setPreferredSize(new Dimension(210, 30));
        Fila3.add(this.tMail);
        
        // agregamos la tercer fila
        Contenedor.add(Fila3);
        
        // ahora definimos la cuarta fila
        JPanel Fila4 = new JPanel();
        Fila4.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
        Fila4.setBorder(BorderFactory.createTitledBorder("Datos del Nacimiento"));
        
        // la nacionalidad
        JLabel lNacionalidad = new JLabel("Nacionalidad:");
        lNacionalidad.setFont(Fuente.Normal);
        Fila4.add(lNacionalidad);
        this.Nacionalidad = new paises();
        this.Nacionalidad.setFont(Fuente.Normal);
        this.Nacionalidad.setToolTipText("Seleccione de la lista el país de nacimiento");
        this.Nacionalidad.setPreferredSize(new Dimension(200, 30));
        Fila4.add(this.Nacionalidad);
        
        // agregamos el listener de cambio
        this.Nacionalidad.addItemListener(new cambiaNacionalidad());        

        // la provincia de nacimiento
        JLabel lProvNacimiento = new JLabel("Provincia:");
        lProvNacimiento.setFont(Fuente.Normal);
        Fila4.add(lProvNacimiento);
        this.ProvNacimiento = new provincias();
        this.ProvNacimiento.setFont(Fuente.Normal);
        this.ProvNacimiento.setToolTipText("Seleccione la provincia de nacimiento");
        this.ProvNacimiento.setPreferredSize(new Dimension(250, 30));
        Fila4.add(this.ProvNacimiento);
        
        // agregamos el listener de cambio
        this.ProvNacimiento.addItemListener(new cambiaProvNacimiento());

        // la localidad de nacimiento
        JLabel lLocNacimiento = new JLabel("Localidad:");
        lLocNacimiento.setFont(Fuente.Normal);
        Fila4.add(lLocNacimiento);
        this.LocNacimiento = new localidades();
        this.LocNacimiento.setFont(Fuente.Normal);
        this.LocNacimiento.setToolTipText("Seleccione la localidad de nacimiento");
        this.LocNacimiento.setPreferredSize(new Dimension(300, 30));
        Fila4.add(this.LocNacimiento);
        
        // agregamos la fila
        Contenedor.add(Fila4);
        
        // ahora definimos la quinta fila
        JPanel Fila5 = new JPanel();
        Fila5.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
        Fila5.setBorder(BorderFactory.createTitledBorder("Datos de la Residencia"));

        // el país de residencia
        JLabel lPaisResidencia = new JLabel("Residencia:");
        lPaisResidencia.setFont(Fuente.Normal);
        Fila5.add(lPaisResidencia);
        this.PaisResidencia = new paises();
        this.PaisResidencia.setFont(Fuente.Normal);
        this.PaisResidencia.setToolTipText("Seleccione el país de residencia");
        this.PaisResidencia.setPreferredSize(new Dimension(200, 30));
        Fila5.add(PaisResidencia);

        // agregamos el listener
        this.PaisResidencia.addItemListener(new cambiaPaisResidencia());        

        // la provincia de residencia
        JLabel lProvResidencia = new JLabel("Provincia:");
        lProvResidencia.setFont(Fuente.Normal);
        Fila5.add(lProvResidencia);
        this.ProvResidencia = new provincias();
        this.ProvResidencia.setFont(Fuente.Normal);
        this.ProvResidencia.setToolTipText("Seleccione la provincia de residencia");
        this.ProvResidencia.setPreferredSize(new Dimension(250, 30));
        Fila5.add(this.ProvResidencia);
        
        // agregamos el listener
        this.ProvResidencia.addItemListener(new cambiaProvResidencia());        

        // la localidad de residencia
        JLabel lLocResidencia = new JLabel("Localidad:");
        lLocResidencia.setFont(Fuente.Normal);
        Fila5.add(lLocResidencia);
        this.LocResidencia = new localidades();
        this.LocResidencia.setFont(Fuente.Normal);
        this.LocResidencia.setToolTipText("Seleccione la localidad de residencia");
        this.LocResidencia.setPreferredSize(new Dimension(300, 30));
        Fila5.add(this.LocResidencia);
        
        // agregamos la fila
        Contenedor.add(Fila5);
        
        // la sexta fila
        JPanel Fila6 = new JPanel();
        Fila6.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
        Fila6.setBorder(BorderFactory.createTitledBorder("Datos de la Madre"));        
        
        // la nacionalidad de la madre
        JLabel lNacMadre = new JLabel("Nacionalidad");
        lNacMadre.setFont(Fuente.Normal);
        Fila6.add(lNacMadre);
        this.NacMadre = new paises();
        this.NacMadre.setFont(Fuente.Normal);
        this.NacMadre.setToolTipText("Seleccione la nacionalidad de la madre");
        this.NacMadre.setPreferredSize(new Dimension(200, 30));
        Fila6.add(this.NacMadre);
        
        // agegamos el listener de cambio
        this.NacMadre.addItemListener(new cambiaNacMadre());

        // agregamos el listener de cambio
        this.NacMadre.addItemListener(new cambiaNacMadre());        

        // la provincia de la madre
        JLabel lProvMadre = new JLabel("Provincia:");
        lProvMadre.setFont(Fuente.Normal);
        Fila6.add(lProvMadre);
        this.ProvMadre = new provincias();
        this.ProvMadre.setFont(Fuente.Normal);
        this.ProvMadre.setToolTipText("Seleccione la provincia de la madre");
        this.ProvMadre.setPreferredSize(new Dimension(250, 30));
        Fila6.add(this.ProvMadre);
        
        // agregamos el listener de cambio
        this.ProvMadre.addItemListener(new cambiaProvMadre());

        // la localidad de la madre
        JLabel lLocMadre = new JLabel("Localidad:");
        lLocMadre.setFont(Fuente.Normal);
        Fila6.add(lLocMadre);
        this.LocMadre = new localidades();
        this.LocMadre.setFont(Fuente.Normal);
        this.LocMadre.setToolTipText("Seleccione la localidad de la madre");
        this.LocMadre.setPreferredSize(new Dimension(300, 30));
        Fila6.add(LocMadre);
        
        // agregamos la fila
        Contenedor.add(Fila6);

        // ahora definimos la segunda fila
        JPanel Fila7 = new JPanel();
        Fila7.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
        
        // si la madre es positiva
        JLabel lMadrePositiva = new JLabel("Madre Positiva:");
        lMadrePositiva.setFont(Fuente.Normal);
        Fila7.add(lMadrePositiva);
        this.cMadrePositiva = new ComboSiNo();
        this.cMadrePositiva.setFont(Fuente.Normal);
        this.cMadrePositiva.setToolTipText("Indique si la madre es positiva");
        this.cMadrePositiva.setPreferredSize(new Dimension(100, 30));
        Fila7.add(this.cMadrePositiva);    
        
        // la ocupación 
        JLabel lOcupacion = new JLabel("Ocupación:");
        lOcupacion.setFont(Fuente.Normal);
        Fila7.add(lOcupacion);
        this.Ocupacion = new ocupaciones();
        this.Ocupacion.setFont(Fuente.Normal);
        this.Ocupacion.setToolTipText("Indique la ocupación del paciente");
        this.Ocupacion.setPreferredSize(new Dimension(130, 30));
        Fila7.add(this.Ocupacion);
        
        // la obra social
        JLabel lObraSocial = new JLabel("Obra Social:");
        lObraSocial.setFont(Fuente.Normal);
        Fila7.add(lObraSocial);
        this.tObraSocial = new JTextField();
        this.tObraSocial.setFont(Fuente.Normal);
        this.tObraSocial.setToolTipText("Indique el nombre de la Obra Social");
        this.tObraSocial.setPreferredSize(new Dimension(150, 30));
        Fila7.add(this.tObraSocial);
        
        // el motivo de consulta
        JLabel lMotivo = new JLabel("Motivo:");
        lMotivo.setFont(Fuente.Normal);
        Fila7.add(lMotivo);
        this.Motivo = new motivos();
        this.Motivo.setFont(Fuente.Normal);
        this.Motivo.setToolTipText("Indique el motivo de consulta");
        this.tCelular.setPreferredSize(new Dimension(130, 30));
        Fila7.add(this.Motivo);
        
        // agregamos la fila
        Contenedor.add(Fila7);

        // ahora definimos la segunda fila
        JPanel Fila8 = new JPanel();
        Fila8.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));

        // el tipo de derivación 
        JLabel lDerivacion = new JLabel("Derivación:");
        lDerivacion.setFont(Fuente.Normal);
        Fila8.add(lDerivacion);
        this.Derivacion = new derivacion();
        this.Derivacion.setFont(Fuente.Normal);
        this.Derivacion.setToolTipText("Indique el tipo de derivación");
        this.Derivacion.setPreferredSize(new Dimension(110, 30));
        Fila8.add(this.Derivacion);
        
        // el nombre del profesional
        JLabel lProfesional = new JLabel("Profesional:");
        lProfesional.setFont(Fuente.Normal);
        Fila8.add(lProfesional);
        this.tProfesional = new JTextField();
        this.tProfesional.setFont(Fuente.Normal);
        this.tProfesional.setToolTipText("Profesional que envía la muestra");
        this.tProfesional.setPreferredSize(new Dimension(150, 30));
        Fila8.add(this.tProfesional);
        
        // la matrícula del profesional
        JLabel lMatricula = new JLabel("Matrícula:");
        lMatricula.setFont(Fuente.Normal);
        Fila8.add(lMatricula);
        this.tMatricula = new JTextField();
        this.tMatricula.setFont(Fuente.Normal);
        this.tMatricula.setToolTipText("Número de matrícula del profesional");
        this.tMatricula.setPreferredSize(new Dimension(110, 30));
        Fila8.add(this.tMatricula);
        
        // si recibió tratamiento
        JLabel lTratamiento = new JLabel("Tratamiento:");
        lTratamiento.setFont(Fuente.Normal);
        Fila8.add(lTratamiento);
        this.cTratamiento = new ComboSiNo();
        this.cTratamiento.setFont(Fuente.Normal);
        this.cTratamiento.setToolTipText("Indique si recibió tratamiento");
        this.cTratamiento.setPreferredSize(new Dimension(100, 30));
        Fila8.add(this.cTratamiento);
        
        // agregamos el botón protocolo
        JButton btnProtocolo = new JButton("Protocolo");
        btnProtocolo.setIcon(new ImageIcon(getClass().getResource("/recursos/print.png")));
        btnProtocolo.setFont(Fuente.Normal);
        btnProtocolo.setToolTipText("Pulse para generar el protocolo");
        btnProtocolo.setPreferredSize(new Dimension(120, 30));
        Fila8.add(btnProtocolo);

        // fijamos el listener
        btnProtocolo.addActionListener(e -> generaProtocolo());

        // agregamos la fila
        Contenedor.add(Fila8);
        
        // la última fila debe contener los comentarios a la 
        // izquierda y los botones de acción a la derecha
        JPanel Fila9 = new JPanel();
        Fila9.setLayout(new BorderLayout(5,5));
        
        // los comentarios
        this.Comentarios = new JTextArea(7,50);
        this.Comentarios.setFont(Fuente.Normal);
        this.Comentarios.setLineWrap(true);
        this.Comentarios.setToolTipText("Ingrese sus comentarios");

        // establecemos que haga word wrap
        this.Comentarios.setLineWrap(true);
        this.Comentarios.setWrapStyleWord(true);

        // el scroll de los comentarios
        JScrollPane sComentarios = new JScrollPane(this.Comentarios);
        sComentarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sComentarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Fila9.add(sComentarios, BorderLayout.WEST); 

        // ahora definimos otro panel con los botones
        JPanel FilaBotones = new JPanel();
        FilaBotones.setLayout(new BoxLayout(FilaBotones, BoxLayout.PAGE_AXIS));
        
        JPanel FilaDatos = new JPanel();
        FilaDatos.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 5));
        
        // el nombre del usuario
        JLabel lUsuario = new JLabel("Usuario:");
        lUsuario.setFont(Fuente.Normal);
        FilaDatos.add(lUsuario);
        this.tUsuario = new JTextField(Seguridad.Usuario);
        this.tUsuario.setFont(Fuente.Normal);
        this.tUsuario.setToolTipText("Usuario que ingresó el registro");
        this.tUsuario.setPreferredSize(new Dimension(90, 30));
        this.tUsuario.setEditable(false);
        FilaDatos.add(this.tUsuario);
        
        // la fecha de alta
        JLabel lAlta = new JLabel("Alta:");
        lAlta.setFont(Fuente.Normal);
        FilaDatos.add(lAlta);
        this.tAlta = new JTextField(this.Herramientas.FechaActual());
        this.tAlta.setFont(Fuente.Normal);
        this.tAlta.setToolTipText("Fecha de alta del registro");
        this.tAlta.setPreferredSize(new Dimension(90, 30));
        this.tAlta.setEditable(false);
        FilaDatos.add(this.tAlta);
        
        // agregamos a la botonera
        FilaBotones.add(FilaDatos);
        
        JPanel FilaControl = new JPanel();
        FilaControl.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 5));

        // el botón grabar
        JButton btnGrabar = new JButton("Grabar");
        btnGrabar.setIcon(new ImageIcon(getClass().getResource("/recursos/save.png")));
        btnGrabar.setFont(Fuente.Normal);
        btnGrabar.setToolTipText("Pulse para grabar el registro");
        btnGrabar.setPreferredSize(new Dimension(120, 30));
        FilaControl.add(btnGrabar);

        // fijamos el listener
        btnGrabar.addActionListener(e -> verificaPaciente());

        // el botón cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setIcon(new ImageIcon(getClass().getResource("/recursos/borrar.png")));
        btnCancelar.setFont(Fuente.Normal);
        btnCancelar.setToolTipText("Pulse para reiniciar el formulario");
        btnCancelar.setPreferredSize(new Dimension(120, 30));        
        FilaControl.add(btnCancelar);

        // fijamos el listener
        btnCancelar.addActionListener(e -> cancelaPaciente());        

        // agregamos la fila
        FilaBotones.add(FilaControl);
        
        // ahora agregamos el panel de botones al este 
        Fila9.add(FilaBotones, BorderLayout.EAST); 
        
        // y agregamos la última fila al contenedor
        Contenedor.add(Fila9);
        
    }

    /**
     * Nota: de aquí en adelante vamos a definir las 
     * clases internas que controlan los select de 
     * nacionalidad, provincia y localidad, lo hacemos
     * con un itemlistener y verificamos el tipo de 
     * cambio porque si usamos un actionlistener se
     * dispara también cuando agregamos elementos 
     * al combo
     */

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Clase que actualiza el combo de provincia de residencia
     */
    class cambiaNacionalidad implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                ProvNacimiento.cargaDiccionario(Nacionalidad.getId());
            }
        }       
    }    

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Clase que actualiza el combo de localidad de residencia
     */
    class cambiaProvNacimiento implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                LocNacimiento.cargaDiccionario(ProvNacimiento.getCodigo());
            }
        }       
    }    

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Clase que actualiza el combo de provincia de residencia
     */
    class cambiaPaisResidencia implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                ProvResidencia.cargaDiccionario(PaisResidencia.getId());
            }
        }       
    }    

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Clase que actualiza el combo de localidad de residencia
     */
    class cambiaProvResidencia implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                LocResidencia.cargaDiccionario(ProvResidencia.getCodigo());
            }
        }       
    }    

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Clase que actualiza el combo de provincia de residencia
     */
    class cambiaNacMadre implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                ProvMadre.cargaDiccionario(NacMadre.getId());
            }
        }       
    }    

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Clase que actualiza el combo de localidad de residencia
     */
    class cambiaProvMadre implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                LocMadre.cargaDiccionario(ProvMadre.getCodigo());
            }
        }       
    }    

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método público que abre el cuadro de diálogo de búsqueda
     * de pacientes
     */
    public void buscaPaciente(){

        // pedimos el texto a buscar
        String mensaje = "Ingrese el texto a buscar:";
        String m = JOptionPane.showInputDialog(null, mensaje);
        if(m != null){
            this.encuentraPaciente(m);
        }
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param texto cadena a buscar
     * Método llamado por el cuadro de diálogo de búsqueda de 
     * pacientes que recibe como parámetro la cadena de 
     * texto a buscar
     */
    public void encuentraPaciente(String texto){

        // buscamos en la base
        ResultSet Nomina = this.Pacientes.buscaPaciente(texto);

        // instanciamos el formulario cargamos los registros 
        // y lo mostramos
        formbuscar Encontrados = new formbuscar(this);
        Encontrados.cargaPacientes(Nomina);
        Encontrados.setVisible(true);
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método público que abre el cuadro de diálogo de verificación
     * del documento del paciente
     */
    public void nuevoPaciente(){

        // pedimos el texto a buscar
        String mensaje = "Ingrese el documento del paciente:";
        String m = JOptionPane.showInputDialog(null, mensaje);
        if(m != null){
            this.validaPaciente(m);
        }
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param texto cadena a buscar
     * Método llamado por el cuadro de diálogo de validación de 
     * pacientes que recibe como parámetro la el documento del
     * paciente
     */
    public void validaPaciente(String texto){

        // validamos si está registrado
        int registrado = this.Pacientes.validaPaciente(texto);

        // si hay registros
        if (registrado > 0){

            // presenta el mensaje
            JOptionPane.showMessageDialog(null, "El paciente ya está registrado");

        // de otra forma
        } else {

            // nos aseguramos de limpiar el formulario
            this.limpiaFormulario();

            // asignamos el número de documento
            this.tDocumento.setText(texto);

            // fijamos el foco
            this.tApellido.requestFocus();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idpaciente - clave del paciente
     * Método que recibe como parámetro la clave de un paciente
     * y carga los valores del mismo en el formulario, es público
     * porque lo llamamos también desde la clase de vertical 
     */
    public void getDatosPaciente(int idpaciente){

        // obtenemos el registro
        this.Pacientes.getDatosPaciente(idpaciente);

        // ahora asignamos en el formulario
        this.tId.setText(Integer.toString(this.Pacientes.getProtocolo()));
        this.tHistoria.setText(this.Pacientes.getHistoria());
        this.tApellido.setText(this.Pacientes.getApellido());
        this.tNombre.setText(this.Pacientes.getNombre());
        this.tDocumento.setText(this.Pacientes.getDocumento());
        this.dNacimiento.setDate(this.Herramientas.StringToDate(this.Pacientes.getFechaNacimiento()));
        this.TipoDoc.setValor(this.Pacientes.getTipoDoc(), this.Pacientes.getNombreDoc());
        this.sEdad.setValue(this.Pacientes.getEdad());
        this.Sexo.setValor(this.Pacientes.getSexo(), this.Pacientes.getNombreSexo());
        this.Estado.setValor(this.Pacientes.getEstado(), this.Pacientes.getNombreEstado());
        this.sHijos.setValue(this.Pacientes.getHijos());
        this.tDireccion.setText(this.Pacientes.getDireccion());
        this.tTelefono.setText(this.Pacientes.getTelefono());
        this.tCelular.setText(this.Pacientes.getCelular());
        this.tMail.setText(this.Pacientes.getMail());
        this.Nacionalidad.setValor(this.Pacientes.getNacionalidad(), this.Pacientes.getNombreNac());
        this.ProvNacimiento.setValor(this.Pacientes.getProvNacimiento(), this.Pacientes.getNomProvNacimiento());
        this.LocNacimiento.setValor(this.Pacientes.getLocNacimiento(), this.Pacientes.getNomLocNacimiento());
        this.PaisResidencia.setValor(this.Pacientes.getNacResidencia(), this.Pacientes.getNombreNacResidencia());
        this.ProvResidencia.setValor(this.Pacientes.getProvResidencia(), this.Pacientes.getNombreProvResidencia());
        this.LocResidencia.setValor(this.Pacientes.getLocResidencia(), this.Pacientes.getNombreLocResidencia());
        this.NacMadre.setValor(this.Pacientes.getNacMadre(), this.Pacientes.getNombreNacMadre());
        this.ProvMadre.setValor(this.Pacientes.getProvMadre(), this.Pacientes.getNombreProvMadre());
        this.LocMadre.setValor(this.Pacientes.getLocMadre(), this.Pacientes.getNombreLocMadre());
        this.cMadrePositiva.setSelectedItem(this.Pacientes.getMadrePositiva());
        this.Ocupacion.setValor(this.Pacientes.getOcupacion(), this.Pacientes.getNombreOcupacion());
        this.tObraSocial.setText(this.Pacientes.getObraSocial());
        this.Motivo.setValor(this.Pacientes.getMotivo(), this.Pacientes.getNombreMotivo());
        this.Derivacion.setValor(this.Pacientes.getDerivacion(), this.Pacientes.getTipoDerivacion());
        this.tProfesional.setText(this.Pacientes.getProfesional());
        this.tMatricula.setText(this.Pacientes.getMatricula());
        this.cTratamiento.setSelectedItem(this.Pacientes.getTratamiento());
        this.tUsuario.setText(this.Pacientes.getUsuario());
        this.tAlta.setText(this.Pacientes.getFechaAlta());

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar el botón grabar que verifica
     * los datos del formulario y ejecuta la consulta en 
     * la base de datos
     */
    protected void verificaPaciente(){

        // verificamos si es un alta
        if (this.tId.getText().equals("")){
            this.Pacientes.setProtocolo(0);
        } else {
            this.Pacientes.setProtocolo(Integer.parseInt(this.tId.getText()));
        }

        // si no ingresó el nombre
        if (this.tApellido.getText().equals("")){
            new Mensaje("Debe ingresar el Apellido");
            return;
        } else {
            this.Pacientes.setApellido(this.tApellido.getText());
        }

        // si no ingresó el nombre
        if (this.tNombre.getText().equals("")){
            new Mensaje("Debe ingresar el Nombre");
            return;
        } else {
            this.Pacientes.setNombre(this.tNombre.getText());
        }

        // asignamos la clave del documento
        this.Pacientes.setTipoDoc(this.TipoDoc.getId());

        // si no ingresó el número de documento
        if (this.tDocumento.getText().equals("")){
            new Mensaje("No hay documento, se graba igualmente");
            this.Pacientes.setDocumento("");
        } else {
            this.Pacientes.setDocumento(this.tDocumento.getText());
        }

        // si no seleccionó el sexo
        if (this.Sexo.getId() == 0){
            new Mensaje("Indique el sexo del paciente");
            return;
        } else {
            this.Pacientes.setSexo(this.Sexo.getId());
        }

        // si no seleccionó el estado civil
        if (this.Estado.getId() == 0){
            new Mensaje("Indique el estado civil");
            return;
        } else {
            this.Pacientes.setEstado(this.Estado.getId());
        }

        // si no ingresó la dirección 
        if (this.tDireccion.getText().equals("")){
            new Mensaje("No hay dirección, se graba igualmente");
            this.Pacientes.setDireccion("");
        } else {
            this.Pacientes.setDireccion(this.tDireccion.getText());
        }

        // si ingresó el mail verifica el formato
        if (!this.tMail.getText().equals("") && !this.Herramientas.esEmailCorrecto(this.tMail.getText())){
            new Mensaje("El mail parece incorrecto");
            return;
        } else {
            this.Pacientes.setEmail(this.tMail.getText());
        }

        // la localidad la permite en blanco
        this.Pacientes.setLocNacimiento(this.LocNacimiento.getCodigo());

        // la localidad de residencia la permite en blanco
        this.Pacientes.setLocResidencia(this.LocResidencia.getCodigo());

        // la localidad de la madre la permite en blanco
        this.Pacientes.setLocMadre(this.LocMadre.getCodigo());

        // si no indicó si la madre es positiva
        if (this.cMadrePositiva.getSelectedItem().toString().equals("")){
            new Mensaje("No hay datos de la madre, se graba igual");
            this.Pacientes.setMadrePositiva("");
        } else {
            this.Pacientes.setMadrePositiva(this.cMadrePositiva.getSelectedItem().toString());
        }

        // si no seleccionó la ocupación
        if (this.Ocupacion.getId() == 0){
            new Mensaje("No hay datos de ocupación, se graba igual");
            this.Pacientes.setOcupacion(0);
        } else {
            this.Pacientes.setOcupacion(this.Ocupacion.getId());
        }

        // la obra social la permite en blanco
        this.Pacientes.setObraSocial(this.tObraSocial.getText());

        // si no indicó el motivo
        if (this.Motivo.getId() == 0){
            new Mensaje("Indique el motivo de consulta");
            return;
        } else {
            this.Pacientes.setMotivo(this.Motivo.getId());
        }

        // si no seleccionó la derivación
        if (this.Derivacion.getId() == 0){
            new Mensaje("Indique la derivación");
            return;
        } else {
            this.Pacientes.setDerivacion(this.Derivacion.getId());
        }

        // si no ingresó el profesional
        if (this.tProfesional.getText().equals("")){
            new Mensaje("Indique el profesional que derivó");
            return;
        } else {
            this.Pacientes.setProfesional(this.tProfesional.getText());
        }

        // la matrícula la permite en blanco
        this.Pacientes.setMatricula(this.tMatricula.getText());

        // si no indicó el tratamiento
        if (this.cTratamiento.getSelectedItem().toString().equals("")){
            new Mensaje("No hay datos de tratamiento, se graba igual");
            this.Pacientes.setTratamiento("");
        } else {
            this.Pacientes.setTratamiento(this.cTratamiento.getSelectedItem().toString());
        }

        // si llegó hasta aquí grabamos el registro
        int id = this.Pacientes.grabaPaciente();

        // según el resultado
        if (id > 0){

            // asignamos en el formulario
            this.tId.setText(Integer.toString(id));
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar el botón cancelar que según 
     * el estado del formulario recarga el registro o limpia
     * el formulario
     */
    protected void cancelaPaciente(){

        // si está insertando
        if (this.tId.getText().equals("")){
            this.limpiaFormulario();
        } else {
            this.getDatosPaciente(Integer.parseInt(this.tId.getText()));
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que limpia el formulario de datos
     */
    public void limpiaFormulario(){

        // limpamos los elementos 
        this.tId.setText(null);
        this.tHistoria.setText(null);
        this.tApellido.setText(null);
        this.tNombre.setText(null);
        this.tDocumento.setText(null);
        this.TipoDoc.setSelectedIndex(0);
        this.dNacimiento.setDate(null);
        this.sEdad.setValue(0);
        this.Sexo.setSelectedIndex(0);
        this.Estado.setSelectedIndex(0);
        this.sHijos.setValue(0);
        this.tDireccion.setText(null);
        this.tTelefono.setText(null);
        this.tCelular.setText(null);
        this.tMail.setText(null);
        this.Nacionalidad.setSelectedIndex(0);
        this.ProvNacimiento.removeAllItems();
        this.LocNacimiento.removeAllItems();
        this.PaisResidencia.setSelectedIndex(0);
        this.ProvResidencia.removeAllItems();
        this.LocResidencia.removeAllItems();
        this.NacMadre.setSelectedIndex(0);
        this.ProvMadre.removeAllItems();
        this.LocMadre.removeAllItems();
        this.cMadrePositiva.setSelectedIndex(0);
        this.Ocupacion.setSelectedIndex(0);
        this.tObraSocial.setText(null);
        this.Motivo.setSelectedIndex(0);
        this.Derivacion.setSelectedIndex(0);
        this.tProfesional.setText(null);
        this.tMatricula.setText(null);
        this.cTratamiento.setSelectedIndex(0);
        this.tUsuario.setText(Seguridad.Usuario);
        this.tAlta.setText(this.Herramientas.FechaActual());

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro
     * Método público llamado por los formularios hijos que 
     * retorna la clave del paciente
     */
    public int getProtocolo(){

        // convertirmos y retornamos
        return Integer.parseInt(this.tId.getText());
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el menú que abre el diálogo con 
     * la declaración de enfermedades
     */
    public void verEnfermedades(){

        // verificamos si hay un paciente activo
        if (this.tId.getText().equals("")){

            // presenta el mensaje
            JOptionPane.showMessageDialog(null, "Debe tener un paciente activo");
            return;

        }

        // instanciamos el diálogo
        new formenfermedades(this);

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el menú que abre el diálogo con 
     * la declaración de transfusiones
     */
    public void verTransfusiones(){

        // verificamos si hay un paciente activo
        if (this.tId.getText().equals("")){

            // presenta el mensaje
            JOptionPane.showMessageDialog(null, "Debe tener un paciente activo");
            return;

        }

        // instanciamos el diálogo
        new formtransfusiones(this);
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el menú que abre el diálogo con 
     * la declaración de transplantes
     */
    public void verTransplantes(){

        // verificamos si hay un paciente activo
        if (this.tId.getText().equals("")){

            // presenta el mensaje
            JOptionPane.showMessageDialog(null, "Debe tener un paciente activo");
            return;

        }

        // mostramos el formulario
        new formtransplantes(this);

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado al pulsar el botón generar protocolo 
     * que implementa la generación de la muestra
     */
    protected void generaProtocolo(){

        // generar el protocolo

    }
    
}
