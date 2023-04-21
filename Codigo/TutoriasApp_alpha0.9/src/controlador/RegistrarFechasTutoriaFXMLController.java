package controlador;

import logicaNegocio.FechaTutoriaDAO;
import logicaNegocio.PeriodoEscolarDAO;
import logicaNegocio.helpers.DateHelper;
import logicaNegocio.helpers.FechaTutoriaHelper;
import dominio.FechaTutoria;
import dominio.globales.InformacionSesion;
import dominio.PeriodoEscolar;
import dominio.ProgramaEducativo;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

public class RegistrarFechasTutoriaFXMLController implements Initializable {

    @FXML
    private DatePicker datePickerFechaTutoria2;
    @FXML
    private DatePicker datePickerFechaTutoria1;
    @FXML
    private DatePicker datePickerFechaTutoria3;
    @FXML
    private DatePicker datePickerFechaCierre1;
    @FXML
    private DatePicker datePickerFechaCierre2;
    @FXML
    private DatePicker datePickerFechaCierre3;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonGuardar;
    @FXML
    private TableView<PeriodoEscolar> tableViewPeriodosEscolares;
    @FXML
    private TableColumn<PeriodoEscolar, String> tableColumnPeriodoNombre;
    @FXML
    private TableColumn<PeriodoEscolar, Date> tableColumnPeriodoFechaInicio;
    @FXML
    private TableColumn<PeriodoEscolar, Date> tableColumnPeriodoFechaFin;
    @FXML
    private CheckBox checkBoxHabilitarPickerFechaCierre;
    
    private final int INDICE_PRIMERA_SESION = 0;
    private final int INDICE_SEGUNDA_SESION = 1;
    private final int INDICE_TERCERA_SESION = 2;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            inicializarTablaPeriodos();
            inicializarColumnasPeriodos();
            verificarExistenciaDePeriodos();
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                cerrar(true);
            });
        } catch (IllegalStateException isException){
            Utilidades.mostrarAlertaSinConfirmacion(
                "Mensaje",isException.getMessage(),Alert.AlertType.INFORMATION);
            Platform.runLater(() -> {
                cerrar(true);
            });
        }
    }
    
    private void verificarExistenciaDePeriodos() throws IllegalStateException{
        if (this.tableViewPeriodosEscolares.getItems().isEmpty()){
            throw new IllegalStateException("No hay periodos escolares con fechas de "
                + "tutoría registrados en el sistema. Debe existir un periodo escolar"
                + " con sus tres fechas de tutoría para poder ser modificadas.");
        }
    }
    
    private void inicializarTablaPeriodos() throws SQLException{
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        ArrayList<PeriodoEscolar> periodosEscolaresObtenidos = 
            periodoEscolarDAO.obtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativo(
                informacionSesion .getProgramaEducativo().getIdProgramaEducativo()
            );
        ObservableList<PeriodoEscolar> listaPeriodosEscolares = 
            FXCollections.observableArrayList(periodosEscolaresObtenidos);
        FXCollections.reverse(listaPeriodosEscolares);
        tableViewPeriodosEscolares.setItems(listaPeriodosEscolares);
        tableViewPeriodosEscolares.getSelectionModel().selectedIndexProperty().addListener(
            click -> {
                this.checkBoxHabilitarPickerFechaCierre.setDisable(false);
                PeriodoEscolar periodoEscolarSeleccionado = 
                    this.tableViewPeriodosEscolares.getSelectionModel().getSelectedItem();
                limpiarDatePickersFechaTutoria();
                asignarFechaCierrePorDefecto(periodoEscolarSeleccionado);
            }
        );
    }
    
    private void limpiarDatePickersFechaTutoria(){
        this.datePickerFechaTutoria1.setValue(null);
        this.datePickerFechaTutoria2.setValue(null);
        this.datePickerFechaTutoria3.setValue(null);
    }
    
    private void asignarFechaCierrePorDefecto(PeriodoEscolar periodoEscolarSeleccionado) {
        if (periodoEscolarSeleccionado!=null){
            DateHelper dateHelper = new DateHelper();
            LocalDate fechaFinal = dateHelper.aLocalDate(periodoEscolarSeleccionado.getFechaFin());
            datePickerFechaCierre1.setValue(fechaFinal);
            datePickerFechaCierre2.setValue(fechaFinal);
            datePickerFechaCierre3.setValue(fechaFinal);
        }
    }

    private void inicializarColumnasPeriodos(){
        tableColumnPeriodoNombre.setCellValueFactory(new PropertyValueFactory<>("NombrePeriodo"));
        tableColumnPeriodoFechaInicio.setCellValueFactory(new PropertyValueFactory<>("FechaInicio"));
        tableColumnPeriodoFechaFin.setCellValueFactory(new PropertyValueFactory<>("FechaFin"));
    }

    @FXML
    private void cancelar(ActionEvent evento) {
        Utilidades.mostrarAlertaConfirmacion(
            "Cancelar operación", "¿Está seguro de que desea cancelar la operación?", 
            Alert.AlertType.CONFIRMATION);
        boolean confirmarCancelacion =
            Utilidades.getOpcion().orElse(ButtonType.CANCEL).getButtonData().isDefaultButton();
        cerrar(confirmarCancelacion);
    }
    
    private void cerrar(boolean confirmacion){
        if (confirmacion == true){
            Stage escenario = (Stage)buttonCancelar.getScene().getWindow();
            escenario.close();   
        }
    }

    @FXML
    private void guardar(ActionEvent evento) {
        try {
            validarCamposLlenos();
            ArrayList<FechaTutoria> nuevasFechasDeTutoria = crearNuevasFechasTutorias();
            validarNuevasFechaTutorias(nuevasFechasDeTutoria);
            registrarInformacionEnBaseDeDatos(nuevasFechasDeTutoria);
            terminar();
        } catch (SQLException sqlException) { 
            Utilidades.mensajePerdidaDeConexion();
        } catch (IllegalArgumentException iaException) {
            Utilidades.mostrarAlertaSinConfirmacion(
                "Datos inválidos", iaException.getMessage(), Alert.AlertType.WARNING);
        } catch (IllegalStateException isException){
            Utilidades.mostrarAlertaSinConfirmacion(
                "Campos vacíos", isException.getMessage(), Alert.AlertType.WARNING);
        }
    }
    
    private void terminar(){
        Utilidades.mostrarAlertaSinConfirmacion(
            "Mensaje", "La información se ha registrado correctamente.", 
            Alert.AlertType.INFORMATION);
        limpiarDatePickersFechaTutoria();
        this.datePickerFechaCierre1.setValue(null);
        this.datePickerFechaCierre2.setValue(null);
        this.datePickerFechaCierre3.setValue(null);
        PeriodoEscolar periodoEscolarSeleccionado = 
            this.tableViewPeriodosEscolares.getSelectionModel().getSelectedItem();
        this.tableViewPeriodosEscolares.getItems().remove(periodoEscolarSeleccionado);
    }
    
    private boolean registrarInformacionEnBaseDeDatos
    (ArrayList<FechaTutoria> nuevasFechaTutorias) 
    throws SQLException{
        FechaTutoriaDAO fechaTutoriaDAO = new FechaTutoriaDAO();
        fechaTutoriaDAO.registrarFechaTutoria(nuevasFechaTutorias.get(INDICE_PRIMERA_SESION));
        fechaTutoriaDAO.registrarFechaTutoria(nuevasFechaTutorias.get(INDICE_SEGUNDA_SESION));
        fechaTutoriaDAO.registrarFechaTutoria(nuevasFechaTutorias.get(INDICE_TERCERA_SESION));
        return true;
    }
    
    private void validarNuevasFechaTutorias(ArrayList<FechaTutoria> nuevasFechasDeTutorias) 
    throws IllegalArgumentException{
        FechaTutoriaHelper fechaTutoriaHelper = new FechaTutoriaHelper();
        boolean fechasSonValidas = fechaTutoriaHelper.validarFechasDeTutoria(nuevasFechasDeTutorias);
        if (fechasSonValidas == false){
            throw new IllegalArgumentException(fechaTutoriaHelper.getMensajeFechasARegistrarInvalidas());
        }
    }    
    
    private ArrayList<FechaTutoria> crearNuevasFechasTutorias()
    throws IllegalArgumentException {
        PeriodoEscolar periodoEscolarSeleccionado = 
            tableViewPeriodosEscolares.getSelectionModel().getSelectedItem();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        ProgramaEducativo programaEducativoDelCoordinador = informacionSesion .getProgramaEducativo();
        ArrayList<FechaTutoria> nuevasFechasDeTutoria = new ArrayList<FechaTutoria>();
        final int ULTIMA_SESION_DEL_PERIODO = 3;
        for (int numeroSesion = 1; numeroSesion <= ULTIMA_SESION_DEL_PERIODO; numeroSesion++){
            FechaTutoria nuevaFecha = new FechaTutoria();
            nuevaFecha.setPeriodo(periodoEscolarSeleccionado);
            nuevaFecha.setProgramaEducativo(programaEducativoDelCoordinador);
            nuevasFechasDeTutoria.add(nuevaFecha);
        }
        nuevasFechasDeTutoria.get(INDICE_PRIMERA_SESION).setNumeroSesion(INDICE_PRIMERA_SESION+1);
        nuevasFechasDeTutoria.get(INDICE_SEGUNDA_SESION).setNumeroSesion(INDICE_SEGUNDA_SESION+1);
        nuevasFechasDeTutoria.get(INDICE_TERCERA_SESION).setNumeroSesion(INDICE_TERCERA_SESION+1);
        
        nuevasFechasDeTutoria  = obtenerFechasDeSesiones(nuevasFechasDeTutoria );
        nuevasFechasDeTutoria  = obtenerFechaCierres(nuevasFechasDeTutoria );
        return nuevasFechasDeTutoria;
    }
    
    private ArrayList<FechaTutoria> obtenerFechasDeSesiones
    (ArrayList<FechaTutoria> nuevasFechasDeTutoria ){
        DateHelper dateHelper = new DateHelper();
        Date fechaTutoria1 = dateHelper.aDate(datePickerFechaTutoria1.getValue());
        Date fechaTutoria2 = dateHelper.aDate(datePickerFechaTutoria2.getValue());
        Date fechaTutoria3 = dateHelper.aDate(datePickerFechaTutoria3.getValue());
        nuevasFechasDeTutoria.get(INDICE_PRIMERA_SESION).setFechaTutoria(fechaTutoria1);
        nuevasFechasDeTutoria.get(INDICE_SEGUNDA_SESION).setFechaTutoria(fechaTutoria2);
        nuevasFechasDeTutoria.get(INDICE_TERCERA_SESION).setFechaTutoria(fechaTutoria3);
        return nuevasFechasDeTutoria ;
    }
      
    private ArrayList<FechaTutoria> obtenerFechaCierres(ArrayList<FechaTutoria> nuevasFechasDeTutoria){
        DateHelper dateHelper = new DateHelper();
        Date fechaCierre1 = dateHelper.aDate(datePickerFechaCierre1.getValue());
        Date fechaCierre2 = dateHelper.aDate(datePickerFechaCierre2.getValue());
        Date fechaCierre3 = dateHelper.aDate(datePickerFechaCierre3.getValue());
        nuevasFechasDeTutoria.get(INDICE_PRIMERA_SESION).setFechaCierreDeReporte(fechaCierre1);
        nuevasFechasDeTutoria.get(INDICE_SEGUNDA_SESION).setFechaCierreDeReporte(fechaCierre2);
        nuevasFechasDeTutoria.get(INDICE_TERCERA_SESION).setFechaCierreDeReporte(fechaCierre3);
        return nuevasFechasDeTutoria ;
    }
    
    private void validarCamposLlenos(){
        boolean camposLlenos = 
            validarPeriodoEscolarSeleccionado()
            && validarDatePickersFechaTutoriasLlenos() 
            && validarDatePickersFechaCierreLlenos();
        if (camposLlenos == false){
            throw new IllegalStateException("No puede dejar ningún campo vacío. Por favor, "
                + "verifique que todos los campos estén llenos.");
        }
    } 

    private boolean validarPeriodoEscolarSeleccionado(){
        boolean periodoEscolarSeleccionado=
            tableViewPeriodosEscolares.getSelectionModel().getSelectedItem()!=null;
        return periodoEscolarSeleccionado;
    }
    
    private boolean validarDatePickersFechaTutoriasLlenos(){
        boolean fechasTutoriaLlenas=
            datePickerFechaTutoria1.getValue() != null
            && datePickerFechaTutoria2.getValue() != null
            && datePickerFechaTutoria3.getValue() != null;
        return fechasTutoriaLlenas;
    }
    
    private boolean validarDatePickersFechaCierreLlenos(){
        boolean fechasCierreLlenas=
            datePickerFechaCierre1.getValue() != null 
            && datePickerFechaCierre2.getValue() != null
            && datePickerFechaCierre3.getValue() != null;
        return fechasCierreLlenas;
    }
    
    @FXML
    private void habilitarDatePickerFechaCierre() {
        boolean desactivar = !checkBoxHabilitarPickerFechaCierre.isSelected();
        datePickerFechaCierre1.setDisable(desactivar);
        datePickerFechaCierre2.setDisable(desactivar);
        datePickerFechaCierre3.setDisable(desactivar);
        if (desactivar == true){
            PeriodoEscolar periodoEscolarSeleccionado =
                this.tableViewPeriodosEscolares.getSelectionModel().getSelectedItem();
            asignarFechaCierrePorDefecto(periodoEscolarSeleccionado);
        }
    }
    
}
