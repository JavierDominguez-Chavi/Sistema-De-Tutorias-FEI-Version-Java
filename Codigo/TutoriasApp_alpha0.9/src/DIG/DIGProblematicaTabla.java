package DIG;

import dominio.Problematica;
import java.util.Date;

public class DIGProblematicaTabla {
    Problematica problematica;

    public Problematica getProblematica() {
        return problematica;
    }
    
    
    public DIGProblematicaTabla(){
        this.problematica = new Problematica();
    }
    
    public void setProblematica(Problematica problematica){
        this.problematica = problematica;
    }
    
    public Date getFecha(){
        return 
        this.problematica.getTutoriaAcademica().getFechaTutoria().getFechaTutoria();
    }
    
    public String getTitulo(){
        return this.problematica.getTitulo();
    }
    
    public String getExperienciaEducativa(){
        return 
            this.problematica.getCurso().getExperienciaEducativa().getNombre();
    }
    
    public String getNombreProfesor(){
        return problematica.getCurso().getAcademico().getApellidoPaterno() + " " +
        problematica.getCurso().getAcademico().getApellidoMaterno() + " " + 
        problematica.getCurso().getAcademico().getNombre();
    }
    
    public String getDescripcion(){
        return problematica.getDescripcion();
    }
    
}
