package controlador;

import logicaNegocio.AcademicoDAO;
import logicaNegocio.EstudianteDAO;
import dominio.Estudiante;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ConsultaEstudianteFXMLController implements Initializable {
    @FXML
    private Label labelNombreEstudiante;
    @FXML
    private Label labelMatricula;
    @FXML
    private Label labelSemestre;
    @FXML
    private Label labelCorreoPersonal;
    @FXML
    private Label labelCorreoInstitucional;
    @FXML
    private Label labelNombreTutorAsignado;
    @FXML
    private Button buttonRegresar;
        
    private Estudiante estudianteConsulta = new Estudiante();
    
    public void setEstudianteConsulta(Estudiante estudianteConsulta){
        this.estudianteConsulta = estudianteConsulta;
        cargarCamposGUI();
    }

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        cargarCamposGUI();
    }

    private void cargarCamposGUI(){
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        AcademicoDAO academicoDAO = new AcademicoDAO();
        try{
            this.estudianteConsulta = 
                    estudianteDAO.obtenerEstudiantePorMatricula(
                            this.estudianteConsulta.getMatricula());
            this.estudianteConsulta.setAcademico(academicoDAO.obtenerAcademicoPorId(
                    this.estudianteConsulta.getAcademico().getIdAcademico()));
            labelNombreEstudiante.setText(this.estudianteConsulta.getNombreCompleto());
            labelMatricula.setText(this.estudianteConsulta.getMatricula());
            labelSemestre.setText(Integer.toString(this.estudianteConsulta.getSemestreQueCursa()));
            labelCorreoPersonal.setText(this.estudianteConsulta.getCorreoElectronicoPersonal());
            labelCorreoInstitucional.setText(this.estudianteConsulta.getCorreoElectronicoInstitucional());
            labelNombreTutorAsignado.setText(this.estudianteConsulta.getAcademico().getNombreCompleto());
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenarioPrincipal = (Stage) labelMatricula.getScene().getWindow();
                escenarioPrincipal.close();
            });
        }
    }
    
    @FXML
    private void regresar(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegresar)){
            Stage escenarioPrincipal = (Stage) labelMatricula.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
    
}
