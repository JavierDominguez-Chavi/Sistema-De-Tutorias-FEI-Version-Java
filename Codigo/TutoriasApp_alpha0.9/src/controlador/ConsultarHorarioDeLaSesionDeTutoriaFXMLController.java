package controlador;

import logicaNegocio.FechaTutoriaDAO;
import logicaNegocio.HorarioDAO;
import logicaNegocio.PeriodoEscolarDAO;
import logicaNegocio.TutoriaAcademicaDAO;
import dominio.FechaTutoria;
import dominio.globales.InformacionSesion;
import dominio.PeriodoEscolar;
import dominio.TutoriaAcademica;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ConsultarHorarioDeLaSesionDeTutoriaFXMLController implements Initializable {

    @FXML
    private ComboBox<PeriodoEscolar> comboBoxPeriodoEscolar;
    @FXML
    private ComboBox<FechaTutoria> comboBoxNumeroDeSesion;
    @FXML
    private Button buttonConsultar;
    @FXML
    private Button buttonCancelar;

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            comboBoxPeriodoEscolar.getItems().addAll(obtenerListaPeriodosEscolares());
            editarFormatoComboBoxPeriodoEscolar();
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                cerrar(true);
            });
        }
    }    
    
    private void cerrar(boolean confirmacion){
        if (confirmacion == true){
            Stage escenario = (Stage)buttonCancelar.getScene().getWindow();
            escenario.close();   
        }
    }
    
    private ArrayList <PeriodoEscolar> obtenerListaPeriodosEscolares() throws SQLException{
        PeriodoEscolarDAO periodoEscolarDao =  new PeriodoEscolarDAO();
        ArrayList <PeriodoEscolar> periodosEscolares =
        periodoEscolarDao.obtenerTodosLosPeriodosEscolaresRegistrados();
        return periodosEscolares;
    }
    
    private void editarFormatoComboBoxPeriodoEscolar(){
        comboBoxPeriodoEscolar.setConverter(new StringConverter<PeriodoEscolar>() {
            @Override
            public String toString(PeriodoEscolar periodoEscolar) {
                return periodoEscolar == null ? null : periodoEscolar.getFechaInicio().toString() 
                + " - " + periodoEscolar.getFechaFin().toString();
            }
            @Override
            public PeriodoEscolar fromString(String string) {
              return null;
            }
        });
    }
    
    @FXML
    private void obtenerNumerosDeSesionDeTutoriaDelPeriodoEscolarSeleccionado(ActionEvent evento){
        try {
            mostrarNumerosDeSesionDeTutoriaPorPeriodo
            (comboBoxPeriodoEscolar.getSelectionModel().getSelectedItem());
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void mostrarNumerosDeSesionDeTutoriaPorPeriodo(PeriodoEscolar periodoSeleccionado) 
    throws SQLException{
        comboBoxNumeroDeSesion.getItems().clear();
        FechaTutoriaDAO fechaTutoriaDAO =  new FechaTutoriaDAO();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        ArrayList <FechaTutoria> fechasTutoria = 
        fechaTutoriaDAO.obtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo(periodoSeleccionado,
        informacionSesion .getProgramaEducativo().getIdProgramaEducativo());
        if(!fechasTutoria.isEmpty()){
            comboBoxNumeroDeSesion.getItems().addAll(fechasTutoria);
            editarFormatoComboBoxNumeroDeSesion();
        }
    }
    
    private void editarFormatoComboBoxNumeroDeSesion(){
        comboBoxNumeroDeSesion.setConverter(new StringConverter<FechaTutoria>() {
            @Override
            public String toString(FechaTutoria fechaTutoria) {
                return  fechaTutoria == null ? null : (Integer.toString(fechaTutoria.getNumeroSesion()));
            }
            @Override
            public FechaTutoria fromString(String string) {
                return null;
            }
        });
    }
    
    @FXML
    private void cancelarConsultaHorarioDeLaSesionDeTutoria(ActionEvent evento){
        Stage escenarioPrincipal = (Stage) buttonConsultar.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    @FXML
    private void consultarHorarioDeLaSesionDeTutoria(ActionEvent evento){
        if(comboBoxPeriodoEscolar.getValue()!=null && comboBoxNumeroDeSesion.getValue()!=null){
            FechaTutoria fechaTutoriaAcademicaSeleccionada = comboBoxNumeroDeSesion.getValue();
            fechaTutoriaAcademicaSeleccionada.setPeriodo(comboBoxPeriodoEscolar.getValue());
            try {
                validarQueExistaElHorarioDeLaSesionDeTutoria(fechaTutoriaAcademicaSeleccionada);
            } catch(SQLException sqlException){
                Utilidades.mensajePerdidaDeConexion();
            }
        }else{
            Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos",
            "No puede dejar ningún campo vacío. Por favor, verifique que todos los campos estén "
            + "seleccionados", Alert.AlertType.WARNING);
        }
    }
    
    private void validarQueExistaElHorarioDeLaSesionDeTutoria
    (FechaTutoria fechaTutoriaAcademicaSeleccionada) throws SQLException{
        TutoriaAcademica tutoriaAcademicaSeleccionada = new TutoriaAcademica();
        HorarioDAO horarioDao = new HorarioDAO();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        tutoriaAcademicaSeleccionada.setTutor(informacionSesion .getAcademicoRol().getAcademico());
        tutoriaAcademicaSeleccionada.setFechaTutoria(fechaTutoriaAcademicaSeleccionada);
        tutoriaAcademicaSeleccionada.getFechaTutoria().setPeriodo(comboBoxPeriodoEscolar.getValue());
        tutoriaAcademicaSeleccionada = 
        tutoriaAcademicaDao.obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademicaSeleccionada);
        if(horarioDao.buscarHorarioExistentePorIdTutoriaAcademica(tutoriaAcademicaSeleccionada)){
            abrirVentanaHorarioDeLaSesionDeTutoria(tutoriaAcademicaSeleccionada);
        }else{
            Utilidades.mostrarAlertaSinConfirmacion("Horario no registrado",
            "El horario de la sesión de tutoría académica seleccionada aún no se encuentra registrado",
            Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void abrirVentanaHorarioDeLaSesionDeTutoria(TutoriaAcademica tutoriaAcademicaSeleccionada){
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource
            ("/vista/HorarioDeLaSesionDeTutoriaFXML.fxml"));
            Parent raiz = cargador.load();
            HorarioDeLaSesionDeTutoriaFXMLController controladorGUIHorarioDeLaSesionDeTutoria = 
            cargador.getController();
            controladorGUIHorarioDeLaSesionDeTutoria.setTutoriaAcademicaSeleccionada(tutoriaAcademicaSeleccionada);
            Scene escenaFormulario = new Scene(raiz);
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setResizable(false);
            escenarioFormulario.setScene(escenaFormulario);
            escenarioFormulario.setTitle("Horario de la sesión de tutoría");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        } catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
}
