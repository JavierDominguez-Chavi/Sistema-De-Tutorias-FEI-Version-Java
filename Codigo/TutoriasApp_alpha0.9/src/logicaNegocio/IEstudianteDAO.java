package logicaNegocio;

import dominio.Academico;
import dominio.Estudiante;
import dominio.ProgramaEducativo;
import java.util.ArrayList;
import java.sql.SQLException;

public interface IEstudianteDAO {
    public Estudiante obtenerEstudiantePorMatricula(String matricula)
        throws SQLException;
    public boolean registrarEstudiante(Estudiante estudiante) 
        throws SQLException;
    public ArrayList<Estudiante> obtenerEstudiantesPorIdDelTutor(Academico tutor) throws SQLException;
    public boolean asignarTutorAcademico(String matricula, int idAcademico)
        throws SQLException;
    public ArrayList<Estudiante> obtenerEstudiantesConTutorPorProgramaEducativo(int idProgramaEducativo) 
        throws SQLException;
    public ArrayList<Estudiante> obtenerEstudiantesSinTutorPorProgramaEducativo(int idProgramaEducativo) 
        throws SQLException;
    public ArrayList<Estudiante> obtenerEstudiantesPorIdProgramaEducativo(int idProgramaEducativo) 
        throws SQLException;
    public ArrayList<Estudiante> obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo
        (Academico tutor, ProgramaEducativo programaEducativo) throws SQLException;
}
