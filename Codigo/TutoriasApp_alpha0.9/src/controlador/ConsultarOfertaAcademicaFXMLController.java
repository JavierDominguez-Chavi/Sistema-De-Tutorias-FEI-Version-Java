package controlador;

import logicaNegocio.AsignaturaDAO;
import logicaNegocio.PeriodoEscolarDAO;
import logicaNegocio.ProgramaEducativoDAO;
import dominio.Academico;
import dominio.Asignatura;
import dominio.PeriodoEscolar;
import dominio.ProgramaEducativo;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ConsultarOfertaAcademicaFXMLController implements Initializable {

    @FXML
    private ComboBox<ProgramaEducativo> comboBoxProgramaEducativo;
    @FXML
    private TableView<Asignatura> tableViewExperiencias;
    @FXML
    private TableColumn<Asignatura, Integer> tableColumnExperienciaNRC;
    @FXML
    private TableColumn<Asignatura, String> tableColumnExperienciaNombre;
    @FXML
    private TableColumn<Asignatura, String> tableColumnExperienciaFacilitador;
    @FXML
    private Button buttonRegresar;
    @FXML
    private ComboBox<PeriodoEscolar> comboBoxPeriodoEscolar;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            verificarExistenciaDeAsignaturas();
            mostrarProgramasEducativos();
            mostrarPeriodosEscolares();
            verificarExistenciaDePeriodos();
            inicializarColumnas();
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
            cerrarPorExcepcion();
        } catch (UnsupportedOperationException uoException){
            Utilidades.mostrarAlertaConfirmacion("Error", uoException.getMessage(), 
            Alert.AlertType.ERROR);
        }
    }   
    
    private void cerrarPorExcepcion(){
        Platform.runLater(() -> {
            regresar(new ActionEvent());
        });
    }
    
    private void inicializarColumnas(){
        tableColumnExperienciaNRC.setCellValueFactory(new PropertyValueFactory<>("NRC"));
        tableColumnExperienciaNombre.setCellValueFactory(
        (TableColumn.CellDataFeatures<Asignatura, String> experiencia) -> new ReadOnlyObjectWrapper(
                experiencia.getValue().getExperienciaEducativa().getNombre()
        ));
        tableColumnExperienciaFacilitador.setCellValueFactory(
        new Callback<TableColumn.CellDataFeatures<Asignatura, String>, 
            ObservableValue<String>>() {
                public ObservableValue<String> call
                (TableColumn.CellDataFeatures<Asignatura, String> experiencia) {
                    Academico facilitador = experiencia.getValue().getAcademico();
                    String nombreCompleto = facilitador.getNombre() == null ? "SIN FACILITADOR" : 
                        facilitador.getNombre()+" "+
                        facilitador.getApellidoPaterno()+" "+
                        facilitador.getApellidoMaterno();
                    return new ReadOnlyObjectWrapper(nombreCompleto);
                }
            }
        );
    }

    private void mostrarProgramasEducativos() throws SQLException, UnsupportedOperationException{
        this.comboBoxProgramaEducativo.setItems(obtenerProgramasEducativos());
        this.comboBoxProgramaEducativo.setConverter(new StringConverter<ProgramaEducativo>(){
            @Override
            public String toString(ProgramaEducativo programa) {
                return programa == null ? null : programa.getNombre();
            }
            @Override
            public ProgramaEducativo fromString(String string) {
                throw new UnsupportedOperationException("Funcionalidad no implementada."); 
            }
        });
    }
    
    private ObservableList<ProgramaEducativo> obtenerProgramasEducativos() throws SQLException{
        ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
        ObservableList<ProgramaEducativo> programasEducativosObtenidos = FXCollections.observableArrayList(
            programaEducativoDAO.obtenerProgramasEducativos()
        );
        return programasEducativosObtenidos;
    }
    
    private void mostrarPeriodosEscolares() throws SQLException, UnsupportedOperationException{
        this.comboBoxPeriodoEscolar.setItems(obtenerPeriodosEscolares());
        this.comboBoxPeriodoEscolar.setConverter(new StringConverter<PeriodoEscolar>(){
            @Override
            public String toString(PeriodoEscolar periodoEscolar) {
                return periodoEscolar == null ? null : periodoEscolar.getNombrePeriodo();
            }
            @Override
            public PeriodoEscolar fromString(String string) {
                throw new UnsupportedOperationException("Funcionalidad no implementada."); 
            }
        });
    }
    
    private ObservableList<PeriodoEscolar> obtenerPeriodosEscolares() throws SQLException{
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();
        ObservableList<PeriodoEscolar> periodosEscolaresObtenidos = FXCollections.observableArrayList(
            periodoEscolarDAO.obtenerTodosLosPeriodosEscolaresRegistrados()
        );
        return periodosEscolaresObtenidos;
    }
    
    private void actualizarTablaOfertaAcademica() {
        try {
            int idProgramaEducativoSeleccionado =
                comboBoxProgramaEducativo.getSelectionModel().getSelectedItem().getIdProgramaEducativo();
            int idPeriodoEscolarSeleccionado = 
                comboBoxPeriodoEscolar.getSelectionModel().getSelectedItem().getIdPeriodoEscolar();
            AsignaturaDAO experienciaDAO = new AsignaturaDAO(); 
            ObservableList<Asignatura> experienciasEducativasObtenidas = FXCollections.observableArrayList();
            experienciasEducativasObtenidas.addAll(
                experienciaDAO.obtenerOfertaAcademicaPorProgramaEducativoYPeriodoEscolar(
                    idProgramaEducativoSeleccionado, idPeriodoEscolarSeleccionado)
            );
            tableViewExperiencias.setItems(experienciasEducativasObtenidas);
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    @FXML
    private void regresar(ActionEvent evento) {
        Stage escenario = (Stage)buttonRegresar.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void validarSeleccionDeComboBoxes(ActionEvent evento) {
        if (comboBoxPeriodoEscolar.getSelectionModel().getSelectedIndex()!=-1
                && comboBoxProgramaEducativo.getSelectionModel().getSelectedIndex()!=-1){
            actualizarTablaOfertaAcademica();
        }
    }    
    
    private void verificarExistenciaDeAsignaturas() throws IllegalStateException, SQLException{
        AsignaturaDAO asignaturaDAO = new AsignaturaDAO();
        if (asignaturaDAO.verificarExistenciaDeAsignaturas()==false){
            throw new IllegalStateException("No hay periodos escolares "
                    + "registrados en el sistema.");
        }
    }
    
    private void verificarExistenciaDePeriodos() throws IllegalStateException{
        if (this.comboBoxPeriodoEscolar.getItems().isEmpty()){
            throw new IllegalStateException("No hay periodos escolares "
                    + "registrados en el sistema.");
        }
    }
}
