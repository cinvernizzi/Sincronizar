/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 11/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que en el constructor abre la conexión con la 
 * base de datos mariadb del servidor leyendo el archivo 
 * de configuración y luego provee los métodos para 
 * interactuar con ella
 * 
 */

// definición del paquete
package dbApi;

// importamos las librerías
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class mariaDb {
    
    // definición de variables
   protected String Servidor;
   protected String Contrasenia;
   protected String Usuario;
   protected Connection Link; 
 
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, leemos el archivo de configuración
     * y establecemos la conexión con el servidor
     */
    public mariaDb(){
        
        // leemos el archivo de configuración
        this.leerConfiguracion();
        
        // nos conectamos a la base de datos
        this.conectarServidor();
        
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el constructor que lee los valores
     * del archivo de configuración
     */
    private void leerConfiguracion(){
        
       // definimos las variables
       Properties Configuracion = new Properties();
       InputStream Entrada;

       // si existe el archivo de configuracion
       File Archivo = new File("Sitracha.properties");
       if (Archivo.exists()){

           // tratamos de leer el archivo
           try {

               // abrimos el archivo
               Entrada = new FileInputStream ("Sitracha.properties");

               // leemos el archivo de propiedades
               Configuracion.load(Entrada);

               // si lo pudo abrir leemos los valores
               this.Servidor = Configuracion.getProperty("Servidor");
               this.Usuario = Configuracion.getProperty("Usuario");
               this.Contrasenia = Configuracion.getProperty("Password");
               
               // cerramos el archivo
               Entrada.close();
               
           // si ocurrió un error
           } catch (IOException ex) {

                // presenta el error
                ex.printStackTrace();			

           }

       // si no encontró el archivo
       } else {
           
           // presenta el mensaje
           JOptionPane.showMessageDialog(null, "No encuentro el archivo");
           
       }
             
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return puntero al servidor
     * Método llamado luego de obtener las variables de 
     * conexión que establece la conexión con el servidor
     */
    private void conectarServidor(){
     
        // intentamos conectar
        try {
            
            // registramos el driver
            Class.forName("org.mariadb.jdbc.Driver");            
            
            // establecemos la conexión
            this.Link = DriverManager.getConnection(this.Servidor, this.Usuario, this.Contrasenia);
            
        // si no pudo conectar
        } catch (SQLException | ClassNotFoundException ex) {

            // presenta el error
            ex.printStackTrace();			

        } 
                
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return conexión, puntero a la base de datos
     * Método que retorna el puntero a la base de datos
     */
    public Connection getEnlace(){

        // retornamos el puntero
        return this.Link;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que simplemente cierra la conexióin
     */
    public void cerrarEnlace(){

        // capturamos el error
        try {

            // liberamos el puntero
            this.Link.close();

        // si no pudo
        } catch (SQLException e){

            // presenta el mensaje
            e.printStackTrace();
            
        }

    }

    /**
     * @author Lic. Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return IdRegistro entero con la clave del registro insertado
     * Método que retorna la id del último registro insertado
     */
    public int UltimoInsertado(){
        
        // declaración de variables
        int IdRegistro = 0;
        ResultSet Resultado;
        Statement SQL;
    
        try {

            // ejecutamos la consulta         
            SQL = this.Link.createStatement();
            Resultado = SQL.executeQuery("SELECT LAST_INSERT_ID() AS idregistro;");
            
            // obtenemos el registro
            Resultado.next();
            IdRegistro = Resultado.getInt("idregistro");
            
            // cerramos el resultset
            Resultado.close();

        // si ocurrió un error
        } catch (SQLException ex){

            // presenta el error
            ex.printStackTrace();			
            return 0;
            
        }
        
        // retornamos la id del registro
        return IdRegistro;
        
    }
     
}
