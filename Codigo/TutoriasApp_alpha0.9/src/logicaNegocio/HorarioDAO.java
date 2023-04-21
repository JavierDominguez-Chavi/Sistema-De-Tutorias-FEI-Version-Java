package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.Horario;
import dominio.TutoriaAcademica;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HorarioDAO implements IHorarioDAO {

    @Override
    public boolean registrarHorarioDeLaTutoria(Horario horario) throws SQLException {
        String consulta = 
        "INSERT INTO horarios (asistencia, riesgo, horaTutoria, idTutoriaAcademica, matricula) "+
        "values(?,?,?,?,?);";
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionRegistro = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setBoolean(1, horario.isAsistencia());
            sentencia.setBoolean(2, horario.isRiesgo());
            sentencia.setTime(3, horario.getHoraTutoria());
            sentencia.setInt(4, horario.getTutoriaAcademica().getIdTutoriaAcademica());
            sentencia.setString(5, horario.getEstudiante().getMatricula());
            verificacionRegistro = sentencia.executeUpdate()!=0;
            verificacionRegistro = true;
        } finally {
            db.desconectar();
        }
        return verificacionRegistro;
    }
	
    @Override
    public ArrayList <Horario> obtenerHorariosDeLosEstudiantesPorIdTutoria(Horario horarioABuscar) 
    throws SQLException{
        String consulta = 
        "SELECT horaTutoria, matricula, idhorario, idTutoriaAcademica FROM horarios "+
        "WHERE idTutoriaAcademica = ?;";
        ArrayList horariosObtenidos = new ArrayList <Horario>();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, horarioABuscar.getTutoriaAcademica().getIdTutoriaAcademica());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            } else{
                do{
                    EstudianteDAO estudianteDao = new EstudianteDAO();
                    Horario horarioEncontrado = new Horario();
                    horarioEncontrado.setHoraTutoria(resultado.getTime("horaTutoria"));
                    horarioEncontrado.setEstudiante(estudianteDao.obtenerEstudiantePorMatricula
                    (resultado.getString("matricula")));
                    horarioEncontrado.setIdHorario(resultado.getInt("idhorario"));
                    horarioEncontrado.getTutoriaAcademica().
                        setIdTutoriaAcademica(resultado.getInt("idTutoriaAcademica"));
                    horariosObtenidos.add(horarioEncontrado);
                }while(resultado.next());  
            }
        } finally{
            db.desconectar();
        }
        return horariosObtenidos;
    }
    
    @Override
    public ArrayList <Horario> obtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoria(Horario horario) 
    throws SQLException{
        String consulta = 
        "SELECT matricula FROM horarios WHERE asistencia = ? AND idTutoriaAcademica = ?;";
        ArrayList horariosObtenidos = new ArrayList <Horario>();
        DataBaseConnection db = new  DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setBoolean(1, horario.isAsistencia());
            sentencia.setInt(2, horario.getTutoriaAcademica().getIdTutoriaAcademica());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            } else{
                do{
                    Horario horarioEncontrado = new Horario();
                    horarioEncontrado.getEstudiante().setMatricula(resultado.getString("matricula"));
                    horariosObtenidos.add(horarioEncontrado);
                }while(resultado.next());  
            }
        } finally{
            db.desconectar();
        }
        return horariosObtenidos;
    }
    
    @Override
    public ArrayList <Horario> obtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoria(Horario horario)
    throws SQLException{
        String consulta = 
        "SELECT matricula FROM horarios WHERE riesgo = ? AND idTutoriaAcademica = ?;";
        ArrayList horariosObtenidos = new ArrayList <Horario>();
        DataBaseConnection db = new  DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setBoolean(1, horario.isRiesgo());
            sentencia.setInt(2, horario.getTutoriaAcademica().getIdTutoriaAcademica());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            } else{
                do{
                    Horario horarioEncontrado = new Horario();
                    horarioEncontrado.getEstudiante().setMatricula(resultado.getString("matricula"));
                    horariosObtenidos.add(horarioEncontrado);
                }while(resultado.next());  
            }
        } finally{
            db.desconectar();
        }
        return horariosObtenidos;
    }
    
    @Override
    public ArrayList <Horario> obtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademica
    (int idTutoriaAcademica) throws SQLException{
        String consulta = 
        "SELECT horarios.asistencia, horarios.riesgo, horarios.matricula, estudiantes.nombre, "+
        "estudiantes.apellidoPaterno, estudiantes.apellidoMaterno FROM horarios "+
        "JOIN estudiantes ON horarios.matricula = estudiantes.matricula "+
        "WHERE idTutoriaAcademica = ? ORDER BY estudiantes.apellidoPaterno;";
        ArrayList horariosObtenidos = new ArrayList <Horario>();
        DataBaseConnection db = new  DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idTutoriaAcademica);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            } else{
                do{
                    Horario horarioEncontrado = new Horario();
                    horarioEncontrado.setAsistencia(resultado.getBoolean("asistencia"));
                    horarioEncontrado.setRiesgo(resultado.getBoolean("riesgo"));
                    horarioEncontrado.getEstudiante().setMatricula(resultado.getString("matricula"));
                    horarioEncontrado.getEstudiante().setNombre(resultado.getString("nombre"));
                    horarioEncontrado.getEstudiante().
                        setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    horarioEncontrado.getEstudiante().
                        setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    horariosObtenidos.add(horarioEncontrado);
                }while(resultado.next());  
            }   
        } finally{
            db.desconectar();
        }
        return horariosObtenidos;
    }
    
    @Override
    public boolean buscarHorarioExistentePorIdTutoriaAcademica(TutoriaAcademica tutoriaAcademica)  
    throws SQLException{
        String consulta = 
        "SELECT idhorario FROM horarios WHERE idTutoriaAcademica= ?;";
        boolean verificacionExistencia = false;
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutoriaAcademica.getIdTutoriaAcademica());
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
    public boolean modificarHorarioDeTutoriaPoIdHorario(Horario horario)  throws SQLException{
        String consulta = 
        "UPDATE horarios SET horaTutoria = ? WHERE idhorario = ?;";
        boolean verificacionModificacion = false;
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setTime(1, horario.getHoraTutoria());
            sentencia.setInt(2, horario.getIdHorario());
            verificacionModificacion = sentencia.executeUpdate()!=0; 
        } finally {
            db.desconectar();
        }
        return verificacionModificacion;
    }
    
    @Override
    public boolean buscarHorarioExistentePorIdTutoriaAcademicaYMatricula
    (TutoriaAcademica tutoriaAcademica, String matricula)  throws SQLException{
        String consulta = 
        "SELECT idhorario FROM horarios WHERE idTutoriaAcademica= ? AND matricula = ?;";
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionExistencia = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutoriaAcademica.getIdTutoriaAcademica());
            sentencia.setString(2, matricula);
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
    public boolean registrarAsistenciaySiEstaEnRiesgoElEstudiante(Horario horario)  throws SQLException{
        String consulta = 
        "UPDATE horarios SET asistencia = ?, riesgo = ? WHERE idTutoriaAcademica = ? AND matricula = ?;";
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionRegistro = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setBoolean(1, horario.isAsistencia());
            sentencia.setBoolean(2, horario.isRiesgo());
            sentencia.setInt(3, horario.getTutoriaAcademica().getIdTutoriaAcademica());
            sentencia.setString(4, horario.getEstudiante().getMatricula());
            sentencia.executeUpdate();
            verificacionRegistro = sentencia.executeUpdate()!=0;
        } finally {
            db.desconectar();
        }
        return verificacionRegistro;
    }
}
