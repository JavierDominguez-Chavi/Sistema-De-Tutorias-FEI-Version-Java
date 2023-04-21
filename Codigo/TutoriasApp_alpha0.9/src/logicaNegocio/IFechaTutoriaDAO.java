package logicaNegocio;

import dominio.FechaTutoria;
import dominio.PeriodoEscolar;
import java.util.ArrayList;
import java.sql.SQLException;


public interface IFechaTutoriaDAO {
    public boolean registrarFechaTutoria(FechaTutoria fecha) throws SQLException;
    public boolean modificarFechaTutoria(FechaTutoria nuevaFecha) throws SQLException;
    public boolean modificarFechaCierre(FechaTutoria nuevaFeha) throws SQLException;
    public ArrayList<FechaTutoria> obtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo 
        (PeriodoEscolar periodoEscolar, int idProgramaEducativo) throws SQLException;
}
