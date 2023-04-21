package controlador;

import logicaNegocio.ProblematicaDAO;
import dominio.Problematica;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import DIG.DIGProblematicaTabla;
import logicaNegocio.SolucionDAO;
import dominio.constantes.Roles;
import dominio.globales.InformacionSesion;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javax.management.modelmbean.InvalidTargetObjectTypeException;

public class ListaSolucionesProblematicasAcademicasFXMLController implements Initializable {

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
    private Button buttonRegistrar;
    @FXML
    private TextField textFieldFiltrar;
    
    private ObservableList<DIGProblematicaTabla> listaProblematicas = FXCollections.observableArrayList();

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
            bloquearBotonParaRegistrarSolucion();
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenarioPrincipal = (Stage) textFieldFiltrar.getScene().getWindow();
                escenarioPrincipal.close();
            });
        }
    }
    
    private void bloquearBotonParaRegistrarSolucion(){
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        final int ID_ROL_JEFEDECARRERA = Roles.JEFE_DE_CARRERA.getIdRol();
        if(informacionSesion.getAcademicoRol().getRol().getIdRol() != ID_ROL_JEFEDECARRERA){
            this.buttonRegistrar.setVisible(false);
            this.buttonRegresar.setLayoutX(430);
        }
    }
    
    private void inicializarColumnas(){
        tableColumnFechaTutoria.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumnExperienciaEducativa.setCellValueFactory(
                new PropertyValueFactory<>("experienciaEducativa"));
    }
    
    private void inicializarFiltro(){
        FilteredList<DIGProblematicaTabla> listaProblematicasFiltrada = 
            new FilteredList(listaProblematicas, problematica -> true);
            textFieldFiltrar.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
                listaProblematicasFiltrada.setPredicate(digProblematicaTabla -> {
                    if(valorNuevo.isEmpty() || valorNuevo.isBlank() || valorNuevo == null){
                        return true;
                    }
                    String busquedaPalabra = valorNuevo.toLowerCase();
                    String datosBusqueda = 
                            digProblematicaTabla.getFecha().toString().toLowerCase()+" "
                            + digProblematicaTabla.getTitulo().toLowerCase()+" "
                            +digProblematicaTabla.getExperienciaEducativa().toLowerCase();
                    if(datosBusqueda.indexOf(busquedaPalabra) > -1){
                        return true;
                    }else{
                        return false;
                    }
                });
            });
            SortedList<DIGProblematicaTabla> listaProblematicasOrdenada = 
                new SortedList<>(listaProblematicasFiltrada);
            listaProblematicasOrdenada.comparatorProperty().bind(tableViewListadoProblematicas.comparatorProperty());
            tableViewListadoProblematicas.setItems(listaProblematicasOrdenada);
    }
    
    private ArrayList<DIGProblematicaTabla> obtenerProblematicas() throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        ProblematicaDAO problematicaDAO = new ProblematicaDAO();
        ArrayList<Problematica> problematicasObtenidas = new ArrayList();
        ArrayList<DIGProblematicaTabla> listaProblematicasObtenidas = new ArrayList();
        DIGProblematicaTabla vistaProblematicaTabla = new DIGProblematicaTabla();
        problematicasObtenidas.addAll(
            problematicaDAO.obtenerFechaExperienciaEducativaProblematicaPorProgramaEducativo(
            informacionSesion .getAcademicoRol()));       
        for(Problematica problematica : problematicasObtenidas){
            vistaProblematicaTabla = new DIGProblematicaTabla();
            vistaProblematicaTabla.setProblematica(problematica);
            listaProblematicasObtenidas.add(vistaProblematicaTabla);
            }
        return listaProblematicasObtenidas;
    }

    @FXML
    private void consultarSolucionProblematicaAcademica(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonConsultar)){
            try{
                DIGProblematicaTabla problematicaSeleccionada = 
                        tableViewListadoProblematicas.getSelectionModel().getSelectedItem();
                validarSeleccionActiva(problematicaSeleccionada);
                validarSinSolucion(problematicaSeleccionada);
                llamarVentanaConsultar(problematicaSeleccionada.getProblematica());
                cargarCamposGUI();
            }catch(UnsupportedOperationException uoException){
                Utilidades.mostrarAlertaSinConfirmacion("Funcionalidad No Disponible",
                "La problematica aun no tiene una Solucion registrada, favor de "
                        + "registrarla primero",
                Alert.AlertType.WARNING);
            }catch(InvalidTargetObjectTypeException itotException){
                Utilidades.mostrarAlertaSinConfirmacion("Sin seleccion en tabla",
                "La problematica aun no ha sido seleccionada, favor de "
                        + "seleccionarla primero",
                        Alert.AlertType.WARNING);
            }catch(SQLException sqlException){
                Utilidades.mensajePerdidaDeConexion();
            }
        }
    }

    @FXML
    private void registrarSolucionProblematicaAcademica(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegistrar)){
            try{
                DIGProblematicaTabla problematicaSeleccionada = 
                    tableViewListadoProblematicas.getSelectionModel().getSelectedItem();
                validarSeleccionActiva(problematicaSeleccionada);
                validarConSolucion(problematicaSeleccionada);
                llamarVentanaRegistrar(problematicaSeleccionada.getProblematica());
            }catch(UnsupportedOperationException uoException){
                Utilidades.mostrarAlertaSinConfirmacion("Funcionalidad No Disponible",
                "La problematica ya tiene una Solucion registrada, favor de "
                        + "consultarla",
                Alert.AlertType.WARNING);
            }catch(InvalidTargetObjectTypeException itotException){
                Utilidades.mostrarAlertaSinConfirmacion("Sin seleccion en tabla",
                "La problematica aun no ha sido seleccionada, favor de "
                        + "seleccionarla primero",
                        Alert.AlertType.WARNING);
            }catch(SQLException sqlException){
                Utilidades.mensajePerdidaDeConexion();
            }
        } 
    }
    
    @FXML
    private void regresar(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegresar)){
                Stage escenarioPrincipal = (Stage) textFieldFiltrar.getScene().getWindow();
                escenarioPrincipal.close();
            }
    }
    
    public void validarSinSolucion(DIGProblematicaTabla problematica) 
            throws UnsupportedOperationException, SQLException{
        SolucionDAO solucionDAO = new SolucionDAO();
        boolean validacion = 
            solucionDAO.verificarSolucionaProblematicaPorIdProblematica(
                    problematica.getProblematica().getIdProblematica());
        if(!validacion){
            throw new UnsupportedOperationException();
        }
    }
    
    public void validarConSolucion(DIGProblematicaTabla problematica) 
            throws UnsupportedOperationException, SQLException{
        SolucionDAO solucionDAO = new SolucionDAO();
        boolean validacion = 
            solucionDAO.verificarSolucionaProblematicaPorIdProblematica(
                    problematica.getProblematica().getIdProblematica());
        if(validacion){
            throw new UnsupportedOperationException();
        }
    }
    
    private void validarSeleccionActiva(Object object) throws InvalidTargetObjectTypeException{
        if(object == null){
            throw new InvalidTargetObjectTypeException();
        }
    }
    
    private void llamarVentanaRegistrar(Problematica problematicaSeleccionada){ 
        try{
            FXMLLoader cargadorFXML = new FXMLLoader(
                    getClass().getResource("/vista/RegistroSolucionProblematicaAcademicaFXML.fxml"));
            Parent raiz = cargadorFXML.load();
            RegistroSolucionProblematicaAcademicaFXMLController controladorGUI = 
                cargadorFXML.getController();
            controladorGUI.setProblematica(problematicaSeleccionada);
            Stage escenario = new Stage();
            escenario.setTitle("Registrar Solucion de Problematica Academica");
            escenario.setScene(new Scene(raiz, 600, 475));
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
    public void llamarVentanaConsultar(Problematica problematicaSeleccionada){
        try{
            FXMLLoader cargadorFXML = new FXMLLoader(
                getClass().getResource("/vista/ConsultaSolucionProblematicaAcademicaFXML.fxml"));
            Parent raiz = cargadorFXML.load();
            ConsultaSolucionProblematicaAcademicaFXMLController controladorGUI = 
                    cargadorFXML.getController();
            controladorGUI.setProblematicaSolucionConsulta(problematicaSeleccionada);
            Stage escenario = new Stage();
            escenario.setTitle("Consulta Solucion Problematica Academica");
            escenario.setScene(new Scene(raiz, 600, 500));
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
}
