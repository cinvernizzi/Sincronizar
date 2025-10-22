/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 10/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que controla las operaciones sobre la tabla de 
 * enfermedades declaradas del paciente
 * 
 */

// definimos el paquete
package dolencias;

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
public class dolencias {

    // declaración de variables
    private final Connection Cursor;   // puntero a la base
    private int Id;                    // clave del registro
    private int Protocolo;             // clave del paciente
    private int IdEnfermedad;          // clave de la enfermedad
    private String Enfermedad;         // nombre de la enfermedad
    private String Fecha;              // fecha de la enfermedad
    private String FechaAlta;          // fecha de alta del registro
    private String Usuario;            // nombre del usuario

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos las variables y 
     * definimos la conexión con la base
     */
    public dolencias(){

        // instanciamos la conexión 
        dbLite Enlace = new dbLite();
        this.Cursor = Enlace.getEnlace();

        // instanciamos las variables
        this.Id = 0;
        this.Protocolo = 0;
        this.IdEnfermedad = 0;
        this.Enfermedad = "";
        this.Fecha = "";
        this.FechaAlta = "";
        this.Usuario = "";

    }

    // métodos de asignación de valores
    public void setId(int id){
        this.Id = id;
    }
    public void setProtocolo(int protocolo){
        this.Protocolo = protocolo;
    }
    public void setIdEnfermedad(int idenfermedad){
        this.IdEnfermedad = idenfermedad;
    }
    public void setFecha(String fecha){
        this.Fecha = fecha;
    }
    public void setFechaAlta(String fecha){
        this.FechaAlta = fecha;
    }
    
    // métodos de retorno de valores
    public int getId(){
        return this.Id;
    }
    public int getProtocolo(){
        return this.Protocolo;
    }
    public int getIdEnfermedad(){
        return this.IdEnfermedad;
    }
    public String getEnfermedad(){
        return this.Enfermedad;
    }
    public String getFecha(){
        return this.Fecha;
    }
    public String getFechaAlta(){
        return this.FechaAlta;
    }
    public String getUsuario(){
        return this.Usuario;
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro 
     * Método que según el valor de las variables de clase 
     * ejecuta la consulta de inserción o edición, retorna la
     * clave del registro afectado o cero en caso de error
     */
    public int grabaEnfermedad(){

        // definimos las variables
        int Resultado; 

        // si está insertando
        if (this.Id == 0){
            Resultado = this.nuevaEnfermedad();
        } else {
            Resultado = this.editaEnfermedad();
        }

        // retornamos 
        return Resultado;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return int clave del nuevo registro
     * Método que ejecuta la consulta de inserción y retorna 
     * la clave del nuevo registro o cero en caso de error
     */
    private int nuevaEnfermedad(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "INSERT INTO dolencias " +
                   "       (protocolo, " + 
                   "        enfermedad, " + 
                   "        fecha, " + 
                   "        fecha_alta, " +
                   "        usuario, " + 
                   "        sincronizado) " +
                   "       VALUES " +
                   "       (?, ?, ?, ?, ?, ?); ";

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, this.Protocolo);
            Preparada.setInt(2, this.IdEnfermedad);
            Preparada.setString(3, this.Fecha);
            Preparada.setString(4, this.FechaAlta);
            Preparada.setInt(5, Seguridad.IdUsuario);
            Preparada.setString(6, "No");

            // ejecutamos la consulta
            Preparada.execute();

            // obtenemos la clave
            return ultimoInsertado();

        // si hubo un error
        } catch (SQLException e){

            // resenta el mensaje
            e.printStackTrace();			
            return 0;
            
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro afectado
     * Método que ejecuta la consulta de edición y retorna la 
     * clave del registro afectado o cero en caso de error
     */
    private int editaEnfermedad(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE dolencias SET " +
                   "       enfermedad = ?, " +
                   "       fecha = ?, " +
                   "       fecha_alta = ? " + 
                   "       usuario = ? " + 
                   "       sincronizado = ? " +
                   "WHERE dolencias.id = ?; ";

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, this.IdEnfermedad);
            Preparada.setString(2, this.Fecha);
            Preparada.setString(3, this.FechaAlta);
            Preparada.setInt(4, Seguridad.IdUsuario);
            Preparada.setString(5, "No");
            Preparada.setInt(6, this.Id);

            // ejecutamos la consulta
            Preparada.execute();

            // retornamos
            return this.Id;

        // si hubo un error
        } catch (SQLException e){
            
            // presenta el mensaje
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

            // resenta el mensaje
            e.printStackTrace();			
            return 0;

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idpaciente - clave del paciente 
     * @return ResultSet con los registros
     * Método que recibe como parámetro la clave de un paciente
     * y retorna el vector con todas las enfermedades de ese 
     * paciente
     */
    public ResultSet nominaEnfermedades(int idpaciente){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT v_dolencias.id AS id, " +
                   "       v_dolencias.protocolo AS protocolo, " +
                   "       v_dolencias.enfermedad AS enfermedad, " +
                   "       v_dolencias.fecha AS fecha, " +
                   "       v_dolencias.usuario AS usuario " +
                   "FROM v_dolencias " +
                   "WHERE v_dolencias.protocolo = ?; ";

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
     * @param idenfermedad clave del registro
     * @return boolean resultado de la operación 
     * Método que recibe como parámetro la clave de un registro
     * y asigna los valores del mismo a las variables de clase, 
     * retorna el resultado de la operación 
     */
    public boolean getDatosEnfermedad(int idenfermedad){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT v_dolencias.id AS id, " +
                   "       v_dolencias.protocolo AS protocolo, " +
                   "       v_dolencias.idenfermedad AS idenfermedad, " +
                   "       v_dolencias.enfermedad AS enfermedad, " +
                   "       v_dolencias.fecha AS fecha, " +
                   "       v_dolencias.fecha_alta AS fechaalta, " +
                   "       v_dolencias.usuario AS usuario " +
                   "FROM v_dolencias " +
                   "WHERE v_dolencias.protocolo = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idenfermedad);

            // ejecutamos la consulta
            Resultado = Preparada.executeQuery();

            // asignamos en la clase
            Resultado.next();
            this.Id = Resultado.getInt("id");
            this.Protocolo = Resultado.getInt("protocolo");
            this.IdEnfermedad = Resultado.getInt("idenfermedad");
            this.Enfermedad = Resultado.getString("enfermedad");
            this.Fecha = Resultado.getString("fecha");
            this.FechaAlta = Resultado.getString("fechaalta");
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
     * @param idenfermedad clave del registro
     * @return boolean resultado de la operación 
     * Método que recibe como parámetro la clave de un registro
     * y ejecuta la consulta de eliminación, retorna el 
     * resultado de la operación
     */
    public boolean borraEnfermedad(int idenfermedad){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "DELETE FROM dolencias " + 
                   "WHERE dolencias.id = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idenfermedad);

            // ejecutamos la consulta
            Preparada.execute();

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
     * @param idenfermedad - clave de la tabla de enfermedades
     * @param idpaciente - clave del paciente 
     * @param fecha - fecha de la enfermedad
     * @return boolean verdadero si puede insertar
     * Método llamado antes de insertar un registro que recibe 
     * como parámetros la clave de la enfermedad, del paciente
     * y la fecha de la enfermedad y verifica que no se encuentre
     * declarada (así evitamos los registros duplicados), retorna
     * verdadero si puede insertar
     */
    public boolean validaEnfermedad(int idenfermedad, 
                                    int idpaciente, 
                                    String fecha){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado;

        // componemos la consulta
        Consulta = "SELECT COUNT(dolencias.id) AS registros " +
                   "FROM dolencias " +
                   "WHERE dolencias.enfermedad = ? AND " +
                   "      dolencias.protocolo = ? AND " +
                   "      dolencias.fecha = ?; ";

        // capturamos el error
        try {

            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idenfermedad);
            Preparada.setInt(2, idpaciente);
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
     * modificados de enfermedades
     */
    public ResultSet getModificados(){

        // definimos las variables
        String Consulta;
        PreparedStatement Preparada;
        ResultSet Resultado = null;

        // componemos la consulta
        Consulta = "SELECT dolencias.id AS id, " + 
                   "       dolencias.protocolo AS protocolo, " +
                   "       dolencias.enfermedad AS enfermedad, " + 
                   "       dolencias.fecha AS fecha, " + 
                   "       dolencias.fecha_alta AS fecha_alta, " + 
                   "       dolencias.usuario AS usuario " + 
                   "FROM dolencias " +
                   "WHERE dolencias.sincronizado = 'No'; ";

        // capturamos el error
        try {

            // preparamos y ejecutamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Resultado = Preparada.executeQuery();

            // retornamos
            return Resultado;

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el mensaje
            e.printStackTrace();
            return Resultado;
            
        }

    }

}
