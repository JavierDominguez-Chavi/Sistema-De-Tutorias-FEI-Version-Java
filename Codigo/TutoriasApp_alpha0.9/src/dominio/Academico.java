package dominio;

public class Academico {
    private int idAcademico;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String contrasenia;
    private String correoElectronicoPersonal;
    private String correoElectronicoInstitucional;


    public Academico() {
    }
   
    public int getIdAcademico() {
        return idAcademico; 
    }
   
    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCorreoElectronico() {
        return correoElectronicoPersonal;
    }

    public void setCorreoElectronicoPersonal(String correoElectronicoPersonal) {
        this.correoElectronicoPersonal = correoElectronicoPersonal;
    }

    public String getCorreoElectronicoInstitucional() {
        return correoElectronicoInstitucional;
    }

    public void setCorreoElectronicoInstitucional(String correoElectronicoInstitucional) {
        this.correoElectronicoInstitucional = correoElectronicoInstitucional;
    }
    
    public String getNombreCompleto(){
        return this.nombre + " " + this.getApellidoPaterno() + " " + this.getApellidoMaterno();
    }
    
    @Override
    public String toString() {
        return "Academico{" + "idAcademico=" + idAcademico + ", nombre=" + nombre + 
                ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + 
                apellidoMaterno + ", contrasenia=" + contrasenia + 
                ", correoElectronicoPersonal=" + correoElectronicoPersonal + 
                ", correoElectronicoInstitucional=" + correoElectronicoInstitucional +  '}';
    }

    public boolean equals(Object objeto){
        boolean verificacion = false;
        if (objeto == this){
            verificacion = true;
        }
        if (objeto!=null && objeto instanceof Academico){
            Academico academico = (Academico) objeto;
            verificacion = this.idAcademico == academico.getIdAcademico()
                && this.nombre.equals(academico.getNombre())
                && this.apellidoPaterno.equals(academico.getApellidoPaterno())
                && this.apellidoMaterno.equals(academico.getApellidoMaterno());
        }
        return verificacion;
    }
}
