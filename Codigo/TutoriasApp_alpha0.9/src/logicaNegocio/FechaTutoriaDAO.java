package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.FechaTutoria;
import dominio.PeriodoEscolar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

public class FechaTutoriaDAO implements IFechaTutoriaDAO {

    public FechaTutoriaDAO() {
    }

    @Override
    public boolean registrarFechaTutoria(FechaTutoria fechaTutoria) 
    throws SQLException {
        String consulta =
        "INSERT INTO fechastutorias " +
        "(fechaTutoria,fechaCierreDeReporte,numeroSesion,idPeriodo, idProgramaEducativo) " +
        "VALUES (?,?,?,?,?);";
        boolean verificacionRegistro = false;
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1,fechaTutoria.getFechaTutoria().toString());
            sentencia.setString(2,fechaTutoria.getFechaCierreDeReporte().toString());
            sentencia.setInt(3,fechaTutoria.getNumeroSesion());
            sentencia.setInt(4,fechaTutoria.getPeriodo().getIdPeriodoEscolar());
            sentencia.setInt(5,fechaTutoria.getProgramaEducativo().getIdProgramaEducativo());
            verificacionRegistro = sentencia.executeUpdate()>0;
        } finally {
            db.desconectar();
        }
        return verificacionRegistro;
    }
    
    
    public boolean modificarFechaTutoria(FechaTutoria fechaDeTutoriaModificada) throws SQLException{
        String consulta = 
        "UPDATE fechastutorias " +
        "SET fechaTutoria = ? " +
        "WHERE idFechaTutoria=?;";
        boolean verificacionModificacion = false;
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, fechaDeTutoriaModificada.getFechaTutoria().toString());
            sentencia.setInt(2, fechaDeTutoriaModificada.getIdFechaTutoria());
            verificacionModificacion = sentencia.executeUpdate()!=0;
        } finally {
            db.desconectar();
        }
        return verificacionModificacion;
    }
    public boolean modificarFechaCierre(FechaTutoria fechaDeCierreModificada) throws SQLException {
        String consulta = 
        "UPDATE fechastutorias " +
        "SET fechaCierreDeReporte = ?" +
        "WHERE fechastutorias.idFechaTutoria=?;";
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionModificacion = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, fechaDeCierreModificada.getFechaCierreDeReporte().toString());
            sentencia.setInt(2, fechaDeCierreModificada.getIdFechaTutoria());
            verificacionModificacion = sentencia.executeUpdate()!=0;  
        } finally {
            db.desconectar();
        }
        return verificacionModificacion;
    }

    @Override
    public ArrayList<FechaTutoria> obtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo
    (PeriodoEscolar periodoEscolar, int idProgramaEducativo) throws SQLException{
        String consulta = 
        "SELECT * " +
        "FROM `fechastutorias`" +
        "WHERE fechastutorias.idPeriodo= ? AND idProgramaEducativo=?;";
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<FechaTutoria> fechasTutoriasObtenidas = new ArrayList();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, periodoEscolar.getIdPeriodoEscolar());
            sentencia.setInt(2, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                do{ 
                    FechaTutoria fechaTutoria = new FechaTutoria();
                    fechaTutoria.setIdFechaTutoria(resultado.getInt("idfechatutoria"));
                    fechaTutoria.setFechaTutoria(resultado.getDate("fechaTutoria"));
                    fechaTutoria.setFechaCierreDeReporte(resultado.getDate("fechaCierreDeReporte"));
                    fechaTutoria.setNumeroSesion(resultado.getInt("numeroSesion"));
                    fechaTutoria.getPeriodo().setIdPeriodoEscolar(resultado.getInt("idPeriodo"));
                    fechaTutoria.getProgramaEducativo().
                        setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                    fechasTutoriasObtenidas.add(fechaTutoria);
                }while(resultado.next());
            }
        } finally {
            db.desconectar();
        }
        return fechasTutoriasObtenidas;
    }

}
