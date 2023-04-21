package dominio;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;

public class PeriodoEscolar {
    private int idPeriodoEscolar;
    private String nombrePeriodo;
    private Date fechaInicio;
    private Date fechaFin;
    private ArrayList<FechaTutoria> fechasDelPeriodo;

    public PeriodoEscolar() { 
        this.idPeriodoEscolar = 0;
        this.fechaInicio = new Date();
        this.fechaFin = new Date();
        this.nombrePeriodo = "SIN_NOMBRE";
    }
    
    
    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) throws DateTimeException {
        this.fechaFin = fechaFin;
    }
    
    public void setNombrePeriodo(String nombrePeriodo){
        this.nombrePeriodo = nombrePeriodo;
    }
    
    public String getNombrePeriodo(){
        return this.nombrePeriodo;
    }
    
    @Override
    public String toString() {
        return "PeriodoEscolar{" + "idPeriodoEscolar=" + idPeriodoEscolar + ", nombrePeriodo=" 
                + nombrePeriodo + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin 
                + ", fechasDelPeriodo=" + fechasDelPeriodo + '}';
    }

    public boolean equals(Object objeto){
        boolean verificacion=false;
        if (objeto == this) {
            verificacion=true;
        }
        if (objeto!= null && objeto instanceof PeriodoEscolar) {
            PeriodoEscolar periodoEscolar = (PeriodoEscolar) objeto;
            verificacion=
                this.idPeriodoEscolar == periodoEscolar.getIdPeriodoEscolar()
                && this.fechaInicio.equals(periodoEscolar.getFechaInicio())
                && this.fechaFin.equals(periodoEscolar.getFechaFin());
        }
        return verificacion;
    }
}
