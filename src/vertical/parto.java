/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 17/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que controla las operaciones sobre la tabla de 
 * datos del parto
 * 
 */

// declaración del paquete
package vertical;

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
public class parto {

    // declaración de variables
    private final Connection Cursor;   // puntero a la base
    private int Id;                    // clave del registro
    private int Protocolo;             // clave del paciente
    private int Madre;                 // clave de la madre
    private String Sivila;             // clave del sivila
    private String Reportado;          // fecha del reporte al sivila
    private String TipoParto;          // tipo de parto (normal / cesárea)
    private int Peso;                  // peso al nacer en gramos
    private String Prematuro;          // si fue prematuro
    private String CodProv;            // código indec de la provincia de la institución
    private String Provincia;          // nombre de la provincia
    private String CodLoc;             // código indec de la localidad de la institución
    private String Localidad;          // nombre de la localidad
    private int IdInstitucion;         // clave de la maternidad
    private String Institucion;        // nombre de la maternidad
    private String Comentarios;        // comentarios del usuario
    private String Fecha;              // fecha de alta
    private String Usuario;            // nombre del usuario

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos la conexión e 
     * inicializamos las variables
     */
    public parto(){

        // instanciamos la conexión 
        dbLite Enlace = new dbLite();
        this.Cursor = Enlace.getEnlace();

        // inicializamos las variables
        this.Id = 0;
        this.Protocolo = 0;
        this.Madre = 0;
        this.Sivila = "";
        this.Reportado = "";
        this.TipoParto = "";
        this.Peso = 0;
        this.Prematuro = "";
        this.CodProv = "";
        this.Provincia = "";
        this.CodLoc = "";
        this.Localidad = "";
        this.IdInstitucion = 0;
        this.Institucion = "";
        this.Comentarios = "";
        this.Fecha = "";
        this.Usuario = "";

    }

    // métodos de asignación de valores
    public void setId(int id){
        this.Id = id;
    }
    public void setProtocolo(int protocolo){
        this.Protocolo = protocolo;
    }
    public void setMadre(int madre){
        this.Madre = madre;
    }
    public void setSivila(String sivila){
        this.Sivila = sivila;
    }
    public void setReportado(String reportado){
        this.Reportado = reportado;
    }
    public void setParto(String parto){
        this.TipoParto = parto;
    }
    public void setPeso(int peso){
        this.Peso = peso;
    }
    public void setPrematuro(String prematuro){
        this.Prematuro = prematuro;
    }
    public void setIdInstitucion(int idinstitucion){
        this.IdInstitucion = idinstitucion;
    }
    public void setComentarios(String comentarios){
        this.Comentarios = comentarios;
    }
    public void setFecha(String fecha){
        this.Fecha = fecha;
    }

    // métodos de retorno de valores
    public int getId(){
        return this.Id;
    }
    public int getProtocolo(){
        return this.Protocolo;
    }
    public int getMadre(){
        return this.Madre;
    }
    public String getSivila(){
        return this.Sivila;
    }
    public String getReportado(){
        return this.Reportado;
    }
    public String getParto(){
        return this.TipoParto;
    }
    public int getPeso(){
        return this.Peso;
    }
    public String getPrematuro(){
        return this.Prematuro;
    }
    public String getCodProv(){
        return this.CodProv;
    }
    public String getProvincia(){
        return this.Provincia;
    }
    public String getCodloc(){
        return this.CodLoc;
    }
    public String getLocalidad(){
        return this.Localidad;
    }
    public int getIdInstitucion(){
        return this.IdInstitucion;
    }
    public String getInstitucion() {
        return this.Institucion;
    }
    public String getComentarios(){
        return this.Comentarios;
    }
    public String getFecha(){
        return this.Fecha;
    }
    public String getUsuario(){
        return this.Usuario;
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro
     * Método que según el valor de las variables de clase 
     * genera la consulta de edición o inserción, retorna la 
     * clave del registro o cero en caso de error
     */
    public int grabaParto(){

        // declaramos las variables
        int Resultado; 

        // si está insertando
        if (this.Id == 0){
            Resultado = this.nuevoParto();
        } else {
            Resultado = this.editaParto();
        }

        // retornamos
        return Resultado;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return int clave del nuevo registro
     * Método que genera la consulta de inserción y retorna la 
     * clave del nuevo registro o cero en caso de error
     */
    private int nuevoParto(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "INSERT INTO vertical " +
                   "       (protocolo, " + 
                   "        madre, " + 
                   "        sivila, " + 
                   "        reportado, " + 
                   "        parto, " + 
                   "        peso, " + 
                   "        prematuro, " + 
                   "        institucion, " + 
                   "        comentarios, " + 
                   "        fecha, " + 
                   "        usuario, " + 
                   "        sincronizado)" + 
                   "       VALUES " + 
                   "       (?, ?, ?, ?, ?, ?, " + 
                   "        ?, ?, ?, ?, ?, ?); ";

        // capturamos el error
        try {

            // preparamos la consulta y asignamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, this.Protocolo);
            Preparada.setInt(2, this.Madre);
            Preparada.setString(3, this.Sivila);
            Preparada.setString(4, this.Reportado);
            Preparada.setString(5, this.TipoParto);
            Preparada.setInt(6, this.Peso);
            Preparada.setString(7, this.Prematuro);
            Preparada.setInt(8, this.IdInstitucion);
            Preparada.setString(9, this.Comentarios);
            Preparada.setString(10, this.Fecha);
            Preparada.setInt(11, Seguridad.IdUsuario);
            Preparada.setString(12, "No");

            // ejecutamos
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
     * Método que ejecuta la consulta de edición y retorna la 
     * clave del registro afectado o cero en caso de error
     */
    private int editaParto(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE vertical SET " +
                   "       sivila = ?, " + 
                   "       reportado = ?, " +
                   "       parto = ?, " + 
                   "       peso = ?, " + 
                   "       prematuro = ?, " + 
                   "       institucion = ?, " + 
                   "       comentarios = ?, " + 
                   "       fecha = ?, " + 
                   "       usuario = ?, " + 
                   "       sincronizado = ? " + 
                   "WHERE vertical.id = ?; ";

        // capturamos el error
        try {

            // preparamos la consulta y asignamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setString(1, this.Sivila);
            Preparada.setString(2, this.Reportado);
            Preparada.setString(3, this.TipoParto);
            Preparada.setInt(4, this.Peso);
            Preparada.setString(5, this.Prematuro);
            Preparada.setInt(6, this.IdInstitucion);
            Preparada.setString(7, this.Comentarios);
            Preparada.setString(8, this.Fecha);
            Preparada.setInt(9, Seguridad.IdUsuario);
            Preparada.setString(10, "No");
            Preparada.setInt(11, this.Id);

            // ejecutamos
            Preparada.execute();

            // retornamos 
            return this.Id;

        // si hubo un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            return 0;
            
        }
                   
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param int idparto - clave del registro
     * @return boolean - resultdo de la operación
     * Método que recibe como parámetro la clave de un registro
     * y asigna los valores del mismo en las variables de clase
     * retorna el resultado de la operación
     */
    public boolean getDatosParto(int idparto){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT v_vertical.id AS id, " +
                   "       v_vertical.protocolo AS protocolo, " +
                   "       v_vertical.madre AS madre, " +
                   "       v_vertical.sivila AS sivila, " +
                   "       v_vertical.reportado AS reportado, " +
                   "       v_vertical.parto AS parto, " +
                   "       v_vertical.peso AS peso, " + 
                   "       v_vertical.prematuro AS prematuro, " + 
                   "       v_vertical.provincia AS provincia, " + 
                   "       v_vertical.codprov AS codprov, " + 
                   "       v_vertical.localidad AS localidad, " + 
                   "       v_vertical.codloc AS codloc, " +
                   "       v_vertical.institucion AS institucio, " + 
                   "       v_vertical.idinstitucion AS idinstitucion, " +
                   "       v_vertical.comentarios AS comentarios, " +
                   "       v_vertical.fecha AS fecha, " + 
                   "       v_vertical.usuario AS usuario " +
                   "FROM v_vertical " +
                   "WHERE v_vertical.id = ?; ";

        // capturamos el error
        try {

            // definimos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idparto);

            // ejecutamos la consulta y asignamos
            Resultado = Preparada.executeQuery();
            Resultado.next();
            this.Id = Resultado.getInt("id");
            this.Protocolo = Resultado.getInt("protocolo");
            this.Madre = Resultado.getInt("madre");
            this.Sivila = Resultado.getString("sivila");
            this.Reportado = Resultado.getString("reportado");
            this.TipoParto = Resultado.getString("parto");
            this.Peso = Resultado.getInt("peso");
            this.Prematuro = Resultado.getString("prematuro");
            this.CodProv = Resultado.getString("codprov");
            this.Provincia = Resultado.getString("provincia");
            this.CodLoc = Resultado.getString("codloc");
            this.Localidad = Resultado.getString("localidad");
            this.IdInstitucion = Resultado.getInt("idinstitucion");
            this.Institucion = Resultado.getString("institucion");
            this.Comentarios = Resultado.getString("comentarios");
            this.Fecha = Resultado.getString("fecha");
            this.Usuario = Resultado.getString("usuario");

            // retornamos
            return true;

        // si ocurrió un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            return false;
            
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

            // resenta el mensaje
            // presenta el error
            e.printStackTrace();			
            return 0;

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param protocolo clave del paciente vertical
     * @return idmadre clave de la madre 
     * Método que recibe como parámetro la clave de un paciente
     * y obtiene (si existe) la clave de la madre de ese paciente
     */
    public int getProtocoloMadre(int protocolo){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT vertical.madre AS idmadre " +
                   "FROM vertical " + 
                   "WHERE vertical.protocolo = ? " + 
                   "LIMIT 1; ";

        // capturamos el error
        try {

            // definimos y asignamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, protocolo);
            Resultado = Preparada.executeQuery();

            // obtenemos el registro y retornamos
            Resultado.next();
            return Resultado.getInt("idmadre");

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error y retorna
            e.printStackTrace();
            return 0;

        }

    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return resultset con los registros modificados
     * Método que retorna el vector con todos los registros 
     * modificados de datos del parto
     */
    public ResultSet getModificados(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT vertica.id AS id, " +
                   "       vertical.protocolo AS protocolo, " + 
                   "       vertical.madre AS madre, " + 
                   "       vertical.sivila AS sivila, " +
                   "       vertical.reportado AS reportado, " +
                   "       vertical.parto AS parto, " + 
                   "       vertical.peso AS peso, " +
                   "       vertical.prematuro AS prematuro, " + 
                   "       vertical.institucion AS institucion, " +
                   "       vertical.comentarios AS comentarios, " + 
                   "       vertical.fecha AS fecha, " + 
                   "       vertical.usuario AS usuario " +
                   "FROM vertical " + 
                   "WHERE vertical.sincronizado = 'No'; ";

        // capturamos el error
        try {

            // asignamos y ejecutamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Resultado = Preparada.executeQuery();

            // retornamos
            return Resultado;

        // si ocurrió un error
        } catch (SQLException e){

            // imprimos el error
            e.printStackTrace();
            return Resultado;

        }
        
    }

}

