package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.TutoriaAcademica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TutoriaAcademicaDAO implements ITutoriaAcademicaDAO{
    @Override
    public TutoriaAcademica obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria
        (TutoriaAcademica tutoriaAcademica) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        String consulta = 
        "SELECT idtutoriaacademica FROM tutoriasacademicas WHERE (idAcademico = ?) AND (idFechaTutoria = ?);";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutoriaAcademica.getTutor().getIdAcademico());
            sentencia.setInt(2, tutoriaAcademica.getFechaTutoria().getIdFechaTutoria());
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                tutoriaAcademica.setIdTutoriaAcademica(resultado.getInt("idtutoriaacademica"));
            }
        } finally {
            db.desconectar();
        }
        return tutoriaAcademica;
    }
 
    @Override
    public boolean validarEntregaDeReportesDeTutoria(TutoriaAcademica tutoriaAcademica)throws SQLException{ 
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionEntregaReporte = false;
        String consulta = "SELECT idtutoriaacademica FROM tutoriasacademicas WHERE reporteEntregado = ?"
        + " AND idFechaTutoria = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setBoolean(1, tutoriaAcademica.isReporteEntregado());
            sentencia.setInt(2, tutoriaAcademica.getFechaTutoria().getIdFechaTutoria());
            ResultSet resultado = sentencia.executeQuery();
            if(resultado.next()){
                verificacionEntregaReporte = true;
            }   
        } finally{
            db.desconectar();
        }
        return verificacionEntregaReporte;
    }
    
    @Override
    public ArrayList<TutoriaAcademica> obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregado
    (TutoriaAcademica tutoriaAcademica)  throws SQLException{
        ArrayList <TutoriaAcademica> tutoriasAcademicas = new ArrayList<>();
        DataBaseConnection db = new DataBaseConnection();
        String consulta = 
        "SELECT*  FROM tutoriasacademicas WHERE idFechaTutoria = ? AND reporteEntregado = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutoriaAcademica.getFechaTutoria().getIdFechaTutoria());
            sentencia.setBoolean(2, tutoriaAcademica.isReporteEntregado());
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                do{
                    TutoriaAcademica tutoria = new TutoriaAcademica();
                    AcademicoDAO daoAcademico = new AcademicoDAO();
                    tutoria.setIdTutoriaAcademica(resultado.getInt("idtutoriaacademica"));
                    tutoria.setComentarioGeneral(resultado.getString("ComentarioGeneral"));
                    tutoria.setTutor(daoAcademico.obtenerAcademicoPorId(resultado.getInt("idAcademico")));
                    tutoria.getFechaTutoria().setIdFechaTutoria(resultado.getInt("idFechaTutoria"));
                    tutoria.setReporteEntregado(resultado.getBoolean("reporteEntregado"));
                    tutoriasAcademicas.add(tutoria);
                }while(resultado.next());  
            }
        } finally {
            db.desconectar();
        }
        return tutoriasAcademicas;
    }
    
    @Override
    public boolean buscarReporteDeTutoriaAcademicaEntregado(TutoriaAcademica tutoriaAcademica)
    throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionReporteEntregado = false;
        String consulta = "SELECT* FROM tutoriasacademicas "
            + "WHERE reporteEntregado = ? AND idFechaTutoria = ? "
            + "AND idAcademico = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setBoolean(1, tutoriaAcademica.isReporteEntregado());
            sentencia.setInt(2, tutoriaAcademica.getFechaTutoria().getIdFechaTutoria());
            sentencia.setInt(3, tutoriaAcademica.getTutor().getIdAcademico());
            ResultSet resultado = sentencia.executeQuery();
            if(resultado.next()){
                verificacionReporteEntregado = true;
            }   
        } finally{
            db.desconectar();
        }
        return verificacionReporteEntregado;
    }
    
    @Override
    public TutoriaAcademica obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(TutoriaAcademica tutoria)
    throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        String consulta = 
        "SELECT idtutoriaacademica, ComentarioGeneral FROM tutoriasacademicas "
                + "WHERE (idAcademico = ?) AND (idFechaTutoria = ?);";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutoria.getTutor().getIdAcademico());
            sentencia.setInt(2, tutoria.getFechaTutoria().getIdFechaTutoria());
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                tutoria.setIdTutoriaAcademica(resultado.getInt("idtutoriaacademica"));
                tutoria.setComentarioGeneral(resultado.getString("ComentarioGeneral"));
            }
        } finally {
            db.desconectar();
        }
        return tutoria;
    }
    
    @Override
    public boolean buscarTutoriaAcademicaExistente(TutoriaAcademica tutoria)throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionTutoriaExiste = false;
        String consulta = "SELECT* FROM tutoriasacademicas WHERE idAcademico = ? AND idFechaTutoria = ?";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutoria.getTutor().getIdAcademico());
            sentencia.setInt(2, tutoria.getFechaTutoria().getIdFechaTutoria());
            ResultSet resultado = sentencia.executeQuery();
            if(resultado.next()){
                verificacionTutoriaExiste = true;
            }   
        } finally{
            db.desconectar();
        }
        return verificacionTutoriaExiste;
    }
    
    @Override
    public boolean registrarTutoriaAcademica(TutoriaAcademica tutoria) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        String consulta =
        "INSERT INTO tutoriasacademicas "
            + "(ComentarioGeneral,idAcademico,idFechaTutoria,reporteEntregado) values(?,?,?,?)";
        boolean verificacionRegistroTutoria = false;      
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1,tutoria.getComentarioGeneral());
            sentencia.setInt(2,tutoria.getTutor().getIdAcademico());
            sentencia.setInt(3,tutoria.getFechaTutoria().getIdFechaTutoria());
            sentencia.setBoolean(4, tutoria.isReporteEntregado());
            sentencia.executeUpdate();
            verificacionRegistroTutoria = sentencia.executeUpdate()!=0;
        }finally{
            db.desconectar();
        }
        return verificacionRegistroTutoria;
    }
    
    @Override
    public boolean registrarComentarioGeneralYEntregaDeReporteDeLaTutoria
        (TutoriaAcademica tutoriaAcademica) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionComentarioReporteEntregado = false;
        String consulta = "UPDATE tutoriasacademicas SET ComentarioGeneral= ?, reporteEntregado= ?"
                + " WHERE idtutoriaacademica  = ?";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, tutoriaAcademica.getComentarioGeneral());
            sentencia.setBoolean(2, tutoriaAcademica.isReporteEntregado());
            sentencia.setInt(3, tutoriaAcademica.getIdTutoriaAcademica());
            sentencia.executeUpdate();
            verificacionComentarioReporteEntregado = sentencia.executeUpdate()!=0;
        } finally {
            db.desconectar();
        }
        return verificacionComentarioReporteEntregado;
    }
}
