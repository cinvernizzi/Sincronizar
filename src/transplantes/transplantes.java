/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 17/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que controla las operaciones sobre la tabla de 
 * transplantes recibidos por el paciente
 * 
 */

// definimos el paquete
package transplantes;

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
public class transplantes {

    // declaramos las variables
    private final Connection Cursor;           // puntero a la base de datos
    private int Id;                            // clave del registro
    private int Protocolo;                     // clave del paciente
    private int IdOrgano;                      // clave del órgano
    private String Organo;                     // nombre del órgano
    private String Positivo;                   // si el órgano es positivo
    private String FechaTransplante;           // la fecha del transplante
    private String Fecha;                      // la fecha de alta
    private String Usuario;                    // el nombre de usuario

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos la conexión e 
     * inicializamos las variables
     */
    public transplantes(){

        // instanciamos la conexión 
        dbLite Enlace = new dbLite();
        this.Cursor = Enlace.getEnlace();

        // iniciamos las variables
        this.Id = 0;
        this.Protocolo = 0;
        this.IdOrgano = 0;
        this.Organo = "";
        this.Positivo = "";
        this.FechaTransplante = "";
        this.Fecha = "";
        this.Usuario = "";

    }

    // métodos de asignación de valores
    public void setId(int id){
        this.Id = id;
    }
    public void setProtocolo(int protocolo) {
        this.Protocolo = protocolo;
    }
    public void setIdOrgano(int idorgano){
        this.IdOrgano = idorgano;
    }
    public void setPositivo(String positivo){
        this.Positivo = positivo;
    }
    public void setFechaTransplante(String fecha){
        this.FechaTransplante = fecha;
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
    public int getIdOrgano(){
        return this.IdOrgano;
    }
    public String getOrgano(){
        return this.Organo;
    }
    public String getPositivo(){
        return this.Positivo;
    }
    public String getFechaTransplante(){
        return this.FechaTransplante;
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
     * genera la consulta de inserción o edición, retorna 
     * la clave del registro afectado o cero en caso de error
     */
    public int grabaTransplante(){

        // declaramos las variables
        int Resultado;

        // si está insertando
        if (this.Id == 0){
            Resultado = this.nuevoTransplante();
        } else {
            Resultado = this.editaTransplante();
        }

        // retornamos
        return Resultado;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del nuevo registro
     * Método que ejecuta la consulta de inserción y retorna
     * la clave del nuevo registro o cero en caso de error
     */
    private int nuevoTransplante(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        int lastId = 0;

        // componemos la consulta
        Consulta = "INSERT INTO transplantes " +
                   "       (protocolo, " +
                   "        organo, " +
                   "        positivo, " +
                   "        fecha_transplante, " +
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
            Preparada.setInt(2, this.IdOrgano);
            Preparada.setString(3, this.Positivo);
            Preparada.setString(4, this.FechaTransplante);
            Preparada.setString(5, this.Fecha);
            Preparada.setInt(6, Seguridad.IdUsuario);
            Preparada.setString(7, "No");

            // ejecutamos la consulta
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
     * @return entero con la clave del registro afectado
     * Método que ejecuta la consulta de edición del registro
     * y retorna la clave del registro afectado o cero en 
     * caso de error
     */
    private int editaTransplante(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE transplantes SET " +
                   "       organo = ?, " +
                   "       positivo = ?, " +
                   "       fecha_transplante = ?, " +
                   "       fecha = ?, " +
                   "       usuario = ?, " +
                   "       sincronizado = ? " +
                   "WHERE transplantes.id = ?; ";

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, this.IdOrgano);
            Preparada.setString(2, this.Positivo);
            Preparada.setString(3, this.FechaTransplante);
            Preparada.setString(4, this.Fecha);
            Preparada.setInt(5, Seguridad.IdUsuario);
            Preparada.setString(6, "No");
            Preparada.setInt(7, this.Id);

            // ejecutamos la consulta
            Preparada.execute();

            // retornamos 
            return this.Id;

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
            e.printStackTrace();			
            return 0;

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idpaciente clave del paciente (protocolo)
     * @return resultset con la nómina de transplantes
     * Método que recibe como parámetro la clave de un paciente
     * y retorna el vector con la nómina de transplantes 
     * recibidos de ese paciente
     */
    public ResultSet nominaTransplantes(int idpaciente){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT v_transplantes.id AS id, " +
                   "       v_transplantes.organo AS organo, " +
                   "       v_transplantes.positivo AS positivo, " +
                   "       v_transplantes.fecha_transplante AS fechatransplante, " +
                   "       v_transplantes.fecha AS fecha, " +
                   "       v_transplantes.usuario AS usuario " +
                   "FROM v_transplantes " +
                   "WHERE v_transplantes.protocolo = ?; ";

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idpaciente);

            // obtenemos el registro y retornamos
            Resultado = Preparada.executeQuery();

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
            e.printStackTrace();			

        }

        // retornamos
        return Resultado;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idtransplante - clave del registro
     * @return boolean resultado de la operación
     * Método que recibe como parámetro la clave de un registro
     * y asigna los valores del mismo a las variables de clase
     * retorna el resultado de la operación 
     */
    public boolean getDatosTransplante(int idtransplante){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT v_transplantes.id AS id, " +
                   "       v_transplantes.protocolo AS protocolo, " +
                   "       v_transplantes.organo AS organo, " +
                   "       v_transplantes.positivo AS positivo, " +
                   "       v_transplantes.idorgano AS idorgano, " +
                   "       v_transplantes.fecha_transplante AS fechatransplante, " +
                   "       v_transplantes.fecha AS fecha, " +
                   "       v_transplantes.usuario AS usuario " +
                   "FROM v_transplantes " +
                   "WHERE v_transplantes.id = ?; ";

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idtransplante);

            // obtenemos el registro
            Resultado = Preparada.executeQuery();
            Resultado.next();

            // asignamos en la clase
            this.Id = Resultado.getInt("id");
            this.Protocolo = Resultado.getInt("protocolo");
            this.IdOrgano = Resultado.getInt("idorgano");
            this.Organo = Resultado.getString("organo");
            this.Positivo = Resultado.getString("positivo");
            this.FechaTransplante = Resultado.getString("fecha_transplante");
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
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idtransplante - clave del registro
     * @return boolean resultado de la operación 
     * Método que recibe como parámetro la clave de un registro
     * y ejecuta la consulta de eliminación, retorna el resultado
     * de la operación
     */
    public boolean borraTransplante(int idtransplante){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "DELETE FROM transplantes " + 
                   "WHERE transplantes.id = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idtransplante);

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
     * @param idpaciente clave del paciente
     * @param idorgano clave del órgano
     * @param fecha fecha del transplante
     * @return boolean si puede insertar
     * Método llamado antes de insertar un registro que recibe
     * como parámetro la clave del paciente, la clave del 
     * órgano y la fecha y verifica que el registro no esté 
     * repetido, retorna verdadero si puede insertar
     */
    public boolean validaTransplante(int idpaciente, 
                                     int idorgano, 
                                     String fecha){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT COUNT(transplantes.id) AS registros " +
                   "FROM transplantes " +
                   "WHERE transplantes.protocolo = ? AND " +
                   "      transplantes.organo = ? AND " +
                   "      transplantes.fecha_transplante = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idpaciente);
            Preparada.setInt(2, idorgano);
            Preparada.setString(3, fecha);

            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // obtenemos el registro
            Resultado.next();
            return Resultado.getInt("registros") == 0;
            
        // si hubo un error
        } catch (SQLException e){
            
            // resenta el mensaje
            e.printStackTrace();			
            return false;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return resultset con los registros modificados
     * Método que retorna el vector con todos los registros 
     * modificados de transplantes
     */
    public ResultSet getModificados(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT transplantes.id AS id, " +
                   "       transplantes.protocolo AS protocolo, " + 
                   "       transplantes.organo AS organo, " + 
                   "       transplantes.positivo AS positivo, " +
                   "       transplantes.fecha_transplante AS fecha_transplante, " + 
                   "       transplantes.fecha AS fecha, " + 
                   "       transplantes.usuario AS usuario " +
                   "FROM transplantes " +
                   "WHERE transplantes.sincronizado = 'No'; ";

        // capturamos el error
        try {

            // asignamos la consulta y ejecutamos
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
