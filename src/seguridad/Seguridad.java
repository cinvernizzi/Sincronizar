/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 11/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase llamada al ingresar el usuario que verifica las 
 * credenciales del mismo 
 * 
 */

// definición del paquete
package seguridad;

// importamos las librerías 
import dbApi.dbLite;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class Seguridad {
    
    // declaramos las variables, las declaramos como static 
    // para luego poder acceder sin instanciar la clase
    private final Connection Cursor;        // puntero a la base de datos
    public static String Usuario;           // nombre del usuario 
    public static int IdUsuario;            // clave del usuario
    public static String Nombre;            // nombre completo 
     
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, establece la conexión con la 
     * base de datos
     */
    public Seguridad(){
        
        // instanciamos la conexión 
        dbLite Enlace = new dbLite();
        this.Cursor = Enlace.getEnlace();
        
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param usuario nombre del usuario
     * @param contrasenia, contraseña del usuario
     * @return {boolean} resultado de la acreditación
     * Método que recibe como parámetros el nombre de usuario 
     * y la contraseña y verifica las credenciales, en caso
     * de ser correctas, asigna en las variables de clase
     * retorna verdadero si acreditó en caso contrario 
     * retorna falso
     */
    public boolean validaAcceso(String usuario, String contrasenia){
        
        // definimos las variables
        String Consulta; 
        PreparedStatement Estado;
        ResultSet Resultado;
                
        // obtenemos el hash md5 de la contraseña
        String encriptado = this.getContrasenia(contrasenia);

        // componemos la consulta convertimos el hash de la 
        // base a mayúsculas porque la rutina de java la 
        // retorna en mayúsculas, hay que verificar que 
        // ambos usan la misma página de códigos 
        Consulta = "SELECT COUNT(usuarios.id) AS registros, " +
                   "       usuarios.id AS id, " +
                   "       usuarios.nombre AS nombre, " +
                   "       usuarios.usuario AS usuario " +
                   "FROM usuarios " +
                   "WHERE usuarios.usuario = ? AND " +
                   "      UPPER(usuarios.password) = ?; ";

        // capturamos el error
        try {
            
            // asignamos la consulta y los parámetros
            Estado = this.Cursor.prepareStatement(Consulta);
            Estado.setString(1, usuario);
            Estado.setString(2, encriptado);
            
            // ejecutamos la consulta
            Resultado = Estado.executeQuery();

            // dirige al primer registro
            Resultado.next();
            
            // si no hubo registros
            if (Resultado.getInt("registros") == 0){
                return false;
            } else {
            
                // asignamos en la clase 
                Seguridad.IdUsuario = Resultado.getInt("id");
                Seguridad.Nombre = Resultado.getString("nombre");
                Seguridad.Usuario = Resultado.getString("usuario");
                return true;
                
            }
            
        // si hubo un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            return false;
            
        }
                
    }
 
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param string cadena a encriptar
     * @return cadena encriptada
     * Método que recibe como parámetro una cadena y retorna 
     * el hash md5 de la misma
     */
    private String getContrasenia(String contrasenia){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(contrasenia.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02X", b)); // mayúsculas
            }
            return hexString.toString();

        // si no encontró el algoritmo
        } catch (NoSuchAlgorithmException ex) {

            // presenta el error
            ex.printStackTrace();	
            return "";		

        }

    }
    
}
