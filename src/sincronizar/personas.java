/*

    Nombre: Personas
    Fecha: 07/10/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: UploadSitracha
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Clase que provee los métodos para verificar
                 e insertar los registros de pacientes en 
                 el servidor 

 */

// definición del paquete
package sincronizar;

// importamos las librerías
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import dbApi.mariaDb;

/*
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class personas {

    // puntero a la base de datos
    private final mariaDb Puntero;
    private final Connection Enlace;

    // definimos las variables 
    protected int Protocolo;        // clave del registro
    protected int IdLaboratorio;    // clave del laboratorio
    protected String Historia;      // historia clínica
    protected String Apellido;      // apellido del paciente
    protected String Nombre;        // nombre del paciente
    protected String Documento;     // número de documento
    protected int IdDocumento;      // clave del tipo de documento
    protected String Nacimiento;    // fecha de nacimiento
    protected int Edad;             // edad en años
    protected int Sexo;             // clave del sexo
    protected int EstadoCivil;      // clave del estado civil
    protected int Hijos;            // número de hijos
    protected String Direccion;     // dirección postal
    protected String Telefono;      // teléfono fijo
    protected String Celular;       // teléfono celular
    protected String Mail;          // correo electrónico
    protected String LocNacimiento; // clave indec de la localidad 
    protected String LocResidencia; // clave indec de la localidad
    protected String LocMadre;      // clave indec de la localidad
    protected String MadrePositiva; // si la madre es positiva
    protected int Ocupacion;        // clave de la ocupación
    protected String ObraSocial;    // nombre de la obra social
    protected int Motivo;           // clave del motivo de consulta
    protected int Derivacion;       // clave del tipo de derivacion
    protected String Profesional;   // nombre del profesional
    protected String Matricula;     // número de matrícula
    protected String Tratamiento;   // si recibió tratamiento
    protected int Usuario;          // clave del usuario
    protected String FechaAlta;     // fecha de alta del registro
    protected String Comentarios;   // comentarios del usuario

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, establecemos la conexión e 
     * inicializamos las variables
     */
    public personas(){
     
        // la conexión al servidor
        this.Puntero = new mariaDb();        
        this.Enlace = Puntero.getEnlace();
        
        // inicializamos las variables
        this.Protocolo = 0;
        this.IdLaboratorio = 397;
        this.Historia = "";
        this.Apellido = "";
        this.Nombre = "";
        this.Documento = "";
        this.IdDocumento = 0;
        this.Nacimiento = "";
        this.Edad = 0;
        this.Sexo = 0;
        this.EstadoCivil = 0;
        this.Hijos = 0;
        this.Direccion = "";
        this.Telefono = "";
        this.Celular = "";
        this.Mail = "";
        this.LocNacimiento = "";
        this.LocResidencia = "";
        this.LocMadre = "";
        this.MadrePositiva = "";
        this.Ocupacion = 0;
        this.ObraSocial = "";
        this.Motivo = 0;
        this.Derivacion = 0;
        this.Profesional = "";
        this.Matricula = "";
        this.Tratamiento = "";
        this.Usuario = 0;
        this.FechaAlta = "";
        this.Comentarios = "";

    }

    // métodos de asignación de valores
    public void setProtocolo(int protocolo){
        this.Protocolo = protocolo;
    }
    public void setHistoria(String historia){
        this.Historia = historia;
    }
    public void setApellido(String apellido){
        this.Apellido = apellido;
    }
    public void setNombre(String nombre){
        this.Nombre = nombre;
    }
    public void setDocumento(String documento){
        this.Documento = documento;
    }
    public void setIdDocumento(int iddocumento){
        this.IdDocumento = 0;
    }
    public void setNacimiento(String nacimiento){
        this.Nacimiento = nacimiento;
    }
    public void setEdad(int edad){
        this.Edad = edad;
    }
    public void setSexo(int sexo){
        this.Sexo = sexo;
    }
    public void setEstadoCivil(int estado){
        this.EstadoCivil = estado;
    }
    public void setHijos(int hijos){
        this.Hijos = hijos;
    }
    public void setDireccion(String direccion){
        this.Direccion = direccion;
    }
    public void setTelefono(String telefono){
        this.Telefono = telefono;
    }
    public void setCelular(String celular){
        this.Celular = celular;
    }
    public void setMail(String mail){
        this.Mail = mail;
    }
    public void setLocNacimiento(String codloc){
        this.LocNacimiento = codloc;
    }
    public void setLocResidencia(String codloc){
        this.LocResidencia = codloc;
    }
    public void setLocMadre(String codloc){
        this.LocMadre = codloc;
    }
    public void setMadrePositiva(String positiva){
        this.MadrePositiva = positiva;
    }
    public void setOcupacion(int ocupacion){
        this.Ocupacion = ocupacion;
    }
    public void setObraSocial(String obrasocial){
        this.ObraSocial = obrasocial;
    }
    public void setMotivo(int motivo){
        this.Motivo = motivo;
    }
    public void setDerivacion(int derivacion){
        this.Derivacion = derivacion;
    }
    public void setProfesional(String profesional){
        this.Profesional = profesional;
    }
    public void setMatricula(String matricula){
        this.Matricula = matricula;
    }
    public void setTratamiento(String tratamiento){
        this.Tratamiento = tratamiento;
    }
    public void setUsuario(int usuario){
        this.Usuario = usuario;
    }
    public void setFechaAlta(String fecha){
        this.FechaAlta = fecha;
    }
    public void setComentarios(String comentarios){
        this.Comentarios = comentarios;
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro en el servidor
     * Método que a partir de los valores de las variables 
     * de clase establece si debe insertar o editar el registro
     */
    protected int verificaPaciente(){

        // definición de variables
        ResultSet Resultado; 
        String Consulta;
        PreparedStatement Preparada;
        int Clave = 0;

        // verificamos si es una edición o inserción 
        Consulta = "SELECT diagnostico.personas.protocolo AS id " +
                   "FROM diagnostico.personas " + 
                   "WHERE diagnostico.personas.documento = ?; ";

        // capturamos el error
        try {

            // asignamos los parámetros 
            Preparada = this.Enlace.prepareStatement(Consulta);
            Preparada.setString(1, this.Documento);

            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // si es una inserción 
            if (!Resultado.next()){
                Clave = this.nuevoPaciente();
            // si es una edición 
            } else {
                Clave = this.editaPaciente(Resultado.getInt("id"));
            }

            // retornamos
            return Clave;

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
            e.printStackTrace();

            // retornamos
            return Clave;

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return id entero con la clave del nuevo registro
     */
    protected int nuevoPaciente(){

        // definición de variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "INSERT INTO diagnostico.personas " +
                   "       (id_laboratorio, " +
                   "        historia_clinica, " + 
                   "        apellido, " + 
                   "        nombre, " + 
                   "        documento, " + 
                   "        tipo_documento, " + 
                   "        fecha_nacimiento, " + 
                   "        edad, " + 
                   "        sexo, " + 
                   "        estado_civil, " + 
                   "        hijos, " + 
                   "        direccion, " + 
                   "        telefono, " + 
                   "        celular, " + 
                   "        mail, " +
                   "        localidad_nacimiento, " + 
                   "        localidad_residencia, " + 
                   "        localidad_madre, " + 
                   "        madre_positiva, " + 
                   "        ocupacion, " + 
                   "        obra_social, " + 
                   "        motivo, " + 
                   "        derivacion, " + 
                   "        profesional, " + 
                   "        matricula, " + 
                   "        tratamiento, " + 
                   "        usuario, " + 
                   "        fecha_alta, " + 
                   "        comentarios) " + 
                   "       VALUES " + 
                   "       (?, ?, ?, ?, ?, ?, " +
                   "        STR_TO_DATE(?, '%d/%m/%Y'), " + 
                   "        ?, ?, ?, ?, ?, ?, ?, " +
                   "        ?, ?, ?, ?, ?, ?, ?, " +
                   "        ?, ?, ?, ?, ?, ?, " +
                   "        STR_TO_DATE(?, '%d/%m/%Y'), ?)";

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            Preparada = this.Enlace.prepareStatement(Consulta);
            Preparada.setInt(1, this.IdLaboratorio);
            Preparada.setString(2, this.Historia);
            Preparada.setString(3, this.Apellido);
            Preparada.setString(4, this.Nombre);
            Preparada.setString(5, this.Documento);
            Preparada.setInt(6, this.IdDocumento);
            Preparada.setString(7, this.Nacimiento);
            Preparada.setInt(8, this.Edad);
            Preparada.setInt(9, this.Sexo);
            Preparada.setInt(10, this.EstadoCivil);
            Preparada.setInt(11, this.Hijos);
            Preparada.setString(12, this.Direccion);
            Preparada.setString(13, this.Telefono);
            Preparada.setString(14, this.Celular);
            Preparada.setString(15, this.Mail);
            Preparada.setString(16, this.LocNacimiento);
            Preparada.setString(17, this.LocResidencia);
            Preparada.setString(18, this.LocMadre);
            Preparada.setString(19, this.MadrePositiva);
            Preparada.setInt(20, this.Ocupacion);
            Preparada.setString(21, this.ObraSocial);
            Preparada.setInt(22, this.Motivo);
            Preparada.setInt(23, this.Derivacion);
            Preparada.setString(24, this.Profesional);
            Preparada.setString(25, this.Matricula);
            Preparada.setString(26, this.Tratamiento);
            Preparada.setInt(27, this.Usuario);
            Preparada.setString(28, this.FechaAlta);
            Preparada.setString(29, this.Comentarios);

            // ejecutamos la consulta
            Preparada.execute();
            return this.Puntero.UltimoInsertado();

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error y retorna
            e.printStackTrace();
            return 0;

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param id la clave del registro a editar
     * @return int clave del registro afectado
     * Método que edita el registro del paciente 
     */
    protected int editaPaciente(int id){

        // definición de variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE diagnostico.personas SET " +
                   "       apellido = ?, " +
                   "       nombre = ?, " +
                   "       documento = ?, " +
                   "       tipo_documento = ?, " +
                   "       fecha_nacimiento = STR_TO_DATE(?, '%d/%m/%Y'), " +
                   "       edad = ?, " + 
                   "       sexo = ?, " + 
                   "       estado_civil = ?, " + 
                   "       hijos = ?, " + 
                   "       direccion = ?, " + 
                   "       telefono = ?, " + 
                   "       celular = ?, " + 
                   "       mail = ?, " + 
                   "       localidad_nacimiento = ?, " + 
                   "       localidad_residencia = ?, " + 
                   "       localidad_madre = ?, " + 
                   "       madre_positiva = ?, " + 
                   "       ocupacion = ?, " + 
                   "       obra_social = ?, " + 
                   "       motivo = ?, " + 
                   "       derivacion = ?, " + 
                   "       profesional = ?, " + 
                   "       matricula = ?, " + 
                   "       tratamiento = ?, " + 
                   "       usuario = ?, " + 
                   "       fecha_alta = STR_TO_DATE(?, '%d/%m/%Y'), " + 
                   "       comentarios = ? " + 
                   "SWHERE diagnostico.personas.protocolo = ?; ";

        // capturamos el error
        try {

            // preparamos y asignamos
            Preparada = this.Enlace.prepareStatement(Consulta);
            Preparada.setString(1, this.Apellido);
            Preparada.setString(2, this.Nombre);
            Preparada.setString(3, this.Documento);
            Preparada.setInt(4, this.IdDocumento);
            Preparada.setString(5, this.Nacimiento);
            Preparada.setInt(6, this.Edad);
            Preparada.setInt(7, this.Sexo);
            Preparada.setInt(8, this.EstadoCivil);
            Preparada.setInt(9, this.Hijos);
            Preparada.setString(10, this.Direccion);
            Preparada.setString(11, this.Telefono);
            Preparada.setString(12, this.Celular);
            Preparada.setString(13, this.Mail);
            Preparada.setString(14, this.LocNacimiento);
            Preparada.setString(15, this.LocResidencia);
            Preparada.setString(16, this.LocMadre);
            Preparada.setString(17, this.MadrePositiva);
            Preparada.setInt(18, this.Ocupacion);
            Preparada.setString(19, this.ObraSocial);
            Preparada.setInt(20, this.Motivo);
            Preparada.setInt(21, this.Derivacion);
            Preparada.setString(22, this.Profesional);
            Preparada.setString(23, this.Matricula);
            Preparada.setString(24, this.Tratamiento);
            Preparada.setInt(25, this.Usuario);
            Preparada.setString(26, this.FechaAlta);
            Preparada.setString(27, this.Comentarios);
            Preparada.setInt(28, id);

            // ejecutamos y retornamos
            Preparada.execute();
            return id;

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error y retorna
            e.printStackTrace();
            return 0;

        }

    }

}
