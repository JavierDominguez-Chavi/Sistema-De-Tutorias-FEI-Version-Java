package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.Academico;
import dominio.Estudiante;
import dominio.ProgramaEducativo;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EstudianteDAO implements IEstudianteDAO{

    @Override
    public Estudiante obtenerEstudiantePorMatricula(String matricula) throws SQLException{
        String consulta = "SELECT matricula, semestreQueCursa, nombre, apellidoPaterno, apellidoMaterno, " +
        "correoElectronicoPersonal, correoElectronicoInstitucional, idAcademico " + 
        "FROM estudiantes WHERE matricula = ?;";
        Estudiante estudianteObtenido = new Estudiante();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, matricula);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
                estudianteObtenido.setMatricula("-1");
            } else{
                estudianteObtenido.setMatricula(resultado.getString("matricula"));
                estudianteObtenido.setSemestreQueCursa(resultado.getInt("semestreQueCursa"));
                estudianteObtenido.setNombre(resultado.getString("nombre"));
                estudianteObtenido.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                estudianteObtenido.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                estudianteObtenido.setCorreoElectronicoPersonal(
                        resultado.getString("correoElectronicoPersonal"));
                estudianteObtenido.setCorreoElectronicoInstitucional(
                        resultado.getString("correoElectronicoInstitucional"));
                estudianteObtenido.getAcademico().setIdAcademico(resultado.getInt("idAcademico"));
            }   
        } finally{
            db.desconectar();
        }
        return estudianteObtenido;
    }

    @Override
    public boolean registrarEstudiante(Estudiante estudiante) throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionRegistro = false;
        String consulta = 
        "INSERT INTO estudiantes (matricula,semestreQueCursa,nombre,apellidoPaterno,apellidoMaterno, " +
        "correoElectronicoPersonal,correoElectronicoInstitucional,idProgramaEducativo) "+
        "values(?,?,?,?,?,?,?,?);";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, estudiante.getMatricula());
            sentencia.setInt(2, estudiante.getSemestreQueCursa());
            sentencia.setString(3, estudiante.getNombre());
            sentencia.setString(4, estudiante.getApellidoPaterno());
            sentencia.setString(5, estudiante.getApellidoMaterno());
            sentencia.setString(6, estudiante.getCorreoElectronicoPersonal());
            sentencia.setString(7, estudiante.getCorreoElectronicoInstitucional());
            sentencia.setInt(8, estudiante.getProgramaEducativo().getIdProgramaEducativo());
            verificacionRegistro = sentencia.executeUpdate()>0;
        } finally{
            db.desconectar();
        }
        return verificacionRegistro;
    }

    @Override
    public ArrayList<Estudiante> obtenerEstudiantesPorIdDelTutor(Academico tutor) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Estudiante> estudiantesObtenidos = new ArrayList();
        String consulta = "SELECT matricula, nombre, apellidoPaterno, apellidoMaterno FROM estudiantes " + 
        "WHERE idAcademico = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutor.getIdAcademico());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            } else{
                do{
                    Estudiante estudiante = new Estudiante();
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiantesObtenidos.add(estudiante);
                }while(resultado.next());  
            }   
        } finally{
            db.desconectar();
        }
        return estudiantesObtenidos;
    }
    
    @Override
    public ArrayList<Estudiante> obtenerEstudiantesConTutorPorProgramaEducativo(int idProgramaEducativo) 
    throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Estudiante> estudiantesObtenidos = new ArrayList();
        int idAcademico = 0;
        String consulta =
        "SELECT * FROM `estudiantes` WHERE idProgramaEducativo = ? AND idAcademico IS NOT NULL;";
        try(Connection conexion = db.getConexion()){
            AcademicoDAO academicoDAO = new AcademicoDAO();
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    Estudiante estudiante = new Estudiante();
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));   
                    idAcademico = resultado.getInt("idAcademico");
                    Academico tutor = new Academico();
                    tutor.setIdAcademico(idAcademico);
                    tutor = academicoDAO.obtenerAcademicoPorId(idAcademico);
                    estudiante.setAcademico(tutor);
                    estudiantesObtenidos.add(estudiante);
                }while(resultado.next());
            }
        } finally{
            db.desconectar();
        }
        return estudiantesObtenidos;
    }
    
    @Override
    public ArrayList<Estudiante> obtenerEstudiantesSinTutorPorProgramaEducativo(int idProgramaEducativo) 
    throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Estudiante> estudiantesObtenidos = new ArrayList();
        String consulta =
        "SELECT* FROM `estudiantes` WHERE idProgramaEducativo = ? AND idAcademico IS NULL;";
        try(Connection conexion = db.getConexion()){
            AcademicoDAO academicoDAO = new AcademicoDAO();
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            Academico tutor = new Academico();
            tutor.setIdAcademico(0);
            tutor.setNombre("@");
            tutor.setApellidoPaterno("SIN");
            tutor.setApellidoMaterno("TUTOR");
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    Estudiante estudiante = new Estudiante();
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));   
                    estudiante.setAcademico(tutor);
                    estudiantesObtenidos.add(estudiante);
                }while(resultado.next());
            }
        } finally{
            db.desconectar();
        }
        return estudiantesObtenidos;
    }    

    @Override
    public boolean asignarTutorAcademico(String matricula, int idAcademico)
    throws SQLException{
        boolean verificacionAsignacion = false;
        String consulta = "UPDATE estudiantes SET idAcademico = ? WHERE matricula = ?;";
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idAcademico);
            sentencia.setString(2, matricula);
            verificacionAsignacion = sentencia.executeUpdate()!=0;
        } finally{
            db.desconectar();
        }
        return verificacionAsignacion;
    }
    
    @Override
    public ArrayList<Estudiante> obtenerEstudiantesPorIdProgramaEducativo(int idProgramaEducativo) 
    throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Estudiante> estudiantesObtenidos = new ArrayList<Estudiante>();
        String consulta =
        "SELECT * FROM estudiantes WHERE idProgramaEducativo = ?";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    Estudiante estudiante = new Estudiante();
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setSemestreQueCursa(resultado.getInt("semestreQueCursa"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setCorreoElectronicoPersonal(resultado.getString("correoElectronicoPersonal"));
                    estudiante.setCorreoElectronicoInstitucional(
                            resultado.getString("correoElectronicoInstitucional"));
                    estudiantesObtenidos.add(estudiante);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return estudiantesObtenidos;
    }
   
    @Override 
    public ArrayList<Estudiante> obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo
    (Academico tutor, ProgramaEducativo programaEducativo) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Estudiante> estudiantesObtenidos = new ArrayList();
        String consulta = "SELECT matricula, nombre, apellidoPaterno, apellidoMaterno FROM estudiantes "
                + "WHERE idAcademico = ? && idProgramaEducativo=? ORDER BY apellidoPaterno;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutor.getIdAcademico());
            sentencia.setInt(2, programaEducativo.getIdProgramaEducativo());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            } else{
                do{
                    Estudiante estudiante = new Estudiante();
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiantesObtenidos.add(estudiante);
                }while(resultado.next());  
            }   
        } finally{
            db.desconectar();
        }
        return estudiantesObtenidos;
    }
}
