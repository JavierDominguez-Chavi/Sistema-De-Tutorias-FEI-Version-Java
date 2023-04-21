package dominio;

import java.util.ArrayList;

public class Asignatura {
    private int NRC;
    private ExperienciaEducativa experienciaEducativa;
    private Academico academico;
    private ProgramaEducativo programaEducativo;
    private ArrayList<Estudiante> estudiantesInscritos;
    
    public Asignatura() {
        this.experienciaEducativa = new ExperienciaEducativa();
        this.academico = new Academico();
        this.estudiantesInscritos = new ArrayList();
    }

    public ExperienciaEducativa getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public Academico getAcademico() {
        return academico;
    }

    public void setExperienciaEducativa(ExperienciaEducativa experiencia) {
        this.experienciaEducativa = experiencia;
    }

    public void setAcademico(Academico academico) {
        this.academico = academico;
    }

    public int getNRC() {
        return NRC;
    }

    public void setNRC(int NRC) {
        this.NRC = NRC;
    }

    public void setEstudiantesInscritos(ArrayList<Estudiante> estudiantesInscritos) {
        this.estudiantesInscritos = estudiantesInscritos;
    }

    public ArrayList<Estudiante> getEstudiantesInscritos() {
        return estudiantesInscritos;
    }
    
    @Override
    public String toString() {
        return "ExperienciaEducativaAcademico{" + "NRC=" + NRC + ", experienciaEducativa=" 
                + experienciaEducativa.toString() + ", academico=" + academico.toString() + '}';
    }

    public boolean equals(Object objeto){
        boolean verificacion = false;
        if (objeto == this){
            verificacion = true;
        }
        if (objeto!=null && objeto instanceof Asignatura){
            Asignatura asignatura = (Asignatura) objeto;
            verificacion = 
                   this.NRC == asignatura.getNRC()
                && this.experienciaEducativa.equals(asignatura.getExperienciaEducativa());
        }
        return verificacion;
    }
}
