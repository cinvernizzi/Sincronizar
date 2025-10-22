/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 11/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que define las fuentes que utilizaremos en el
 * el sistema, leemos el archivo ttf que se encuentra en 
 * la carpeta recursos para estar seguros que se mostrará
 * correctamente en cualquier estación
 * 
 */

// definición del paquete
package dbApi;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class fuentes {
    
    // definimos las variables
    public Font Normal; 
    public Font Negrita;
    public Font Titulo;
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, definimos las fuentes
     */
    public fuentes(){
        
        // capturamos el error
        try {
            
            // leemos el input stream como recurso para incluir las fuentes 
            // en el archivo jar
            InputStream inputStream = getClass().getResourceAsStream("/recursos/arial.ttf");            
            
            // creamos la fuente a partir del archivo
            Font Fuente = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            
            // establecemos las variantes
            this.Normal = Fuente.deriveFont(Font.PLAIN, 14f);            
            this.Negrita = Fuente.deriveFont(Font.BOLD, 14f);
            this.Titulo = Fuente.deriveFont(Font.PLAIN, 16f);
            
        // si ocurrió un error
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }   
    
    }
    
}
