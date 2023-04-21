package logicaNegocio;

import dominio.ProgramaEducativo;
import java.util.ArrayList;
import java.sql.SQLException;

public interface IProgramaEducativoDAO {
    public ProgramaEducativo obtenerProgramaEducativoPorId(int idProgramaEducativo) throws SQLException;
    public ArrayList<ProgramaEducativo> obtenerProgramasEducativos() throws SQLException;
}
