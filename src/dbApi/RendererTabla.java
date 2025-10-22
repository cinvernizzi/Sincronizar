/*

    Nombre: rendererTabla
    Fecha: 16/06/2017
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
    Comentarios: Clase extiende el default renderer de las tablas y genera una
                 etiqueta con la imagen a presentar en la tabla

 */

// declaración del paquete
package dbApi;

// importamos las librerías
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

// definición de la clase
public class RendererTabla extends DefaultTableCellRenderer {

	// agregamos el serial id
	private static final long serialVersionUID = 2428473167134935146L;

	// sobreescribimos el método por default y recibimos como parámetros
    // la table, el objeto, y las propiedades de la tabla
    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {

        // si es un label
        if (value instanceof JLabel) {

        	// crea el label
            JLabel label = (JLabel) value;
            label.setOpaque(true);
            fillColor(table, label, isSelected);
            return label;

        // si es un combo
        } else if (value instanceof JComboBox) {

        	// crea el combo
        	JComboBox<?> combo = (JComboBox<?>) value;
        	return combo;

        // en cualquier otro caso
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

    }

    // este método pinta el fondo de la celda seleccionada
    public void fillColor(JTable t, JLabel l, boolean isSelected) {
        if (isSelected) {
            l.setBackground(t.getSelectionBackground());
            l.setForeground(t.getSelectionForeground());
        } else {
            l.setBackground(t.getBackground());
            l.setForeground(t.getForeground());
        }
    }

}
