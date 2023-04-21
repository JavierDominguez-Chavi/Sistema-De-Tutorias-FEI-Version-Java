package controlador;

import logicaNegocio.AcademicoDAO;
import logicaNegocio.FechaTutoriaDAO;
import logicaNegocio.PeriodoEscolarDAO;
import logicaNegocio.TutoriaAcademicaDAO;
import dominio.Academico;
import dominio.FechaTutoria;
import dominio.globales.InformacionSesion;
import dominio.PeriodoEscolar;
import dominio.Rol;
import dominio.TutoriaAcademica;
import dominio.constantes.Roles;
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

public class ConsultarReportePorTutorAcademicoFXMLController implements Initializable {
    @FXML
    private ComboBox<PeriodoEscolar> comboBoxPeriodoEscolar;
    @FXML
    private ComboBox<FechaTutoria> comboBoxNumeroDeSesion;
    @FXML
    private ComboBox<Academico> comboBoxTutorAcademico;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonBuscar;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            comboBoxPeriodoEscolar.getItems().addAll(obtenerListaPeriodosEscolares());
            editarFormatoComboBoxPeriodoEscolar();
            comboBoxTutorAcademico.getItems().addAll(obtenerListaTutoresAcademicos());
            editarFormatoComboBoxTutorAcademico();
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                cerrar(true);
            });
        }
    }    
    
    private void cerrar(boolean confirmacion){
        if (confirmacion == true){
            Stage escenario = (Stage)buttonBuscar.getScene().getWindow();
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
    
    private ArrayList<Academico> obtenerListaTutoresAcademicos() throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        AcademicoDAO academicoDao = new AcademicoDAO();
        Rol rolTutor = new Rol();
        int tutorAcademicoRol = Roles.TUTOR_ACADEMICO.getIdRol();
        rolTutor.setIdRol(tutorAcademicoRol);
        ArrayList<Academico> tutoresAcademicos = 
        academicoDao.obtenerTutoresAcademicosPorIdProgramaEducativo
            (informacionSesion .getProgramaEducativo().getIdProgramaEducativo(), rolTutor.getIdRol());
        return tutoresAcademicos;
    }
    
     private void editarFormatoComboBoxTutorAcademico(){
        comboBoxTutorAcademico.setConverter(new StringConverter<Academico>() {
            @Override
            public String toString(Academico tutorAcademico) {
                return tutorAcademico == null ? null : tutorAcademico.getApellidoPaterno() + " " + 
                tutorAcademico.getApellidoMaterno() + " " + tutorAcademico.getNombre();
            }
            @Override
            public Academico fromString(String string) {
              return null;
            }
        });
    }
    
    @FXML
    private void obtenerNumerosDeSesionDeTutoriaPorPeriodo(ActionEvent evento){
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
    private void cancelarConsultaReportePorTutorAcademico(){
        Stage escenarioPrincipal = (Stage) buttonCancelar.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    @FXML
    private void consultarReporteDeTutoriaAcademicaDelTutorAcademicoSeleccionado(){
        if(comboBoxPeriodoEscolar.getValue()!=null && comboBoxNumeroDeSesion.getValue()!=null 
           && comboBoxTutorAcademico.getValue()!=null){
            FechaTutoria fechaTutoriaAcademicaSeleccionada = comboBoxNumeroDeSesion.getValue();
            fechaTutoriaAcademicaSeleccionada.setPeriodo(comboBoxPeriodoEscolar.getValue());
            Academico tutorAcademicoSeleccionado = comboBoxTutorAcademico.getValue();
            try {
                validarQueElTutorAcademicoSeleccionadoEntregoSuReporteDeTutoriaAcademica
                (fechaTutoriaAcademicaSeleccionada, tutorAcademicoSeleccionado);
            } catch(SQLException sqlException){
                Utilidades.mensajePerdidaDeConexion();
            }
        }else{
            Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos",
            "No puede dejar ningún campo vacío. Por favor, verifique que todos los campos estén "
            + "seleccionados", Alert.AlertType.WARNING);
        }
    }
    
    private void validarQueElTutorAcademicoSeleccionadoEntregoSuReporteDeTutoriaAcademica
    (FechaTutoria fechaTutoriaAcademicaSeleccionada, Academico tutorAcademicoSeleccionado) throws SQLException{
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademicaABuscar = new TutoriaAcademica();
        tutoriaAcademicaABuscar.setFechaTutoria(fechaTutoriaAcademicaSeleccionada);
        tutoriaAcademicaABuscar.setReporteEntregado(true);
        tutoriaAcademicaABuscar.setTutor(tutorAcademicoSeleccionado);
        if(tutoriaAcademicaDao.buscarReporteDeTutoriaAcademicaEntregado(tutoriaAcademicaABuscar)){
            abrirVentanaReporteDeTutoriaAcademicaDelTutorAcademicoSeleccionado
            (fechaTutoriaAcademicaSeleccionada, tutorAcademicoSeleccionado);
        }else{
            Utilidades.mostrarAlertaSinConfirmacion("Reporte de tutoría académica no entregado",
            "El tutor académico seleccionado aún no ha entregado el reporte de tutoría de la fecha elegida",
            Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void abrirVentanaReporteDeTutoriaAcademicaDelTutorAcademicoSeleccionado
    (FechaTutoria fechaTutoriaAcademicaSeleccionada, Academico tutorAcademicoSeleccionado){
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource
            ("/vista/ReporteDeTutoriaAcademicaFXML.fxml"));
            Parent raiz = cargador.load();
            ReporteDeTutoriaAcademicaFXMLController 
            controladorGUIReporteDeTutoriaAcademica = cargador.getController();
            controladorGUIReporteDeTutoriaAcademica.setFechaTutoriaAcademicaYTutorAcademicoSeleccionado
            (tutorAcademicoSeleccionado, fechaTutoriaAcademicaSeleccionada);
            Scene escenaFormulario = new Scene(raiz);
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setResizable(false);
            escenarioFormulario.setScene(escenaFormulario);
            escenarioFormulario.setTitle("Reporte de tutoría académica");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        } catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
}
