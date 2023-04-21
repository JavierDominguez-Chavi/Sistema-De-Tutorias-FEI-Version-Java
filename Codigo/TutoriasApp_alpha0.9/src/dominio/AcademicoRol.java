package dominio;

public class AcademicoRol {
    private Academico academico;
    private Rol rol;
    private ProgramaEducativo programaEducativo;
    private AcademicoUsuario academicoUsuario;

    public AcademicoUsuario getAcademicoUsuario() {
        return academicoUsuario;
    }

    public void setAcademicoUsuario(AcademicoUsuario academicoUsuario) {
        this.academicoUsuario = academicoUsuario;
    }
    
    public Academico getAcademico() {
        return this.academico;
    }
    
    public void setAcademico(final Academico academico) {
        this.academico = academico;
    }
    
    public Rol getRol() {
        return this.rol;
    }
    
    public void setRol(final Rol rol) {
        this.rol = rol;
    }
    
    public ProgramaEducativo getProgramaEducativo() {
        return this.programaEducativo;
    }
    
    public void setProgramaEducativo(final ProgramaEducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }
    
    public AcademicoRol() {
        this.academico = new Academico();
        this.rol = new Rol();
        this.programaEducativo = new ProgramaEducativo();
        this.academicoUsuario = new AcademicoUsuario();
    }
    
    @Override
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof AcademicoRol) {
            AcademicoRol academicoRol = (AcademicoRol) objeto;
            esIgual=this.getAcademico().getIdAcademico() == academicoRol.getAcademico().getIdAcademico() &&
                    this.getRol().getIdRol() == academicoRol.getRol().getIdRol() &&
                    this.getProgramaEducativo().getIdProgramaEducativo() == 
                    academicoRol.getProgramaEducativo().getIdProgramaEducativo() &&
                    this.getAcademicoUsuario().getIdUsuario() == academicoRol.getAcademicoUsuario().getIdUsuario();
        }
        return esIgual;
    }

    @Override
    public String toString() {
        return "Academico Rol{" + "Academico: " + academico.toString() + ", Rol: " + rol.toString() 
                + ", Programa educativo: " + programaEducativo.toString() + ", Usuario: " 
                + academicoUsuario.toString() + '}';
    }
}
