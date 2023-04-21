package dominio.constantes;

public enum Roles {
    JEFE_DE_CARRERA("Jefe(a) de Carrera",2), PROFESOR("Profesor",0), 
    COORDINADOR_DE_TUTORIAS_ACADEMICAS("Coordinador(a) de Tutorías Académicas",3),
    TUTOR_ACADEMICO("Tutor Académico",4), ADMINISTRADOR("Administrador",9), SIN_ROL("Sin Rol",11);

    private String nombreRol;
    private int idRol;

    private Roles(String nombreRol, int idRol) {
        this.nombreRol = nombreRol;
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
    
}
