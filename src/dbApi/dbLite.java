/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 10/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que en el constructor se abre la conexión con la 
 * base de datos local y luego provee los métodos para 
 * interactuar con ella
 * 
 */

// definición del paquete
package dbApi;

// importamos las librerías
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbLite {

    // Atributo de la clase: almacena la conexión
    private static Connection enlace;

    // Método para abrir la conexión
    public dbLite() {
        
        // verificamos que no exista una conexión 
        if (dbLite.enlace == null){

            // Ruta de la base (relativa a la carpeta de ejecución)
            String url = "jdbc:sqlite:sitracha.db";

            try {

                // registramos el driver
                Class.forName("org.sqlite.JDBC");
                
                // Creamos la conexión y la guardamos en el atributo
                dbLite.enlace = DriverManager.getConnection(url);
                
            // si ocurrió un error
            } catch (SQLException e) {
                
                // presenta el error
                e.printStackTrace();			

                
            // si no pudo registrar el driver
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();			

            }

        }

    }

    // Getter para obtener la conexión
    public Connection getEnlace() {
        return dbLite.enlace;                               
    }

    // Método opcional para cerrar la conexión
    public void cerrar() {
        
        // capturamos el error
        try {
            
            dbLite.enlace.close();
            
        // si ocurrió un error
        } catch (SQLException e) {
            
            // presenta el error
            e.printStackTrace();			
            
        }
        
    }
    
}
