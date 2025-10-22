/*

    Nombre: Sincronizar
    Fecha: 06/10/2025
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: UploadSitracha
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Clase que verifica las novedades de la base local y 
                 sincroniza las mismas con el servidor

    Nota: vamos a recorrer las novedades de los pacientes y luego 
    por cada uno de ellos obtener las novedades de los registros 
    hijos (enfermedades, transplantes, etc.) obtenemos primero la 
    nueva clave en el servidor y luego sincronizamos con los 
    registros hijos para estar seguros que no perdemos la 
    relación

 */

// definición del paquete
package sincronizar;

// importamos las librerías
import java.sql.ResultSet;
import java.sql.SQLException;
import pacientes.pacientes;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class sincronizar {

    // definimos las variables
    protected personas Personas;
    protected pacientes Pacientes;
    protected enfermedades Enfermedades;
    protected transfusiones Transfusiones;
    protected transplantes Transplantes;
    protected parto Parto;
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor de la clase, establecemos la conexión con 
     * ambas bases y lanzamos la sincronización
     */
    public sincronizar(){

        // instanciamos las clases
        this.Personas = new personas();
        this.Pacientes = new pacientes();
        this.Enfermedades = new enfermedades();
        this.Transfusiones = new transfusiones();
        this.Transplantes = new transplantes();
        this.Parto = new parto();

        // lanzamos la sincronización 
        this.leerNovedades();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que obtiene las novedades del servidor local
     */
    protected void leerNovedades(){

        // obtenemos los pacientes nuevos
        ResultSet nomina = this.Pacientes.getModificados();

        // recorremos el vector
        try {

            // recorremos el vector
            while (nomina.next()){

                // asignamos en la clase de personas
                Personas.setProtocolo(nomina.getInt("protocolo"));
                Personas.setHistoria(nomina.getString("historia"));
                Personas.setApellido(nomina.getString("apellido"));
                Personas.setNombre(nomina.getString("nombre"));
                Personas.setDocumento(nomina.getString("documento"));
                Personas.setIdDocumento(nomina.getInt("tipodoc"));
                Personas.setNacimiento(nomina.getString("fechanacimiento"));
                Personas.setEdad(nomina.getInt("edad"));
                Personas.setSexo(nomina.getInt("sexo"));
                Personas.setEstadoCivil(nomina.getInt("estado"));
                Personas.setDireccion(nomina.getString("direccion"));
                Personas.setTelefono(nomina.getString("telefono"));
                Personas.setCelular(nomina.getString("calular"));
                Personas.setMail(nomina.getString("mail"));
                Personas.setLocNacimiento(nomina.getString("locnacimiento"));
                Personas.setLocResidencia(nomina.getString("locresidencia"));
                Personas.setLocMadre(nomina.getString("locmadre"));
                Personas.setMadrePositiva(nomina.getString("madrepositiva"));
                Personas.setOcupacion(nomina.getInt("ocupacion"));;
                Personas.setObraSocial(nomina.getString("obrasocial"));
                Personas.setMotivo(nomina.getInt(nomina.getInt("motivo")));
                Personas.setDerivacion(nomina.getInt("derivacion"));
                Personas.setProfesional(nomina.getString("profesional"));
                Personas.setMatricula(nomina.getString("matricula"));
                Personas.setTratamiento(nomina.getString("tratamiento"));
                Personas.setUsuario(nomina.getInt("usuario"));
                Personas.setFechaAlta(nomina.getString("fechaalta"));
                Personas.setComentarios(nomina.getString("comentaros"));

                // insertamos el registroy obtenemos la id
                int idnueva = this.Personas.verificaPaciente();

                // verificamos las enfermedades pasándole la id 
                // de la base local y la del servidor
                this.Enfermedades.actualizaEnfermedades(nomina.getInt("protocolo"), idnueva);

                // verificamos las transfusiones
                this.Transfusiones.actualizaTransfusiones(nomina.getInt("protocolo"), idnueva);

                // verificamos los transplantes
                this.Transplantes.actualizaTransplantes(nomina.getInt("protocolo"), idnueva);

                // verificamos los datos del parto 
                this.Parto.actualizaParto(idnueva, idnueva);

            }

        // si ocurrió un error
        } catch (SQLException e) {
            
            // presenta al log de errores
            e.printStackTrace();

        }
        
        // ahora llamamos la actualización de todos los 
        // registros en los que no se modificó el 
        // registro del paciente y que quedaron sin 
        // sincronizar
        this.Enfermedades.sinActualizar();
        this.Transfusiones.sinActualizar();
        this.Transplantes.sinActualizar();
        this.Parto.sinActualizar();

    }

}
