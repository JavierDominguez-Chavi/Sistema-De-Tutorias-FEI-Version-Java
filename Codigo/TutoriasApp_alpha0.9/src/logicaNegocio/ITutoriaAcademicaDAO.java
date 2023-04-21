package logicaNegocio;

import dominio.TutoriaAcademica;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ITutoriaAcademicaDAO { 
    public TutoriaAcademica obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria
        (TutoriaAcademica tutoriaAcademica) throws SQLException;
    public boolean validarEntregaDeReportesDeTutoria(TutoriaAcademica tutoriaAcademica)
        throws SQLException;
    public ArrayList <TutoriaAcademica> obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregado
        (TutoriaAcademica tutoriaAcademica)  throws SQLException;  
    public boolean buscarReporteDeTutoriaAcademicaEntregado(TutoriaAcademica tutoriaAcademica)
        throws SQLException;
    public TutoriaAcademica obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(TutoriaAcademica tutoria) 
        throws SQLException;
    public boolean buscarTutoriaAcademicaExistente(TutoriaAcademica tutoria) 
        throws SQLException;
    public boolean registrarTutoriaAcademica(TutoriaAcademica tutoria)
        throws SQLException;
    public boolean registrarComentarioGeneralYEntregaDeReporteDeLaTutoria(TutoriaAcademica tutoriaAcademica)  
        throws SQLException;
}
