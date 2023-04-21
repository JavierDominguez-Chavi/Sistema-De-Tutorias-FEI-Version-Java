package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class RolDAO implements IRolDAO{
    @Override
    public Rol obtenerRolPorIdRol(int idRol) throws SQLException { 
        DataBaseConnection db = new DataBaseConnection();
        Rol rolConseguido = new Rol();
        String consulta = 
        "SELECT * FROM roles WHERE idrol = ?";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idRol);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                rolConseguido.setIdRol(resultado.getInt("idRol"));
                rolConseguido.setNombreRol(resultado.getString("nombreRol"));
            }
        }finally{
            db.desconectar();
        }
        return rolConseguido;
    }
    
}
