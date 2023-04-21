package logicaNegocio;

import dominio.Horario;
import dominio.TutoriaAcademica;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IHorarioDAO {
    public ArrayList <Horario> obtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoria(Horario horario) 
        throws SQLException;
    public ArrayList <Horario> obtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoria(Horario horario)
        throws SQLException;
    public ArrayList <Horario> obtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademica(int idTutoriaAcademica)
        throws SQLException;
    public boolean buscarHorarioExistentePorIdTutoriaAcademica(TutoriaAcademica tutoriaAcademica)  
        throws SQLException;
    public boolean modificarHorarioDeTutoriaPoIdHorario(Horario horario)  
        throws SQLException;
    public ArrayList <Horario> obtenerHorariosDeLosEstudiantesPorIdTutoria(Horario horarioABuscar) 
        throws SQLException;
    public boolean buscarHorarioExistentePorIdTutoriaAcademicaYMatricula
        (TutoriaAcademica tutoriaAcademica, String matricula)  throws SQLException;
    public boolean registrarAsistenciaySiEstaEnRiesgoElEstudiante(Horario horario)  
        throws SQLException;
    public boolean registrarHorarioDeLaTutoria(Horario horario) 
        throws SQLException;
}
