package controlador;

import logicaNegocio.EstudianteDAO;
import logicaNegocio.AsignaturaDAO;
import logicaNegocio.ProblematicaDAO;
import logicaNegocio.TutoriaAcademicaDAO;
import dominio.Estudiante;
import dominio.Asignatura;
import dominio.globales.InformacionSesion;
import dominio.Problematica;
import dominio.TutoriaAcademica;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RegistrarProblematicaAcademicaFXMLController implements Initializable {
    @FXML
    private Label labelTitulo;
    @FXML
    private ComboBox <Estudiante> comboBoxEstudiante;
    @FXML
    private TextField textFieldTitulo;
    @FXML
    private TextArea textAreaDescripcion;
    @FXML
    private Button buttonGuardar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private ComboBox<Asignatura> comboBoxAsignaturas;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            comboBoxEstudiante.getItems().addAll(obtenerEstudiantesDelTutorAcademico());
            editarFormatoComboBoxEstudiantes();
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    } 
    
    private ArrayList <Estudiante> obtenerEstudiantesDelTutorAcademico() throws SQLException{
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        estudiantes = 
        estudianteDAO.obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo
        (informacionSesion .getAcademicoRol().getAcademico(), informacionSesion .getProgramaEducativo());
        return estudiantes;
    }
    
    private void editarFormatoComboBoxEstudiantes(){
        comboBoxEstudiante.setConverter(new StringConverter<Estudiante>(){
            @Override
            public String toString(Estudiante estudiante) {
                return estudiante == null ? null : estudiante.getApellidoPaterno() + " " 
                + estudiante.getApellidoMaterno() + " " + estudiante.getNombre();
            }
            @Override
            public Estudiante fromString(String string) {
              return null;
            }
        });
    }
    
    private void obtenerListaAsignaturasDelEstudiante(Estudiante estudianteSeleccionado) 
    throws SQLException{
        comboBoxAsignaturas.getItems().clear();
        AsignaturaDAO asignaturaDAO = 
        new AsignaturaDAO();
        ArrayList<Asignatura> asignaturas = asignaturaDAO.obtenerExperienciasEducativasActivasporMatricula
        (estudianteSeleccionado);
        if(!asignaturas.isEmpty()){
            comboBoxAsignaturas.getItems().addAll(asignaturas);
            editarFormatoComboBoxAsignaturas();
        }
    }
    
    private void editarFormatoComboBoxAsignaturas(){
        comboBoxAsignaturas.setConverter(new StringConverter<Asignatura>(){
            @Override
            public String toString(Asignatura asignatura) {
                return  asignatura == null ? null : 
                asignatura.getExperienciaEducativa().getNombre();
            }
            @Override
            public Asignatura fromString(String string) {
                return null;
            }
        });
    }
    
    private void registrarProlematicaAcademica() throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        Problematica problematica = new Problematica();
        ProblematicaDAO problematicaDAO = new ProblematicaDAO();
        problematica.setTitulo(textFieldTitulo.getText());
        problematica.setDescripcion(textAreaDescripcion.getText());
        problematica.getCurso().setNRC(comboBoxAsignaturas.getValue().getNRC());
        problematica.setEstudiante(comboBoxEstudiante.getValue());
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setTutor(informacionSesion .getAcademicoRol().getAcademico());
        tutoriaAcademica.setFechaTutoria(informacionSesion .getFechaTutoria());
        TutoriaAcademicaDAO daoTutoriaAcademica = new TutoriaAcademicaDAO();
        tutoriaAcademica = 
        daoTutoriaAcademica.obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademica);
        problematica.setTutoriaAcademica(tutoriaAcademica);
        problematicaDAO.registrarProblematica(problematica);
    }
    
    @FXML
    private void obtenerAsignaturasDelEstudiante(ActionEvent evento){
        try {
            obtenerListaAsignaturasDelEstudiante
            (comboBoxEstudiante.getSelectionModel().getSelectedItem());
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    @FXML
    private void guardarProblematicaAcademica(ActionEvent evento){
        if(comboBoxEstudiante.getValue() !=null && comboBoxAsignaturas.getValue() != null 
        && !textFieldTitulo.getText().isBlank()&& !textAreaDescripcion.getText().isBlank()){
            try {
                registrarProlematicaAcademica();
                Utilidades.mostrarAlertaConfirmacion("Información guardada",
                "La información se registró correctamente en el sistema.",Alert.AlertType.INFORMATION);
                if (Utilidades.getOpcion().get() == ButtonType.OK) {
                    Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                    escenarioPrincipal.close();
                }
            } catch(SQLException sqlException){
                Utilidades.mensajePerdidaDeConexion();
            }
        }else{
            Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos",
            "No puede dejar ningún campo vacío. Por favor, verifique que todos los campos estén llenos",
            Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void cancelarRegistroProblematica(ActionEvent evento){
        Utilidades.mostrarAlertaConfirmacion("Cancelar operación",
        "¿Está seguro de que desea cancelar la operación?", Alert.AlertType.CONFIRMATION);
        if (Utilidades.getOpcion().get() == ButtonType.OK) {
            Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
}
