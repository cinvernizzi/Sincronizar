/**
 * 
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 12/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que extiende el jcombobox para cargar automáticamente
 * los datos de la tabla de órganos de transplante, provee también 
 * los métodos para leer los valores seleccionados
 * 
 */

// definimos el paquete
package transplantes;

// importamos las librerías
import dbApi.dbLite;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JComboBox;
import dbApi.comboClave;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Declaración de la clase
 */
public class organos extends JComboBox<Object>{

    // definimos las variables
    private final Connection Cursor;
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instancia la conexión con la 
     * base de datos local y lanza la carga del combo
     */
    public organos(){

        // llamamos al constructor del padre
        super();

        // instanciamos la conexión 
        dbLite Enlace = new dbLite();
        this.Cursor = Enlace.getEnlace();
        
        // cargamos los elementos
        this.cargaDiccionario();
        
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado desde el constructor que obtiene el 
     * vector con los elementos a agregar
     */
    private void cargaDiccionario(){
        
        // definimos las variables
        String Consulta;
        Statement Estado;
        ResultSet Resultado;
        
        // componemos la consulta
        Consulta = "SELECT organos.id AS id, " +
                   "       organos.organo AS organo " +
                   "FROM organos " +
                   "ORDER BY organos.organo; ";
        
        // capturamos el error
        try {
        
            // asignamos la consulta
            Estado = this.Cursor.createStatement();
            
            // ejecutamos la consulta
            Resultado = Estado.executeQuery(Consulta);
     
            // cargamos el combo
            this.cargaCombo(Resultado);
            
        // si ocurrió un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            
        }
             
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param Resultado el vector con los registros
     * Método llamado luego de cargar el diccionario que recibe
     * el resultset con los registros y los agrega al combo
     */
    private void cargaCombo(ResultSet Resultado){
        
        // recorremos el vector
        try {
            
            // limpiamos los elementos
            this.removeAllItems();
            
            // agregamos el primer elemento 
            this.addItem(new comboClave(0,""));
           
            // recorremos el vector
            while (Resultado.next()){

                // agregamos el elemento
                this.addItem(new comboClave(Resultado.getInt("id"),Resultado.getString("organo")));
                
            }
            
        // si ocurrió un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            
        }
        
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return int clave del registro seleccionado
     * Método que retorna el valor de la clave del registro 
     * seleccionado del combo
     */
    public int getId(){
        
        // obtenemos la clave seleccionada
        comboClave item = (comboClave) this.getSelectedItem();
        return item.getClave();
                
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return String texto del registro seleccionado
     * Método que retorna el texto del registro seleccionado
     * del combo
     */
    public String getTexto(){
    
        // obtenemos el texto 
        comboClave item = (comboClave) this.getSelectedItem();
        return item.getNombre();
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idregistro entero con la clave del registro
     * @param contenido texto con la descripción del registro
     * Método utilizado al actualizar el valor del select 
     * que recibe como parámetros la clave del registro y el 
     * valor del mismo, instancia la clase para obtener el 
     * index del control y fija el valor del mismo
     */
    public void setValor(int idregistro, String contenido){

        // instanciamos el combo
        comboClave item = new comboClave(idregistro, contenido);
        this.getModel().setSelectedItem(item);

    }
    
}
