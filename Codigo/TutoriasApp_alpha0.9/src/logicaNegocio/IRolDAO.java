package logicaNegocio;

import dominio.Rol;
import java.sql.SQLException;

public interface IRolDAO {
    public Rol obtenerRolPorIdRol(int idRol) throws SQLException;
}
