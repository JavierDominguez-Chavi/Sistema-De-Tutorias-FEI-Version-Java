package logicaNegocio;

import dominio.Estudiante;
import dominio.Asignatura;
import java.util.ArrayList;
import java.sql.SQLException;


public interface IAsignaturaDAO {
    public Asignatura obtenerAsignaturaPorNrc(int NRC)
        throws SQLException;
    public ArrayList<Asignatura> obtenerAsignaturasSinAcademico()
        throws SQLException;
    public boolean asignarAsignaturaAProfesor(int idAcademico, int NRC)
        throws SQLException;
    public ArrayList<Asignatura> 
    obtenerOfertaAcademicaPorProgramaEducativoYPeriodoEscolar
        (int idProgramaEducativo, int idPeriodo) throws SQLException;
    public ArrayList<Asignatura> 
        obtenerExperienciasEducativasActivasporMatricula(Estudiante estudiante) throws SQLException;
    public boolean verificarExistenciaDeAsignaturas() throws SQLException;
}
