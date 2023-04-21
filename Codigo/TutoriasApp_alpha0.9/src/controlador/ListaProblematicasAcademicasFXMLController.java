package controlador;

import logicaNegocio.ProblematicaDAO;
import dominio.Problematica;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import DIG.DIGProblematicaTabla;
import dominio.globales.InformacionSesion;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javax.management.modelmbean.InvalidTargetObjectTypeException;

public class ListaProblematicasAcademicasFXMLController implements Initializable {
    @FXML
    private TableView<DIGProblematicaTabla> tableViewListadoProblematicas;
    @FXML
    private TableColumn<DIGProblematicaTabla, Date> tableColumnFechaTutoria;
    @FXML
    private TableColumn<DIGProblematicaTabla, String> tableColumnTitulo;
    @FXML
    private TableColumn<DIGProblematicaTabla, String> tableColumnExperienciaEducativa;
    @FXML
    private Button buttonConsultar;
    @FXML
    private Button buttonRegresar;
    @FXML
    private TextField textFieldFiltrarProblematicas;
    
    private ObservableList<DIGProblematicaTabla> listaProblematicas = 
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        inicializarColumnas();
        cargarCamposGUI();
        
    }    
    
    private void cargarCamposGUI(){
        try{
            listaProblematicas = FXCollections.observableArrayList(obtenerProblematicas());
            tableViewListadoProblematicas.setItems(listaProblematicas);
            inicializarFiltro();
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenarioPrincipal = (Stage) textFieldFiltrarProblematicas.getScene().getWindow();
                escenarioPrincipal.close();
            });
        }
    }
    
    private void inicializarColumnas(){
        tableColumnFechaTutoria.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumnExperienciaEducativa.setCellValueFactory(
                new PropertyValueFactory<>("experienciaEducativa"));
    }
    
    private void inicializarFiltro(){
        FilteredList<DIGProblematicaTabla> listaProblematicasFiltradas = 
                        new FilteredList(listaProblematicas, problematica -> true);
            textFieldFiltrarProblematicas.textProperty().addListener((observable, valorAnterior, nuevoValor) -> {
                    listaProblematicasFiltradas.setPredicate(digProblematicaTabla -> {
                        if(nuevoValor.isEmpty() || nuevoValor.isBlank() || nuevoValor == null){
                            return true;
                        }
                        String busquedaPalabra = nuevoValor.toLowerCase();
                        String datosBusqueda = 
                                digProblematicaTabla.getExperienciaEducativa().toLowerCase()+" "
                                +digProblematicaTabla.getFecha().toString().toLowerCase()+" "
                                +digProblematicaTabla.getTitulo().toLowerCase()+" ";
                        if(datosBusqueda.indexOf(busquedaPalabra) > -1){
                            return true;
                        }else{
                            return false;
                        }
                    });
            });
            SortedList<DIGProblematicaTabla> listaProblematicasOrdenada = 
                    new SortedList<>(listaProblematicasFiltradas);
            listaProblematicasOrdenada.comparatorProperty().bind(
                    tableViewListadoProblematicas.comparatorProperty());
            tableViewListadoProblematicas.setItems(listaProblematicasOrdenada);
    }

    private ArrayList<DIGProblematicaTabla> obtenerProblematicas() throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        ProblematicaDAO problematicaDAO = new ProblematicaDAO();
        ArrayList<Problematica> problematicasObtenidas = new ArrayList();
        ArrayList<DIGProblematicaTabla> listaProblematicasObtenida = new ArrayList();
        DIGProblematicaTabla visualizacionProblematicaTabla = new DIGProblematicaTabla();
        problematicasObtenidas.addAll(
            problematicaDAO.obtenerFechaExperienciaEducativaProblematicaPorRolAcademico(
                informacionSesion .getAcademicoRol()));       
        for(Problematica problematica : problematicasObtenidas){
            visualizacionProblematicaTabla = new DIGProblematicaTabla();
            visualizacionProblematicaTabla.setProblematica(problematica);
            listaProblematicasObtenida.add(visualizacionProblematicaTabla);
        }
        return listaProblematicasObtenida;
    }

    @FXML
    private void consultarProblematicaAcademica(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonConsultar)){
            try{
                DIGProblematicaTabla problematicaSeleccionada = 
                    tableViewListadoProblematicas.getSelectionModel().getSelectedItem();
                validarSeleccionActiva(problematicaSeleccionada);
                llamarConsultarProblematicaGUI(problematicaSeleccionada.getProblematica());
                cargarCamposGUI();
            } catch (InvalidTargetObjectTypeException itotException) {
                Utilidades.mostrarAlertaSinConfirmacion("Sin seleccion en tabla",
                "La problematica aun no ha sido seleccionada, favor de "
                        + "seleccionarla primero",
                        Alert.AlertType.WARNING);
            }   
        }
    }

    @FXML
    private void regresar(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegresar)){
                Stage escenarioPrincipal = 
                    (Stage) textFieldFiltrarProblematicas.getScene().getWindow();
                escenarioPrincipal.close();
            }
    }
    
    private void validarSeleccionActiva(Object object) throws InvalidTargetObjectTypeException{
        if(object == null){
            throw new InvalidTargetObjectTypeException();
        }
    }
    
    private void llamarConsultarProblematicaGUI(Problematica problematicaSeleccionada){
        try{
            FXMLLoader cargadorFXML = new FXMLLoader(
                getClass().getResource("/vista/ConsultaProblematicaAcademicaFXML.fxml"));
            Parent raiz = cargadorFXML.load();
            ConsultaProblematicaAcademicaFXMLController 
                    controladorGUI = cargadorFXML.getController();
            controladorGUI.setProblematicaConsulta(problematicaSeleccionada);
            Stage escenario = new Stage();
            escenario.setTitle("Consulta Problematica Academica");
            escenario.setScene(new Scene(raiz, 600, 300));
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
}

