/*

    Nombre: Filtrar
    Fecha: 18/02/2020
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: Clínica
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
    Comentarios: Clase que recibe como parámetros en el constructor
                 un objeto jtable y un texto y crea el autofiltro
                 para esa tabla basado en el texto

*/

// declaramos el paquete
package dbApi;

// importamos las librerías
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

// definición de la clase
public class Filtrar{

    // constructor de la clase
    public Filtrar(String Consulta, JTable tablaBuscar){

        // instanciamos el tablemodel y aplicamos el filtro
        DefaultTableModel dm = (DefaultTableModel) tablaBuscar.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        tablaBuscar.setRowSorter(tr);
		tr.setRowFilter(RowFilter.regexFilter("(?i)" + Consulta));

    }
    
}