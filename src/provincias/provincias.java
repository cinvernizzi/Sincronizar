/**
 * 
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 12/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que extiende el jcombobox para cargar automáticamente
 * los datos de la tabla de provincias, provee también 
 * los métodos actualizar y leer los valores seleccionados
 * 
 */

// definimos el paquete
package provincias;

// importamos las librerías
import dbApi.dbLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import dbApi.comboClave;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Declaración de la clase
 */
public class provincias extends JComboBox<Object>{

    // definimos las variables
    private final Connection Cursor;
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instancia la conexión con la 
     * base de datos local y lanza la carga del combo
     */
    public provincias(){

        // llamamos al constructor del padre
        super();

        // instanciamos la conexión 
        dbLite Enlace = new dbLite();
        this.Cursor = Enlace.getEnlace();
                
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idpais entero con la clave del país
     * Método llamado desde el constructor que obtiene el 
     * vector con los elementos a agregar
     */
    public void cargaDiccionario(int idpais){
        
        // definimos las variables
        String Consulta;
        PreparedStatement Estado;
        ResultSet Resultado;
        
        // componemos la consulta
        Consulta = "SELECT provincias.cod_prov AS codprov, " +
                   "       provincias.nom_prov AS provincia " +
                   "FROM provincias " +
                   "WHERE provincias.pais = ? " +
                   "ORDER BY provincias.nom_prov; ";
        
        // capturamos el error
        try {
        
            // asignamos la consulta
            Estado = this.Cursor.prepareStatement(Consulta);
            Estado.setInt(1, idpais);

            // ejecutamos la consulta
            Resultado = Estado.executeQuery();
     
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
            this.addItem(new comboClave("",""));
           
            // recorremos el vector
            while (Resultado.next()){

                // agregamos el elemento
                this.addItem(new comboClave(Resultado.getString("codprov"),Resultado.getString("provincia")));
                
            }
            
        // si ocurrió un error
        } catch (SQLException e){
            
            // presenta el error
            e.printStackTrace();			
            
        }
        
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return string clave indec del registro seleccionado
     * Método que retorna el valor de la clave del registro 
     * seleccionado del combo
     */
    public String getCodigo(){
        
        // obtenemos la clave seleccionada
        comboClave item = (comboClave) this.getSelectedItem();
        return item.getCodigo();
                
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
     * @param codprov clave indec del registro
     * @param contenido texto con la descripción del registro
     * Método utilizado al actualizar el valor del select 
     * que recibe como parámetros la clave del registro y el 
     * valor del mismo, instancia la clase para obtener el 
     * index del control y fija el valor del mismo
     */
    public void setValor(String codprov, String contenido){

        // instanciamos el combo
        comboClave item = new comboClave(codprov, contenido);
        this.getModel().setSelectedItem(item);

    }
    
}
