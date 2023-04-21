package dominio;

public class ProgramaEducativo {
    private int idProgramaEducativo;
    private String nombre;

    public ProgramaEducativo() {
    }

    public int getIdProgramaEducativo() {
        return this.idProgramaEducativo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ProgramaEducativo{" + "IdProgramaEducativo=" + idProgramaEducativo 
                + ", nombre=" + nombre + '}';
    }

    
    @Override
    public boolean equals(Object objeto){
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof ProgramaEducativo) {
            ProgramaEducativo programaEducativo = (ProgramaEducativo) objeto;
            esIgual=this.getIdProgramaEducativo()==programaEducativo.getIdProgramaEducativo() && 
                    this.getNombre().equals(programaEducativo.getNombre());
        }
        return esIgual;
    }

    
}
