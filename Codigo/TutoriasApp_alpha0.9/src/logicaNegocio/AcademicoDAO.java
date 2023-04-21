package logicaNegocio;

import dominio.Academico;
import accesoADatos.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AcademicoDAO implements IAcademicoDAO{
    @Override
    public boolean registrarAcademico(Academico academico) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        String consulta = 
        "INSERT INTO academicos values (NULL,?,?,?,?,?);";
        boolean verificacionRegistro = false;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1,academico.getNombre());
            sentencia.setString(2,academico.getApellidoPaterno());
            sentencia.setString(3,academico.getApellidoMaterno());
            sentencia.setString(4,academico.getCorreoElectronico());
            sentencia.setString(5,academico.getCorreoElectronicoInstitucional());
            verificacionRegistro = sentencia.executeUpdate()!=0;
        }finally{
            db.desconectar();
        }
        return verificacionRegistro;
    }

    @Override
    public Academico obtenerAcademicoPorId(int idAcademico) throws SQLException {
        Academico academicoObtenido = new Academico();
        DataBaseConnection db = new DataBaseConnection();
        String consulta =
        "Select* FROM academicos WHERE idAcademico = ?";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idAcademico);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                academicoObtenido.setIdAcademico(resultado.getInt("idAcademico"));
                academicoObtenido.setNombre(resultado.getString("nombre"));
                academicoObtenido.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                academicoObtenido.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                academicoObtenido.setCorreoElectronicoPersonal(resultado.getString("correoElectronicoPersonal"));
                academicoObtenido.setCorreoElectronicoInstitucional(resultado.getString
                ("correoElectronicoInstitucional"));
            }
        } finally{
            db.desconectar();
        }
        return academicoObtenido;
    }
    


    @Override
    public ArrayList<Academico> obtenerAcademicosPorProgramaEducativo(int idProgramaEducativo)  
    throws SQLException{
    ArrayList<Academico> academicosObtenidos = new ArrayList();
        DataBaseConnection db = new DataBaseConnection();
        String consulta =
        "SELECT DISTINCT academicos.idAcademico, academicos.nombre, academicos.apellidoPaterno, "+
        "academicos.apellidoMaterno FROM academicos "+
        "JOIN academicosroles ON academicos.idacademico = academicosroles.idAcademico "+
        "JOIN roles ON academicosroles.idRol = roles.idrol "+
        "JOIN  programaseducativos ON academicosroles.idProgramaEducativo = programaseducativos.IdProgramaEducativo "+
        "WHERE programaseducativos.idProgramaEducativo = ? ORDER BY academicos.apellidoPaterno;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    Academico academico = new Academico();
                    academico.setIdAcademico(resultado.getInt("idAcademico"));
                    academico.setNombre(resultado.getString("nombre"));
                    academico.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    academico.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    academicosObtenidos.add(academico);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return academicosObtenidos;
    }
    
    @Override
    public ArrayList<Academico> obtenerNombreCompletoDeTutoresPorIdProgramaEducativo
    (int idProgramaEducativo, int idRol) throws SQLException{
        String consulta =
        "SELECT academicos.idacademico, academicos.nombre, academicos.apellidoPaterno, " +
        " academicos.apellidoMaterno FROM academicos INNER JOIN academicosroles " +
        "ON academicos.idacademico = academicosroles.idAcademico " +
        "WHERE academicosroles.idProgramaEducativo = ? AND academicosroles.idRol = ?";
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Academico> tutoresObtenidos = new ArrayList<Academico>();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            sentencia.setInt(2, idRol);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    int idAcademico = resultado.getInt("idAcademico");
                    Academico tutor = new Academico();
                    tutor.setIdAcademico(idAcademico);
                    tutor.setNombre(resultado.getString("nombre"));
                    tutor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    tutor.setApellidoMaterno(resultado.getString("apellidoMaterno"));

                    tutoresObtenidos.add(tutor);
                }while(resultado.next());
            }
        } finally{
            db.desconectar();
        }
        return tutoresObtenidos;
    }
    
    @Override
    public ArrayList<Academico> obtenerNombreCompletoDeProfesores(int idRol) throws SQLException{ 
        DataBaseConnection db = new DataBaseConnection();
        ArrayList<Academico> profesoresObtenidos = new ArrayList<Academico>();
        String consulta =
        "SELECT academicos.idAcademico, academicos.nombre, academicos.apellidoPaterno, " +
        "academicos.apellidoMaterno, academicos.correoElectronicoPersonal, " + 
        "academicos.correoElectronicoInstitucional FROM academicos INNER JOIN academicosroles " +
        "ON academicos.idacademico = academicosroles.idAcademico WHERE academicosroles.idRol = ?;";
        try(Connection conneccion = db.getConexion()){
            PreparedStatement sentencia = conneccion.prepareStatement(consulta);
            sentencia.setInt(1, idRol);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    Academico profesor = new Academico();
                    profesor.setIdAcademico(resultado.getInt("idAcademico"));
                    profesor.setNombre(resultado.getString("nombre"));
                    profesor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    profesor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    profesor.setCorreoElectronicoPersonal(resultado.getString("correoElectronicoPersonal"));
                    profesor.setCorreoElectronicoInstitucional
                    (resultado.getString("correoElectronicoInstitucional"));
                    profesoresObtenidos.add(profesor);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return profesoresObtenidos;
    }

    @Override
    public int obtenerNumeroDeTutoradosDeTutorPorIdAcademico(int idAcademico) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        int numTutoradosDelTutor = 0;
        String consulta =
        "SELECT SUM(CASE WHEN academicos.idAcademico = estudiantes.idAcademico THEN 1 ELSE 0 END) " + 
        "AS numTutorados FROM academicos INNER JOIN estudiantes " +
        "ON academicos.idacademico = estudiantes.idAcademico WHERE academicos.idacademico = ?; ";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idAcademico);
            ResultSet resultado = sentencia.executeQuery();
            resultado.next();
            numTutoradosDelTutor = resultado.getInt("numTutorados");
        }finally{
            db.desconectar();
        }
        return numTutoradosDelTutor;
    }
    
    @Override
    public boolean modificarAcademico (Academico academicoModificado) throws SQLException {
        String consulta =
        "UPDATE academicos SET academicos.nombre = ?, academicos.apellidoPaterno = ?, " +
        "academicos.apellidoMaterno = ?, academicos.correoElectronicoPersonal = ?, " +
        "academicos.correoElectronicoInstitucional = ? WHERE idAcademico = ?";
        boolean verificacionModificacion = false;
        DataBaseConnection db = new DataBaseConnection();
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1,academicoModificado.getNombre());
            sentencia.setString(2,academicoModificado.getApellidoPaterno());
            sentencia.setString(3,academicoModificado.getApellidoMaterno());
            sentencia.setString(4,academicoModificado.getCorreoElectronico());
            sentencia.setString(5,academicoModificado.getCorreoElectronicoInstitucional());
            sentencia.setInt(6, academicoModificado.getIdAcademico());
            verificacionModificacion = sentencia.executeUpdate()>0;
        } finally{
            db.desconectar();
        }
        return verificacionModificacion;
    }
    
    @Override
    public boolean buscarNombreDeAcademicoExistente(Academico academico) throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionExistencia = false;
        String consulta = "SELECT* FROM academicos WHERE (nombre = ?) AND (apellidoPaterno= ?) " + 
        "AND (apellidoMaterno = ?);";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, academico.getNombre());
            sentencia.setString(2, academico.getApellidoPaterno());
            sentencia.setString(3, academico.getApellidoMaterno());
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
    public boolean buscarCorreoElectronicoPersonalDeAcademicoExistente(Academico academico)  throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionExistencia = false;
        String consulta = "SELECT correoElectronicoPersonal FROM academicos WHERE correoElectronicoPersonal = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, academico.getCorreoElectronico());
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
    public boolean buscarCorreoElectronicoInstitucionalDeAcademicoExistente(Academico academico) 
    throws SQLException{
        DataBaseConnection db = new DataBaseConnection();
        boolean verificacionExistencia = false;
        String consulta = "SELECT correoElectronicoInstitucional FROM academicos " + 
        "WHERE correoElectronicoInstitucional = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, academico.getCorreoElectronicoInstitucional());
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
    public int obtenerMaximoIdAcademicoInsertado() throws SQLException {
        String consulta = 
        "SELECT MAX(idAcademico) FROM academicos;";
        DataBaseConnection db = new DataBaseConnection();
        int maximoIdAcademicoInsertado = 0;
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            resultado.next();
            maximoIdAcademicoInsertado = resultado.getInt("MAX(idAcademico)");
        } finally{
            db.desconectar();
        }
        return maximoIdAcademicoInsertado;
    }
    
    @Override
    public ArrayList<Academico> obtenerTutoresAcademicosPorIdProgramaEducativo
    (int idProgramaEducativo, int idRol)  throws SQLException{
    ArrayList<Academico> academicosObtenidos = new ArrayList();
        DataBaseConnection db = new DataBaseConnection();
        String consulta =
        "SELECT academicos.idAcademico, academicos.nombre, academicos.apellidoPaterno, " +
        "academicos.apellidoMaterno FROM academicos " +
        "JOIN academicosroles ON academicos.idacademico = academicosroles.idAcademico "+
        "JOIN roles ON academicosroles.idRol = roles.idrol "+
        "JOIN  programaseducativos ON academicosroles.idProgramaEducativo = " +
        "programaseducativos.IdProgramaEducativo WHERE programaseducativos.idProgramaEducativo = ? " + 
        "AND academicosRoles.idRol = ? ORDER BY academicos.apellidoPaterno;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1,idProgramaEducativo);
            sentencia.setInt(2, idRol);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                Academico academico = new Academico();
                academico.setIdAcademico(resultado.getInt("idAcademico"));
                academico.setNombre(resultado.getString("nombre"));
                academico.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                academico.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                academicosObtenidos.add(academico);
                }while(resultado.next());
            }
        }finally{
            db.desconectar();
        }
        return academicosObtenidos;
    }
}

