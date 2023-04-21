package controlador;

import logicaNegocio.PeriodoEscolarDAO;
import logicaNegocio.helpers.DateHelper;
import logicaNegocio.helpers.PeriodoEscolarHelper;
import dominio.PeriodoEscolar;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RegistrarPeriodoEscolarFXMLController implements Initializable {
    @FXML
    private Button buttonCancelar;
    @FXML
    private TableView<PeriodoEscolar> tableViewPeriodosEscolares;
    @FXML
    private TableColumn<PeriodoEscolar, String> tableColumnPeriodoNombre;
    @FXML
    private TableColumn<PeriodoEscolar, Date> tableColumnPeriodoFechaInicio;
    @FXML
    private TableColumn<PeriodoEscolar, Date> tableColumnPeriodoFechaFin;
    @FXML
    private Button buttonRegistrarPeriodo;
    @FXML
    private DatePicker datePickerFechaFin;
    @FXML
    private DatePicker datePickerFechaInicio;
    @FXML
    private Label labelNombrePeriodo;

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            inicializarTablaPeriodosEscolares();
            inicializarColumnasTablaPeriodosEscolares();
            inicializarDatePickers();
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                cerrar(true);
            });
        }
    }    
    
    private void inicializarTablaPeriodosEscolares() throws SQLException{
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();
        ArrayList<PeriodoEscolar> periodosEscolaresObtenidos = 
            periodoEscolarDAO.obtenerTodosLosPeriodosEscolaresRegistrados();
        ObservableList<PeriodoEscolar> listaPeriodosEscolares = 
            FXCollections.observableArrayList(periodosEscolaresObtenidos);
        tableViewPeriodosEscolares.setItems(listaPeriodosEscolares);
    }
    
    private void inicializarColumnasTablaPeriodosEscolares(){
        tableColumnPeriodoNombre.setCellValueFactory(new PropertyValueFactory<>("NombrePeriodo"));
        tableColumnPeriodoFechaInicio.setCellValueFactory(new PropertyValueFactory<>("FechaInicio"));
        tableColumnPeriodoFechaFin.setCellValueFactory(new PropertyValueFactory<>("FechaFin"));
    }
    
    private void inicializarDatePickers(){
        this.datePickerFechaInicio.valueProperty().addListener((observable, valorAnterior, valorNuevo)->{
            if (datePickerFechaFin.getValue()!=null){
                LocalDate fechaFin = datePickerFechaFin.getValue();
                PeriodoEscolarHelper periodoEscolarHelper = new PeriodoEscolarHelper();
                String nombrePeriodo = periodoEscolarHelper.crearNombreDePeriodo(valorNuevo, fechaFin);
                this.labelNombrePeriodo.setText(nombrePeriodo);
            }
        });
        this.datePickerFechaFin.valueProperty().addListener((observable, valorAnterior, nuevoValor)->{
            if (datePickerFechaInicio.getValue()!=null){
                LocalDate fechaInicio = datePickerFechaInicio.getValue();
                PeriodoEscolarHelper periodoEscolarHelper = new PeriodoEscolarHelper();
                String nombrePeriodo = periodoEscolarHelper.crearNombreDePeriodo(fechaInicio, nuevoValor);
                this.labelNombrePeriodo.setText(nombrePeriodo);
            }
        });
    }


    @FXML
    private void cancelar(ActionEvent evento) {
        Utilidades.mostrarAlertaConfirmacion(
            "Cancelar operación", 
            "¿Está seguro de que desea cancelar la operación?", 
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
            PeriodoEscolar nuevoPeriodoEscolar = crearPeriodoEscolar();
            validarPeriodoEscolar(nuevoPeriodoEscolar);
            registrarInformacion(nuevoPeriodoEscolar);
            Utilidades.mostrarAlertaSinConfirmacion(
                "Mensaje", 
                "La información se ha registrado correctamente.", 
                Alert.AlertType.INFORMATION);
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        } catch (IllegalArgumentException iaException) {
            Utilidades.mostrarAlertaSinConfirmacion(
                "Datos inválidos", 
                iaException.getMessage(), 
                Alert.AlertType.WARNING);
        }
    }
    
    
    private void registrarInformacion(PeriodoEscolar nuevoPeriodoEscolar) throws SQLException{
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();
        periodoEscolarDAO.registrarPeriodoEscolar(nuevoPeriodoEscolar);
        tableViewPeriodosEscolares.getItems().add(nuevoPeriodoEscolar);
        tableViewPeriodosEscolares.refresh();
    }
    
    private void validarPeriodoEscolar(PeriodoEscolar nuevoPeriodoEscolar) 
    throws SQLException, IllegalArgumentException{
        PeriodoEscolarHelper periodoEscolarHelper = new PeriodoEscolarHelper();
        periodoEscolarHelper.validarPeriodoEscolar(nuevoPeriodoEscolar);
    }
    
    private PeriodoEscolar crearPeriodoEscolar(){
        LocalDate fechaInicio = this.datePickerFechaInicio.getValue();
        LocalDate fechaFin = this.datePickerFechaFin.getValue();
        DateHelper dateHelper = new DateHelper();
        Date DateFechaInicio = dateHelper.aDate(fechaInicio);
        Date DateFechaFin = dateHelper.aDate(fechaFin);
        PeriodoEscolar nuevoPeriodoEscolar = new PeriodoEscolar();
        nuevoPeriodoEscolar.setFechaInicio(DateFechaInicio);
        nuevoPeriodoEscolar.setFechaFin(DateFechaFin);
        String nombrePeriodo = this.labelNombrePeriodo.getText();
        nuevoPeriodoEscolar.setNombrePeriodo(nombrePeriodo);
        return nuevoPeriodoEscolar;
    }
    
}
