package dominio;

public class Estudiante {
    private String matricula;
    private String nombre;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private int semestreQueCursa;
    private String correoElectronicoPersonal;
    private String correoElectronicoInstitucional;
    private ProgramaEducativo programaEducativo;
    private Academico academico;
    

    public Estudiante() {
        this.programaEducativo = new ProgramaEducativo();
        this.academico = new Academico();
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    } 

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public int getSemestreQueCursa() {
        return semestreQueCursa;
    }

    public void setSemestreQueCursa(int semestreQueCursa) {
        this.semestreQueCursa = semestreQueCursa;
    }

    public String getCorreoElectronicoPersonal() {
        return correoElectronicoPersonal;
    }

    public void setCorreoElectronicoPersonal(String correoElectronicoPersonal) {
        this.correoElectronicoPersonal = correoElectronicoPersonal;
    }

    public String getCorreoElectronicoInstitucional() {
        return correoElectronicoInstitucional;
    }

    public void setCorreoElectronicoInstitucional(String correoElectronicoInstitucional) {
        this.correoElectronicoInstitucional = correoElectronicoInstitucional;
    }

    public ProgramaEducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(ProgramaEducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public Academico getAcademico() {
        return academico;
    }
    

    public void setAcademico(Academico academico) {
        this.academico = academico;
    }

    public String getNombreCompleto(){
        return this.nombre + " " + this.apellidoPaterno + " " + this.apellidoMaterno;
    }
    
    @Override
    public String toString() {
        return "Estudiante{" + "matricula=" + matricula + ", nombre=" + nombre + ", apellidoMaterno=" + 
               apellidoMaterno + ", apellidoPaterno=" + apellidoPaterno + ", semestreQueCursa=" 
               + semestreQueCursa + ", correoElectronicoPersonal=" + correoElectronicoPersonal 
               + ", correoElectronicoInstitucional=" + correoElectronicoInstitucional + ", programaEducativo=" 
               + programaEducativo + ", academico=" + academico.toString() + "}\n";
    }
    
    @Override
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) objeto;
            esIgual=this.getMatricula().equals(estudiante.getMatricula()) 
                    && this.getNombre().equals(estudiante.getNombre()) 
                    && this.getApellidoPaterno().equals(estudiante.getApellidoPaterno()) 
                    && this.getApellidoMaterno().equals(estudiante.getApellidoMaterno());          
        }
        return esIgual;
    }
}
