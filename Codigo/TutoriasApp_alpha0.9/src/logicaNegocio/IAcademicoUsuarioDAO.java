package logicaNegocio;

import dominio.AcademicoUsuario;
import java.sql.SQLException;

public interface IAcademicoUsuarioDAO {
    public AcademicoUsuario obtenerIdAcademicoUsuarioPorNombreUsuarioyContrasenia(AcademicoUsuario academicoUsuario)
        throws SQLException;
    public boolean registrarAcademicoUsuario(AcademicoUsuario academicoUsuario)  throws SQLException;
    public int obtenerMaximoIdUsuarioInsertado() throws SQLException;
     public boolean actualizarAcademicoUsuarioExistente(AcademicoUsuario academicoUsuario)  
        throws SQLException;
}
