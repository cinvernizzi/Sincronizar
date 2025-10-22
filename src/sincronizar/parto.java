/*

    Nombre: Parto
    Fecha: 09/10/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: UploadSitracha
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Clase que provee los métodos para verificar
                 e insertar los registros de los partos de 
                 los pacientes que han sido modificados

    Vamos a utilizar dos tiempos en la sincronización, el 
    primero recibe la clave del registro de pacientes 
    modificado y verifica si hay cambios en el registro 
    de partos, de haberlo, sincroniza con el servidor
    y lo marca como actualizado.

    El segundo momento, obtiene los registros de partos
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
public class parto {

    // puntero a la base de datos
    private final mariaDb Puntero;         // clase de la base del servidor
    private final Connection Enlace;       // puntero a la base de datos
    private final Connection Cursor;       // puntero a la base de datos

    // declaramos las variables
    protected int Id;                      // clave del registro
    protected int Protocolo;               // clave del paciente
    protected int Madre;                   // clave de la madre
    protected String Sivila;               // clave del sivila
    protected String Reportado;            // fecha de reporte al sivila
    protected String Parto;                // tipo de parto
    protected int Peso;                    // peso en gramos
    protected String Prematuro;            // si era prematuro
    protected int Institucion;             // clave de la maternidad
    protected int Usuario;                 // clave del usuario
    protected String FechaAlta;            // fecha de alta del registro
    protected String Comentarios;          // comentarios del usuario

    // vamos a declarar como de clase los statement de la base
    // de datos ya que la mayoría de los mismos se realizan 
    // a través de un bucle
    protected PreparedStatement nominaNovedades;
    protected PreparedStatement verificaNovedad;
    protected PreparedStatement nuevoParto;
    protected PreparedStatement actualizaParto;

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos las conexiónes con 
     * las bases de datos e inicializamos las variables
     */
    public parto(){

        // la conexión al servidor
        this.Puntero = new mariaDb();        
        this.Enlace = Puntero.getEnlace();

        // la conexión a la base local
        dbLite PunteroLite = new dbLite();
        this.Cursor = PunteroLite.getEnlace();

        // inicializamos las variables
        this.Id = 0;
        this.Protocolo = 0;
        this.Madre = 0;
        this.Sivila = "";
        this.Reportado = "";
        this.Parto = "";
        this.Peso = 0;
        this.Prematuro = "";
        this.Institucion = 0;
        this.Usuario = 0;
        this.FechaAlta = "";
        this.Comentarios = "";

        // la consulta de novedades
        String Novedades = "SELECT vertical.id AS id, " +
                           "       vertical.protocolo AS protocolo, " +
                           "       vertical.madre AS madre, " +
                           "       vertical.sivila AS sivila, " + 
                           "       vertical.reportado AS reportado, " + 
                           "       vertical.parto AS parto, " + 
                           "       vertical.peso AS peso, " + 
                           "       vertical.prematuro AS prematuro, " + 
                           "       vertical.institucion AS institucion, " + 
                           "       vertical.comentarios AS comentarios, " + 
                           "       vertical.fecha AS fecha, " + 
                           "       vertical.usuario AS usuario " +
                           "FROM vertical " + 
                           "WHERE vertical.sincronizado = 'No' AND " + 
                           "      vertical.protocolo = ?; ";

        // la consulta sobre si es un alta o edición
        String Verifica = "SELECT diagnostico.congenido.id AS id " +
                          "FROM diagnostico.congenito " + 
                          "WHERE diagnostico.congenido.id = ?; ";

        // componemos la consulta de inserción
        String Nueva = "UPDATE diagnostico.congenito SET " + 
                       "       id_sivila = ?, " + 
                       "       reportado = STR_TO_DATE(?, '%d/%m/%Y'), " + 
                       "       parto = ?, " + 
                       "       peso = ?, " + 
                       "       prematuro = ?, " + 
                       "       institucion = ?, " + 
                       "       id_usuario = ?, " + 
                       "       fecha_alta = STR_TO_DATE(?, '%d/%m/%Y'), " + 
                       "       comentarios = ? " +
                       "WHERE diagnostico.contenido.id = ?; ";


        // componemos la consulta de edición
        String Edicion = "INSERT INTO diagnostico.congenito " + 
                         "       (id_protocolo, " +
                         "        id_madre, " + 
                         "        id_sivila, " + 
                         "        reportado, " + 
                         "        parto, " + 
                         "        peso, " + 
                         "        prematuro, " +
                         "        institucion, " +
                         "        id_usuario, " +
                         "        fecha_alta, " + 
                         "        comentarios) " + 
                         "       VALUES " + 
                         "       (?, ?, ?, STR_TO_DATE(?, '%/%m/%Y'), " + 
                         "        ?, ?, ?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?); ";


        // capturamos el error
        try {

            // asignamos la consulta de novedades sobre la base local
            this.nominaNovedades = this.Cursor.prepareStatement(Novedades);

            // asignamos la consulta de verificación en el servidor remoto
            this.verificaNovedad = this.Enlace.prepareStatement(Verifica);

            // la consulta de inserción en el servidor
            this.nuevoParto = this.Enlace.prepareStatement(Nueva);

            // asignamos la consulta de edición en el servidor remoto
            this.actualizaParto = this.Enlace.prepareStatement(Edicion);
            
        // si ocurrió un error
        } catch (SQLException e){

            // lo presenta
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
     * no hacerlo), verifica si tiene registros de partos
     * modificados y los inserta o actualiza en el servidor
     */
    public void actualizaParto(int idvieja, int idnueva){

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
                this.Madre = Resultado.getInt("madre");
                this.Sivila = Resultado.getString("sivila");
                this.Reportado = Resultado.getString("reportado");
                this.Parto = Resultado.getString("parto");
                this.Peso = Resultado.getInt("peso");
                this.Prematuro = Resultado.getString("prematuro");
                this.Institucion = Resultado.getInt("institucion");
                this.Comentarios = Resultado.getString("comentarios");
                this.FechaAlta = Resultado.getString("fecha");
                this.Usuario = Resultado.getInt("usuario");

                // verificamos si tiene que insertar o editar
                this.verificaParto(Resultado.getInt("id"));

            }

            // liberamos memoria
            Resultado.close();

            // actualizamos la base local
            this.desmarcaNovedades(idvieja);

        // si ocurrió un error
        } catch (SQLException e){

            // lo presentamos
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método llamado luego de actualizar los datos de partos de 
     * los registros de pacientes modificados que obtiene todos
     * los registros de partos sin actualizar 
     */
    public void sinActualizar(){

        // definición de variables
        ResultSet Resultado; 
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "SELECT vertical.id AS id, " +
                   "       vertical.protocolo AS protocolo, " +
                   "       vertical.madre AS madre, " +
                   "       vertical.sivila AS sivila, " + 
                   "       vertical.reportado AS reportado, " + 
                   "       vertical.parto AS parto, " + 
                   "       vertical.peso AS peso, " + 
                   "       vertical.prematuro AS prematuro, " + 
                   "       vertical.institucion AS institucion, " + 
                   "       vertical.comentarios AS comentarios, " + 
                   "       vertical.fecha AS fecha, " + 
                   "       vertical.usuario AS usuario " +
                   "FROM vertical " + 
                   "WHERE vertical.sincronizado = 'No'; ";

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
                this.Madre = Resultado.getInt("madre");
                this.Sivila = Resultado.getString("sivila");
                this.Reportado = Resultado.getString("reportado");
                this.Parto = Resultado.getString("parto");
                this.Peso = Resultado.getInt("peso");
                this.Prematuro = Resultado.getString("prematuro");
                this.Institucion = Resultado.getInt("institucion");
                this.Comentarios = Resultado.getString("comentarios");
                this.FechaAlta = Resultado.getString("fecha");
                this.Usuario = Resultado.getInt("usuario");

                // verificamos si tiene que insertar o editar
                this.verificaParto(Resultado.getInt("id"));

            }

        // si ocurrió un error
        } catch (SQLException e){

            // lo presentamos
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param int idparto clave del registro
     * Método que recibe como parámetro la clave del registro 
     * de vertical y verifica si debe realizar la consulta
     * de inserción o edición
     */
    protected void verificaParto(int idparto){

        // definición de variables
        ResultSet Resultado; 

        // capturamos el error
        try {

            // preparamos y ejecutamos
            this.verificaNovedad.setInt(1, idparto);
            Resultado = this.verificaNovedad.executeQuery();

            // si hay registros
            if (Resultado.next()){
                this.editaParto();
            } else {
                this.insertaParto();
            }

            // liberamos memoria
            Resultado.close();

        // si ocurrió un error
        } catch (SQLException e){

            // presenta el mensaje
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que genera la consulta de inserción
     */
    protected void editaParto(){

        // capturamos el error
        try {

            // preparamos y asignamos
            this.actualizaParto.setInt(1, this.Protocolo);
            this.actualizaParto.setInt(2, this.Madre);
            this.actualizaParto.setString(3, this.Sivila);
            this.actualizaParto.setString(4, this.Reportado);
            this.actualizaParto.setString(5, this.Parto);
            this.actualizaParto.setInt(6, this.Peso);
            this.actualizaParto.setString(7, this.Prematuro);
            this.actualizaParto.setInt(8, this.Institucion);
            this.actualizaParto.setInt(9, this.Usuario);
            this.actualizaParto.setString(10, this.FechaAlta);
            this.actualizaParto.setString(11, this.Comentarios);

            // ejecutamos
            this.actualizaParto.execute();

        // si hubo un error
        } catch (SQLException e){

            // presenta el mensaje
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que genera la consulta de edición
     */
    protected void insertaParto(){

        // capturamos el error
        try {

            // preparamos y asignamos
            this.nuevoParto.setString(1, this.Sivila);
            this.nuevoParto.setString(2, this.Reportado);
            this.nuevoParto.setString(3, this.Parto);
            this.nuevoParto.setInt(4, this.Peso);
            this.nuevoParto.setString(5, this.Prematuro);
            this.nuevoParto.setInt(6, this.Institucion);
            this.nuevoParto.setInt(7, this.Usuario);
            this.nuevoParto.setString(8, this.FechaAlta);
            this.nuevoParto.setString(9, this.Comentarios);
            this.nuevoParto.setInt(10, this.Id);

            // ejecutamos
            this.nuevoParto.execute();
            
        // si ocurrió un error
        } catch (SQLException e){

            // lo imprimimos
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param int idpaciente - clave del paciente
     * Método que actualiza la tabla local marcando los registros
     * actualizados del paciente, terminando así con la primera
     * fase
     */
    protected void desmarcaNovedades(int idpaciente){

        // definición de variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE vertical SET " + 
                   "       sincronizado = 'Si' " + 
                   "WHERE vertical.protocolo = ?; ";

        // capturamos el error
        try {

            // preparamos, asignamos y ejecutamos
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
