package dominio;

public class Rol {
    private int idRol;
    private String nombreRol;

    public Rol() {
        
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Override
    public String toString() {
        return "Rol{" + "idRol=" + idRol + ", nombreRol=" + nombreRol + '}';
    }

    public Rol(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }
    
    @Override
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof Rol) {
            Rol rol = (Rol) objeto;
            esIgual=
                this.getIdRol()==rol.getIdRol()&& 
                this.nombreRol.equals(rol.getNombreRol());
        }
        return esIgual;
    }
}
