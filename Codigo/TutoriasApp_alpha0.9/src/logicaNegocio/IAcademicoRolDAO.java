package logicaNegocio;

import dominio.AcademicoRol;
import java.sql.SQLException;

public interface IAcademicoRolDAO {
    public AcademicoRol obtenerAcademicoRolPorIdUsuario(AcademicoRol rolAcademico) throws SQLException;
    public boolean registrarAcademicoRol(AcademicoRol rolAcademico) throws SQLException;
    public boolean buscarAcademicoRolExistente(AcademicoRol rolAcademico) throws SQLException;
    public boolean actualizarAcademicoRolExistente(AcademicoRol rolAcademico) throws SQLException;
    public AcademicoRol obtenerIdUsuarioDeAcademicoRolExistente(AcademicoRol rolAcademico) throws SQLException;
}
