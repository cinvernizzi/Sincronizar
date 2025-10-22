/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 10/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase principal de la aplicación, presenta la pantalla de 
 * inicio y llama la acreditación del usuario
 * 
 */

// definición del paquete
package pacientes;

// importamos las librerías
// importamos las librerías
import dbApi.dbLite;
import seguridad.Seguridad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class pacientes {

    // declaramos las variables
    private final Connection Cursor;       // puntero a la base de datos
    private int Protocolo;                 // clave del registro
    private String Historia;               // nro. de historia clínica
    private String Apellido;               // apellido del paciente
    private String Nombre;                 // nombre del paciente
    private String Documento;              // número de documento
    private int TipoDoc;                   // clave del tipo de documento
    private String NombreDoc;              // nombre del tipo de documento
    private String FechaNacimiento;        // fecha de nacimiento dd/mm/yyyy
    private int Edad;                      // edad en años
    private int Sexo;                      // clave del sexo
    private String NombreSexo;             // nombre del sexo
    private int Estado;                    // clave del estado civil
    private String NombreEstado;           // nombre del estado civil
    private int Hijos;                     // número de hijos
    private String Direccion;              // dirección postal
    private String Telefono;               // número de teléfono
    private String Celular;                // número de celular
    private String Mail;                   // correo electrónico
    private String LocNacimiento;          // clave indec de la localidad de nacimiento
    private String NomLocNacimiento;       // nombre de la localidad de nacimiento
    private String ProvNacimiento;         // clave indec de la provincia de nacimiento
    private String NomProvNacimiento;      // nombre de la provincia de nacimiento
    private int Nacionalidad;              // clave de la nacionalidad
    private String NombreNac;              // nombre de la nacionalidad
    private String LocResidencia;          // clave indec de la localidad de residencia
    private String NombreLocResidencia;    // nombre de la localidad de residencia
    private String ProvResidencia;         // clave indec de la provincia de residencia
    private String NombreProvResidencia;   // nombre de la provincia de residencia
    private int NacResidencia;             // clave del país de residencia
    private String NombreNacResidencia;    // nombre del país de residencia
    private String LocMadre;               // clave indec de la localidad de la madre
    private String NombreLocMadre;         // nombre de la localidad de la madre
    private String ProvMadre;              // clave indec de la provincia de la madre
    private String NombreProvMadre;        // nombre de la provincia de la madre
    private int NacMadre;                  // clave de la nacionalidad de la madre
    private String NombreNacMadre;         // nombre de la nacionalidad de la madre
    private String MadrePositiva;          // si la madre es positiva
    private int Ocupacion;                 // clave de la ocupación
    private String NombreOcupacion;        // descripción de la ocupación
    private String ObraSocial;             // nombre de la obra social
    private String NombreMotivo;           // descripción del motivo de consulta
    private int Motivo;                    // clave del motivo de consulta
    private int Derivacion;                // clave de la derivación
    private String TipoDerivacion;         // descripción de la derivación
    private String Profesional;            // nombre del profesional
    private String Matricula;              // matrícula del profesional
    private String Tratamiento;            // si ha recibido tratamiento
    private String Usuario;                // nombre del usuario
    private String FechaAlta;              // fecha de alta del registro
    private String Comentarios;            // comentarios del usuario

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos la conexión e
     * instanciamos las variables de clase
     */
    public pacientes(){

        // instanciamos la conexión 
        dbLite Enlace = new dbLite();
        this.Cursor = Enlace.getEnlace();

        // inicializamos las variables
        this.Protocolo = 0;
        this.Historia = "";
        this.Apellido = "";
        this.Nombre = "";
        this.Documento = "";
        this.TipoDoc = 0;
        this.NombreDoc = "";
        this.FechaNacimiento = "";
        this.Edad = 0;
        this.Sexo = 0;
        this.NombreSexo = "";
        this.Estado = 0;
        this.NombreEstado = "";
        this.Hijos = 0;
        this.Direccion = "";
        this.Telefono = "";
        this.Celular = "";
        this.Mail = "";
        this.LocNacimiento = "";
        this.NomLocNacimiento = "";
        this.ProvNacimiento = "";
        this.NomProvNacimiento = "";
        this.Nacionalidad = 0;
        this.NombreNac = "";
        this.LocResidencia = "";
        this.NombreLocResidencia = "";
        this.ProvResidencia = "";
        this.NombreProvResidencia = "";
        this.NacResidencia = 0;
        this.NombreNacResidencia = "";
        this.LocMadre = "";
        this.NombreLocMadre = "";
        this.ProvMadre = "";
        this.NombreProvMadre = "";
        this.NacMadre = 0;
        this.NombreNacMadre = "";
        this.MadrePositiva = "";
        this.Ocupacion = 0;
        this.NombreOcupacion = "";
        this.ObraSocial = "";
        this.Motivo = 0;
        this.NombreMotivo = "";
        this.Derivacion = 0;
        this.TipoDerivacion = "";
        this.Profesional = "";
        this.Matricula = "";
        this.Tratamiento = "";
        this.Usuario = "";
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
    public void setNombre(String nombre){
        this.Nombre = nombre;
    }
    public void setApellido(String apellido){
        this.Apellido = apellido;
    }
    public void setDocumento(String documento){
        this.Documento = documento;
    }
    public void setTipoDoc(int tipodoc){
        this.TipoDoc = tipodoc;
    }
    public void setFechaNacimiento(String fecha){
        this.FechaNacimiento = fecha;
    }
    public void setEdad(int edad){
        this.Edad = edad;
    }
    public void setSexo(int sexo){
        this.Sexo = sexo;
    }
    public void setEstado(int estado){
        this.Estado = estado;
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
    public void setEmail(String mail){
        this.Mail = mail;
    }
    public void setLocNacimiento(String localidad){
        this.LocNacimiento = localidad;
    }
    public void setLocResidencia(String localidad){
        this.LocResidencia = localidad;
    }
    public void setLocMadre(String localidad){
        this.LocMadre = localidad;
    }
    public void setMadrePositiva(String madre){
        this.MadrePositiva = madre;
    }
    public void setOcupacion(int ocupacion){
        this.Ocupacion = ocupacion;
    }
    public void setObraSocial(String obra){
        this.ObraSocial = obra;
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
    public void setComentarios(String comentarios){
        this.Comentarios = comentarios;
    }

    // métodos de retorno de valores
    public int getProtocolo(){
        return this.Protocolo;
    }
    public String getHistoria(){
        return this.Historia;
    }
    public String getApellido(){
        return this.Apellido;
    }
    public String getNombre(){
        return this.Nombre;
    }
    public String getDocumento(){
        return this.Documento;
    }
    public int getTipoDoc(){
        return this.TipoDoc;
    }
    public String getNombreDoc(){
        return this.NombreDoc;
    }
    public String getFechaNacimiento(){
        return this.FechaNacimiento;
    }
    public int getEdad(){
        return this.Edad;
    }
    public int getSexo(){
        return this.Sexo;
    }
    public String getNombreSexo(){
        return this.NombreSexo;
    }
    public int getEstado(){
        return this.Estado;
    }
    public String getNombreEstado(){
        return this.NombreEstado;
    }
    public int getHijos(){
        return this.Hijos;
    }
    public String getDireccion(){
        return this.Direccion;
    }
    public String getTelefono(){
        return this.Telefono;
    }
    public String getCelular(){
        return this.Celular;
    }
    public String getMail(){
        return this.Mail;
    }
    public String getLocNacimiento(){
        return this.LocNacimiento;
    }
    public String getNomLocNacimiento(){
        return this.NomLocNacimiento;
    }
    public String getProvNacimiento(){
        return this.ProvNacimiento;
    }
    public String getNomProvNacimiento(){
        return this.NomProvNacimiento;
    }
    public int getNacionalidad(){
        return this.Nacionalidad;
    }
    public String getNombreNac(){
        return this.NombreNac;
    }
    public String getLocResidencia(){
        return this.LocResidencia;
    }
    public String getNombreLocResidencia(){
        return this.NombreLocResidencia;
    }
    public String getProvResidencia(){
        return this.ProvResidencia;
    }
    public String getNombreProvResidencia(){
        return this.NombreProvResidencia;
    }
    public int getNacResidencia(){
        return this.NacResidencia;
    }
    public String getNombreNacResidencia(){
        return this.NombreNacResidencia;
    }
    public String getLocMadre(){
        return this.LocMadre;
    }
    public String getNombreLocMadre(){
        return this.NombreLocMadre;
    }
    public String getProvMadre(){
        return this.ProvMadre;
    }
    public String getNombreProvMadre(){
        return this.NombreProvMadre;
    }
    public int getNacMadre(){
        return this.NacMadre;
    }
    public String getNombreNacMadre(){
        return this.NombreNacMadre;
    }
    public String getMadrePositiva(){
        return this.MadrePositiva;
    }
    public int getOcupacion(){
        return this.Ocupacion;
    }
    public String getNombreOcupacion(){
        return this.NombreOcupacion;
    }
    public String getObraSocial(){
        return this.ObraSocial;
    }
    public int getMotivo(){
        return this.Motivo;
    }
    public String getNombreMotivo(){
        return this.NombreMotivo;
    }
    public int getDerivacion(){
        return this.Derivacion;
    }
    public String getTipoDerivacion(){
        return this.TipoDerivacion;
    }
    public String getProfesional(){
        return this.Profesional;
    }
    public String getMatricula(){
        return this.Matricula;
    }
    public String getTratamiento(){
        return this.Tratamiento;
    }
    public String getUsuario(){
        return this.Usuario;
    }
    public String getFechaAlta(){
        return this.FechaAlta;
    }
    public String getComentarios(){
        return this.Comentarios;
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idpaciente - entero con la clave del registro
     * @return boolean resultado de la operación
     * Método que recibe como parámetro la clave de un paciente
     * y asigna los valores del registro en las variables de 
     * clase
     */
    public boolean getDatosPaciente(int idpaciente){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT v_pacientes.protocolo AS protocolo, " +
                   "       v_pacientes.historia_clinica AS historia, " +
                   "       v_pacientes.apellido AS apellido, " +
                   "       v_pacientes.nombre AS nombre, " +
                   "       v_pacientes.documento AS documento, " +
                   "       v_pacientes.iddocumento AS tipodoc, " + 
                   "       v_pacientes.tipo_documento AS nombredoc, " +
                   "       v_pacientes.fecha_nacimiento AS fechanacimiento, " +
                   "       v_pacientes.edad AS edad, " +
                   "       v_pacientes.idsexo AS sexo, " +
                   "       v_pacientes.sexo AS nombresexo, " +
                   "       v_pacientes.idestado AS estado, " +
                   "       v_pacientes.estado_civil AS nombreestado, " +
                   "       v_pacientes.hijos AS hijos, " +
                   "       v_pacientes.direccion AS direccion, " +
                   "       v_pacientes.telefono AS telefono, " +
                   "       v_pacientes.celular AS celular, " + 
                   "       v_pacientes.mail AS mail, " +
                   "       v_pacientes.codloc_nacimiento AS locnacimiento, " + 
                   "       v_pacientes.localidad_nacimiento AS nomlocnacimiento, " +
                   "       v_pacientes.codprov_nacimiento AS provnacimiento, " + 
                   "       v_pacientes.provincia_nacimiento AS nomprovnacimiento, " +
                   "       v_pacientes.idpais_nacimiento AS nacionalidad, " +
                   "       v_pacientes.pais_nacimiento AS nombrenac, " +
                   "       v_pacientes.codloc_residencia AS locresidencia, " + 
                   "       v_pacientes.localidad_residencia AS nombrelocresidencia, " +
                   "       v_pacientes.codprov_residencia AS provresidencia, " + 
                   "       v_pacientes.provincia_residencia AS nombreprovresidencia, " +
                   "       v_pacientes.idpais_residencia AS paisresidencia, " + 
                   "       v_pacientes.pais_residencia AS nombrenacresidencia, " + 
                   "       v_pacientes.codloc_madre AS locmadre, " + 
                   "       v_pacientes.localidad_madre AS nombrelocmadre, " +
                   "       v_pacientes.codprov_madre AS provmadre, " + 
                   "       v_pacientes.provincia_madre AS nombreprovmadre, " +
                   "       v_pacientes.idpais_madre AS paismadre, " + 
                   "       v_pacientes.pais_madre AS nombrenacmadre, " +
                   "       v_pacientes.madre_positiva AS madrepositiva, " +
                   "       v_pacientes.idocupacion AS ocupacion, " + 
                   "       v_pacientes.ocupacion AS nombreocupacion, " +
                   "       v_pacientes.obra_social AS obrasocial, " + 
                   "       v_pacientes.idmotivo AS motivo, " + 
                   "       v_pacientes.motivo AS nombremotivo, " +
                   "       v_pacientes.idderivacion AS derivacion, " +
                   "       v_pacientes.derivacion AS tipoderivacion, " + 
                   "       v_pacientes.profesional AS profesional, " +
                   "       v_pacientes.matricula AS matricula, " + 
                   "       v_pacientes.tratamiento AS tratamiento, " + 
                   "       v_pacientes.usuario AS usuario, " + 
                   "       v_pacientes.fecha_alta AS fechaalta, " + 
                   "       v_pacientes.comentarios AS comentarios " +
                   "FROM v_pacientes " + 
                   "WHERE v_pacientes.protocolo = ?; ";

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idpaciente);
            
            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // dirige al primer registro
            Resultado.next();
            
            // asignamos en la clase
            this.Protocolo = Resultado.getInt("protocolo");
            this.Historia = Resultado.getString("historia");
            this.Apellido = Resultado.getString("apellido");
            this.Nombre = Resultado.getString("nombre");
            this.Documento = Resultado.getString("documento");
            this.TipoDoc = Resultado.getInt("tipodoc");
            this.NombreDoc = Resultado.getString("nombredoc");
            this.FechaNacimiento = Resultado.getString("fechanacimiento");
            this.Edad = Resultado.getInt("edad");
            this.Sexo = Resultado.getInt("sexo");
            this.NombreSexo = Resultado.getString("nombresexo");
            this.Estado = Resultado.getInt("estado");
            this.NombreEstado = Resultado.getString("nombreestado");
            this.Hijos = Resultado.getInt("hijos");
            this.Direccion = Resultado.getString("direccion");
            this.Telefono = Resultado.getString("telefono");
            this.Celular = Resultado.getString("celular");
            this.Mail = Resultado.getString("mail");
            this.LocNacimiento = Resultado.getString("locnacimiento");
            this.NomLocNacimiento = Resultado.getString("nomlocnacimiento");
            this.ProvNacimiento = Resultado.getString("provnacimiento");
            this.NomProvNacimiento = Resultado.getString("nomprovnacimiento");
            this.Nacionalidad = Resultado.getInt("nacionalidad");
            this.NombreNac = Resultado.getString("nombrenac");
            this.LocResidencia = Resultado.getString("provresidencia");
            this.NombreLocResidencia = Resultado.getString("nombreprovresidencia");
            this.ProvResidencia = Resultado.getString("provresidencia");
            this.NombreProvResidencia = Resultado.getString("nombreprovresidencia");
            this.NacResidencia = Resultado.getInt("paisresidencia");
            this.NombreNacResidencia = Resultado.getString("nombrenacresidencia");
            this.LocMadre = Resultado.getString("locmadre");
            this.NombreLocMadre = Resultado.getString("nombrelocmadre");
            this.ProvMadre = Resultado.getString("provmadre");
            this.NombreProvMadre = Resultado.getString("nombreprovmadre");
            this.NacMadre = Resultado.getInt("paismadre");
            this.NombreNacMadre = Resultado.getString("nombrenacmadre");
            this.MadrePositiva = Resultado.getString("madrepositiva");
            this.Ocupacion = Resultado.getInt("ocupacion");
            this.NombreOcupacion = Resultado.getString("nombreocupacion");
            this.ObraSocial = Resultado.getString("obrasocial");
            this.Motivo = Resultado.getInt("motivo");
            this.NombreMotivo = Resultado.getString("nombremotivo");
            this.Derivacion = Resultado.getInt("derivacion");
            this.TipoDerivacion = Resultado.getString("tipoderivacion");
            this.Profesional = Resultado.getString("profesional");
            this.Matricula = Resultado.getString("matricula");
            this.Tratamiento = Resultado.getString("tratamiento");
            this.Usuario = Resultado.getString("usuario");
            this.FechaAlta = Resultado.getString("fechaalta");
            this.Comentarios = Resultado.getString("comentarios");

            // retornamos
            return true;

        // si hubo un error
        } catch (SQLException e){
            
            // resenta el mensaje
            e.printStackTrace();			
            return false;
            
        }
                   
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro
     * Método que según el estado de las variables de clase
     * genera la consulta de inserción o edición, retorna 
     * la clave del registro o cero en caso de error
     */
    public int grabaPaciente(){

        // declaramos las variables
        int Resultado;

        // según la clave
        if (this.Protocolo == 0){
            Resultado = this.nuevoPaciente();
        } else {
            Resultado = this.editaPaciente();
        }

        // retornamos
        return Resultado;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return boolean el resultado de la operación
     * Método llamado en caso de un alta que genera un número 
     * de historia clínica
     */
    protected boolean nuevaHistoria(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta obteniendo la última historia
        Consulta = "SELECT MAX(SUBSTRING(pacientes.historia_clinica, 0, 6)) AS historia " +
                   "FROM pacientes;";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            
            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // dirige al primer registro
            Resultado.next();

            // asignamos la historia
            this.Historia = Resultado.getString("historia") + "397";

            // retornamos
            return true;
            
        // si hubo un error
        } catch (SQLException e){
            
            // resenta el mensaje
            e.printStackTrace();			
            return false;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro
     * Método que ejecuta la consulta de inserción, retorna la 
     * clave del nuevo registro o cero en caso de error
     */
    protected int nuevoPaciente(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // generamos la historia clínica
        this.nuevaHistoria();

        // componemos la consulta
        Consulta = "INSERT INTO pacientes " + 
                   "       (historia_clinica, " +
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
                   "        comentarios, " + 
                   "        sincronizado) " + 
                   "       VALUES " +
                   "       (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + 
                   "        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                   "        ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setString(1, this.Historia);
            Preparada.setString(2, this.Apellido);
            Preparada.setString(3, this.Nombre);
            Preparada.setString(4, this.Documento);
            Preparada.setInt(5, this.TipoDoc);
            Preparada.setString(6, this.FechaNacimiento);
            Preparada.setInt(7, this.Edad);
            Preparada.setInt(8, this.Sexo);
            Preparada.setInt(9, this.Estado);
            Preparada.setInt(10, this.Hijos);
            Preparada.setString(11, this.Direccion);
            Preparada.setString(12, this.Telefono);
            Preparada.setString(13, this.Celular);
            Preparada.setString(14, this.Mail);
            Preparada.setString(15, this.LocNacimiento);
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
            Preparada.setInt(25, Seguridad.IdUsuario);
            Preparada.setString(26, this.FechaAlta);
            Preparada.setString(27, this.Comentarios);
            Preparada.setString(28, "No");

            // ejecutamos la consulta
            Preparada.execute();

            // obtenemos la clave
            return this.ultimoInsertado();

        // si hubo un error
        } catch (SQLException e){
            
            // resenta el mensaje
            e.printStackTrace();			
            return 0;
            
        }
                   
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro
     * Método que ejecuta la consulta de edición, retorna la 
     * clave del registro afectado o cero en caso de error
     */
    protected int editaPaciente(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE pacientes SET " +
                   "       historia_clinica = ?, " +
                   "       apellido = ?, " +
                   "       nombre = ?, " + 
                   "       documento = ?, " +
                   "       tipo_documento = ?, " +
                   "       fecha_nacimiento = ?, " +
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
                   "       fecha_alta = ?, " +
                   "       comentarios = ?, " + 
                   "       sincronizado = ? " + 
                   "WHERE pacientes.protocolo = ?; ";
                   
        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setString(1, this.Historia);
            Preparada.setString(2, this.Apellido);
            Preparada.setString(3, this.Nombre);
            Preparada.setString(4, this.Documento);
            Preparada.setInt(5, this.TipoDoc);
            Preparada.setString(6, this.FechaNacimiento);
            Preparada.setInt(7, this.Edad);
            Preparada.setInt(8, this.Sexo);
            Preparada.setInt(9, this.Estado);
            Preparada.setInt(10, this.Hijos);
            Preparada.setString(11, this.Direccion);
            Preparada.setString(12, this.Telefono);
            Preparada.setString(13, this.Celular);
            Preparada.setString(14, this.Mail);
            Preparada.setString(15, this.LocNacimiento);
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
            Preparada.setInt(25, Seguridad.IdUsuario);
            Preparada.setString(26, this.FechaAlta);
            Preparada.setString(27, this.Comentarios);
            Preparada.setString(28, "No");
            Preparada.setInt(29, this.Protocolo);

            // ejecutamos la consulta
            Preparada.execute();

            // retornamos
            return this.Protocolo;

        // si hubo un error
        } catch (SQLException e){
            
            // resenta el mensaje
            e.printStackTrace();			
            return 0;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.siteX
     * @return entero con la clave del último registro insertado
     * Método que simplemente retorna la clave del ultimo registro
     */
    private int ultimoInsertado(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT last_insert_rowid() AS ultimo; ";

        // capturamos el error
        try {

            // preparamos y ejecutamos 
            Preparada = this.Cursor.prepareStatement(Consulta);
            Resultado = Preparada.executeQuery();
            Resultado.next();
            return Resultado.getInt("ultimo");

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
            e.printStackTrace();			
            return 0;

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param documento cadena con el documento del paciente
     * @return entero con la clave del registro
     * Método que recibe como parámetro el documento de un 
     * paciente y verifica que no se encuentre declarado, 
     * retorna cero si no existe y en caso de encontrarlo 
     * retorna la clave del registro
     */
    public int validaPaciente(String documento){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT COUNT(pacientes.protocolo) AS registros " +
                   "FROM pacientes " + 
                   "WHERE pacientes.documento = ?; ";

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setString(1, documento);
            
            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // dirige al primer registro
            Resultado.next();
            
            // retornamos
            return Resultado.getInt("registros");

        // si hubo un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            return 1;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param texto con la cadena a buscar
     * @return resultset con los registros coincidentes
     * Método que recibe como parámetro una cadena y busca la 
     * ocurrencia de la misma en el nombre y documento de 
     * los pacientes adultos
     * 
     * Atención con la sintaxis de ISNULL que es distinta
     * en sqlite
     */
    public ResultSet buscaPaciente(String texto){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT v_pacientes.protocolo AS protocolo, " +
                   "       v_pacientes.completo AS nombre, " + 
                   "       v_pacientes.documento AS documento, " + 
                   "       v_pacientes.localidad_residencia AS localidad, " + 
                   "       v_pacientes.motivo AS motivo, " + 
                   "       v_pacientes.tratamiento AS tratamiento, " +
                   "       v_pacientes.usuario AS usuario, " + 
                   "       v_pacientes.fecha_alta AS alta " + 
                   "FROM v_pacientes LEFT JOIN v_vertical ON v_pacientes.protocolo = v_vertical.protocolo " + 
                   "WHERE v_vertical.protocolo IS NULL AND " + 
                   "      v_pacientes.completo LIKE ? OR " + 
                   "      v_pacientes.documento = ? " + 
                   "ORDER BY v_pacientes.protocolo, " + 
                   "         v_pacientes.completo; "; 

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setString(1, "%" + texto + "%");
            Preparada.setString(2, texto);
            
            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // retornamos
            return Resultado;

        // si hubo un error
        } catch (SQLException e){
            
            // resenta el mensaje
            e.printStackTrace();			
            return Resultado;
            
        }
                   
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param texto con la cadena a buscar
     * @return resultset con los registros coincidentes
     * Método que recibe como parámetro una cadena y busca la 
     * ocurrencia del mismo en el nombre y documento de los 
     * pacientes verticales
     */
    public ResultSet buscaVertical(String texto){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT v_pacientes.protocolo AS protocolo, " +
                   "       v_pacientes.completo AS nombre, " + 
                   "       v_pacientes.documento AS documento, " + 
                   "       v_pacientes.localidad_residencia AS localidad, " + 
                   "       v_pacientes.motivo AS motivo, " + 
                   "       v_pacientes.tratamiento AS tratamiento, " +
                   "       v_pacientes.usuario AS usuario, " + 
                   "       v_pacientes.fecha_alta AS alta " + 
                   "FROM v_pacientes INNER JOIN v_vertical ON v_pacientes.protocolo = v_vertical.protocolo " + 
                   "WHERE v_pacientes.completo LIKE ? OR " + 
                   "      v_pacientes.documento = ? " + 
                   "ORDER BY v_pacientes.protocolo, " + 
                   "         v_pacientes.completo; "; 

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setString(1, "%" + texto + "%");
            Preparada.setString(2, texto);
            
            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // retornamos
            return Resultado;

        // si hubo un error
        } catch (SQLException e){
            
            // resenta el mensaje
            e.printStackTrace();			
            return Resultado;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return resultset con los registros modificados
     * Método llamado antes de sincronizar que obtiene el vector
     * con todos los registros nuevos y modificados que no están 
     * sincronizados con el servidor
     */
    public ResultSet getModificados(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT v_pacientes.protocolo AS protocolo, " +
                   "       v_pacientes.historia_clinica AS historia, " +
                   "       v_pacientes.apellido AS apellido, " +
                   "       v_pacientes.nombre AS nombre, " +
                   "       v_pacientes.documento AS documento, " +
                   "       v_pacientes.iddocumento AS tipodoc, " + 
                   "       v_pacientes.fecha_nacimiento AS fechanacimiento, " +
                   "       v_pacientes.edad AS edad, " +
                   "       v_pacientes.idsexo AS sexo, " +
                   "       v_pacientes.idestado AS estado, " +
                   "       v_pacientes.hijos AS hijos, " +
                   "       v_pacientes.direccion AS direccion, " +
                   "       v_pacientes.telefono AS telefono, " +
                   "       v_pacientes.celular AS celular, " + 
                   "       v_pacientes.mail AS mail, " +
                   "       v_pacientes.codloc_nacimiento AS locnacimiento, " + 
                   "       v_pacientes.codloc_residencia AS locresidencia, " + 
                   "       v_pacientes.codloc_madre AS locmadre, " + 
                   "       v_pacientes.madre_positiva AS madrepositiva, " +
                   "       v_pacientes.idocupacion AS ocupacion, " + 
                   "       v_pacientes.obra_social AS obrasocial, " + 
                   "       v_pacientes.idmotivo AS motivo, " + 
                   "       v_pacientes.idderivacion AS derivacion, " + 
                   "       v_pacientes.profesional AS profesional, " +
                   "       v_pacientes.matricula AS matricula, " + 
                   "       v_pacientes.tratamiento AS tratamiento, " + 
                   "       v_pacientes.idusuario AS idusuario, " + 
                   "       v_pacientes.fecha_alta AS fechaalta " + 
                   "       v_pacientes.comentarios As comentarios " +
                   "FROM v_pacientes " + 
                   "WHERE v_pacientes.sincronizado = 'No'; ";

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            
            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // retornamos
            return Resultado;

        // si hubo un error
        } catch (SQLException e){
            
            // resenta el mensaje
            e.printStackTrace();			
            return Resultado;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param documento número de documento del paciente
     * @return el protocolo del paciente
     * Método utilizado en el alta de pacientes verticales
     * recibe como parámetro el número de documento 
     * retorna el protocolo o cero en caso de no encontrarlo
     */
    public int getProtocoloPaciente(String documento){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT pacientes.protocolo AS protocolo " + 
                   "FROM pacientes " +
                   "WHERE pacientes.documento = ?; ";

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setString(1, documento);
            Resultado = Preparada.executeQuery();

            // si hay registros
            if (Resultado.next()){
                return Resultado.getInt("protocolo");
            } else {
                return 0;
            }

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
            e.printStackTrace();
            return 0;

        }

    }

}
