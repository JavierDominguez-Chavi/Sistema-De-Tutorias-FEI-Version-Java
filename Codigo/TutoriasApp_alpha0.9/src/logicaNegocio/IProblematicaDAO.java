package logicaNegocio;
import dominio.AcademicoRol;
import dominio.Problematica;
import dominio.TutoriaAcademica;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IProblematicaDAO {
    public boolean registrarProblematica(Problematica problematica) throws SQLException;
    public boolean eliminarProblematica(int idProblematica) throws SQLException;
    public Problematica obtenerProblematicaPorId(int idProblematica) throws SQLException;
    public List<Problematica> obtenerFechaExperienciaEducativaProblematicaPorProgramaEducativo
        (AcademicoRol academicoRol) throws SQLException;
    public boolean modificarProblematica(Problematica problematica) throws SQLException;
    public ArrayList<Problematica> obtenerListadoDeProblematicasPorIdTutoriaAcademica(TutoriaAcademica tutoria)
        throws SQLException;
    public List<Problematica> obtenerFechaExperienciaEducativaProblematicaPorRolAcademico 
        (AcademicoRol academicoRol) throws SQLException;
}
