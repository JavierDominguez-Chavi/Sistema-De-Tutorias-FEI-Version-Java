package logicaNegocio;

import dominio.Academico;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAcademicoDAO {
    public boolean registrarAcademico(Academico academico) throws SQLException;
    public Academico obtenerAcademicoPorId(int idAcademico) throws SQLException;
    public ArrayList<Academico> 
        obtenerNombreCompletoDeTutoresPorIdProgramaEducativo(int idProgramaEducativo, int idRol) 
        throws SQLException; 
    public int obtenerNumeroDeTutoradosDeTutorPorIdAcademico(int idAcademico)  throws SQLException; 
    public ArrayList<Academico> obtenerNombreCompletoDeProfesores(int idRol) throws SQLException; 
    public boolean modificarAcademico (Academico academicoModificado) throws SQLException; 
    public int obtenerMaximoIdAcademicoInsertado() throws SQLException;
    public ArrayList<Academico> obtenerAcademicosPorProgramaEducativo(int idProgramaEducativo) 
        throws SQLException;
    public ArrayList<Academico> obtenerTutoresAcademicosPorIdProgramaEducativo
        (int idProgramaEducativo, int idRol)  throws SQLException;
    public boolean buscarNombreDeAcademicoExistente(Academico academico) throws SQLException;
    public boolean buscarCorreoElectronicoPersonalDeAcademicoExistente(Academico academico)  
        throws SQLException;
    public boolean buscarCorreoElectronicoInstitucionalDeAcademicoExistente(Academico academico) 
        throws SQLException;
}
