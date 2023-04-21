package dominio;

public class AcademicoUsuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasenia;
    private boolean credencial;
    
    public AcademicoUsuario(){}
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public boolean getCredencial() {
        return credencial;
    }

    public void setCredencial(boolean credencial) {
        this.credencial = credencial;
    }
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof AcademicoUsuario) {
            AcademicoUsuario academicoUsuario = (AcademicoUsuario) objeto;
            esIgual= this.getIdUsuario() == academicoUsuario.getIdUsuario() && 
                    this.getNombreUsuario().equals(academicoUsuario.nombreUsuario);
        }
        return esIgual;
    }
    
    @Override
    public String toString(){
        return "Academico Usuario{" + "idUsuario: " + idUsuario + " Nombre usuario: " + nombreUsuario + '}';
    }
}
