/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 17/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que controla las operaciones sobre la tabla de 
 * transfusiones recibidas por el paciente
 * 
 */

// declaración del paquete
package transfusiones;

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
public class transfusiones {

    // declaramos las variables
    private final Connection Cursor;           // puntero a la base de datos
    private int Id;                            // clave del registro
    private int Protocolo;                     // clave del paciente
    private String FechaTransfusion;           // fecha de la transfusión
    private String CodLoc;                     // código indec de la localidad
    private String Localidad;                  // nombre de la localidad
    private String CodProv;                    // códigoi indec de la provincia
    private String Provincia;                  // nombre de la provincia
    private int IdInstitucion;                 // clave de la institución
    private String Institucion;                // nombre de la institución
    private String Motivo;                     // descripción del motivo
    private String Fecha;                      // fecha de alta
    private String Usuario;                    // nombre del usuario

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos la conexión e 
     * inicializamos las variables
     */
    public transfusiones(){

        // instanciamos la conexión 
        dbLite Enlace = new dbLite();
        this.Cursor = Enlace.getEnlace();

        // inicializamos las variables
        this.Id = 0;
        this.Protocolo = 0;
        this.FechaTransfusion = "";
        this.CodLoc = "";
        this.Localidad = "";
        this.CodProv = "";
        this.Provincia = "";
        this.IdInstitucion = 0;
        this.Institucion = "";
        this.Motivo = "";
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
    public void setFechaTransfusion(String fecha){
        this.FechaTransfusion = fecha;
    }
    public void setIdInstitucion(int idinstitucion){
        this.IdInstitucion = idinstitucion;
    }
    public void setMotivo(String motivo){
        this.Motivo = motivo;
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
    public String getFechaTransfusion(){
        return this.FechaTransfusion;
    }
    public String getCodLoc(){
        return this.CodLoc;
    }
    public String getLocalidad(){
        return this.Localidad;
    }
    public String getCodProv(){
        return this.CodProv;
    }
    public String getProvincia(){
        return this.Provincia;
    }
    public int getIdInstitucion(){
        return this.IdInstitucion;
    }
    public String getInstitucion(){
        return this.Institucion;
    }
    public String getMotivo(){
        return this.Motivo;
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
     * Método que ejecuta la consulta de actualización o 
     * inserción según corresponda, retorna la clave del 
     * registro o cero en caso de error
     */
    public int grabaTransfusion(){

        // declaramos las variables
        int Resultado;

        // si está insertando
        if (this.Id == 0){
            Resultado = this.nuevaTransfusion();
        } else {
            Resultado = this.editaTransfusion();
        }

        // retornamos
        return Resultado;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del nuevo registro
     * Método que ejecuta la consulta de inserción, retorna la 
     * clave del nuevo registro o cero en caso de error
     */
    private int nuevaTransfusion(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        int lastId = 0;

        // componemos la consulta
        Consulta = "INSERT INTO transfusiones " +
                   "       (protocolo, " +
                   "        fecha_transfusion, " + 
                   "        institucion, " + 
                   "        motivo, " +
                   "        fecha, " + 
                   "        usuario, " + 
                   "        sincronizado) " + 
                   "       VALUES " + 
                   "       (?, ?, ?, ?, ?, ?, ?); ";

        // capturamos el error
        try {

            // preparamos la consulta y asignamos los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, this.Protocolo);
            Preparada.setString(2, this.FechaTransfusion);
            Preparada.setInt(3, this.IdInstitucion);
            Preparada.setString(4, this.Motivo);
            Preparada.setString(5, this.Fecha);
            Preparada.setInt(6, Seguridad.IdUsuario);
            Preparada.setString(7, "No");

            // ejecutamos
            Preparada.execute();

            // obtenemos la clave
            ResultSet generatedKeys = Preparada.getGeneratedKeys(); 
            if (generatedKeys.next()) {
                lastId = generatedKeys.getInt(1);
            }

            // retornamos
            return lastId;

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
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
    private int editaTransfusion(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE transfusiones SET " +
                   "       fecha_transfusion = ?, " +
                   "       institucion = ?, " +
                   "       motivo = ?, " +
                   "       fecha = ?, " +
                   "       usuario = ? , " +
                   "       sincronizado = ? " + 
                   "WHERE transfusiones.id = ?; ";

        // capturamos el error
        try {

            // preparamos y asignamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setString(1, this.FechaTransfusion);
            Preparada.setInt(2, this.IdInstitucion);
            Preparada.setString(3, this.Motivo);
            Preparada.setString(4, this.Fecha);
            Preparada.setInt(5, Seguridad.IdUsuario);
            Preparada.setString(6, "No");
            Preparada.setInt(7, this.Id);

            // ejecutamos y retornamos
            Preparada.execute();
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
     * @param idtransfusion clave del registro
     * @return boolean resultado de la operación
     * Método que recibe como parámetro la clave de un registro
     * y asigna los valores del mismo en las variables de clase
     * retorna el resultado de la operación
     */
    public boolean getDatosTransfusion(int idtransfusion){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT v_transfusiones.id AS id, " +
                   "       v_transfusiones.protocolo AS protocolo, " +
                   "       v_transfusiones.motivo AS motivo, " +
                   "       v_transfusiones.provincia AS provincia, " +
                   "       v_transfusiones.codprov AS codprov, " +
                   "       v_transfusiones.localidad AS localidad, " +
                   "       v_transfusiones.codloc AS codloc, " +
                   "       v_transfusiones.idinstitucion AS idinstitucion, " +
                   "       v_transfusiones.institucion AS institucion, " +
                   "       v_transfusiones.fecha_transfusion AS fechatransfusion, " +
                   "       v_transfusiones.fecha As fecha, " +
                   "       v_transfusiones.usuario AS usuario " +
                   "FROM v_dolencias " +
                   "WHERE v_dolencias.protocolo = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idtransfusion);

            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // asignamos en la clase
            Resultado.next();
            this.Id = Resultado.getInt("id");
            this.Protocolo = Resultado.getInt("protocolo");
            this.FechaTransfusion = Resultado.getString("fechatransfusion");
            this.Provincia = Resultado.getString("provincia");
            this.CodProv = Resultado.getString("codprov");
            this.Localidad = Resultado.getString("localidad");
            this.CodLoc = Resultado.getString("codloc");
            this.IdInstitucion = Resultado.getInt("idinstitucion");
            this.Institucion = Resultado.getString("institucion");
            this.Motivo = Resultado.getString("motivo");
            this.Fecha = Resultado.getString("fecha");
            this.Usuario = Resultado.getString("usuario");

            // retornamos
            return true;

        // si hubo un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            return false;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idpaciente clave del paciente
     * @return resultset con los registros coincidentes
     * Método que recibe como parámetro la clave de un paciente
     * y retorna el vector con todos las transfusiones 
     * recibidas por ese paciente
     */
    public ResultSet nominaTransfusiones(int idpaciente){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT v_transfusiones.id AS id, " +
                   "       v_transfusiones.protocolo AS protocolo, " +
                   "       v_transfusiones.motivo AS motivo, " +
                   "       v_transfusiones.institucion AS institucion, " +
                   "       v_transfusiones.localidad AS localidad, " +
                   "       v_transfusiones.fecha_transfusion AS fecha, " +
                   "       v_transfusiones.usuario AS usuario " +
                   "FROM v_transfusiones " +
                   "WHERE v_transfusiones.protocolo = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idpaciente);

            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

        // si hubo un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            
        }

        // retornamos
        return Resultado;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idpaciente clave del paciente
     * @param idinstitucion clave de la institucion
     * @param fecha fecha de la transfusión
     * @return boolean si puede insertar
     * Método llamado antes de insertar un registro que verifica
     * no esté repedido
     */
    public boolean validaTransfusion(int idpaciente, 
                                     int idinstitucion, 
                                     String fecha){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT COUNT(transfusiones.id) AS registros " +
                   "FROM transfusiones " +
                   "WHERE transfusiones.protocolo = ? AND " +
                   "      transfusiones.institucion = ? AND " +
                   "      transfusiones.fecha_transfusion = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idpaciente);
            Preparada.setInt(2, idinstitucion);
            Preparada.setString(3, fecha);

            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // obtenemos el registro
            Resultado.next();
            return Resultado.getInt("registros") == 0;
            
        // si hubo un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            return false;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idtransfusion clave del registro
     * @return boolean resultado de la operación
     * Método que recibe como parámetro la clave de un registro
     * y ejecuta la consulta de eliminación, retorna el 
     * resultado de la operación
     */
    public boolean borraTransfusion(int idtransfusion){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "DELETE FROM transfusiones " + 
                   "WHERE transfusion.id = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idtransfusion);

            // ejecutamos la consulta
            Preparada.execute();

            // retornamos
            return true;
            
        // si hubo un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            return false;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return resultset con los registros modificados
     * Método que retorna el vector con todos los registros 
     * de transfusiones modificados
     */
    public ResultSet getModificados(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT transfusiones.id AS id, " +
                   "       transfusiones.protocolo AS protocolo, " + 
                   "       transfusiones.fecha_transfusion AS fecha_transfusion, " + 
                   "       transfusiones.institucion AS institucion, " + 
                   "       transfusiones.motivo AS motivo, " + 
                   "       transfusiones.fecha AS fecha, " + 
                   "       transfusiones.usuario AS usuario " + 
                   "FROM transfusiones " + 
                   "WHERE transfusiones.sincronizado = 'No'; ";

        // capturamos el error
        try {

            // preparamos y ejecutamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Resultado = Preparada.executeQuery();

            // retornamos 
            return Resultado;

        // si hubo un error
        } catch (SQLException e){

            // presenta el mensaje
            e.printStackTrace();
            return Resultado;

        }
        
    }

}
