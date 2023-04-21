package controlador;

import DIG.DIGEstudianteTabla;
import logicaNegocio.AcademicoDAO;
import logicaNegocio.EstudianteDAO;
import dominio.Academico;
import dominio.Estudiante;
import dominio.globales.InformacionSesion;
import dominio.ProgramaEducativo;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ConsultarTutorFXMLController implements Initializable {
    @FXML
    private Label labelNombreAcademico;
    @FXML
    private Label labelCorreoPersonal;
    @FXML
    private Label labelCorreoInstitucional;
    @FXML
    private TableView<DIGEstudianteTabla> tableEstudiantesAsignados;
    @FXML
    private TableColumn<DIGEstudianteTabla, String> tableColumnMatricula;
    @FXML
    private TableColumn<DIGEstudianteTabla, String> tableColumnNombre;
    @FXML
    private Button buttonRegresar;
    
    private ObservableList<DIGEstudianteTabla> listaEstudiantes = FXCollections.observableArrayList();
    private Academico academicoConsulta = new Academico();
    
    public void setAcademicoConsulta(Academico academicoConsulta){
        this.academicoConsulta = academicoConsulta;
        cargarCamposGUI();
    }

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        inicializarColumnas();
        cargarCamposGUI();
    }

    private void cargarCamposGUI(){
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        AcademicoDAO academicoDAO = new AcademicoDAO();
        try{
            this.academicoConsulta = academicoDAO.obtenerAcademicoPorId(
                    this.academicoConsulta.getIdAcademico());
            this.listaEstudiantes = FXCollections.observableArrayList(obtenerEstudiantes(
                    this.academicoConsulta,informacionSesion .getProgramaEducativo()));
            tableEstudiantesAsignados.setItems(listaEstudiantes);
            String nombreCompletoAcademico = this.academicoConsulta.getNombre() + " " 
                    + this.academicoConsulta.getApellidoPaterno() + " " 
                    + this.academicoConsulta.getApellidoMaterno();
            labelNombreAcademico.setText(nombreCompletoAcademico);
            labelCorreoPersonal.setText(this.academicoConsulta.getCorreoElectronico());
            labelCorreoInstitucional.setText(
                    this.academicoConsulta.getCorreoElectronicoInstitucional());
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenarioPrincipal = (Stage) labelNombreAcademico.getScene().getWindow();
                escenarioPrincipal.close(); 
            });
        }
    }
    
    private void inicializarColumnas(){
        tableColumnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }
    
    private ArrayList<DIGEstudianteTabla> obtenerEstudiantes
        (Academico academico, ProgramaEducativo programaEducativo) throws SQLException{
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ArrayList<Estudiante> estudiantesObtenidos = new ArrayList();
        ArrayList<DIGEstudianteTabla> listaEstudiantesObtenida = new ArrayList();
        DIGEstudianteTabla visualizacionEstudianteTabla = new DIGEstudianteTabla();
        estudiantesObtenidos.addAll(
                estudianteDAO.obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo(
                        academico, programaEducativo));
        for(Estudiante estudiante : estudiantesObtenidos){
            visualizacionEstudianteTabla = new DIGEstudianteTabla();
            visualizacionEstudianteTabla.setEstudiante(estudiante);
            listaEstudiantesObtenida.add(visualizacionEstudianteTabla);
        }
        return listaEstudiantesObtenida;
    }

    @FXML
    private void regresar(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegresar)){
                Stage escenarioPrincipal = (Stage) labelNombreAcademico.getScene().getWindow();
                escenarioPrincipal.close();
            }
    }
}
