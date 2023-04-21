package dominio.globales;

import dominio.AcademicoRol;
import dominio.FechaTutoria;
import dominio.ProgramaEducativo;

public class  InformacionSesion {
    private ProgramaEducativo programaEducativo = new ProgramaEducativo();
    private AcademicoRol academicoRol = new AcademicoRol();
    private FechaTutoria fechaTutoria = new FechaTutoria();
    private static InformacionSesion instancia;
    
    private InformacionSesion() {
    }

    public static InformacionSesion getInformacionSesion(){
        if(instancia == null){
            instancia = new InformacionSesion();
        }
        return instancia;
    }
    
    public ProgramaEducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(ProgramaEducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }
    
    public void setAcademicoRol(AcademicoRol academicoRol) {
        this.academicoRol = academicoRol;
    }

    public AcademicoRol getAcademicoRol() {
        return academicoRol;
    }

  
    public void setFechaTutoria(FechaTutoria fechaTutoria){
        this.fechaTutoria = fechaTutoria;
    }
    
    public FechaTutoria getFechaTutoria(){
        return this.fechaTutoria;
    }
    
}
