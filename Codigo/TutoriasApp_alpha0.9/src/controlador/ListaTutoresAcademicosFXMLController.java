package controlador;

import logicaNegocio.AcademicoDAO;
import dominio.Academico;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import DIG.DIGAcademicoTabla;
import dominio.globales.InformacionSesion;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javax.management.modelmbean.InvalidTargetObjectTypeException;

public class ListaTutoresAcademicosFXMLController implements Initializable {

    @FXML
    private TableView<DIGAcademicoTabla> tableViewListadoAcademicos;
    @FXML
    private Button buttonRegresar;
    @FXML
    private Button buttonConsultar;
    @FXML
    private TableColumn<DIGAcademicoTabla, ?> tableColumnIdentificador;
    @FXML
    private TableColumn<DIGAcademicoTabla, String> tableColumnNombre;
    @FXML
    private TextField textFieldFiltrarAcademicos;
    
    private ObservableList<DIGAcademicoTabla> listaAcademicos = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        inicializarColumnas();
        cargarCamposGUI();
    }
    
    public void cargarCamposGUI(){
        try{
            listaAcademicos = FXCollections.observableArrayList(obtenerAcademicos());
            tableViewListadoAcademicos.setItems(listaAcademicos);
            inicializarFiltro();
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenarioPrincipal = (Stage) textFieldFiltrarAcademicos.getScene().getWindow();
                escenarioPrincipal.close();
            });
        }
    }
    
    
    public void inicializarColumnas(){
        tableColumnIdentificador.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }
    
    public void inicializarFiltro(){
        FilteredList<DIGAcademicoTabla> listaAcademicosFiltrada = 
            new FilteredList(listaAcademicos, academico -> true);
            textFieldFiltrarAcademicos.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
                listaAcademicosFiltrada.setPredicate(digAcademicoTabla -> {
                    if(valorNuevo.isEmpty() || valorNuevo.isBlank() || valorNuevo == null){
                        return true;
                    }
                    String busquedaPalabra = valorNuevo.toLowerCase();
                    String datosBusqueda = digAcademicoTabla.getNombre().toLowerCase();
                    if(datosBusqueda.indexOf(busquedaPalabra) > -1){
                        return true;
                    }else{
                        return false;
                    }
                });
            });
            SortedList<DIGAcademicoTabla> listaAcademicosOrdenada = 
                                                    new SortedList<>(listaAcademicosFiltrada);
            listaAcademicosOrdenada.comparatorProperty().bind(
                                            tableViewListadoAcademicos.comparatorProperty());
            tableViewListadoAcademicos.setItems(listaAcademicosOrdenada);
    }
    
    
    public ArrayList<DIGAcademicoTabla> obtenerAcademicos() throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        AcademicoDAO academicoDAO = new AcademicoDAO();
        ArrayList<Academico> academicosObtenidos = new ArrayList();
        ArrayList<DIGAcademicoTabla> listaAcademicosObtenida = new ArrayList();
        DIGAcademicoTabla vistaAcademicoTabla = new DIGAcademicoTabla();
        academicosObtenidos.addAll(
        academicoDAO.obtenerAcademicosPorProgramaEducativo(
                informacionSesion .getProgramaEducativo().getIdProgramaEducativo()));
        for(Academico academico : academicosObtenidos){
            vistaAcademicoTabla = new DIGAcademicoTabla();
            vistaAcademicoTabla.setAcademico(academico);
            listaAcademicosObtenida.add(vistaAcademicoTabla);
        }
        return listaAcademicosObtenida;
    }

    @FXML
    private void regresar(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegresar)){
                Stage escenarioPrincipal = 
                    (Stage) textFieldFiltrarAcademicos.getScene().getWindow();
                escenarioPrincipal.close();
            }
    }

    @FXML
    private void consultarTutor(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonConsultar)){
                try{
                    DIGAcademicoTabla academicoSeleccionado = 
                        tableViewListadoAcademicos.getSelectionModel().getSelectedItem();
                    validarSeleccionTablaActiva(academicoSeleccionado);
                    llamarConsultar(academicoSeleccionado.getAcademico());
                }catch(InvalidTargetObjectTypeException itotException){
                    Utilidades.mostrarAlertaSinConfirmacion("Sin seleccion en tabla",
                    "El académico aun no ha sido seleccionada, favor de "
                    + "seleccionarlo primero",
                    Alert.AlertType.WARNING);
                }
            }
    }
      
    private void validarSeleccionTablaActiva(Object object) throws InvalidTargetObjectTypeException{
        if(object == null){
            throw new InvalidTargetObjectTypeException();
        }
    }
    
    public void llamarConsultar(Academico academicoSeleccionado){
        try {
            FXMLLoader cargadorFXML = new FXMLLoader(
                getClass().getResource("/vista/ConsultarTutorFXML.fxml"));
            Parent raiz = cargadorFXML.load();
            ConsultarTutorFXMLController controladorGUI = cargadorFXML.getController();
            controladorGUI.setAcademicoConsulta(academicoSeleccionado);
            Stage escenario = new Stage();
            escenario.setTitle("Consultar Academico");
            escenario.setScene(new Scene(raiz, 600, 385));
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
}
