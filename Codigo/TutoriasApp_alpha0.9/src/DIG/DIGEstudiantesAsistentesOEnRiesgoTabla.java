package DIG;

import dominio.Estudiante;
import javafx.scene.control.CheckBox;

public class DIGEstudiantesAsistentesOEnRiesgoTabla {
    private Estudiante estudiante;
    private CheckBox checkBoxAsistencia;
    private CheckBox checkBoxRiesgo;
    private String asistenciaEstudiante;
    private String EstudianteEnRiesgo;
    
    public DIGEstudiantesAsistentesOEnRiesgoTabla() {
        this.estudiante = new Estudiante();
        this.checkBoxAsistencia = new CheckBox();
        this.checkBoxRiesgo = new CheckBox();
    }
    
    public Estudiante getEstudiante(){
        return this.estudiante;
    }
    
    public void setEstudiante(Estudiante estudiante){
        this.estudiante = estudiante;
    }
    
    public String getNombreCompleto(){
        return this.estudiante.getApellidoPaterno() + " " + this.estudiante.getApellidoMaterno()
               + " " + this.estudiante.getNombre();
    }
    
    public CheckBox getCheckBoxAsistencia() {
        return checkBoxAsistencia;
    }
    
    public CheckBox getCheckBoxRiesgo() {
        return checkBoxRiesgo;
    }
    
    public String getAsistenciaEstudiante() {
        return asistenciaEstudiante;
    }

    public void setAsistenciaEstudiante(boolean asistencia) {
        if(asistencia){
            this.asistenciaEstudiante = "Sí";
        }else{
            this.asistenciaEstudiante = "No";
        }
    }

    public String getEstudianteEnRiesgo() {
        return EstudianteEnRiesgo;
    }

    public void setEstudianteEnRiesgo(boolean riesgo) {
        if(riesgo){
            this.EstudianteEnRiesgo = "Sí";
        }else{
            this.EstudianteEnRiesgo = "No";
        }
    }
    
}
