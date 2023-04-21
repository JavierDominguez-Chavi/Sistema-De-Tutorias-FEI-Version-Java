package controlador;

import logicaNegocio.FechaTutoriaDAO;
import logicaNegocio.PeriodoEscolarDAO;
import logicaNegocio.helpers.DateHelper;
import logicaNegocio.helpers.FechaTutoriaHelper;
import dominio.FechaTutoria;
import dominio.globales.InformacionSesion;
import dominio.PeriodoEscolar;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.SQLDataException;
import javafx.application.Platform;

public class ModificarFechasTutoriaFXMLController implements Initializable {

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
    private TableColumn<PeriodoEscolar, String> tableColumnPeriodoFechaInicio;
    @FXML
    private TableColumn<PeriodoEscolar, String> tableColumnPeriodoFechaFin;
    @FXML
    private CheckBox checkBoxHabilitarPickerFechaCierre;
    private final int PRIMERA_SESION = 0;
    private final int SEGUNDA_SESION = 1;
    private final int TERCERA_SESION = 2;
    private ArrayList<FechaTutoria> fechasDeTutoriaDelPeriodoSeleccionado;

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try{
            inicializarTablaPeriodos();
            inicializarColumnasPeriodos();
            verificarExistenciaDePeriodos();
        }catch(SQLException sqlException){
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
    
    @FXML
    private void validarFechasDeTutoriaEstablecidas(ActionEvent evento){
        final boolean LAS_FECHAS_TUTORIA_YA_SE_ESTABLECIERON=
            this.datePickerFechaTutoria1.getValue()!=null
            && this.datePickerFechaTutoria2.getValue()!=null
            && this.datePickerFechaTutoria3.getValue()!=null;
        if (LAS_FECHAS_TUTORIA_YA_SE_ESTABLECIERON == false){
            Utilidades.mostrarAlertaSinConfirmacion(
                "Campos vacíos", 
                "Debe establecer las fechas de sesión de tutoría antes de poder "
                + "establecer las fechas de cierre correspondientes.", 
                Alert.AlertType.WARNING
            );
            this.checkBoxHabilitarPickerFechaCierre.setSelected(false);
        } else {
            habilitarDatePickerFechaCierre();
        }
    }
    
    private void habilitarDatePickerFechaCierre() {
        boolean desactivar = !checkBoxHabilitarPickerFechaCierre.isSelected();
        datePickerFechaCierre1.setDisable(desactivar);
        datePickerFechaCierre2.setDisable(desactivar);
        datePickerFechaCierre3.setDisable(desactivar);
    }

    
    private void inicializarTablaPeriodos() throws SQLException {
        ObservableList<PeriodoEscolar> observableListPeriodosEscolares = 
            FXCollections.observableArrayList(obtenerPeriodosEscolares());
        FXCollections.reverse(observableListPeriodosEscolares);
        tableViewPeriodosEscolares.setItems(observableListPeriodosEscolares);
        tableViewPeriodosEscolares.getSelectionModel().selectedIndexProperty().addListener(
            click -> {
                this.checkBoxHabilitarPickerFechaCierre.setDisable(false);
                obtenerFechasTutoriasDePeriodoSeleccionado();
            }
        );
    }
    
    private ArrayList<PeriodoEscolar> obtenerPeriodosEscolares() throws SQLException{
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();
        InformacionSesion informacionSesion= InformacionSesion.getInformacionSesion();
        int idProgramaEducativoDelCoordinador = 
            informacionSesion.getProgramaEducativo().getIdProgramaEducativo();
        ArrayList<PeriodoEscolar> periodosEscolaresObtenidos =
            periodoEscolarDAO.obtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativo(
                idProgramaEducativoDelCoordinador
            );
        return periodosEscolaresObtenidos;
    }
    
    private void inicializarColumnasPeriodos(){
        tableColumnPeriodoNombre.setCellValueFactory(new PropertyValueFactory<>("NombrePeriodo"));
        tableColumnPeriodoFechaInicio.setCellValueFactory(new PropertyValueFactory<>("FechaInicio"));
        tableColumnPeriodoFechaFin.setCellValueFactory(new PropertyValueFactory<>("FechaFin"));
    }
    private void obtenerFechasTutoriasDePeriodoSeleccionado(){
        try {
            PeriodoEscolar periodoEscolarSeleccionado = 
                tableViewPeriodosEscolares.getSelectionModel().getSelectedItem();
            
            if (periodoEscolarSeleccionado!=null){
                InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
                int idProgramaEducativoDelCoordinador = 
                        informacionSesion .getProgramaEducativo().getIdProgramaEducativo();
                FechaTutoriaDAO fechaTutoriaDAO = new FechaTutoriaDAO();
                this.fechasDeTutoriaDelPeriodoSeleccionado =
                    fechaTutoriaDAO.obtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo(
                        periodoEscolarSeleccionado, idProgramaEducativoDelCoordinador
                    );
                asignarPeriodoEscolarALasFechasTutoriaObtenidas(periodoEscolarSeleccionado);
                validarFechasDeTutoriaObtenidas();
                validarNumeroDeFechasObtenidas();
                inicializarDatePickersFechaTutorias(this.fechasDeTutoriaDelPeriodoSeleccionado);
                inicializarDatePickersFechaCierres(this.fechasDeTutoriaDelPeriodoSeleccionado);
            }

        } catch (SQLDataException sqldException){
            Utilidades.mostrarAlertaConfirmacion(
                "Error en los datos obtenidos", 
                sqldException.getMessage(), 
                Alert.AlertType.ERROR);
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        } 
    }
    
    private void asignarPeriodoEscolarALasFechasTutoriaObtenidas(PeriodoEscolar periodoEscolarSeleccionado){
        fechasDeTutoriaDelPeriodoSeleccionado.get(PRIMERA_SESION).setPeriodo(periodoEscolarSeleccionado);
        fechasDeTutoriaDelPeriodoSeleccionado.get(SEGUNDA_SESION).setPeriodo(periodoEscolarSeleccionado);
        fechasDeTutoriaDelPeriodoSeleccionado.get(TERCERA_SESION).setPeriodo(periodoEscolarSeleccionado);
    }
    
    private void validarFechasDeTutoriaObtenidas(){
        FechaTutoriaHelper fechaTutoriaHelper = new FechaTutoriaHelper();
        boolean fechasSonValidas = 
            fechaTutoriaHelper.validarFechasDeTutoria(this.fechasDeTutoriaDelPeriodoSeleccionado);
        if (fechasSonValidas == false){
            Utilidades.mostrarAlertaConfirmacion(
                "Mensaje", 
                fechaTutoriaHelper.getMensajeFechasObtenidasInvalidas(), 
                Alert.AlertType.WARNING);
        }
    }
    
    private void validarNumeroDeFechasObtenidas() throws SQLDataException{
        int numeroFechasObtenidas = this.fechasDeTutoriaDelPeriodoSeleccionado.size();
        if (numeroFechasObtenidas>3){
            Utilidades.mostrarAlertaConfirmacion(
                "Error en los datos obtenidos", 
                "Se han recuperado más de tres (3) fechas de sesión de tutoría para el periodo "
                + "seleccionado. A continuación se mostrarán sólo las primeras tres.", 
                Alert.AlertType.WARNING);
        } else if (numeroFechasObtenidas<3){
            throw new SQLDataException(
                "Se han recuperado menos de tres (3) fechas de sesión de tutoría para el periodo "
                + "seleccionado. No se podrán modificar.");
        }
    }
    
    private void inicializarDatePickersFechaTutorias
    (ArrayList<FechaTutoria> fechasDeTutoriaDelPeriodoSeleccionado){
        Date DateFechaTutoria1 = fechasDeTutoriaDelPeriodoSeleccionado.get(PRIMERA_SESION).getFechaTutoria();
        Date DateFechaTutoria2 = fechasDeTutoriaDelPeriodoSeleccionado.get(SEGUNDA_SESION).getFechaTutoria();
        Date DateFechaTutoria3 = fechasDeTutoriaDelPeriodoSeleccionado.get(TERCERA_SESION).getFechaTutoria();
        
        DateHelper dateHelper = new DateHelper();
        LocalDate fechaTutoria1 = dateHelper.aLocalDate(DateFechaTutoria1);
        LocalDate fechaTutoria2 = dateHelper.aLocalDate(DateFechaTutoria2);
        LocalDate fechaTutoria3 = dateHelper.aLocalDate(DateFechaTutoria3);
        
        this.datePickerFechaTutoria1.setValue(fechaTutoria1);
        this.datePickerFechaTutoria2.setValue(fechaTutoria2);
        this.datePickerFechaTutoria3.setValue(fechaTutoria3);
    }
    
    private void inicializarDatePickersFechaCierres
    (ArrayList<FechaTutoria> fechasDeTutoriaDelPeriodoSeleccionado){
        Date DateFechaCierre1 = 
            fechasDeTutoriaDelPeriodoSeleccionado.get(PRIMERA_SESION).getFechaCierreDeReporte();
        Date DateFechaCierre2 = 
            fechasDeTutoriaDelPeriodoSeleccionado.get(SEGUNDA_SESION).getFechaCierreDeReporte();
        Date DateFechaCierre3 = 
            fechasDeTutoriaDelPeriodoSeleccionado.get(TERCERA_SESION).getFechaCierreDeReporte();
        
        DateHelper dateHelper = new DateHelper();
        LocalDate fechaCierre1 = dateHelper.aLocalDate(DateFechaCierre1);
        LocalDate fechaCierre2 = dateHelper.aLocalDate(DateFechaCierre2);
        LocalDate fechaCierre3 = dateHelper.aLocalDate(DateFechaCierre3);
        
        this.datePickerFechaCierre1.setValue(fechaCierre1);
        this.datePickerFechaCierre2.setValue(fechaCierre2);
        this.datePickerFechaCierre3.setValue(fechaCierre3);
    }

    @FXML
    private void cancelar(ActionEvent evento) {
        Utilidades.mostrarAlertaConfirmacion(
            "Cancelar la operación", 
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
            validarCamposLlenos();
            ArrayList<FechaTutoria> fechasDeTutoriaAModificar = 
                this.fechasDeTutoriaDelPeriodoSeleccionado;
            asignarValoresEnDatePickers(fechasDeTutoriaAModificar);
            validarNuevasFechaTutorias(fechasDeTutoriaAModificar);
            registrarInformacion(fechasDeTutoriaAModificar);
            terminar();
        } catch (SQLException sqlException) { 
            Utilidades.mensajePerdidaDeConexion();
        } catch (IllegalArgumentException iaException) {
            Utilidades.mostrarAlertaSinConfirmacion("Datos inválidos", iaException.getMessage(), 
                Alert.AlertType.WARNING
            );
        } catch (IllegalStateException isException){
            Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos", isException.getMessage(), 
                Alert.AlertType.WARNING
            );
        }
    }
    
    private void terminar(){
        Utilidades.mostrarAlertaSinConfirmacion(
            "Mensaje", 
            "La información se ha registrado correctamente", 
            Alert.AlertType.INFORMATION);
        this.datePickerFechaTutoria1.setValue(null);
        this.datePickerFechaTutoria2.setValue(null);
        this.datePickerFechaTutoria3.setValue(null);
        this.datePickerFechaCierre1.setValue(null);
        this.datePickerFechaCierre2.setValue(null);
        this.datePickerFechaCierre3.setValue(null);
        deseleccionarPeriodo();
    }
    
    private void deseleccionarPeriodo(){
        tableViewPeriodosEscolares.getSelectionModel().clearSelection(0);
    }
    
    
    private boolean validarCamposLlenos() throws IllegalStateException{
        boolean camposLlenos = 
            validarPeriodoEscolarSeleccionado()
            && validarDatePickersFechaTutoriasLlenos() 
            && validarDatePickersFechaCierreLlenos();
        if (camposLlenos == false){
            throw new IllegalStateException("No puede dejar ningún campo vació");
        }
        return camposLlenos;
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
    
    private boolean registrarInformacion
    (ArrayList<FechaTutoria> nuevasFechaTutorias) 
    throws SQLException{
        FechaTutoriaDAO fechaTutoriaDAO = new FechaTutoriaDAO();
        fechaTutoriaDAO.modificarFechaTutoria(nuevasFechaTutorias.get(PRIMERA_SESION));
        fechaTutoriaDAO.modificarFechaTutoria(nuevasFechaTutorias.get(SEGUNDA_SESION));
        fechaTutoriaDAO.modificarFechaTutoria(nuevasFechaTutorias.get(TERCERA_SESION));
        if (this.checkBoxHabilitarPickerFechaCierre.isSelected()){
            fechaTutoriaDAO.modificarFechaCierre(nuevasFechaTutorias.get(PRIMERA_SESION));
            fechaTutoriaDAO.modificarFechaCierre(nuevasFechaTutorias.get(SEGUNDA_SESION));
            fechaTutoriaDAO.modificarFechaCierre(nuevasFechaTutorias.get(TERCERA_SESION));
        }
        return true;
    }
    
    private void validarNuevasFechaTutorias (ArrayList<FechaTutoria> fechasDeTutoriasModificadas)
    throws IllegalArgumentException{
        FechaTutoriaHelper fechaTutoriaHelper = new FechaTutoriaHelper();
        boolean lasFechasSonValidas =
            fechaTutoriaHelper.validarFechasDeTutoria(fechasDeTutoriasModificadas);
        if (!lasFechasSonValidas){
            throw new IllegalArgumentException(fechaTutoriaHelper.getMensajeFechasARegistrarInvalidas());
        }
    }    
    
    private void asignarValoresEnDatePickers(ArrayList<FechaTutoria> fechasDeTutoriaOriginales){
        DateHelper dateHelper = new DateHelper();
        Date nuevaFechaTutoria1 = dateHelper.aDate(this.datePickerFechaTutoria1.getValue());
        Date nuevaFechaTutoria2 = dateHelper.aDate(this.datePickerFechaTutoria2.getValue());
        Date nuevaFechaTutoria3 = dateHelper.aDate(this.datePickerFechaTutoria3.getValue());
        fechasDeTutoriaOriginales.get(PRIMERA_SESION).setFechaTutoria(nuevaFechaTutoria1);
        fechasDeTutoriaOriginales.get(SEGUNDA_SESION).setFechaTutoria(nuevaFechaTutoria2);
        fechasDeTutoriaOriginales.get(TERCERA_SESION).setFechaTutoria(nuevaFechaTutoria3);
        
        if (this.checkBoxHabilitarPickerFechaCierre.isSelected()){
            Date nuevaFechaCierre1 = dateHelper.aDate(this.datePickerFechaCierre1.getValue());
            Date nuevaFechaCierre2 = dateHelper.aDate(this.datePickerFechaCierre2.getValue());
            Date nuevaFechaCierre3 = dateHelper.aDate(this.datePickerFechaCierre3.getValue());
            fechasDeTutoriaOriginales.get(PRIMERA_SESION).setFechaCierreDeReporte(nuevaFechaCierre1);
            fechasDeTutoriaOriginales.get(SEGUNDA_SESION).setFechaCierreDeReporte(nuevaFechaCierre2);
            fechasDeTutoriaOriginales.get(TERCERA_SESION).setFechaCierreDeReporte(nuevaFechaCierre3);   
        }
    }
    
    private void verificarExistenciaDePeriodos() throws IllegalStateException{
        if (this.tableViewPeriodosEscolares.getItems().isEmpty()){
            throw new IllegalStateException("No hay periodos escolares con fechas de "
                + "tutoría registrados en el sistema. Debe existir un periodo escolar"
                + " con sus tres fechas de tutoría para poder ser modificadas.");
        }
    }
}
