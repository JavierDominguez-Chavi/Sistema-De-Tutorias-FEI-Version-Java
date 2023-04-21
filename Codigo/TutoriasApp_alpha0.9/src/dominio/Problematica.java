package dominio;

public class Problematica {
    private int idProblematica;
    private String titulo;
    private String descripcion;
    private Asignatura curso;
    private Estudiante estudiante;
    private TutoriaAcademica tutoriaAcademica;
    private Solucion solucion;

    public Problematica(){ 
        this.curso = new Asignatura();
        this.estudiante = new Estudiante();
        this.tutoriaAcademica = new TutoriaAcademica();
        this.solucion = new Solucion();
    }

    public int getIdProblematica() {
        return idProblematica;
    }

    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Asignatura getCurso() {
        return curso;
    }

    public void setCurso(Asignatura curso) {
        this.curso = curso;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public TutoriaAcademica getTutoriaAcademica() {
        return tutoriaAcademica;
    }

    public void setTutoriaAcademica(TutoriaAcademica tutoriaAcademica) {
        this.tutoriaAcademica = tutoriaAcademica;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }

    @Override
    public String toString() {
        return "Problematica{" + "idProblematica=" + idProblematica + ", titulo=" + titulo 
                + ", descripcion=" + descripcion + ", curso=" + curso + ", estudiante=" 
                + estudiante + ", tutoriaAcademica=" + tutoriaAcademica + ", solucion=" 
                + solucion + '}';
    }
    
    

    @Override
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof Problematica) {
            Problematica problematica = (Problematica) objeto;
            esIgual=this.getTitulo().equals(problematica.getTitulo());
            esIgual=this.getDescripcion().equals(problematica.getDescripcion());
        }
        return esIgual;
    }
}
