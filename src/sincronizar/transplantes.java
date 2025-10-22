/*

    Nombre: Transplantes
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
    de transplantes, de haberlo, sincroniza con el servidor
    y lo marca como actualizado.

    El segundo momento, obtiene los registros de transplantes
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
public class transplantes {

    // puntero a la base de datos
    private final mariaDb Puntero;         // clase de la base del servidor
    private final Connection Enlace;       // puntero a la base de datos
    private final Connection Cursor;       // puntero a la base de datos

    // definición de variables
    protected int Id;                      // clave del registro
    protected int Protocolo;               // protocolo del paciente
    protected int Organo;                  // clave del órgano
    protected String Positivo;             // si es positivo para chagas
    protected String FechaTransplante;     // la fecha del transplante
    protected int Usuario;                 // clave del usuario
    protected String Fecha;                // fecha de alta del registro

    // como vamos a recorrer las consultas a través de bucles, definimos
    // las sentencias preparadas como de clase
    protected PreparedStatement nominaNovedades;
    protected PreparedStatement verificaNovedad;
    protected PreparedStatement nuevoTransplante;
    protected PreparedStatement actualizaTransplante;

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, inicializamos las variables y 
     * establecemos la conexión con las bases de datos
     */
    public transplantes(){

        // la conexión al servidor
        this.Puntero = new mariaDb();        
        this.Enlace = Puntero.getEnlace();

        // la conexión a la base local
        dbLite PunteroLite = new dbLite();
        this.Cursor = PunteroLite.getEnlace();

        // inicializamos las variables
        this.Id = 0;
        this.Protocolo = 0;
        this.Organo = 0;
        this.Positivo = "";
        this.FechaTransplante = "";
        this.Usuario = 0;
        this.Fecha = "";

        // la consulta de novedades en el servidor local
        String Novedades = "SELECT transplantes.id AS id, " +
                           "       transplantes.protocolo AS protocolo, " +
                           "       transplantes.organo AS organo, " +
                           "       transplantes.positivo AS positivo, " +
                           "       transplantes.fecha_transplante AS fecha_transplante, " +
                           "       transplantes.fecha AS fecha, " +
                           "       transplantes.usuario AS usuario " +
                           "FROM transplantes " +
                           "WHERE transplantes.sincronizado = 'No' AND " + 
                           "      transplantes.protocolo = ?; ";

        // la consulta de verificación en el servidor
        String Verifica = "SELECT diagnostico.transplantes.id AS id " +
                          "FROM diagnostico.transplantes " +
                          "WHERE diagnostico.transplantes.id = ?; ";
        
        // la consulta de inserción en el servidor
        String Nuevo = "INSERT INTO diagnostico.transplantes " +
                       "       (id_protocolo, " +
                       "        organo, " +
                       "        positivo, " +
                       "        fecha_transplante, " +
                       "        id_usuario, " + 
                       "        fecha) " + 
                       "       VALUES " + 
                       "       (?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), " +
                       "        ?, STR_TO_DATE(?, '%d/%m/%Y'); ";

        // la consulta de edición en el servidor
        String Edicion = "UPDATE diagnostico.transplantes SET " + 
                         "       organo = ?, " +
                         "       positivo = ?, " + 
                         "       fecha_transplante = STR_TO_DATE(?, '%d/%m/%Y'), " + 
                         "       id_usuario = ?, " + 
                         "       fecha = STR_TO_DATE(?, '%d/%m/%Y') " + 
                         "WHERE diagnostico.transplantes.id = ?; ";


        // preparamos las consultas
        try {

            // la nómina de novedades en la base local
            this.nominaNovedades = this.Cursor.prepareStatement(Novedades);

            // la consulta de verificación 
            this.verificaNovedad = this.Enlace.prepareStatement(Verifica);

            // la inserción en el servidor
            this.nuevoTransplante = this.Enlace.prepareStatement(Nuevo);

            // la edición en el servidor
            this.actualizaTransplante = this.Enlace.prepareStatement(Edicion);

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
    public void actualizaTransplantes(int idvieja, int idnueva){

        // definición de variables
        ResultSet Resultado; 

        // capturamos el error
        try {

            // preparamos y asignamos
            this.nominaNovedades.setInt(1, idvieja);
            Resultado = nominaNovedades.executeQuery();

            // recorremos el vector
            while (Resultado.next()){

                // asignamos en la clase
                this.Id = Resultado.getInt("id");
                this.Protocolo = idnueva;
                this.Organo = Resultado.getInt("organo");
                this.Positivo = Resultado.getString("positivo");
                this.FechaTransplante = Resultado.getString("fecha_transplante");
                this.Fecha = Resultado.getString("fecha");
                this.Usuario = Resultado.getInt("usuario");

                // verificamos si inserta o edita
                this.verificaTransplante(Resultado.getInt("id"));

            }

            // liberamos memoria
            Resultado.close();

            // actualizamos en la base local
            this.desmarcaNovedades(idvieja);

        // si ocurrió un error
        } catch (SQLException e){

            // lo presenta
            e.printStackTrace();;

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado luego de actualizar los transplantes de 
     * los registros de pacientes modificados que obtiene todos
     * los registros de transplantes sin actualizar 
     */
    public void sinActualizar(){

        // definición de variables
        ResultSet Resultado; 
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "SELECT transplantes.id AS id, " +
                   "       transplantes.protocolo AS protocolo, " +
                   "       transplantes.organo AS organo, " +
                   "       transplantes.positivo AS positivo, " +
                   "       transplantes.fecha_transplante AS fecha_transplante, " +
                   "       transplantes.fecha AS fecha, " +
                   "       transplantes.usuario AS usuario " +
                   "FROM transplantes " +
                   "WHERE transplantes.sincronizado = 'No' AND " + 
                   "      transplantes.protocolo = ?; ";

        // capturamos el error
        try {

            // preparamos y asignamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Resultado = Preparada.executeQuery();

            // recorremos el vector
            while (Resultado.next()){

                // asignamos en la clase
                this.Id = Resultado.getInt("id");
                this.Protocolo = Resultado.getInt("protocolo");
                this.Organo = Resultado.getInt("organo");
                this.Positivo = Resultado.getString("positivo");
                this.FechaTransplante = Resultado.getString("fecha_transplante");
                this.Fecha = Resultado.getString("fecha");
                this.Usuario = Resultado.getInt("usuario");

                // verificamos si inserta o edita
                this.verificaTransplante(Resultado.getInt("id"));

            }

        // si ocurrió un error
        } catch (SQLException e){

            // lo presenta
            e.printStackTrace();;

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param id clave del registro 
     * Método que recibe la clave de un registro y verifica si 
     * debe editar o insertar
     */
    protected void verificaTransplante(int id){

        // definición de variables
        ResultSet Resultado; 

        // capturamos el error
        try {

            // preparamos y asignamos
            this.verificaNovedad.setInt(1, id);
            Resultado = this.verificaNovedad.executeQuery();

            // si hay registros 
            if (Resultado.next()){
                this.editaTransplante();
            } else {
                this.insertaTransplante();
            }

            // liberamos recursos
            Resultado.close();

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el mensaje
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que ejecuta la consulta de inserción
     */
    protected void insertaTransplante(){

        // capturamos el error
        try {

            // preparamos y asignamos
            this.nuevoTransplante.setInt(1, this.Protocolo);
            this.nuevoTransplante.setInt(2, this.Organo);
            this.nuevoTransplante.setString(3, this.Positivo);
            this.nuevoTransplante.setString(4, this.FechaTransplante);
            this.nuevoTransplante.setInt(5, this.Usuario);
            this.nuevoTransplante.setString(6, this.Fecha);

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el error
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que ejecuta la consulta de actualización
     */
    protected void editaTransplante(){

        // capturamos el error
        try {

            // preparamos y asignamos
            this.actualizaTransplante.setInt(1, this.Organo);
            this.actualizaTransplante.setString(2, this.Positivo);
            this.actualizaTransplante.setString(3, this.FechaTransplante);
            this.actualizaTransplante.setInt(4, this.Usuario);
            this.actualizaTransplante.setString(5, this.Fecha);
            this.actualizaTransplante.setInt(6, this.Id);

            // ejecutamos
            this.actualizaTransplante.execute();
            
        // si ocurrió un error
        } catch (SQLException e){

            // presenta el mensaje
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

        // definimos la consulta
        Consulta = "UPDATE transplantes SET " +
                   "       sincronizado = 'Si' " + 
                   "WHERE transplantes.protocolo = ?; ";

        // capturamos el error
        try {

            // preparamos y asignamos
            Preparada = this.Cursor.prepareStatement(Consulta);
            Preparada.setInt(1, idpaciente);
            Preparada.execute();

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el mensaje
            e.printStackTrace();

        }

    }

}