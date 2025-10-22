/*

    Nombre: FormVertical
    Fecha: 22/09/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: UploadSitracha
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Método que arma el formulario para el abm
	             de los datos del paciente vertical 

    Nota: Vamos a extender la clase de pacientes para implementar
          los métodos propios de vertical, al mismo tiempo 
          sobreescribimos algunos métodos (como el nuevo paciente)
          para adaptarlo

 */

// definición del paquete
package vertical;

// importamos las librerías
import pacientes.formbuscar;
import pacientes.formpacientes;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class formvertical extends formpacientes{

    // las variables de clase ya están definidas solo 
    // agregaremos las propias 
    private formpacientes Madre;

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param Contenedor el panel contenedor del formulario
     * @param Madre el panel con los datos de la madre
     * Constructor de la clase, recibe como parámetro el panel 
     * contenedor (el tabulador con los datos del paciente 
     * vertical)
     */
    public formvertical(JPanel Contenedor, formpacientes madre){

        // llamamos al constructor del padre
        super(Contenedor);
        
        // asignamos el formulario de la madre
        this.Madre = madre;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site|
     * Sobreescribimos el método nuevo paciente del padre
     */
    @Override
    public void nuevoPaciente(){

        // instanciamos el formulario
        new nuevo(this);
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que sobreescribe el del padre para buscar pacientes
     * que tengan relación con la tabla de datos del parto
     */
    @Override
    public void buscaPaciente() {

        // sobreescribimos la búsqueda
        // pedimos el texto a buscar
        String mensaje = "Ingrese el texto a buscar:";
        String m = JOptionPane.showInputDialog(null, mensaje);
        if(m != null){
            this.encuentraPaciente(m);
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que sobreescribe el del padre 
     */
    @Override
    public void encuentraPaciente(String texto){

        // buscamos en la base
        ResultSet Nomina = this.Pacientes.buscaVertical(texto);

        // instanciamos el formulario cargamos los registros 
        // y lo mostramos
        formbuscar Encontrados = new formbuscar(this);
        Encontrados.cargaPacientes(Nomina);
        Encontrados.setVisible(true);

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return entero con la clave del registro
     * Método público llamado por los formularios hijos que 
     * retorna la clave del paciente, en este caso 
     * sobreescribimos el de la clase heredada
     */
    @Override
    public int getProtocolo(){

        // convertirmos y retornamos
        return Integer.parseInt(this.tId.getText());
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param idmadre - protocolo de la madre
     * Método llamado luego de presentar los datos del paciente que 
     * carga en el formulario de adultos los datos de la madre
     */
    public void cargaDatosMadre(int idmadre){

        // aquí llamamos el método de la clase
        this.Madre.getDatosPaciente(idmadre);

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Aquí agregamos el nuevo método de datos del parto
     */
    public void partoVertical(){

        // verificamos si hay un paciente activo
        if (this.tId.getText().equals("")){

            // presenta el mensaje
            JOptionPane.showMessageDialog(null, "Debe tener un paciente activo");
            return;

        }

        // mostramos el formulario 
        new formparto(this);

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return idmadre protocolo de la madre
     * Método público que retorna la clave de la madre, llamado 
     * desde el formulario de datos del parto
     */
    public int getIdMadre(){

        // retornamos 
        return this.Madre.getProtocolo();
        
    }

}
