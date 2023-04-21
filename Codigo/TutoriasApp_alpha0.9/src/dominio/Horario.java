package dominio;
import java.sql.Time;

public class Horario {
    private int idHorario;
    private boolean asistencia;
    private boolean riesgo;
    private Time horaTutoria;
    private TutoriaAcademica tutoriaAcademica;
    private Estudiante estudiante;
    
    public Horario() {
        this.tutoriaAcademica = new TutoriaAcademica();
        this.estudiante = new Estudiante();
    }

    public int getIdHorario() {
        return this.idHorario;
    }
    
    public void setIdHorario(final int idHorario) {
        this.idHorario = idHorario;
    }
    
    public boolean isAsistencia() {
        return this.asistencia;
    }
    
    public void setAsistencia(final boolean asistencia) {
        this.asistencia = asistencia;
    }
    
    public boolean isRiesgo() {
        return this.riesgo;
    }
    
    public void setRiesgo(final boolean riesgo) {
        this.riesgo = riesgo;
    }
    
    public Time getHoraTutoria() {
        return this.horaTutoria;
    }
    
    public void setHoraTutoria(final Time horaTutoria) {
        this.horaTutoria = horaTutoria;
    }
    
    public TutoriaAcademica getTutoriaAcademica() {
        return this.tutoriaAcademica;
    }
    
    public void setTutoriaAcademica(final TutoriaAcademica tutoriaAcademica) {
        this.tutoriaAcademica = tutoriaAcademica;
    }
    
    public Estudiante getEstudiante() {
        return this.estudiante;
    }
    
    public void setEstudiante(final Estudiante estudiante) {
        this.estudiante = estudiante;
    } 

    @Override
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof Horario) {
            Horario horario = (Horario) objeto;
            esIgual= this.getHoraTutoria().equals(horario.getHoraTutoria()) && 
                    this.getEstudiante().getMatricula().equals(horario.getEstudiante().getMatricula());
        }
        return esIgual;
    }

    @Override
    public String toString() {
        return "Horario{" + "idHorario=" + idHorario + ", asistencia=" + asistencia + ", riesgo=" 
                + riesgo + ", horaTutoria=" + horaTutoria.toString() + ", tutoriaAcademica=" 
                + tutoriaAcademica.toString() + ", estudiante=" + estudiante.toString() + '}';
    }
}
