package controlador;

import logicaNegocio.AcademicoDAO;
import dominio.Academico;
import dominio.AcademicoRol;
import dominio.globales.InformacionSesion;
import dominio.Rol;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.util.Callback;

public class ConsultaDeProfesoresFXMLController implements Initializable {

    @FXML
    private TableView<Academico> tableViewTablaProfesor;
    @FXML
    private Button buttonRegresar;
    @FXML
    private Button buttonSeleccionarProfesor;
    @FXML
    private TextField textFieldFiltro;
    @FXML
    private TableColumn<Academico, Integer> tableColumnIdentificador;
    @FXML
    private TableColumn<Academico, String> tableColumnNombreProfesor;
    @FXML
    private TableColumn<Academico, String> tableColumnCorreoPersonal;
    @FXML
    private TableColumn<Academico, String> tableColumnCorreoInstitucional;
    
    private ObservableList<Academico> listaProfesores;
    private AcademicoRol usuario;

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try{
            inicializarTabla();
            inicializarColumnas();
            inicializarFiltro();
        }catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenario = (Stage)buttonRegresar.getScene().getWindow();
                escenario.close();
            });
        }
    }
    
    private void inicializarTabla() throws SQLException{
        this.usuario = InformacionSesion.getInformacionSesion().getAcademicoRol();
        Rol rol = usuario.getRol();
        boolean usuarioEsAdmin = rol.getIdRol()==9;
        this.buttonSeleccionarProfesor.setVisible(usuarioEsAdmin);
        listaProfesores = obtenerProfesores();
        tableViewTablaProfesor.setItems(listaProfesores);
    }
    
    private ObservableList<Academico> obtenerProfesores() throws SQLException{
        AcademicoDAO academicoDAO = new AcademicoDAO();
        
        ArrayList<Academico> profesoresObtenidos = 
            academicoDAO.obtenerNombreCompletoDeProfesores(Roles.PROFESOR.getIdRol());
        return FXCollections.observableArrayList(profesoresObtenidos);
    }
    
    private void inicializarColumnas(){
        tableColumnIdentificador.setCellValueFactory(new PropertyValueFactory<>("idAcademico"));
        tableColumnCorreoPersonal.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        tableColumnCorreoInstitucional.setCellValueFactory(
                new PropertyValueFactory<>("correoElectronicoInstitucional")
        );
        tableColumnNombreProfesor.setCellValueFactory(
            new Callback<CellDataFeatures<Academico, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<Academico, String> academico) {
                    return new ReadOnlyObjectWrapper(
                        academico.getValue().getNombre() +" "+ 
                        academico.getValue().getApellidoPaterno()+" " +
                        academico.getValue().getApellidoMaterno()
                    );
                }
            }
        );
    }

    @FXML
    private void cerrar(ActionEvent evento) {
        Stage escenario = (Stage)buttonRegresar.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void seleccionarProfesor(ActionEvent evento) {
        Academico academicoSeleccionado = tableViewTablaProfesor.getSelectionModel().getSelectedItem();
        if (academicoSeleccionado == null){
            Utilidades.mostrarAlertaSinConfirmacion(
                "Mensaje", 
                "Debe seleccionar un profesor de la tabla "
                + "antes de poder modificarlo.", 
                Alert.AlertType.WARNING
            );       
        }else{
            abrirModificarProfesor(academicoSeleccionado);
        }
    }
    
    private void abrirModificarProfesor(Academico academicoSeleccionado){
        Parent raiz;
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                "/vista/ModificarProfesorFXML.fxml"));
            raiz = cargador.load();
            ModificarProfesorFXMLController controller =
                cargador.getController();
            controller.setProfesorSeleccionado(academicoSeleccionado);
            Stage subEscenario = new Stage();
            Stage escenarioActual = (Stage)buttonRegresar.getScene().getWindow();
            subEscenario.initOwner(escenarioActual);
            subEscenario.initModality(Modality.APPLICATION_MODAL);
            subEscenario.setTitle("Modificación de profesor");
            subEscenario.setScene(new Scene(raiz, 550, 400));
            subEscenario.showAndWait();
            this.tableViewTablaProfesor.refresh();
        } catch (IOException ioException) {
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        } catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
    private void inicializarFiltro(){
        FilteredList<Academico> listaFiltradaDeProfesores =
            new FilteredList<>(listaProfesores, profesor -> true);
        
        textFieldFiltro.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            listaFiltradaDeProfesores.setPredicate(profesor -> {
                boolean registroEncontrado = false;
                if (valorNuevo.isEmpty() || valorNuevo.isBlank() || valorNuevo == null){
                    registroEncontrado = true;
                }
                String entradaDelUsuario = valorNuevo.toLowerCase();
                String informacionDelProfesor = 
                    Integer.toString(profesor.getIdAcademico())+" "+
                    profesor.getNombre()+" "+
                    profesor.getApellidoPaterno()+" "+
                    profesor.getApellidoMaterno();
                if (informacionDelProfesor.toLowerCase().contains(entradaDelUsuario)){
                    registroEncontrado = true;
                }
                return registroEncontrado;
            });
        });
        SortedList<Academico> listaOrdenadaDeProfesores = new SortedList<>(listaFiltradaDeProfesores);
        listaOrdenadaDeProfesores.comparatorProperty().bind(tableViewTablaProfesor.comparatorProperty());
        tableViewTablaProfesor.setItems(listaOrdenadaDeProfesores);
    }
    

}
