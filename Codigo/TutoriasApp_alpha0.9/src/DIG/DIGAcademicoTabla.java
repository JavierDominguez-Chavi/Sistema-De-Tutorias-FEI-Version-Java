package DIG;

import dominio.Academico;

public class DIGAcademicoTabla {
    private Academico academico;
    public DIGAcademicoTabla(){
        this.academico = new Academico();
    }
    
    public Academico getAcademico(){
        return this.academico;
    }
    public void setAcademico(Academico academico){
        this.academico = academico;
    }
    public int getIdentificador(){
        return this.academico.getIdAcademico();
    }
    public String getNombre(){
        return this.academico.getNombre() + " " 
                + this.academico.getApellidoPaterno() + " "
                + this.academico.getApellidoMaterno();
    }
}
