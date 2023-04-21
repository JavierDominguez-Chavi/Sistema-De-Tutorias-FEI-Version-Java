package controlador;

import logicaNegocio.EstudianteDAO;
import dominio.Estudiante;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import DIG.DIGEstudianteTabla;
import dominio.constantes.Roles;
import dominio.globales.InformacionSesion;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javax.management.modelmbean.InvalidTargetObjectTypeException;

public class ListaEstudiantesFXMLController implements Initializable {
    @FXML
    private TableView<DIGEstudianteTabla> tableViewListadoEstudiantes;
    @FXML
    private TableColumn<DIGEstudianteTabla, String> tableColumnMatricula;
    @FXML
    private TableColumn<DIGEstudianteTabla, String> tableColumnNombre;
    @FXML
    private Button buttonConsultar;
    @FXML
    private Button buttonRegresar;
    @FXML
    private Button buttonRegistrarEstudiante;
    @FXML
    private TextField textFieldFiltrarEstudiantes;
    
    private ObservableList<DIGEstudianteTabla> listaEstudiantes = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        inicializarColumnas();
        cargarCamposGUI();
    }
    
    private void mostrarButtonRegistrarEstudiante(){
        final int ROL_COORDINADOR = Roles.COORDINADOR_DE_TUTORIAS_ACADEMICAS.getIdRol();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        final int ROL_USUARIO = informacionSesion .getAcademicoRol().getRol().getIdRol();
        final boolean MOSTRAR_BOTON = ROL_USUARIO==ROL_COORDINADOR;
        this.buttonRegistrarEstudiante.setVisible(MOSTRAR_BOTON);
    }
    
    private void cargarCamposGUI(){
        try{
            listaEstudiantes = FXCollections.observableArrayList(obtenerEstudiantes());
            tableViewListadoEstudiantes.setItems(listaEstudiantes);
            mostrarButtonRegistrarEstudiante();
            inicializarFiltro();
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenarioPrincipal = (Stage) textFieldFiltrarEstudiantes.getScene().getWindow();
                escenarioPrincipal.close();
            });
        }
    }
    private void inicializarColumnas(){
        tableColumnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }
    
    private void inicializarFiltro(){
        FilteredList<DIGEstudianteTabla> listaFiltradaDeEstudiantes = 
        new FilteredList(listaEstudiantes, estudiante -> true);
        textFieldFiltrarEstudiantes.textProperty().addListener((observable, valorAnterior, nuevoValor) -> {
            listaFiltradaDeEstudiantes.setPredicate(digEstudianteTabla -> {
                if(nuevoValor.isEmpty() || nuevoValor.isBlank() || nuevoValor == null){
                    return true;
                }
                String busquedaPalabra = nuevoValor.toLowerCase();
                String datosBusqueda = digEstudianteTabla.getNombre().toLowerCase()+" "
                        +digEstudianteTabla.getMatricula().toLowerCase();
                if(datosBusqueda.indexOf(busquedaPalabra) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<DIGEstudianteTabla> listaOrdenadaDeEstudiantes = new SortedList<>(listaFiltradaDeEstudiantes);
        listaOrdenadaDeEstudiantes.comparatorProperty().bind(tableViewListadoEstudiantes.comparatorProperty());
        tableViewListadoEstudiantes.setItems(listaOrdenadaDeEstudiantes);
    }
    
    private ArrayList<DIGEstudianteTabla> obtenerEstudiantes() throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ArrayList<Estudiante> estudiantesObtenidos = new ArrayList();
        ArrayList<DIGEstudianteTabla> listaEstudiantesObtenida = new ArrayList();
        DIGEstudianteTabla visualizacionEstudianteTabla = new DIGEstudianteTabla();
        estudiantesObtenidos.addAll(estudianteDAO.obtenerEstudiantesPorIdProgramaEducativo(
                informacionSesion .getAcademicoRol().getProgramaEducativo().getIdProgramaEducativo()));
        for(Estudiante estudiante : estudiantesObtenidos){
            visualizacionEstudianteTabla = new DIGEstudianteTabla();
            visualizacionEstudianteTabla.setEstudiante(estudiante);
            listaEstudiantesObtenida.add(visualizacionEstudianteTabla);
        }
        return listaEstudiantesObtenida;
    }
    
    @FXML
    private void consultarEstudiante(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonConsultar)){
            try{
                DIGEstudianteTabla estudianteSeleccionado = 
                        tableViewListadoEstudiantes.getSelectionModel().getSelectedItem();
                validarSeleccionActiva(estudianteSeleccionado);
                llamarConsultarEstudianteGUI(estudianteSeleccionado.getEstudiante());
            }catch(InvalidTargetObjectTypeException itotException){
                Utilidades.mostrarAlertaSinConfirmacion("Sin seleccion en tabla",
                "El estudiante aun no ha sido seleccionado, favor de "
                        + "seleccionarlo primero",
                        Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    private void regresar(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegresar)){
                Stage escenarioPrincipal = (Stage) textFieldFiltrarEstudiantes.getScene().getWindow();
                escenarioPrincipal.close();
            }
    }
    
    @FXML
    private void registrarEstudiante(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegistrarEstudiante)){
            llamarRegistrarEstudianteGUI();
            cargarCamposGUI();
        }
    }
    private void validarSeleccionActiva(Object object) throws InvalidTargetObjectTypeException{
        if(object == null){
            throw new InvalidTargetObjectTypeException();
        }
    }
    
    
    private void llamarConsultarEstudianteGUI(Estudiante estudianteSeleccionado){
        try {
            FXMLLoader cargadorFXML = new FXMLLoader(
                getClass().getResource("/vista/ConsultaEstudianteFXML.fxml"));
            Parent raiz = cargadorFXML.load();
            ConsultaEstudianteFXMLController controladorGUI = cargadorFXML.getController();
            controladorGUI.setEstudianteConsulta(estudianteSeleccionado);
            Stage escenario = new Stage();
            escenario.setTitle("Consulta Estudiante");
            escenario.setScene(new Scene(raiz, 600, 250));
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch(IOException ioException){
             Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
    private void llamarRegistrarEstudianteGUI(){
        try {
            
            Parent raiz = FXMLLoader.load(getClass().getResource("/vista/RegistroDeEstudiantesFXML.fxml"));
            Scene escena = new Scene(raiz, 660, 420);
            Stage escenario = new Stage();
            escenario.setTitle("Registro de Estudiante");
            escenario.setResizable(false);
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }

    
}
