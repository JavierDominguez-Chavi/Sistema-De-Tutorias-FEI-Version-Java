package logicaNegocio;
import dominio.Problematica;
import dominio.Solucion;
import java.sql.SQLException;

public interface ISolucionDAO {
    public boolean registrarSolucionaProblematica(Problematica problematicaSolucion) throws SQLException;
    public Solucion obtenerSolucionaProblematicaPorIdProblematicaRelacionAcademico(int idProblematica) 
        throws SQLException;
    public boolean verificarSolucionaProblematicaPorIdProblematica(int idProblematica) throws SQLException;
    public boolean modificarSolucionProblematica(Solucion solucion) throws SQLException;
}
