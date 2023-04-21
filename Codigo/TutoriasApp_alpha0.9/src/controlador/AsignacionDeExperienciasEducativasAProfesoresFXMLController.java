package controlador;

import logicaNegocio.AsignaturaDAO;
import DIG.DIGExperienciaEducativaAcademicoAsignacionTabla;
import dominio.Academico;
import dominio.Asignatura;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AsignacionDeExperienciasEducativasAProfesoresFXMLController implements Initializable {

    private TextField textFieldProfesorSeleccionado;
    @FXML
    private TableView<DIGExperienciaEducativaAcademicoAsignacionTabla> tableViewExperienciaEducativa;
    @FXML
    private TableColumn<DIGExperienciaEducativaAcademicoAsignacionTabla, Integer> 
        tableColumnExperienciaEducativaNRC;
    @FXML
    private TableColumn<DIGExperienciaEducativaAcademicoAsignacionTabla, String> 
        tableColumnExperienciaEducativaNombre;
    @FXML
    private TableColumn<DIGExperienciaEducativaAcademicoAsignacionTabla, CheckBox> 
        tableColumnExperienciaEducativaAsignacion;
    @FXML
    private TextField textFieldFiltro;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonGuardar;
    @FXML
    private Label labelProfesorSeleccionado;
    
    private Academico profesor;
    private ObservableList<DIGExperienciaEducativaAcademicoAsignacionTabla> listaExperienciasDIG;
    private HashSet<Integer> asignaciones;

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            profesor = new Academico();
            asignaciones = new HashSet<>();
            inicializarTabla();
            inicializarColumnas();
            inicializarFiltro();
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void inicializarTabla() throws SQLException {
        ArrayList<Asignatura> experienciasObtenidas = 
            obtenerExperienciasEducativasAcademicos();
        ArrayList<DIGExperienciaEducativaAcademicoAsignacionTabla> arrayExperienciasDIG = 
            crearDIGs(experienciasObtenidas);
        listaExperienciasDIG = FXCollections.observableArrayList(arrayExperienciasDIG);
        tableViewExperienciaEducativa.setItems(listaExperienciasDIG);
    }
    
    public void setProfesor(Academico profesor){
        this.profesor = profesor;
        cargarLabels();
    }
    
    private void cargarLabels(){
        labelProfesorSeleccionado.setText(
            "  "+this.profesor.getNombre() + " "+
            this.profesor.getApellidoPaterno()+ " "+
            this.profesor.getApellidoMaterno()
        );
    }
    
    private ArrayList<DIGExperienciaEducativaAcademicoAsignacionTabla> crearDIGs
    (ArrayList<Asignatura> experienciasObtenidas){
        ArrayList<DIGExperienciaEducativaAcademicoAsignacionTabla> listaExperienciasDIG =
            new ArrayList<DIGExperienciaEducativaAcademicoAsignacionTabla>();
        for (Asignatura experiencia : experienciasObtenidas){
            DIGExperienciaEducativaAcademicoAsignacionTabla experienciaDIG = 
                new DIGExperienciaEducativaAcademicoAsignacionTabla();
            experienciaDIG.setExperienciaEducativa(experiencia);
            experienciaDIG.setAsignaciones(asignaciones);
            listaExperienciasDIG.add(experienciaDIG);
        }
        return listaExperienciasDIG;
    }
    
    private ArrayList<Asignatura> obtenerExperienciasEducativasAcademicos() 
    throws SQLException{
        AsignaturaDAO ExperienciaDAO = new AsignaturaDAO();
        return ExperienciaDAO.obtenerAsignaturasSinAcademico();
    }
    
    private void inicializarColumnas(){
        tableColumnExperienciaEducativaNRC.setCellValueFactory(new PropertyValueFactory<>("NRC"));
        tableColumnExperienciaEducativaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableColumnExperienciaEducativaAsignacion.setCellValueFactory(
                new PropertyValueFactory<>("CheckBoxAsignacion")
        );
    }

    @FXML
    private void cancelar(ActionEvent evento) {
        Utilidades.mostrarAlertaConfirmacion(
            "Cancelar la operación", 
            "¿Está seguro de cancelar?", 
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
        try{
            validarCamposLlenos();
            AsignaturaDAO experienciaDAO = new AsignaturaDAO();
            int idAcademico = this.profesor.getIdAcademico();
            for (Integer NRC : asignaciones){
                experienciaDAO.asignarAsignaturaAProfesor(idAcademico, NRC);
            } 
            terminar();
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        }catch (IllegalArgumentException iaException){
            Utilidades.mostrarAlertaSinConfirmacion(
                "Sin experiencia educativa", iaException.getMessage(), Alert.AlertType.WARNING);
        }
    }
    
    private void terminar(){
        Utilidades.mostrarAlertaSinConfirmacion(
        "Mensaje", 
        "La información se ha actualizado correctamente", 
        Alert.AlertType.INFORMATION);
        cerrar(true); 
    }
    
    private void validarCamposLlenos() throws IllegalArgumentException{
        final boolean CAMPOS_LLENOS = asignaciones.size()>0;
        if (!CAMPOS_LLENOS){
            throw new IllegalArgumentException("Debe seleccionar al menos una experiencia "
                    + "educativa antes de guardar la información.");
        }
    }
    
    private void inicializarFiltro(){
        FilteredList<DIGExperienciaEducativaAcademicoAsignacionTabla> experienciasFiltradas =
            new FilteredList<>(listaExperienciasDIG, experiencia -> true);
        
        textFieldFiltro.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            experienciasFiltradas.setPredicate(experiencia -> {
                boolean registroEncontrado = false;
                if (valorNuevo.isEmpty() || valorNuevo.isBlank() || valorNuevo == null){
                    registroEncontrado = true;
                }
                String entradaDeUsuario = valorNuevo.toLowerCase();
                if (experiencia.getNombre().toLowerCase().contains(entradaDeUsuario)){
                    registroEncontrado = true;
                }
                if (Integer.toString(experiencia.getNRC()).contains(entradaDeUsuario)){
                    registroEncontrado = true;
                }
                return registroEncontrado;
            });
        });
        SortedList<DIGExperienciaEducativaAcademicoAsignacionTabla> listaDeExperienciasOrdanada = 
            new SortedList<>(experienciasFiltradas);
        listaDeExperienciasOrdanada.comparatorProperty().bind(tableViewExperienciaEducativa.comparatorProperty());
        tableViewExperienciaEducativa.setItems(listaDeExperienciasOrdanada);
    }
    
}
