package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.ExperienciaEducativa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExperienciaEducativaDAO implements IExperienciaEducativaDAO {

    @Override
    public ExperienciaEducativa obtenerExperienciaEducativa(int idExperienciaEducativa) 
    throws SQLException{
        String consulta = 
        "SELECT * " +
        "FROM `experienciaseducativas`" +
        "WHERE idExperienciaEducativa = ?;";
        ExperienciaEducativa experienciaEducativaObtenida = new ExperienciaEducativa();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idExperienciaEducativa);
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                experienciaEducativaObtenida.setIdExperienciaEducativa(
                        resultado.getInt("IdExperienciaEducativa"));
                experienciaEducativaObtenida.setNombre(resultado.getString("nombre"));
            }
        } finally {
            db.desconectar();
        }
        return experienciaEducativaObtenida;
    }    
    
}
