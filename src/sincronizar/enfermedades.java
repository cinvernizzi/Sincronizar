/*

    Nombre: Enfermedades
    Fecha: 08/10/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: UploadSitracha
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Clase que provee los métodos para verificar
                 e insertar los registros de enfermedades de 
                 los pacientes que han sido modificados

    Vamos a utilizar dos tiempos en la sincronización, el 
    primero recibe la clave del registro de pacientes 
    modificado y verifica si hay cambios en el registro 
    de enfermedades, de haberlo, sincroniza con el servidor
    y lo marca como actualizado.

    El segundo momento, obtiene los registros de enfermedades
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
public class enfermedades {

    // puntero a la base de datos
    private final mariaDb Puntero;         // clase de la base del servidor
    private final Connection Enlace;       // puntero a la base de datos
    private final Connection Cursor;       // puntero a la base de datos

    // declaramos las variables
    protected int Id;              // clave del registro
    protected int Protocolo;       // clave del paciente
    protected int Enfermedad;      // clave de la enfermedad
    protected int Usuario;         // clave del usuario
    protected String FechaAlta;    // fecha de alta del registro
    protected String Fecha;        // fecha de la enfermedad

    // las consultas 
    protected PreparedStatement nominaNovedades; 
    protected PreparedStatement verificaNovedad;
    protected PreparedStatement nuevaEnfermedad;
    protected PreparedStatement actualizaEnfermedad;

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, instanciamos las variables 
     */
    public enfermedades(){

        // la conexión al servidor
        this.Puntero = new mariaDb();        
        this.Enlace = Puntero.getEnlace();

        // la conexión a la base local
        dbLite PunteroLite = new dbLite();
        this.Cursor = PunteroLite.getEnlace();

        // inicializamos las variables
        this.Id = 0;
        this.Protocolo = 0;
        this.Enfermedad = 0;
        this.Usuario = 0;
        this.FechaAlta = "";
        this.Fecha = "";

        // vamos a predefinir y compilar las consultas para 
        // ahorrar recursos ya que las usaremos dentro de 
        // bucles

        // componemos la consulta
        String Novedades = "SELECT dolencias.id AS id, " +
                           "       dolencias.protocolo AS protocolo, " +
                           "       dolencias.enfermedad AS enfermedad, " +
                           "       dolencias.fecha AS fecha, " +
                           "       dolencias.fecha_alta AS fecha_alta, " +
                           "       dolencias.usuario AS usuario " +
                           "FROM dolencias " +
                           "WHERE dolencias.sincronizado = 'No' AND " +
                           "      dolencias.protocolo = ?; ";

        // componemos la consulta de alta o edición
        String Verifica = "SELECT diagnostico.dolencias.id AS id " + 
                          "FROM diagnostico.dolencias " + 
                          "WHERE diagnostico.dolencias.id = ?; ";
                        
        // la consulta de inserción 
        String Nueva = "INSERT INTO diagnostico.dolencias " +
                       "            (id_protocolo, " +
                       "             id_enfermedad, " + 
                       "             id_usuario, " + 
                       "             fecha, " +
                       "             fecha_alta) " +
                       "            VALUES " +
                       "            (?, ?, ?, " + 
                       "             STR_TO_DATE(?, '%d/%m/%Y'), " + 
                       "             STR_TO_DATE(?, '%d/%m/%Y'); "; 

        // la consulta de edición 
        String Edicion  = "UPDATE diagnostico.enfermedades SET " + 
                          "       id_enfermedad = ?, " +
                          "       id_usuario = ?, " +
                          "       fecha = ?, " +
                          "       fecha_alta = ? " + 
                          "WHERE diagnostico.enfermedades.id = ?; ";


        
        try {

            // asignamos en las novedades del servidor local
            this.nominaNovedades = this.Cursor.prepareStatement(Novedades);

            // la veriicación de alta o edición del servidor remoto
            this.verificaNovedad = this.Enlace.prepareStatement(Verifica);

            // la consulta de inserción en el servidor remoto
            this.nuevaEnfermedad = this.Enlace.prepareStatement(Nueva);

            // la consulta de edición en el servidor remoto
            this.actualizaEnfermedad = this.Enlace.prepareStatement(Edicion);

        } catch (SQLException e) {

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
     * no hacerlo), verifica si tiene registros de enfermedades
     * modificados y los inserta o actualiza en el servidor
     */
    public void actualizaEnfermedades(int idvieja, int idnueva){

        // definición de variables
        ResultSet Resultado; 

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            this.nominaNovedades.setInt(1, idvieja);
            Resultado = this.nominaNovedades.executeQuery();

            // ahora deberíamos recorrer el vector
            while (Resultado.next()){

                // asignamos en las variables de clase
                this.Id = Resultado.getInt("id");
                this.Protocolo = idnueva;
                this.Enfermedad = Resultado.getInt("enfermedad");
                this.Fecha = Resultado.getString("fecha");
                this.FechaAlta = Resultado.getString("fecha_alta");
                this.Usuario = Resultado.getInt("usuario");

                // verificamos si debe insertar o editar
                this.verificaEnfermedad(Resultado.getInt("id"));

            }

            // actualizamos la tabla local
            this.desmarcaNovedades(idvieja);

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
     * Método llamado luego de actualizar los registros de 
     * enfermedades de los pacientes que fueron modificados 
     * y obtiene los registros pendientes de sincronización 
     */
    public void sinActualizar(){

        // definición de variables
        ResultSet Resultado; 
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "SELECT dolencias.id AS id, " +
                   "       dolencias.protocolo AS protocolo, " +
                   "       dolencias.enfermedad AS enfermedad, " +
                   "       dolencias.fecha AS fecha, " +
                   "       dolencias.fecha_alta AS fecha_alta, " +
                   "       dolencias.usuario AS usuario " +
                   "FROM dolencias " +
                   "WHERE dolencias.sincronizado = 'No'; ";

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            Preparada = this.Cursor.prepareStatement(Consulta);
            Resultado = Preparada.executeQuery();

            // ahora deberíamos recorrer el vector
            while (Resultado.next()){

                // asignamos en las variables de clase
                this.Id = Resultado.getInt("id");
                this.Protocolo = Resultado.getInt("protocolo");
                this.Enfermedad = Resultado.getInt("enfermedad");
                this.Fecha = Resultado.getString("fecha");
                this.FechaAlta = Resultado.getString("fecha_alta");
                this.Usuario = Resultado.getInt("usuario");

                // verificamos si debe insertar o editar
                this.verificaEnfermedad(Resultado.getInt("id"));

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
     * @param int idenfermedad - clave del registro
     * Método que verifica si corresponde insertar o editar
     * un registro 
     */
    protected void verificaEnfermedad(int idenfermedad){

        // definición de variables
        ResultSet Resultado; 
    
        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            this.verificaNovedad.setInt(1, idenfermedad);
            Resultado = this.verificaNovedad.executeQuery();

            // si hubo registros 
            if (Resultado.next()){
                this.editaEnfermedad();
            } else {
                this.insertaEnfermedad();
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
     * Método que inserta el registro de una nueva enfermedad
     * en el servidor
     */
    protected void insertaEnfermedad(){

        // capturamos el error
        try {

            // preparamos y asignamos
            this.nuevaEnfermedad.setInt(1, this.Protocolo);
            this.nuevaEnfermedad.setInt(2, this.Enfermedad);
            this.nuevaEnfermedad.setInt(3, this.Usuario);
            this.nuevaEnfermedad.setString(4, this.Fecha);
            this.nuevaEnfermedad.setString(5, this.FechaAlta);

            // ejecutamos
            this.nuevaEnfermedad.execute();

        // si hubo un error
        } catch (SQLException e){

            // lo presenta
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que edita el registro de una enfermedad en el 
     * servidor
     */
    protected void editaEnfermedad(){

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
            this.actualizaEnfermedad.setInt(1, this.Enfermedad);
            this.actualizaEnfermedad.setInt(2, this.Usuario);
            this.actualizaEnfermedad.setString(5, this.Fecha);
            this.actualizaEnfermedad.setString(6, this.FechaAlta);

            // ejecutamos 
            this.actualizaEnfermedad.execute();
            
        // si ocurrió un error
        } catch (SQLException e){

            // lo presenta
            e.printStackTrace();

        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param int idpaciente - clave del paciente
     * Método llamado luego de actualizar los registros de 
     * un paciente que marca el estado de la base de datos 
     * local como actualizado
     */
    protected void desmarcaNovedades(int idpaciente){

        // declaramos las variables
        String Consulta;
        PreparedStatement Preparada;

        // componemos la consulta
        Consulta = "UPDATE dolencias SET " +
                   "       sincronizado = 'Si' " +
                   "WHERE protocolo = ?; ";

        // capturamos el error
        try {

            // asignamos la consulta y los parámetros
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
