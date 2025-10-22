/*

    Nombre: Transfusiones
    Fecha: 08/10/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: UploadSitracha
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Clase que provee los métodos para verificar
                 e insertar los registros de transfusiones de 
                 los pacientes que han sido modificados

    Vamos a utilizar dos tiempos en la sincronización, el 
    primero recibe la clave del registro de pacientes 
    modificado y verifica si hay cambios en el registro 
    de transfusiones, de haberlo, sincroniza con el servidor
    y lo marca como actualizado.

    El segundo momento, obtiene los registros de transfusiones
    que han sido modificados y sincroniza con el servidor 
    los registros restantes.

    Estos dos tiempos se dan por varias razones, primero, 
    siempre existe la posibilidad de que en los registros 
    nuevos de pacientes exista una inconsistencia en la 
    clave (baja probabilidad pero posible) el primer tiempo 
    nos garantiza que no existirán incosistencias y el 
    segundo tiempo garantiza no perder novedades.

 */

// definición del paquete
package sincronizar;

// importamos las librerías
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import dbApi.mariaDb;
import dbApi.dbLite;


/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class transfusiones {

    // puntero a la base de datos
    private final mariaDb Puntero;         // clase de la base del servidor
    private final Connection Enlace;       // puntero a la base de datos
    private final Connection Cursor;       // puntero a la base de datos

    // declaramos las variables
    protected int Id;                  // clave del registro
    protected int Protocolo;           // clave del paciente
    protected String FechaTransfusion; // fecha de la transfusión
    protected int Institucion;         // clave de la institución
    protected String Motivo;           // motivo de la transfusión
    protected int Usuario;             // clave del usuario
    protected String Fecha;            // fecha de alta 

    // como la mayoría de las consultas recorren un bucle 
    // definimos las consultas preparadas como de clase
    protected PreparedStatement nominaNovedades;
    protected PreparedStatement verificaNovedad;
    protected PreparedStatement nuevaTransfusion;
    protected PreparedStatement actualizaTransfusion;

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos la conexión y 
     * definimos las variables
     */
    public transfusiones(){

        // la conexión al servidor
        this.Puntero = new mariaDb();        
        this.Enlace = Puntero.getEnlace();

        // la conexión a la base local
        dbLite PunteroLite = new dbLite();
        this.Cursor = PunteroLite.getEnlace();

        // inicializamos las variables
        this.Id = 0;
        this.Protocolo = 0; 
        this.FechaTransfusion = "";
        this.Institucion = 0;
        this.Motivo = "";
        this.Usuario = 0;
        this.Fecha = "";

        // la consulta de novedades
        String Novedades = "SELECT transfusiones.id AS id, " +
                           "       transfusiones.protocolo AS protocolo, " +
                           "       transfusiones.fecha_transfusion AS fechatransfusion, " +
                           "       transfusiones.institucion AS institucion, " +
                           "       transfusiones.motivo AS motivo, " +
                           "       transfusiones.fecha AS fecha, " +
                           "       transfusiones.usuario AS usuario " +
                           "FROM transfusiones " +
                           "WHERE transfusiones.sincronizado = 'No' AND " + 
                           "      transfusiones.protocolo = ?; ";

        // la consulta de edición o inserción en el servidor
        String Verifica = "SELECT diagnostico.transfusiones.id AS id " +
                          "FROM diagnostico.transfusiones " +
                          "WHERE diagnostico.transfusiones.id = ?; ";

        // la consulta de inserción en el servidor
        String Nueva = "INSERT INTO diagnostico.transfusiones " +
                       "            (protocolo, " +
                       "             fecha_transfusion, " +
                       "             institucion, " +
                       "             motivo, " +
                       "             fecha, " +
                       "             usuario) " +
                       "            VALUES " +
                       "            (?, STR_TO_DATE(?, '%d/%m/%Y'), " +
                       "             ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?; ";

        // la consulta de edición en el servidor
        String Edicion = "UPDATE diagnostico.transfusiones SET " +
                         "       fecha_transfusion = STR_TO_DATE(?, '%d/%m/%Y'), " +
                         "       institucion = ?, " +
                         "       motivo = ?, " +
                         "       fecha = ?, " + 
                         "       usuario = ? " +
                         "WHERE diagnostico.transfusiones.id = ?; ";

        // definimos las consultas
        try {

            // la consulta de novedades
            this.nominaNovedades = this.Cursor.prepareStatement(Novedades);

            // la verificación en el servidor
            this.verificaNovedad = this.Enlace.prepareStatement(Verifica);

            // la consulta de inserción en el servidor
            this.nuevaTransfusion = this.Enlace.prepareStatement(Nueva);

            // la consulta de edición en el servidor
            this.actualizaTransfusion = this.Enlace.prepareStatement(Edicion);

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el mensaje
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param int idvieja la clave del paciente de la base local
     * @param int idnueva la clave del paciente del servidor
     * Método que recibe como parámetro la clave de un paciente
     * en la base de datos local y la clave del mismo paciente
     * en el servidor (que deberían coincidir pero pueden 
     * no hacerlo), verifica si tiene registros de transfusiones
     * modificados y los inserta o actualiza en el servidor
     */
    public void actualizaTransfusiones(int idvieja, int idnueva){

        // definición de variables
        ResultSet Resultado; 

        // capturamos el error
        try {

            // preparamos y asignamos
            this.nominaNovedades.setInt(1, idvieja);
            Resultado = this.nominaNovedades.executeQuery();

            // si hay registros 
            while (Resultado.next()){

                // asignamos en la clase
                this.Id = Resultado.getInt("id");
                this.Protocolo = idnueva;
                this.FechaTransfusion = Resultado.getString("fechatransfusion");
                this.Institucion = Resultado.getInt("institucion");
                this.Motivo = Resultado.getString("motivo");
                this.Fecha = Resultado.getString("fecha");
                this.Usuario = Resultado.getInt("usuario");

                // verificamos si tiene que insertar
                this.verificaTransfusion(Resultado.getInt("id"));

            }

            // liberamos memoria
            Resultado.close();

            // marcamos como sincronizado
            this.desmarcaNovedades(idvieja);

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado luego de actualizar las transfusiones de 
     * los registros de pacientes modificados que obtiene todos
     * los registros de transfusiones sin actualizar 
     */
    public void sinActualizar(){

        // definición de variables
        ResultSet Resultado; 
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "SELECT transfusiones.id AS id, " +
                   "       transfusiones.protocolo AS protocolo, " +
                   "       transfusiones.fecha_transfusion AS fechatransfusion, " +
                   "       transfusiones.institucion AS institucion, " +
                   "       transfusiones.motivo AS motivo, " +
                   "       transfusiones.fecha AS fecha, " +
                   "       transfusiones.usuario AS usuario " +
                   "FROM transfusiones " +
                   "WHERE transfusiones.sincronizado = 'No'; ";

        // capturamos el error
        try {

            // preparamos y asignamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Resultado = Preparada.executeQuery();

            // si hay registros 
            while (Resultado.next()){

                // asignamos en la clase
                this.Id = Resultado.getInt("id");
                this.Protocolo = Resultado.getInt("protocolo");
                this.FechaTransfusion = Resultado.getString("fechatransfusion");
                this.Institucion = Resultado.getInt("institucion");
                this.Motivo = Resultado.getString("motivo");
                this.Fecha = Resultado.getString("fecha");
                this.Usuario = Resultado.getInt("usuario");

                // verificamos si tiene que insertar
                this.verificaTransfusion(Resultado.getInt("id"));

            }

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param int idtransfusion clave del registro
     * Método que recibe como parámetro la clave del registro 
     * de transfusiones y verifica si debe realizar la consulta
     * de inserción o edición
     */
    protected void verificaTransfusion(int idtransfusion){

        // definición de variables
        ResultSet Resultado; 

        // capturamos el error
        try {

            // preparamos y asignamos
            this.verificaNovedad.setInt(1, idtransfusion);
            Resultado = this.verificaNovedad.executeQuery();

            // si hubo registros
            if (Resultado.next()){
                this.editaTransfusion();
            } else {
                this.insertaTransfusion();
            }

            // liberamos memoria
            Resultado.close();

        // si ocurrió un error
        } catch (SQLException e){

            // lo presenta
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que realiza la inserción del registro
     */
    protected void insertaTransfusion(){

        // capturamos el error
        try {

            // preparamos y asignamos
            this.nuevaTransfusion.setInt(1, this.Protocolo);
            this.nuevaTransfusion.setString(2, this.FechaTransfusion);
            this.nuevaTransfusion.setInt(3, this.Institucion);
            this.nuevaTransfusion.setString(4, this.Motivo);
            this.nuevaTransfusion.setString(5, this.Fecha);
            this.nuevaTransfusion.setInt(6, this.Usuario);

            // ejecutamos
            this.nuevaTransfusion.execute();

        // si ocurrió un error
        } catch (SQLException e){

            // lo presenta
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que realiza la consulta de edición
     */
    protected void editaTransfusion(){

        // capturamos el error
        try {

            // preparamos y asignamos
            this.actualizaTransfusion.setString(1, this.FechaTransfusion);
            this.actualizaTransfusion.setInt(2, this.Institucion);
            this.actualizaTransfusion.setString(3, this.Motivo);
            this.actualizaTransfusion.setString(4, this.Fecha);
            this.actualizaTransfusion.setInt(5, this.Usuario);
            this.actualizaTransfusion.setInt(6, this.Id);

            // ejecutamos 
            this.actualizaTransfusion.execute();

        // si ocurrió un error
        } catch (SQLException e){

            // lo presenta
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param int idpaciente la clave del paciente en local
     * Método llamado luego de actualizar en el servidor que 
     * marca los registros del paciente actual como sincronizados
     */
    protected void desmarcaNovedades(int idpaciente){

        // definición de variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE transfusiones SET " +
                   "       sincronizado = 'Si' " + 
                   "WHERE transfusiones.protocolo = ?; ";

        // capturamos el error
        try {

            // asignamos y ejecutamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idpaciente);
            Preparada.execute();

        // si hubo un error
        } catch (SQLException e){

            // lo presenta
            e.printStackTrace();

        }

    }

}
