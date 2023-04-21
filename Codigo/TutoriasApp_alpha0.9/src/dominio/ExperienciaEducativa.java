package dominio;

public class ExperienciaEducativa {
    private int idExperienciaEducativa;
    private String nombre;

    public ExperienciaEducativa() {
        this.idExperienciaEducativa = 0;
        this.nombre = new String();
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ExperienciaEducativa{" + "idExperienciaEducativa=" + idExperienciaEducativa 
                + ", nombre=" + nombre + '}';
    }
    
    public boolean equals(Object objeto){
        ExperienciaEducativa experienciaRecibida = (ExperienciaEducativa)objeto;
        return (this.idExperienciaEducativa == experienciaRecibida.getIdExperienciaEducativa()
            && this.nombre.equals(experienciaRecibida.getNombre()));
        
    }
    
}
