package logicaNegocio;

import dominio.Solucion;
import accesoADatos.DataBaseConnection;
import dominio.Academico;
import dominio.Problematica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SolucionDAO implements ISolucionDAO{

    
    @Override
    public boolean registrarSolucionaProblematica(Problematica problematicaSolucion) throws SQLException{ 
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionRegistroSolucion = false;
        String consulta = "INSERT INTO soluciones  "
            + "(fecha, descripcion, idAcademico, idProblematica) values(?,?,?,?);"; 
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, problematicaSolucion.getSolucion().getFecha().toString());
            sentencia.setString(2, problematicaSolucion.getSolucion().getDescripcion());
            sentencia.setInt(3,problematicaSolucion.getSolucion().getTutor().getIdAcademico());
            sentencia.setInt(4,problematicaSolucion.getIdProblematica());
            sentencia.executeUpdate();
            verificacionRegistroSolucion = true;
        }finally{
            db.desconectar();
        }
        return verificacionRegistroSolucion;
    }

 
    
    @Override
    public Solucion obtenerSolucionaProblematicaPorIdProblematicaRelacionAcademico
        (int idProblematica) throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        Solucion solucion = new Solucion();
        String consulta = "SELECT * FROM soluciones WHERE idProblematica = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idProblematica);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                AcademicoDAO academicoDAO = new AcademicoDAO();
                Academico academicoConseguido = 
                    academicoDAO.obtenerAcademicoPorId(resultado.getInt("idAcademico"));
                solucion.setIdSolucion(resultado.getInt("idsolucion"));
                solucion.setFecha(resultado.getDate("fecha"));
                solucion.setDescripcion(resultado.getString("descripcion"));
                solucion.setTutor(academicoConseguido);
            } 
        }finally{
            db.desconectar();
        }
        return solucion;
    }
    
    @Override
    public boolean verificarSolucionaProblematicaPorIdProblematica(int idProblematica) throws SQLException{ 
        DataBaseConnection db = new DataBaseConnection();
        String consulta = "SELECT * FROM soluciones WHERE idProblematica = ?;";
        boolean verificacionSolucionDeProblematica = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idProblematica);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                verificacionSolucionDeProblematica = true;
            } 
        } finally{
            db.desconectar();
        }
        return verificacionSolucionDeProblematica;
    }
    
    @Override
    public boolean modificarSolucionProblematica(Solucion solucion) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        String consulta = 
        "UPDATE soluciones " +
        "SET fecha = ?, descripcion = ?, idAcademico = ? "+
        "WHERE soluciones.idSolucion = ?";
        boolean verificacionModificacionSolucion = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setDate(1, java.sql.Date.valueOf(solucion.getFecha().toString()));
            sentencia.setString(2,solucion.getDescripcion());
            sentencia.setInt(3, solucion.getTutor().getIdAcademico());
            sentencia.setInt(4,solucion.getIdSolucion());
            if (sentencia.executeUpdate()!=0){
                verificacionModificacionSolucion = true;
            }
        }finally {
            db.desconectar();
        }
        return verificacionModificacionSolucion;
    }
}