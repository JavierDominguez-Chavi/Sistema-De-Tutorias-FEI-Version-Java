package DIG;

import dominio.Estudiante;

public class DIGEstudianteTabla {
    private Estudiante estudiante;
    
    public DIGEstudianteTabla(){
        this.estudiante = new Estudiante();
    }
    
    public Estudiante getEstudiante(){
        return this.estudiante;
    }
    public void setEstudiante(Estudiante estudiante){
        this.estudiante = estudiante;
    }
    public String getMatricula(){
        return this.estudiante.getMatricula();
    }
    public String getNombre(){
        return this.estudiante.getNombre() + " "
                + this.estudiante.getApellidoPaterno() + " "
                + this.estudiante.getApellidoMaterno();
    }
}
