/*

    Nombre: Utilidades
    Fecha: 17/11/2016
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
    Comentarios: Utilidades del sistema, herramientas para conversión de
                 tipos de datos y validación de fechas

 */

// definición del paquete
package dbApi;

// importamos las librerías
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import java.awt.Desktop;

/**
 * @author Lic. Claudio Invernizzi
 */
public class Utilidades {

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return fechaActual un string con la fecha en formato dd/mm/YYYY
     * Método que obtiene la fecha actual del sistema, la formatea y
     * la retorna como una cadena de texto
     */
    public String FechaActual(){

        // declaración de variables
        String fechaActual;

        // instanciamos el objeto calendario
        Calendar fecha = new GregorianCalendar();

        // obtenemos el año mes y día
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        // componemos la cadena
        fechaActual = dia + "/" + mes + "/" + anio;

        // retornamos la fecha
        return fechaActual;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @return fechaCadena un string con la fecha en letras
     * Método que genera la fecha en letras a partir de la fecha actual
     */
    public String fechaLetras(){

        // declaración de variables
        String fechaCadena;
        String mesLetras = "";

        // instanciamos el objeto calendario
        Calendar fecha = new GregorianCalendar();

        // obtenemos el año mes y día (como el mes cuenta desde 0
        // hay que recordar sumarle uno
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        // según el mes
        switch (mes) {

            // actualizamos la variable
            case 0:
                mesLetras = "Enero";
                break;
            case 1:
                mesLetras = "Febrero";
                break;
            case 2:
                mesLetras = "Marzo";
                break;
            case 3:
                mesLetras = "Abril";
                break;
            case 4:
                mesLetras = "Mayo";
                break;
            case 5:
                mesLetras = "Junio";
                break;
            case 6:
                mesLetras = "Julio";
                break;
            case 7:
                mesLetras = "Agosto";
                break;
            case 8:
                mesLetras = "Septiembre";
                break;
            case 9:
                mesLetras = "Octubre";
                break;
            case 10:
                mesLetras = "Noviembre";
                break;
            case 11:
                mesLetras = "Diciembre";
                break;
            default:
                mesLetras = "Indeterminado";
                break;

        }

        // obtenemos la cadena
        fechaCadena = Integer.toString(dia) + " de " + mesLetras + " de " + Integer.toString(anio);

        // retorna la cadena
        return fechaCadena;

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param jd un objeto jdatechooser
     * @return fecha una cadena formateada
     */
    public String fechaJDate(JDateChooser jd){

        // definimos el formato de la fecha
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        // si la fecha es válida
        if(jd.getDate() != null){
            return formato.format(jd.getDate());
        } else {
            return null;
        }

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param fecha - cadena con una fecha
     * @return date - un objeto fecha
     * Método que recibe como parámetro una cadena de texto
     * formateada y retorna un objeto fecha, si no puede
     * convertirlo retorna null
     */
    public Date StringToDate(String fecha){

    	
        // definimos el formato e inicializamos las variables
        try {
			 return new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
                
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param email strin con el correo a verificar
     * @return true si es correcta o false si no lo es. Valida si es correcta la
     *         dirección de correo electrónica dada.
     */
    public boolean esEmailCorrecto(String email) {

        // inicializa las variables
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        // fijamos el patrón con expresiones regulares
        Pattern patronEmail = Pattern.compile(PATTERN_EMAIL);

        // buscamos la ocurrencia del patrón
        Matcher mEmail = patronEmail.matcher(email.toLowerCase());

        // retornamos el resultado
        return mEmail.matches();

    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param longitud - entero con la longitud de la cadena a retornar
     * @return cadena - string con una cadena aleatoria
     * Método que recibe como parámetro un entero con la longitud
     * de la cadena a retornar y retorna una cadena aleatoria (usada
     * para generar contraseñas temporales de acceso
     */
    public String generaRandom(int longitud){
        
        // definimos las variables
        String cadena = null;
        String temp;
        String [] abecedario = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", 
                                "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", 
                                "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", 
                                "o", "p", "q", "r", "s", "t", "u", "v", "w", "z", 
                                "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", 
                                "8", "9"};

        // generamos la cadena
        for (int i = 0; i < longitud; i++){

            // obtenemos un caracter al azar y lo agregamos
            int numRandon = (int) Math.round(Math.random() * 61 );
            temp = abecedario[numRandon];
            cadena = cadena + temp;
            
        }
        
        // retornamos la cadena
        return cadena;
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que recibe como parámetro una cadena con la 
     * ubicación de un archivo y utiliza la clase desktop
     * para abrirlo con la ubicación predeterminada del 
     * sistema
     */
    public void abrirArchivo(String archivo){

        try {
        
            // mostramos el archivo 
            if (Desktop.isDesktopSupported()){

                // esto funciona perfectamente en linux
                // pero en algunos windows sigue de largo
                File path = new File (archivo);
                Desktop.getDesktop().open(path);

            // si no soporta el protocolo
            } else {

                // obtenemos el runtime
                Runtime runtime = Runtime.getRuntime();

                // si es windows o si no soporta getdesktop
                if (System.getenv("OS") != null && System.getenv("OS").contains("Windows")){
                    runtime.exec("rundll32 url.dll,FileProtocolHandler " + archivo);
                } else {
                    runtime.exec("xdg-open " + archivo);
                }

            }

        // si hubo un error de entrada salida 
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
