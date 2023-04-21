package logicaNegocio;

import dominio.PeriodoEscolar;
import accesoADatos.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class PeriodoEscolarDAO implements IPeriodoEscolarDAO {
    
    @Override
    public boolean registrarPeriodoEscolar(PeriodoEscolar periodoEscolar) throws SQLException{
        String consulta =
        "INSERT INTO periodosescolares (nombre, fechaInicio, fechaFin) values (?,?,?);";
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionRegistro = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, periodoEscolar.getNombrePeriodo());
            sentencia.setString(2,periodoEscolar.getFechaInicio().toString());
            sentencia.setString(3,periodoEscolar.getFechaFin().toString());
            verificacionRegistro = sentencia.executeUpdate()!=0;
        }  finally{
            db.desconectar();
        }
        return verificacionRegistro;
    }

    @Override 
    public PeriodoEscolar obtenerPeriodoEscolarPorId(int idPeriodoEscolar) throws SQLException{
        String consulta =
        "Select * FROM periodosescolares WHERE idPeriodo = ?";
        PeriodoEscolar periodoEscolarConseguido = new PeriodoEscolar();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idPeriodoEscolar);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                periodoEscolarConseguido.setNombrePeriodo(resultado.getString("nombre"));
                periodoEscolarConseguido.setIdPeriodoEscolar(resultado.getInt("idPeriodo"));
                periodoEscolarConseguido.setFechaInicio(resultado.getDate("FechaInicio"));
                periodoEscolarConseguido.setFechaFin(resultado.getDate("FechaFin"));
            }
        } finally{
            db.desconectar();
        }
        return periodoEscolarConseguido;
    }
    
    @Override
    public ArrayList <PeriodoEscolar> obtenerTodosLosPeriodosEscolaresRegistrados() 
    throws SQLException {
        String consulta = 
        "SELECT * FROM periodosescolares";
        ArrayList <PeriodoEscolar> periodosEscolaresObtenidos = new ArrayList<PeriodoEscolar>();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                    periodoEscolar.setNombrePeriodo(resultado.getString("nombre"));
                    periodoEscolar.setIdPeriodoEscolar(resultado.getInt("idPeriodo"));
                    periodoEscolar.setFechaInicio(resultado.getDate("FechaInicio"));
                    periodoEscolar.setFechaFin(resultado.getDate("FechaFin"));
                    periodosEscolaresObtenidos.add(periodoEscolar);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return periodosEscolaresObtenidos;
    }
    
    public boolean validarPeriodoEscolarNuevo(PeriodoEscolar periodoEscolar) 
    throws SQLException{
        String consulta =
        "SELECT * FROM `periodosescolares` " +
        "WHERE periodosescolares.nombre = ? ";
        boolean verificacionNoExistencia = false;
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
           PreparedStatement sentencia = conexion.prepareStatement(consulta);
           sentencia.setString(1, periodoEscolar.getNombrePeriodo());
           ResultSet resultado = sentencia.executeQuery();
           verificacionNoExistencia = !resultado.next();
       } finally{
           db.desconectar();
       }
       return verificacionNoExistencia;
    }

    @Override
    public PeriodoEscolar obtenerPeriodoEscolarActualPorFechaDelSistema(LocalDate fechaDelSistema)
    throws SQLException {
        String consulta =
        "SELECT * FROM `periodosescolares` " +
        "WHERE ? " +
        "BETWEEN fechaInicio AND fechaFin;";
        PeriodoEscolar periodoEscolarObtenido = new PeriodoEscolar();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, fechaDelSistema.toString());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    periodoEscolarObtenido.setNombrePeriodo(resultado.getString("nombre"));
                    periodoEscolarObtenido.setIdPeriodoEscolar(resultado.getInt("idPeriodo"));
                    periodoEscolarObtenido.setFechaInicio(resultado.getDate("FechaInicio"));
                    periodoEscolarObtenido.setFechaFin(resultado.getDate("FechaFin"));
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return periodoEscolarObtenido;
    }

    @Override
    public ArrayList<PeriodoEscolar> obtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativo
    (int idProgramaEducativo) throws SQLException {
        String consulta = 
        "SELECT periodosescolares.* "+
        "FROM periodosescolares "+
        "WHERE periodosescolares.idPeriodo NOT IN ( "+
            "SELECT fechastutorias.idPeriodo "+
            "FROM fechastutorias "+ 
            "WHERE fechastutorias.idProgramaEducativo = ? "+
        ");";
        ArrayList<PeriodoEscolar> periodosEscolaresObtenidos = new ArrayList<PeriodoEscolar>();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    PeriodoEscolar periodoEscolarObtenido = new PeriodoEscolar();
                    periodoEscolarObtenido.setNombrePeriodo(resultado.getString("nombre"));
                    periodoEscolarObtenido.setIdPeriodoEscolar(resultado.getInt("idPeriodo"));
                    periodoEscolarObtenido.setFechaInicio(resultado.getDate("FechaInicio"));
                    periodoEscolarObtenido.setFechaFin(resultado.getDate("FechaFin"));
                    periodosEscolaresObtenidos.add(periodoEscolarObtenido);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return periodosEscolaresObtenidos;
    }
    
    @Override
    public ArrayList<PeriodoEscolar> obtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativo
    (int idProgramaEducativo) throws SQLException {
        String consulta = 
        "SELECT periodosescolares.* "+
        "FROM periodosescolares "+
        "WHERE periodosescolares.idperiodo IN ( "+
            "SELECT fechastutorias.idPeriodo "+
            "FROM fechastutorias "+ 
            "WHERE fechastutorias.idProgramaEducativo = ? "+
        ");";
        ArrayList<PeriodoEscolar> periodosEscolaresObtenidos = new ArrayList<PeriodoEscolar>();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    PeriodoEscolar periodoEscolarObtenido = new PeriodoEscolar();
                    periodoEscolarObtenido.setNombrePeriodo(resultado.getString("nombre"));
                    periodoEscolarObtenido.setIdPeriodoEscolar(resultado.getInt("idPeriodo"));
                    periodoEscolarObtenido.setFechaInicio(resultado.getDate("FechaInicio"));
                    periodoEscolarObtenido.setFechaFin(resultado.getDate("FechaFin"));
                    periodosEscolaresObtenidos.add(periodoEscolarObtenido);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return periodosEscolaresObtenidos;
    }

    @Override
    public boolean verificarExistenciaDePeriodosEscolares() throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        String consulta ="SELECT COUNT(*) FROM periodosescolares";
        boolean verificacionExistencia = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            resultado.next();
            verificacionExistencia = resultado.getInt("COUNT(*)")>0;
        } finally{
            db.desconectar();
        }
        return verificacionExistencia;    
    }
    
    
}
