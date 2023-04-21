package logicaNegocio.helpers;

import logicaNegocio.PeriodoEscolarDAO;
import dominio.PeriodoEscolar;
import java.sql.SQLException;
import java.time.LocalDate;

public class PeriodoEscolarHelper {
    private enum MESES {Enero, Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre,
    Octubre, Nomviembre, Diciembre}
    
    public String crearNombreDePeriodo(LocalDate fechaInicio, LocalDate fechaFin){
        int mesInicio = fechaInicio.getMonthValue()-1;
        int mesFin = fechaFin.getMonthValue()-1;
        return
            MESES.values()[mesInicio] + " " + fechaInicio.getYear() + " - " +
            MESES.values()[mesFin] + " " + fechaFin.getYear();
    }
    
    public boolean validarPeriodoEscolar(PeriodoEscolar periodo) throws IllegalArgumentException, SQLException{
        validarOrdenDeFechas(periodo);
        validarPeriodoEscolarNuevo(periodo);
        return true;
    }
    
    public boolean validarOrdenDeFechas(PeriodoEscolar periodo) throws IllegalArgumentException{
        final boolean LAS_FECHAS_ESTAN_ORDENADAS = 
            (periodo.getFechaInicio().compareTo(periodo.getFechaFin()) < 0);
        if (LAS_FECHAS_ESTAN_ORDENADAS == false){
            throw new IllegalArgumentException(
                "Las fechas del nuevo periodo escolar están desordenadas. "+ 
                "Por favor, verifique la información e intente de nuevo."
            );
        }
        return LAS_FECHAS_ESTAN_ORDENADAS;
    }
    
    public boolean validarPeriodoEscolarNuevo(PeriodoEscolar periodo) 
    throws IllegalArgumentException, SQLException{
        PeriodoEscolarDAO periodoDAO = new PeriodoEscolarDAO();
        final boolean EL_PERIODO_ES_NUEVO = periodoDAO.validarPeriodoEscolarNuevo(periodo);
        if (EL_PERIODO_ES_NUEVO == false){
            throw new IllegalArgumentException("El periodo escolar ya existe en el sistema. "
                + "Por favor, verifique la información e intente de nuevo.");
        }
        return EL_PERIODO_ES_NUEVO;
    }
}
