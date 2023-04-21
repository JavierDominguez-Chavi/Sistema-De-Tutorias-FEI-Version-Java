package logicaNegocio.helpers;

import dominio.FechaTutoria;
import java.util.ArrayList;
import java.util.Date;

public class FechaTutoriaHelper {
    public FechaTutoriaHelper(){}
    private String mensajeFechasARegistrarInvalidas = 
    "Los datos ingresados no son válidos. "
    + "Por favor, verifique que estén en orden ascendente, que estén dentro de las fechas seleccionadas "
    + "para el periodo escolar y que cada fecha sea menor a su fecha de cierre correspondiente.";
    private String mensajeFechasObtenidasInvalidas = 
    "Las fechas obtenidas para el periodo escolar seleccionado son inválidas. "
    + "antes de guardarlas, por favor verifique que estén en orden ascendente, "
    + "que estén dentro de las fechas seleccionadas para el periodo escolar y que "
    + "cada fecha sea menor a su fecha de cierre correspondiente.";
    
    public String getMensajeFechasARegistrarInvalidas(){
        return this.mensajeFechasARegistrarInvalidas;
    }

    public String getMensajeFechasObtenidasInvalidas() {
        return mensajeFechasObtenidasInvalidas;
    }
    
    public boolean validarFechasDeTutoria(ArrayList<FechaTutoria> nuevasFechasDeTutoria) {
        boolean validacionFechasIndividuales = true;
        for (FechaTutoria fechaIndividual : nuevasFechasDeTutoria){
            validacionFechasIndividuales = validarFechaTutoria(fechaIndividual);
            if (validacionFechasIndividuales == false){
                break;
            }
        }
        boolean validacionOrdenDeFechas = validarOrdenDeMultiplesFechaTutorias(nuevasFechasDeTutoria);
        return (validacionFechasIndividuales && validacionOrdenDeFechas);
    }
    
    public boolean validarFechaTutoria(FechaTutoria fecha) {
        boolean validacion = 
            validarFechaTutoriaDespuesDePeriodoFechaInicio(fecha) &&
            validarFechaTutoriaAntesDePeriodoFechaFin(fecha) &&
            validarFechaTutoriaAntesDeFechaCierre(fecha);
        return validacion;
    }
    
    public boolean validarFechaTutoriaDespuesDePeriodoFechaInicio(FechaTutoria fecha) {
        return validarPrimeraFechaMenor(fecha.getPeriodo().getFechaInicio(), fecha.getFechaTutoria());
    }
    
    public boolean validarFechaTutoriaAntesDePeriodoFechaFin(FechaTutoria fecha) {
        return validarPrimeraFechaMenor(fecha.getFechaTutoria(), fecha.getPeriodo().getFechaFin());
    }
    
    public boolean validarFechaTutoriaAntesDeFechaCierre(FechaTutoria fecha) {
        return validarPrimeraFechaMenor(fecha.getFechaTutoria(),fecha.getFechaCierreDeReporte());
    }
    
    public boolean validarOrdenDeMultiplesFechaTutorias(ArrayList<FechaTutoria> fechas){
        final int NUMERO_FECHAS = fechas.size()-1;
        
        boolean fechasSonValidas = true;
        for (int indice = 0; indice<NUMERO_FECHAS; indice++){
            Date fechaAnterior = fechas.get(indice).getFechaTutoria();
            Date fechaPosterior = fechas.get(indice+1).getFechaTutoria();
            fechasSonValidas = validarPrimeraFechaMenor(fechaAnterior, fechaPosterior);
            if(!fechasSonValidas){
                break;
            }
        }
        return fechasSonValidas;
    }
    
    private boolean validarPrimeraFechaMenor(Date fecha1, Date fecha2){
        return fecha1.compareTo(fecha2)<0;
    }
}
