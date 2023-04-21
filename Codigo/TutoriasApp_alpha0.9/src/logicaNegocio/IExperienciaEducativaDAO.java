package logicaNegocio;

import dominio.ExperienciaEducativa;
import java.sql.SQLException;

public interface IExperienciaEducativaDAO {
    public ExperienciaEducativa obtenerExperienciaEducativa(int idExperienciaEducativa)
        throws SQLException;
}
