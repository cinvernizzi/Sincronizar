/*

    Nombre: calculaFechas
    Fecha: 14/06/2020
    Autor: Lic. Claudio Invernizzi
    E-Mail: cinvernizzi@dsgestion.site
    Proyecto: diagnostico
    Licencia: GPL
    Producido en: INP - Dr. Mario Fatala Chaben
    Buenos Aires - Argentina
	Comentarios: Clase que provee métodos para el calcula 
                 del tiempo transcurrido entre dos fechas

 */

// definición del paquete
package dbApi;

// importamos las librerías
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
 * Definición de la clase
 */
public class calculaFechas {

    // declaración de variables
    private String FechaInicio;        // fecha inicial a calcular
    private String FechaFinal;         // fecha final a calcular
    private int Anios;                 // años transcurridos
    private int Meses;                 // meses transcurridos
    private int Dias;                  // dias transcurridos
    private Date FechaActual;          // fecha actual del sistema
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param fechainicio - fecha inicial a calcular
     * Constructor de la clase cuando se recibe solo la 
     * fecha inicial y asume la fecha actual como la 
     * fecha final
     */
    public calculaFechas(String fechainicio) {

        // asignamos en la clase
        this.FechaInicio = fechainicio;

        // obtenemos la fecha actual
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.FechaFinal = df.format(date);
        
        // calculamos el tiempo transcurrido
        this.tiempoTranscurrido();
        
    }

    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Constructor llamado cuando instanciamos la clase 
     * sin parametros
     */
    public calculaFechas(){
    
        // obtenemos la fecha actual
        this.FechaActual = new Date();
        
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param fechainicio - fecha inicial a calcular
     * @param fechafinal - fecha final a calcular
     * Constructor llamado cuando recibe tanto la fecha 
     * inicial como la final
     */
    public calculaFechas(String fechainicio, String fechafinal){
        
        // asignamos en la clase
        this.FechaInicio = fechainicio;
        this.FechaFinal = fechafinal;
        
        // calculamos el tiempo transcurrido
        this.tiempoTranscurrido();
        
    }

    // métodos de retorno de valores
    public int getAnios(){
        return this.Anios;
    }
    public int getMeses(){
        return this.Meses;
    }
    public int getDias(){
        return this.Dias;
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * Método que a partir de las variables de clase calcula
     * la diferencia entre dos fechas y asigna los valores
     * a las variables de clase
     */
    private void tiempoTranscurrido(){

        // parseamos la fecha de inicio
        String[] aFechaIng = this.FechaInicio.split("/");
        Integer diaInicio = Integer.parseInt(aFechaIng[0]);
        Integer mesInicio = Integer.parseInt(aFechaIng[1]);
        Integer anioInicio = Integer.parseInt(aFechaIng[2]);

        // parseamos la fecha final
        String[] aFecha = this.FechaFinal.split("/");
        int diaActual = Integer.parseInt(aFecha[0]);
        int mesActual = Integer.parseInt(aFecha[1]);
        int anioActual = Integer.parseInt(aFecha[2]);

        // inicializamos los contadores
        int b = 0;
        int dias = 0;
        int mes = 0;
        int anios = 0;
        int meses = 0;
        mes = mesInicio - 1;
        
        // si es un año bisiesto
        if (mes == 2) {
            if ((anioActual % 4 == 0) && ((anioActual % 100 != 0) || (anioActual % 400 == 0))) {
                b = 29;
            } else {
                b = 28;
            }
        } else if (mes <= 7) {
            if (mes == 0) {
                b = 31;
            } else if (mes % 2 == 0) {
                b = 30;
            } else {
                b = 31;
            }
        } else if (mes > 7) {
            if (mes % 2 == 0) {
                b = 31;
            } else {
                b = 30;
            }
        }
    
        // verificamos que la fecha final sea posterior a la inicial
        if ((anioInicio > anioActual) || (anioInicio == anioActual && mesInicio > mesActual)
                || (anioInicio == anioActual && mesInicio == mesActual && diaInicio > diaActual)) {

            // presenta el mensaje
            JOptionPane.showMessageDialog(null,
                        "La fecha final debe ser posterior\n " +
                        "la fecha inicial",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

        // si está correcto
        } else {
            
            // si el mes es inferior
            if (mesInicio <= mesActual) {
                anios = anioActual - anioInicio;
                if (diaInicio <= diaActual) {
                    meses = mesActual - mesInicio;
                    dias = b - (diaInicio - diaActual);
                } else {
                    if (mesActual == mesInicio) {
                        anios = anios - 1;
                    }
                    meses = (mesActual - mesInicio - 1 + 12) % 12;
                    dias = b - (diaInicio - diaActual);
                }
                
            // si el mes es posterior
            } else {
                anios = anioActual - anioInicio - 1;
                if (diaInicio > diaActual) {
                    meses = mesActual - mesInicio - 1 + 12;
                    dias = b - (diaInicio - diaActual);
                } else {
                    meses = mesActual - mesInicio + 12;
                    dias = diaActual - diaInicio;
                }
                
            }
            
        }

        // asignamos en la clase
        this.Anios = anios;
        this.Meses = meses;
        this.Dias = dias;
                
    }
    
    /**
     * @author Claudio Invernizzi <cinvernizzi@dsgestion.site>
     * @param tiempo - entero con el tiempo transcurrido
     * @param tipo - string indicando el periodo a calcular
     * @return Date - fecha calculada
     * Metodo que recibe como parametro un entero con la 
     * cantidad de tiempo a sumar o restar, un string 
     * indicando el tipo de operacion (si suma años, meses 
     * o dias y si va a sumar o restar queda determinado 
     * por el signo del tiempo y retorna la fecha 
     * actual mas o menos la cantidad de tiempo recibidos
     */
    public Date sumarFecha(int tiempo, String tipo){
    
        // instanciamos el calendario
        Calendar calendario = Calendar.getInstance();
        
        // fijamos la fecha 
        calendario.setTime(this.FechaActual);
        
        // si fueron años 
        if (tipo.equals("Anios")){

            // agregamos los años
            calendario.add(Calendar.YEAR, tiempo);

        // si fueron meses
        } else if (tipo.equals("Meses")){
        
            // agregamos los meses
            calendario.add(Calendar.MONTH, tiempo);
            
        // si fueron dias
        } else if (tipo.equals ("Dias")){
            
            // agregamos los dias
            calendario.add(Calendar.DAY_OF_YEAR, tiempo);
            
        }

        // retornamos la fecha
        return calendario.getTime();
        
    }
    
}
