package dominio;

import java.util.Date;

public class Solucion {
    private int idSolucion;
    private Date fecha;
    private String descripcion;
    private Academico tutor;

    public Solucion() {
        this.fecha = new Date();
        this.tutor = new Academico();
    }

    public int getIdSolucion() {
        return idSolucion;
    }

    public void setIdSolucion(int idSolucion) {
        this.idSolucion = idSolucion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Academico getTutor() {
        return tutor;
    }

    public void setTutor(Academico tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "Solucion{" + "idSolucion=" + idSolucion + ", fecha=" + fecha 
                + ", descripcion=" + descripcion + ", tutor=" + tutor + '}';
    }
    
    
    
    @Override
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof Solucion) {
            Solucion solucion = (Solucion) objeto;
            esIgual=this.getIdSolucion()==solucion.getIdSolucion() 
                    && this.getDescripcion().equals(solucion.getDescripcion()) 
                    && this.getTutor().getIdAcademico() == solucion.getTutor().getIdAcademico()
                    && this.getFecha().equals(solucion.getFecha());

        }
        return esIgual;
    }
}
