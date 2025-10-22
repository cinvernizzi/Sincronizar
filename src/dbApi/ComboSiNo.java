/*

    Nombre: ComboSiNo
    Fecha: 03/02/2020
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: diagnostico
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
    Comentarios: Clase que recibe como parámetro en el constructor 
                 un objeto combo box y agrega los elementos si / no

 */

// definición del paquete
package dbApi;

// importamos las librerías
import javax.swing.JComboBox;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Declaración de la clase
 */
public class ComboSiNo extends JComboBox<Object>{
	
    // constructor de la clase
    public ComboSiNo() {
		
        // limpiamos el combo
        this.removeAllItems();
		
	    // agregamos los elementos al combo
        this.addItem("");
    	this.addItem("Si");
	    this.addItem("No");
	    this.addItem("No Sabe");
		
    }

}
