package dominio;

import java.util.Date;

public class FechaTutoria implements Comparable<FechaTutoria>{
    private int idFechaTutoria;
    private int numeroSesion;
    private Date fechaTutoria;
    private Date fechaCierreDeReporte;
    private ProgramaEducativo programaEducativo;
    private PeriodoEscolar periodo;

    public FechaTutoria(){
        this.numeroSesion = 0;
        this.idFechaTutoria = 0;
        this.periodo = new PeriodoEscolar(); 
        this.fechaTutoria = new Date();
        this.fechaCierreDeReporte = new Date();
        this.programaEducativo = new ProgramaEducativo();
    } 
    
    public int getIdFechaTutoria() {
        return idFechaTutoria;
    }

    public int getNumeroSesion() {
        return numeroSesion;
    }

    public Date getFechaTutoria() {
        return fechaTutoria;
    }

    public Date getFechaCierreDeReporte() {
        return fechaCierreDeReporte;
    }

    public PeriodoEscolar getPeriodo() {
        return periodo;
    }

    public void setIdFechaTutoria(int idFechaTutoria) {
        this.idFechaTutoria = idFechaTutoria;
    }

    public void setNumeroSesion(int numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public void setFechaTutoria(Date fechaTutoria){
        this.fechaTutoria = fechaTutoria;
    }

    public void setFechaCierreDeReporte(Date fechaCierreDeReporte) {
        this.fechaCierreDeReporte = fechaCierreDeReporte;
    }

    public void setPeriodo(PeriodoEscolar periodo) {
        this.periodo = periodo;
    }

    public ProgramaEducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(ProgramaEducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    @Override
    public String toString() {
        return "FechaTutoria{" + "idFechaTutoria=" + idFechaTutoria + ", numeroSesion=" 
                + numeroSesion + ", fechaTutoria=" + fechaTutoria + ", fechaCierreDeReporte=" 
                + fechaCierreDeReporte + ", programaEducativo=" + programaEducativo + ", periodo="
                + periodo + '}';
    }
    

    @Override
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof FechaTutoria) {
            FechaTutoria fechaTutoria = (FechaTutoria) objeto;
            esIgual=this.getIdFechaTutoria()==fechaTutoria.getIdFechaTutoria() && 
                this.getFechaTutoria().equals(fechaTutoria.getFechaTutoria()) &&
                this.getFechaCierreDeReporte().equals(fechaTutoria.getFechaCierreDeReporte()) &&
                this.getNumeroSesion()==fechaTutoria.getNumeroSesion();
        }
        return esIgual;
    }
    
    @Override
    public int compareTo(FechaTutoria fecha) {
        int resultado = 0;
        if (!equals(fecha)){
            if (this.fechaTutoria.compareTo(fecha.getFechaTutoria()) > 0 &&
                this.fechaCierreDeReporte.compareTo(fecha.getFechaCierreDeReporte()) > 0){
                resultado = 1;
            }else{
                resultado = -1;
            }
        }
        return resultado;
    }

}

