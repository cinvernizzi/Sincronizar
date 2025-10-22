/**
 * 
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * @date 12/09/2025
 * @Projecto: UploadSitracha
 * @Copyright DsGestion <dsgestion.site>
 * @Licence: GPL
 * Clase que extiende el jcombobox para poder asignar los 
 * registros con la combinación clave - valor 
 * 
 */

// definición del paquete
package dbApi;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 */
public class comboClave {
 
    // definimos las variables
    private String nombre;             // valor que muestra en el combo
    private int clave;                 // clave que retorna el item
    private String codigo;             // clave alfabética que retorna el item

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param clave - entero con la clave del elemento
     * @param nombre - string con la cadena a presentar
     * Constructor de la clase, recibe como parámetros el
     * par clave - valor
     */
    public comboClave(int clave, String nombre){

        // los asignamos a las variables de clase
        this.nombre = nombre;
        this.clave = clave;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param codigo - cadena con la clave
     * @param nombre - string con la cadena a presentar
     * Constructor alternativo cuando necesitamos un combo
     * que retorne una clave alfabética (como en las localidades)
     */
    public comboClave(String codigo, String nombre){

        // los asignamos a las variables de clase
        this.nombre = nombre;
        this.codigo = codigo;

    }

    // este método retorna el valor del texto
    public String getNombre(){
        return this.nombre;
    }

    // este método asigna el valor del texto
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    // metodo que retorna el valor de la clave
    public int getClave(){
        return this.clave;
    }

    // metodo que asigna el valor de la clave
    public void setClave(int clave){
        this.clave = clave;
    }

    // método que retorna el valor del código
    public String getCodigo(){
        return this.codigo;
    }

    // método que asigna el valor del código
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    // este método va a sobrecargar el del combo
    // para retornar el valor
    @Override
    public String toString(){
        return this.nombre;
    }

    // este método sobreescribe el constructor del combo
    // lo usamos para fijar un valor ya que como tenemos
    // objetos, el método setselecteditem usa el hast
    // y el equals para determinar si corresponde
    // seleccionarlos
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clave;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    // sobreescribimos el método del objeto
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
           return false;
        if (getClass() != obj.getClass())
            return false;
        comboClave other = (comboClave) obj;
        if (clave != other.clave)
            return false;
        if (nombre == null) {
           if (other.nombre != null)
               return false;
            } else if (!nombre.equals(other.nombre))
                return false;
        return true;
            
    }
    
}
