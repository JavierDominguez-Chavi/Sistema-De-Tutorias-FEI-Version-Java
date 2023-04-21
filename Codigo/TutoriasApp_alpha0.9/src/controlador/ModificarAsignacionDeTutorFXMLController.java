package controlador;

import logicaNegocio.EstudianteDAO;
import dominio.Estudiante;
import dominio.globales.InformacionSesion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class ModificarAsignacionDeTutorFXMLController extends AsignacionDeTutorFXMLController 
implements  Initializable{
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        super.initialize(localizadorRecursos, paqueteRecursos);
    }
    protected ArrayList<Estudiante> obtenerEstudiantes() throws SQLException{
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ArrayList<Estudiante> listaEstudiantes = new ArrayList<Estudiante>();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        int programaEducativo = 
            informacionSesion.getProgramaEducativo().getIdProgramaEducativo();
        listaEstudiantes.addAll(
            estudianteDAO.obtenerEstudiantesConTutorPorProgramaEducativo(programaEducativo)
        );
        listaEstudiantes.addAll(
            estudianteDAO.obtenerEstudiantesSinTutorPorProgramaEducativo(programaEducativo)
        );
        return listaEstudiantes;
    }
}
