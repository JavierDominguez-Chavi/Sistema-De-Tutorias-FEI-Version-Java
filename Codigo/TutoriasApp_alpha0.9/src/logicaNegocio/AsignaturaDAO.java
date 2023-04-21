package logicaNegocio;

import accesoADatos.DataBaseConnection;
import dominio.Academico;
import dominio.Estudiante;
import dominio.ExperienciaEducativa;
import dominio.Asignatura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AsignaturaDAO implements IAsignaturaDAO{

    @Override
    public Asignatura obtenerAsignaturaPorNrc(int NRC) throws SQLException {
        String consulta = 
        "SELECT experienciaseducativas.*, experienciaseducativasacademicos.* " +
        "FROM `experienciaseducativasacademicos` INNER JOIN experienciaseducativas " +
        "ON experienciaseducativasacademicos.idExperienciaEducativa=experienciaseducativas.idexperienciaeducativa " +
        "WHERE NRC = ?;  ";
        Asignatura asignaturaObtenida = new Asignatura();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, NRC);
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                Academico facilitadorDeLaAsignatura = new Academico();
                if (resultado.getInt("idAcademico") != 0){
                    AcademicoDAO academicoDAO = new AcademicoDAO();
                    facilitadorDeLaAsignatura = academicoDAO.obtenerAcademicoPorId(resultado.getInt("idAcademico"));
                }
                ExperienciaEducativa experienciaEducativaDeLaAsignatura = new ExperienciaEducativa();
                experienciaEducativaDeLaAsignatura.setIdExperienciaEducativa(
                        resultado.getInt("idexperienciaeducativa"));
                experienciaEducativaDeLaAsignatura.setNombre(resultado.getString("nombre"));
                
                asignaturaObtenida.setNRC(resultado.getInt("NRC"));
                asignaturaObtenida.setExperienciaEducativa(experienciaEducativaDeLaAsignatura);
                asignaturaObtenida.setAcademico(facilitadorDeLaAsignatura);
            }
        } finally {
            db.desconectar();
        }
        return asignaturaObtenida;
    }
    
    @Override
    public ArrayList<Asignatura> obtenerAsignaturasSinAcademico() throws SQLException {
        String consulta = 
        "SELECT* FROM experienciaseducativasacademicos WHERE idAcademico IS NULL;";
        ArrayList<Asignatura> asignaturasObtenidas = new ArrayList();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            AcademicoDAO academicoDAO = new AcademicoDAO();
            if (!resultado.next()){
            }else{
                do{ 
                    ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
                    Asignatura asignaturaObtenida = new Asignatura();
                    asignaturaObtenida.setNRC(resultado.getInt("NRC"));
                    asignaturaObtenida.setExperienciaEducativa(
                        experienciaEducativaDAO.obtenerExperienciaEducativa(
                            resultado.getInt("idExperienciaEducativa")
                        )
                    );
                    asignaturasObtenidas.add(asignaturaObtenida);
                }while(resultado.next());
            }
        } finally {
            db.desconectar();
        }
        return asignaturasObtenidas;
    }

    @Override
    public boolean asignarAsignaturaAProfesor(int idAcademico, int NRC) 
    throws SQLException{ 
        String consulta =
        "UPDATE experienciaseducativasacademicos SET idAcademico = ? WHERE NRC = ?;";
        boolean verificacionAsignacion = false;
        DataBaseConnection db = new DataBaseConnection();
        try(Connection connection = db.getConexion()){
            PreparedStatement sentencia = connection.prepareStatement(consulta);
            sentencia.setInt(1,idAcademico);
            sentencia.setInt(2,NRC);
            verificacionAsignacion = sentencia.executeUpdate()!=0;
        } finally{
            db.desconectar();
        }
        return verificacionAsignacion; 
    }

    @Override
    public ArrayList<Asignatura> obtenerOfertaAcademicaPorProgramaEducativoYPeriodoEscolar
    (int idProgramaEducativo, int idPeriodo) throws SQLException {
        String consulta = 
        "SELECT  \n" +
        "   experienciaseducativas.idExperienciaEducativa, \n" +
        "   experienciaseducativasacademicos.NRC,\n" +
        "   experienciaseducativas.nombre AS experiencaEducativa, \n" +
        "   academicos.nombre, academicos.apellidoPaterno, academicos.apellidoMaterno\n" +
        "FROM  experienciaseducativasacademicos INNER JOIN  experienciaseducativas \n" +
        "   ON experienciaseducativasacademicos.idExperienciaEducativa = "
            + "experienciaseducativas.idExperienciaEducativa \n" +
        "LEFT JOIN  academicos ON experienciaseducativasacademicos.idAcademico = academicos.idacademico\n" +
        "INNER JOIN experienciasacademicasperiodosescolares ON experienciaseducativasacademicos.NRC = "
            + "experienciasacademicasperiodosescolares.NRC\n" +
        "WHERE experienciaseducativasacademicos.IdProgramaEducativo = ? "
        + "AND experienciasacademicasperiodosescolares.idPeriodo = ?;";
        ArrayList<Asignatura> asignaturasDeLaOfertaAcademica = new ArrayList();
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            sentencia.setInt(2, idPeriodo);
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                do{ 
                    ExperienciaEducativa experienciaEducativaDeLaAsignatura = new ExperienciaEducativa();
                    experienciaEducativaDeLaAsignatura.setIdExperienciaEducativa(
                            resultado.getInt("idExperienciaEducativa"));
                    experienciaEducativaDeLaAsignatura.setNombre(resultado.getString("experiencaEducativa"));
                    Academico facilitadorDeLaAsignatura = new Academico();
                    facilitadorDeLaAsignatura.setNombre(resultado.getString("nombre"));
                    facilitadorDeLaAsignatura.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    facilitadorDeLaAsignatura.setApellidoMaterno(resultado.getString("apellidoMaterno"));           
                    Asignatura asignaturaObtenida = new Asignatura();
                    asignaturaObtenida.setNRC(resultado.getInt("NRC"));                    
                    asignaturaObtenida.setExperienciaEducativa(experienciaEducativaDeLaAsignatura);
                    asignaturaObtenida.setAcademico(facilitadorDeLaAsignatura);
                    asignaturasDeLaOfertaAcademica.add(asignaturaObtenida);
                }while(resultado.next());
            }
        } finally {
            db.desconectar();
        }
        return asignaturasDeLaOfertaAcademica;
    }
    
    @Override
    public ArrayList<Asignatura> 
    obtenerExperienciasEducativasActivasporMatricula(Estudiante estudiante) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Asignatura> experienciasEducativasAcademicos = 
            new ArrayList<Asignatura>();
        String consulta = 
        "SELECT  experienciaseducativas.idexperienciaEducativa, "
        +"experienciaseducativas.nombre, experienciaseducativasacademicos.NRC, "
        +"academicos.nombre, academicos.apellidoPaterno, "
        +"academicos.apellidoMaterno FROM experienciaseducativasestudiantes "
        +"JOIN experienciaseducativasacademicos ON "
        +"experienciaseducativasestudiantes.NRC = experienciaseducativasacademicos.NRC "
        +"JOIN experienciaseducativas ON experienciaseducativasacademicos.idExperienciaEducativa "
        +"= experienciaseducativas.idexperienciaEducativa " 
        +"JOIN academicos ON experienciaseducativasacademicos.idAcademico = academicos.idAcademico "
        +"WHERE experienciaseducativasestudiantes.Matricula = ? ";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, estudiante.getMatricula());
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                do{
                    Asignatura experienciaEducativaAcademico 
                            = new Asignatura();
                    experienciaEducativaAcademico.getExperienciaEducativa().
                            setIdExperienciaEducativa(resultado.getInt("IdexperienciaEducativa"));
                    experienciaEducativaAcademico.getExperienciaEducativa().
                            setNombre(resultado.getString("nombre"));
                    experienciaEducativaAcademico.setNRC(resultado.getInt("NRC"));
                    experienciaEducativaAcademico.getAcademico().setNombre(resultado.getString("nombre"));
                    experienciaEducativaAcademico.getAcademico().
                            setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    experienciaEducativaAcademico.getAcademico().
                            setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    experienciasEducativasAcademicos.add(experienciaEducativaAcademico);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return experienciasEducativasAcademicos;
    }

    @Override
    public boolean verificarExistenciaDeAsignaturas() throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        String consulta = "SELECT * FROM experienciaseducativasacademicos;";
        boolean verificacionExistencia = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            verificacionExistencia = resultado.next();
        }finally{
            db.desconectar();
        }
        return verificacionExistencia;
    }
}