package logicaNegocio;

import dominio.ProgramaEducativo;
import accesoADatos.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgramaEducativoDAO implements IProgramaEducativoDAO{
    @Override
    public ProgramaEducativo obtenerProgramaEducativoPorId(int idProgramaEducativo) throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        ProgramaEducativo programaEducativo = null;
        String consulta = 
        "SELECT * " +
        "FROM `programaseducativos`" +
        "WHERE idProgramaEducativo = ?;";
        try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if (!resultado.next()){
            }else{
                programaEducativo = new ProgramaEducativo();
                programaEducativo.setIdProgramaEducativo(resultado.getInt("idprogramaeducativo"));
                programaEducativo.setNombre(resultado.getString("nombreProgramaEducativo"));            
            }
        } finally {
            db.desconectar();
        }
        return programaEducativo;
    }

    @Override
    public ArrayList<ProgramaEducativo> obtenerProgramasEducativos() throws SQLException{ 
        ArrayList <ProgramaEducativo> programasEducativos = new ArrayList<ProgramaEducativo>();
        DataBaseConnection db = new DataBaseConnection();
        String consulta =
        "SELECT * FROM programaseducativos";
         try(Connection conexion = db.getConexion()){
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            if(!resultado.next()){
            }else{
                do{
                    ProgramaEducativo programaEducativo = new ProgramaEducativo();
                    programaEducativo.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                    programaEducativo.setNombre(resultado.getString("nombreProgramaEducativo"));
                    programasEducativos.add(programaEducativo);
                }while(resultado.next());
            }
        } finally{
            db.desconectar();
        }
        return programasEducativos;
    }

}
