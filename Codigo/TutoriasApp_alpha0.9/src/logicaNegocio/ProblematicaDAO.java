package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.AcademicoRol;
import dominio.Problematica;
import dominio.TutoriaAcademica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProblematicaDAO implements IProblematicaDAO{

    @Override
    public boolean registrarProblematica(Problematica problematica) throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        String consulta =
        "INSERT INTO problematicas values (NULL,?,?,?,?,?)";
        boolean verificacionRegistroProblematica = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1,problematica.getTitulo());
            sentencia.setString(2,problematica.getDescripcion());
            sentencia.setInt(3,problematica.getCurso().getNRC());
            sentencia.setString(4,problematica.getEstudiante().getMatricula());
            sentencia.setInt(5,problematica.getTutoriaAcademica().getIdTutoriaAcademica());
            sentencia.executeUpdate();
            verificacionRegistroProblematica = true;
        }finally{
            db.desconectar();
        }
        return verificacionRegistroProblematica;
    }
    
    @Override
    public boolean eliminarProblematica(int idProblematica) throws SQLException { 
       DataBaseConnection db = new DataBaseConnection();
       String consulta1 = 
       "DELETE FROM problematicas WHERE idProblematica = ?;";
       String consulta2 =
       "DELETE FROM soluciones WHERE idProblematica = ?;";
       boolean verificacionEliminarProblematica = false;
       try(Connection conexion = db.getConexion()){
           PreparedStatement sentencia1 = conexion.prepareStatement(consulta1);
           sentencia1.setInt(1, idProblematica);
           sentencia1.executeUpdate();
           PreparedStatement sentencia2 = conexion.prepareStatement(consulta2);
           sentencia2.setInt(1,idProblematica);
           sentencia2.executeUpdate();
           verificacionEliminarProblematica = true;
        }finally{
           db.desconectar();
       }
       return verificacionEliminarProblematica;
    }
    
    @Override
    public Problematica obtenerProblematicaPorId(int idProblematica) throws SQLException {
        Problematica problematicaConseguida = new Problematica();
        DataBaseConnection db = new DataBaseConnection();
        String consulta =
        "Select * FROM problematicas WHERE idproblematica = ?";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idProblematica);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                problematicaConseguida.setIdProblematica(resultado.getInt("idproblematica"));
                problematicaConseguida.setTitulo(resultado.getString("titulo"));
                problematicaConseguida.setDescripcion(resultado.getString("descripcion"));
                problematicaConseguida.getCurso().setNRC(resultado.getInt("NRC"));
                problematicaConseguida.getEstudiante().setMatricula(resultado.getString("Matricula"));
            }
        }finally{
            db.desconectar();
        }
        return problematicaConseguida;
    }
    
    @Override
    public List<Problematica> obtenerFechaExperienciaEducativaProblematicaPorProgramaEducativo 
        (AcademicoRol academicoRol) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Problematica> problematicas = new ArrayList<>();
        String consulta = 
        "SELECT problematicas.idproblematica, problematicas.titulo, fechastutorias.fechaTutoria, "
        + "experienciaseducativas.nombre FROM problematicas "+ 
        "JOIN estudiantes on problematicas.matricula = estudiantes.matricula "+
        "JOIN experienciaseducativasacademicos ON problematicas.NRC = experienciaseducativasacademicos.NRC " +
        "JOIN experienciaseducativas ON experienciaseducativasacademicos.idExperienciaEducativa = "
            + "experienciaseducativas.idexperienciaeducativa "+
        "JOIN tutoriasacademicas ON problematicas.idTutoriaAcademica = "
            + "tutoriasacademicas.idTutoriaAcademica "+
        "JOIN fechastutorias ON tutoriasacademicas.idFechaTutoria = fechastutorias.idfechatutoria "+
        "WHERE estudiantes.idProgramaEducativo = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, academicoRol.getProgramaEducativo().getIdProgramaEducativo());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    Problematica problematica = new Problematica();
                    problematica.setIdProblematica(resultado.getInt("idProblematica"));
                    problematica.setTitulo(resultado.getString("titulo"));
                    problematica.getTutoriaAcademica().getFechaTutoria().
                        setFechaTutoria(resultado.getDate("fechaTutoria"));
                    problematica.getCurso().getExperienciaEducativa().
                        setNombre(resultado.getString("nombre"));
                    problematicas.add(problematica);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return problematicas;
    }
    
    @Override
    public List<Problematica> obtenerFechaExperienciaEducativaProblematicaPorRolAcademico 
        (AcademicoRol academicoRol) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Problematica> problematicas = new ArrayList<>();
        String consulta = 
        "SELECT problematicas.idproblematica, problematicas.titulo, fechastutorias.fechaTutoria, "
        + "experienciaseducativas.nombre FROM problematicas "+ 
        "JOIN estudiantes on problematicas.matricula = estudiantes.matricula "+
        "JOIN experienciaseducativasacademicos ON problematicas.NRC = experienciaseducativasacademicos.NRC " +
        "JOIN experienciaseducativas ON experienciaseducativasacademicos.idExperienciaEducativa = "
            + "experienciaseducativas.idexperienciaeducativa "+
        "JOIN tutoriasacademicas ON problematicas.idTutoriaAcademica = "
            + "tutoriasacademicas.idTutoriaAcademica "+
        "JOIN fechastutorias ON tutoriasacademicas.idFechaTutoria = fechastutorias.idfechatutoria "+
        "WHERE estudiantes.idProgramaEducativo = ? && estudiantes.idAcademico = ?";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, academicoRol.getProgramaEducativo().getIdProgramaEducativo());
            sentencia.setInt(2, academicoRol.getAcademico().getIdAcademico());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    Problematica problematica = new Problematica();
                    problematica.setIdProblematica(resultado.getInt("idProblematica"));
                    problematica.setTitulo(resultado.getString("titulo"));
                    problematica.getTutoriaAcademica().getFechaTutoria().
                        setFechaTutoria(resultado.getDate("fechaTutoria"));
                    problematica.getCurso().getExperienciaEducativa().
                        setNombre(resultado.getString("nombre"));
                    problematicas.add(problematica);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return problematicas;
    }    
        
    @Override
    public boolean modificarProblematica(Problematica problematica) throws SQLException{ 
        DataBaseConnection db = new DataBaseConnection();
        String consulta = 
        "UPDATE problematicas " +
        "SET titulo = ?, descripcion = ?,"+
        "NRC = ?, matricula = ? "+
        "WHERE problematicas.idProblematica = ?";
        boolean verificacionModificacionProblematica = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, problematica.getTitulo());
            sentencia.setString(2,problematica.getDescripcion());
            sentencia.setInt(3,problematica.getCurso().getNRC());
            sentencia.setString(4, problematica.getEstudiante().getMatricula());
            sentencia.setInt(5, problematica.getIdProblematica());
            if (sentencia.executeUpdate()!=0){
                verificacionModificacionProblematica = true;
            }
        }finally {
            db.desconectar();
        }
        return verificacionModificacionProblematica;
    }
    
    @Override
    public ArrayList<Problematica> obtenerListadoDeProblematicasPorIdTutoriaAcademica(TutoriaAcademica tutoria)
    throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Problematica> problematicas = new ArrayList();
        String consulta = 
        "SELECT problematicas.titulo, problematicas.descripcion, "
            + "experienciaseducativas.nombre, academicos.nombre, "
            + "academicos.apellidoPaterno, academicos.apellidoMaterno FROM problematicas "
        +"JOIN experienciaseducativasacademicos "
                + "ON problematicas.NRC = experienciaseducativasacademicos.NRC "
        +"JOIN experienciaseducativas ON experienciaseducativasacademicos.idExperienciaEducativa = "
            + "experienciaseducativas.idexperienciaeducativa "
        +"JOIN tutoriasacademicas "
            + "ON problematicas.idTutoriaAcademica = tutoriasacademicas.idTutoriaAcademica "
        +"JOIN academicos ON experienciaseducativasacademicos.idAcademico = academicos.idAcademico "
        +"WHERE problematicas.idTutoriaAcademica = ? "
        +"ORDER BY academicos.apellidoPaterno"; 
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tutoria.getIdTutoriaAcademica());
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    Problematica problematica = new Problematica();
                    problematica.setTitulo(resultado.getString("titulo"));
                    problematica.setDescripcion(resultado.getString("descripcion"));
                    problematica.getCurso().getExperienciaEducativa().
                            setNombre(resultado.getString("nombre"));
                    problematica.getCurso().getAcademico().setNombre(
                            resultado.getString("academicos.nombre"));
                    problematica.getCurso().getAcademico().setApellidoPaterno(
                            resultado.getString("apellidoPaterno"));
                    problematica.getCurso().getAcademico().setApellidoMaterno(
                            resultado.getString("apellidoMaterno"));
                    problematicas.add(problematica);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return problematicas;
    }
}
