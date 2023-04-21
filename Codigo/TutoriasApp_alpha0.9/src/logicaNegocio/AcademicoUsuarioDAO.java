package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.AcademicoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import encriptador.SHA_512;

public class AcademicoUsuarioDAO implements IAcademicoUsuarioDAO{

    @Override
    public AcademicoUsuario obtenerIdAcademicoUsuarioPorNombreUsuarioyContrasenia(AcademicoUsuario academicoUsuario) 
    throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        SHA_512 encriptador = new SHA_512();
        String consulta = 
        "SELECT idUsuario FROM academicosusuarios WHERE (nombreUsuario = ?) AND (contrasenia = ?)";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1,academicoUsuario.getNombreUsuario());
            sentencia.setString(2,encriptador.getSHA512(academicoUsuario.getContrasenia()));
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                academicoUsuario.setIdUsuario(resultado.getInt("idUsuario"));
                academicoUsuario.setCredencial(true);
            }
        }finally{
            db.desconectar();
        }
        return academicoUsuario;
    }
    
    @Override
    public boolean registrarAcademicoUsuario(AcademicoUsuario academicoUsuario)  throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionRegistro = false;
        SHA_512 encriptador = new SHA_512();
        String consulta = "INSERT INTO academicosusuarios (nombreUsuario, contrasenia) values(?,?);";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, academicoUsuario.getNombreUsuario());
            sentencia.setString(2, encriptador.getSHA512(academicoUsuario.getContrasenia()));
            verificacionRegistro = sentencia.executeUpdate()!=0;
        } finally {
            db.desconectar();
        }
        return verificacionRegistro;
    }
    
    @Override
    public boolean actualizarAcademicoUsuarioExistente(AcademicoUsuario academicoUsuario)  throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionActualizacion = false;
        SHA_512 encriptador = new SHA_512();
        String consulta = "UPDATE academicosusuarios SET nombreUsuario = ?, contrasenia = ? WHERE idUsuario = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, academicoUsuario.getNombreUsuario());
            sentencia.setString(2, encriptador.getSHA512(academicoUsuario.getContrasenia()));
            sentencia.setInt(3, academicoUsuario.getIdUsuario());
            verificacionActualizacion = sentencia.executeUpdate()!=0;
        } finally{
            db.desconectar();
        }
        return verificacionActualizacion;
    }
    
    @Override
    public int obtenerMaximoIdUsuarioInsertado() throws SQLException {
        String consulta = 
        "SELECT MAX(idUsuario) FROM academicosusuarios;";
        DataBaseConnection db = new DataBaseConnection();
        int maximoIdUsuarioInsertado = 0;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            resultado.next();
            maximoIdUsuarioInsertado = resultado.getInt("MAX(idUsuario)");
        } finally{
            db.desconectar();
        }
        return maximoIdUsuarioInsertado;
    }
 }

