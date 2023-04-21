package DIG;

import dominio.TutoriaAcademica;

public class DIGTutoriaAcademicaComentarioGeneralTabla {
    private TutoriaAcademica tutoriaAcademica;

    public DIGTutoriaAcademicaComentarioGeneralTabla() {
        this.tutoriaAcademica = new TutoriaAcademica();
    }

    public void setTutoriaAcademica(TutoriaAcademica tutoriaAcademica) {
        this.tutoriaAcademica = tutoriaAcademica;
    }
    
    public String getNombreTutorAcademico(){
        return tutoriaAcademica.getTutor().getApellidoPaterno() + " " + 
        tutoriaAcademica.getTutor().getApellidoMaterno() + " " + 
        tutoriaAcademica.getTutor().getNombre();
    }
    
    public String getComentarioGeneral(){
        return tutoriaAcademica.getComentarioGeneral();
    }
}
