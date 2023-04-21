package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.AcademicoRol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AcademicoRolDAO implements IAcademicoRolDAO{
    
    @Override
    public boolean registrarAcademicoRol(AcademicoRol academicoRol)  throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        String consulta =
        "INSERT INTO academicosroles VALUES (?,?,?,?);";
        boolean verificacionRegistro = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, academicoRol.getAcademico().getIdAcademico());
            sentencia.setInt(2, academicoRol.getRol().getIdRol());
            sentencia.setInt(3, academicoRol.getProgramaEducativo().getIdProgramaEducativo());
            sentencia.setInt(4, academicoRol.getAcademicoUsuario().getIdUsuario());
            verificacionRegistro = sentencia.executeUpdate()!=0;
        } finally {
            db.desconectar();
        }
        return verificacionRegistro;
    }
    
    @Override
    public AcademicoRol obtenerAcademicoRolPorIdUsuario(AcademicoRol academicoRol) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        String consulta = 
        "SELECT* FROM academicosroles WHERE idUsuario = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,academicoRol.getAcademicoUsuario().getIdUsuario());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
               ProgramaEducativoDAO programaEducativoDao = new ProgramaEducativoDAO();
               RolDAO rolDao = new RolDAO();
               AcademicoDAO daoAcademico = new AcademicoDAO();
               academicoRol.setProgramaEducativo(programaEducativoDao.obtenerProgramaEducativoPorId
               (resultado.getInt("idProgramaEducativo")));
               academicoRol.setRol(rolDao.obtenerRolPorIdRol(resultado.getInt("idRol")));
               academicoRol.setAcademico(daoAcademico.obtenerAcademicoPorId(resultado.getInt("idacademico")));
            }
        } finally{
            db.desconectar();
        }
        return academicoRol;
    }
    
    @Override
    public boolean buscarAcademicoRolExistente(AcademicoRol academicoRol)  throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionExistencia = false;
        String consulta = "SELECT* FROM academicosroles WHERE (idacademico = ?) AND (idProgramaEducativo = ?) " +
        "AND (idRol = ?);";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, academicoRol.getAcademico().getIdAcademico());
            sentencia.setInt(2, academicoRol.getProgramaEducativo().getIdProgramaEducativo());
            sentencia.setInt(3, academicoRol.getRol().getIdRol());
            ResultSet resultado = sentencia.executeQuery();
            if(resultado.next()){
                verificacionExistencia = true;
            }   
        } finally{
            db.desconectar();
        }
        return verificacionExistencia;
    }
    
    @Override
    public boolean actualizarAcademicoRolExistente(AcademicoRol academicoRol)  throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionActualizacion = false;
        String consulta = "UPDATE academicosroles SET idRol = ? WHERE idacademico = ? AND idProgramaEducativo = ? " +
        " AND idUsuario = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, academicoRol.getRol().getIdRol());
            sentencia.setInt(2, academicoRol.getAcademico().getIdAcademico());
            sentencia.setInt(3, academicoRol.getProgramaEducativo().getIdProgramaEducativo());
            sentencia.setInt(4, academicoRol.getAcademicoUsuario().getIdUsuario());
            verificacionActualizacion = sentencia.executeUpdate()!=0;
        } finally{
            db.desconectar();
        }
        return verificacionActualizacion;
    }
    
    @Override
    public AcademicoRol obtenerIdUsuarioDeAcademicoRolExistente(AcademicoRol academicoRol)  throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        String consulta = "SELECT idUsuario FROM academicosroles WHERE (idacademico = ?) " + 
        "AND (idProgramaEducativo = ?) AND (idRol = ?);";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, academicoRol.getAcademico().getIdAcademico());
            sentencia.setInt(2, academicoRol.getProgramaEducativo().getIdProgramaEducativo());
            sentencia.setInt(3, academicoRol.getRol().getIdRol());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                academicoRol.getAcademicoUsuario().setIdUsuario(resultado.getInt("idUsuario"));
            }
        } finally{
            db.desconectar();
        }
        return academicoRol;
    }
}
