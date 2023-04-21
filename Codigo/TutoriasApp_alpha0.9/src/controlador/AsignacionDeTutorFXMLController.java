package controlador;

import DIG.DIGAcademicoAsignacionTutorTabla;
import logicaNegocio.AcademicoDAO;
import logicaNegocio.EstudianteDAO;
import dominio.Academico;
import dominio.Estudiante;
import dominio.constantes.Roles;
import dominio.globales.InformacionSesion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public abstract class AsignacionDeTutorFXMLController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private GridPane gridPane;
    @FXML
    private TableView<Estudiante> tableViewEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> tableColumnMatricula;
    @FXML
    private TableView<DIGAcademicoAsignacionTutorTabla> tableViewTutores;
    @FXML
    private TableColumn<DIGAcademicoAsignacionTutorTabla, Integer> tableColumnNumeroTutorados;
    @FXML
    private TextField textFieldFiltroEstudiantes;
    @FXML
    private TextField textFieldFiltroTutores;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonGuardar;    
    @FXML
    private TableColumn<Estudiante, String> tableColumnNombreEstudiante;
    @FXML
    private TableColumn<Estudiante, String> tableColumnTutorAsignado;
    @FXML
    private TableColumn<Estudiante, Void> tableColumnAsignar;
    @FXML
    private TableColumn<DIGAcademicoAsignacionTutorTabla, String> tableColumnNombreTutor;
    
    private ObservableList<Estudiante> listaEstudiantes;
    private ObservableList<DIGAcademicoAsignacionTutorTabla> listaTutores;
    private HashMap<Integer, DIGAcademicoAsignacionTutorTabla> hashMapNumeroTutoradosPorTutor;   
    
    protected abstract ArrayList<Estudiante> obtenerEstudiantes() throws SQLException;

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            listaTutores = FXCollections.observableArrayList(obtenerTutores());
            llenarHashMapNumeroTutoradosPorTutor();
            listaEstudiantes = FXCollections.observableArrayList(obtenerEstudiantes());
            
            this.tableViewTutores.setItems(listaTutores);
            this.tableViewEstudiantes.setItems(listaEstudiantes);
            inicializarColumnas();
            inicializarFiltroTutores();
            inicializarFiltroEstudiantes();
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                cerrar(true);
            });
        }
    }
    
    private void inicializarFiltroEstudiantes(){
        FilteredList<Estudiante> listaDeEstudiantesFiltrados =
            new FilteredList<>(listaEstudiantes, (Estudiante estudiante) -> true);
        textFieldFiltroEstudiantes.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            listaDeEstudiantesFiltrados.setPredicate(estudiante -> {
                boolean registroEncontrado = false;
                if (valorNuevo.isEmpty() || valorNuevo.isBlank() || valorNuevo == null){
                    registroEncontrado = true;
                }else{
                    String entradaDelUsuario = valorNuevo.toLowerCase();
                    String datosDelEstudiante = 
                       estudiante.getMatricula()+" "+
                       estudiante.getNombre()+" "+
                       estudiante.getApellidoMaterno()+" "+
                       estudiante.getApellidoPaterno()+" "+
                       estudiante.getAcademico().getNombre()+" "+
                       estudiante.getAcademico().getApellidoPaterno()+" "+
                       estudiante.getAcademico().getApellidoMaterno()+" ";
                    registroEncontrado = datosDelEstudiante.toLowerCase().contains(entradaDelUsuario);
                }
                return registroEncontrado;
            });
        });
        SortedList<Estudiante> listaDeEstudiantesOrdenados = new SortedList<>(listaDeEstudiantesFiltrados);
        listaDeEstudiantesOrdenados.comparatorProperty().bind(tableViewEstudiantes.comparatorProperty());
        tableViewEstudiantes.setItems(listaDeEstudiantesOrdenados);
        tableViewEstudiantes.refresh();
    }
    
    private void inicializarFiltroTutores(){
        FilteredList<DIGAcademicoAsignacionTutorTabla> listaDeTutoresFiltrados =
            new FilteredList<>(listaTutores, (DIGAcademicoAsignacionTutorTabla tutor) -> true);
        textFieldFiltroTutores.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            listaDeTutoresFiltrados.setPredicate(tutor -> {
                boolean registroEncontrado = false;
                if (valorNuevo.isEmpty() || valorNuevo.isBlank() || valorNuevo == null){
                    registroEncontrado = true;
                }else{
                    String entradaDelUsuario = valorNuevo.toLowerCase();
                    registroEncontrado = 
                        tutor.getNombreCompleto().toLowerCase().contains(entradaDelUsuario);
                }
                return registroEncontrado;
            });
        });
        SortedList<DIGAcademicoAsignacionTutorTabla> listaDeTutoresOrdenados = 
            new SortedList<>(listaDeTutoresFiltrados);
        listaDeTutoresOrdenados.comparatorProperty().bind(tableViewTutores.comparatorProperty());
        tableViewTutores.setItems(listaDeTutoresOrdenados);
    }
    
    private void llenarHashMapNumeroTutoradosPorTutor(){
        this.hashMapNumeroTutoradosPorTutor = new HashMap<>();
        for (DIGAcademicoAsignacionTutorTabla tutor : listaTutores){
            hashMapNumeroTutoradosPorTutor.put(
                tutor.getAcademico().getIdAcademico(), 
                tutor
            );
        }
    }

    private ArrayList<DIGAcademicoAsignacionTutorTabla> obtenerTutores() throws SQLException{
        AcademicoDAO academicoDAO = new AcademicoDAO();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        int programaEducativoDelCoordinador = 
            informacionSesion.getProgramaEducativo().getIdProgramaEducativo();
        ArrayList<Academico> academicosObtenidos = 
            academicoDAO.obtenerNombreCompletoDeTutoresPorIdProgramaEducativo(
                programaEducativoDelCoordinador, Roles.TUTOR_ACADEMICO.getIdRol()
            );
        ArrayList<DIGAcademicoAsignacionTutorTabla> tutores = 
                new ArrayList<DIGAcademicoAsignacionTutorTabla>();
        for (Academico tutor : academicosObtenidos){
            int idTutor = tutor.getIdAcademico();
            int numeroTutorados = academicoDAO.obtenerNumeroDeTutoradosDeTutorPorIdAcademico(idTutor);
            DIGAcademicoAsignacionTutorTabla DIGTutor = new DIGAcademicoAsignacionTutorTabla(numeroTutorados);
            DIGTutor.setAcademico(tutor);
            tutores.add(DIGTutor);
        }
        return tutores;
    }
    
    private boolean validarSiEstudianteTieneTutor(Estudiante estudiante){
        return estudiante.getAcademico().getIdAcademico()>0;
    }
    
    private boolean validarQueNoSeaMismoTutor(Academico tutorActual, Academico tutorSeleccionado){
        return tutorActual.getIdAcademico()!=tutorSeleccionado.getIdAcademico();
    }
    
    private boolean tutorSeleccionadoPuedeRecibirOtroTutorado
    (DIGAcademicoAsignacionTutorTabla tutorSeleccionado){
        boolean validacion = true;
        if (tutorSeleccionado.puedeAceptarMasTutorados() == false){
            validacion = pedirConfirmacionDeAsignacion(tutorSeleccionado.getNombreCompleto());
        }
        return validacion;
    }
    
    private boolean pedirConfirmacionDeAsignacion(String nombreTutor) {
        Utilidades.mostrarAlertaConfirmacion(
            "Confirmar asignación", 
            "El tutor "+nombreTutor
            + " ya tiene de mas de 30 tutorados asignados. "
            + "¿Desea asignarle más?",
            Alert.AlertType.CONFIRMATION);
        boolean confirmarAsignacion =
            Utilidades.getOpcion().orElse(ButtonType.CANCEL).getButtonData().isDefaultButton();
        return confirmarAsignacion;
    }
    
    private void validarQueSeSeleccionoUnTutor() throws IllegalArgumentException{
        if (tableViewTutores.getSelectionModel().getSelectedItem()==null){
            throw new IllegalArgumentException("Debe seleccionar un tutor académico "
                    + "antes de hacer una asignación");
        }
    }
    
    private void removerTutorActualDelEstudianteSeleccionado
    (Estudiante estudianteSeleccionado, Academico tutorAnterior, 
    DIGAcademicoAsignacionTutorTabla tutorSeleccionado){
        Academico tutorNuevo = tutorSeleccionado.getAcademico();
        boolean estudianteTieneTutor = validarSiEstudianteTieneTutor(estudianteSeleccionado);
        boolean tutorEsDistinto = validarQueNoSeaMismoTutor(tutorNuevo, tutorAnterior);
        if (estudianteTieneTutor && tutorEsDistinto){
            hashMapNumeroTutoradosPorTutor.get(
                tutorAnterior.getIdAcademico()).restarTutorado(estudianteSeleccionado);  
        }
    }
    
    private void asignarTutorSeleccionadoAlEstudianteSeleccionado
    (Estudiante estudianteSeleccionado, Academico tutorAnterior, 
    DIGAcademicoAsignacionTutorTabla tutorSeleccionado){
        Academico tutorNuevo = tutorSeleccionado.getAcademico();
        boolean tutorEsDistinto = validarQueNoSeaMismoTutor(tutorNuevo, tutorAnterior);
        boolean tutorPuedeRecibirOtroTutorado = 
            tutorSeleccionadoPuedeRecibirOtroTutorado(tutorSeleccionado);
        if (tutorEsDistinto && tutorPuedeRecibirOtroTutorado){
            tutorSeleccionado.sumarTutorado(estudianteSeleccionado);
            estudianteSeleccionado.setAcademico(tutorSeleccionado.getAcademico());  
        }
    }
    
    private void cambiarTutores(Estudiante estudianteSeleccionado){
        try{
            Academico tutorActual = estudianteSeleccionado.getAcademico();
            DIGAcademicoAsignacionTutorTabla tutorSeleccionado = 
                tableViewTutores.getSelectionModel().getSelectedItem();
            validarQueSeSeleccionoUnTutor();
            validarTutorSeleccionadoPerteneceAProgramaEducativoDeCoordinador(tutorActual);
            removerTutorActualDelEstudianteSeleccionado(estudianteSeleccionado, tutorActual, tutorSeleccionado);
            asignarTutorSeleccionadoAlEstudianteSeleccionado(
                estudianteSeleccionado, tutorActual, tutorSeleccionado);
            tableViewEstudiantes.refresh();
            tableViewTutores.refresh();
        } catch (IllegalArgumentException iaException){
            Utilidades.mostrarAlertaConfirmacion(
                "Mensaje", iaException.getMessage(), 
                Alert.AlertType.WARNING);
        }
    }
    
    private void validarTutorSeleccionadoPerteneceAProgramaEducativoDeCoordinador (Academico tutorActual) 
    throws IllegalArgumentException{
        if (this.hashMapNumeroTutoradosPorTutor.containsKey(tutorActual.getIdAcademico()) == false
            && tutorActual.getIdAcademico()!=0){
            throw new IllegalArgumentException(
                "El estudiante seleccionado tiene un tutor que no"
                + " fue registrado al programa educativo actual. "
                + "Por favor, registre a "+tutorActual.getNombreCompleto()+" como tutor "
                + "de su programa educativo en Tutores Académicos y Estudiantes > Registrar Tutor Académico");
        }
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
        try{
            for (DIGAcademicoAsignacionTutorTabla tutor : listaTutores){
                if (tutor.cambiaronSusAsignaciones()){
                    registrarAsignacionesDeTutor(tutor);
                }
            }
            Utilidades.mostrarAlertaSinConfirmacion(
                "Mensaje", 
                "La información se ha actualizado correctamente", 
                Alert.AlertType.INFORMATION);
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void registrarAsignacionesDeTutor(DIGAcademicoAsignacionTutorTabla tutor) throws SQLException {
        HashSet<String>nuevosTutoradosAsignados=tutor.getNuevosTutoradosAsignados();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        for (String matricula : nuevosTutoradosAsignados){
            estudianteDAO.asignarTutorAcademico(matricula, tutor.getAcademico().getIdAcademico());
        }
        tutor.limpiarHashSetNuevosTutoradosAsignados();
    }
    
    private void inicializarColumnas(){
        tableColumnNombreTutor.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        tableColumnNumeroTutorados.setCellValueFactory(new PropertyValueFactory<>("numeroTutorados"));
        tableColumnNombreEstudiante.setCellValueFactory(
            (TableColumn.CellDataFeatures<Estudiante, String> estudiante) -> new ReadOnlyObjectWrapper(
                estudiante.getValue().getNombre() +" "+ 
                estudiante.getValue().getApellidoPaterno()+" " +
                estudiante.getValue().getApellidoMaterno()
            )
        );
        tableColumnMatricula.setCellValueFactory(
            (TableColumn.CellDataFeatures<Estudiante, String> estudiante) -> new ReadOnlyObjectWrapper(
                estudiante.getValue().getMatricula()
            )
        );
        tableColumnTutorAsignado.setCellValueFactory(
            (TableColumn.CellDataFeatures<Estudiante, String> estudiante) -> {
                Academico tutor = estudiante.getValue().getAcademico();
                String nombreTutor = 
                    tutor.getNombre()+" "+
                    tutor.getApellidoPaterno()+" "+
                    tutor.getApellidoMaterno();
                return new ReadOnlyObjectWrapper(nombreTutor);
            }
        );
        Callback<TableColumn<Estudiante, Void>, TableCell<Estudiante, Void>> constructorCelda = 
        (final TableColumn<Estudiante, Void> parametro) -> {
            final TableCell<Estudiante, Void> celda = new TableCell<Estudiante, Void>() {
                private final Button buttonAsignarTutor = new Button("+");
                
                {
                    buttonAsignarTutor.setDefaultButton(true);
                    buttonAsignarTutor.setOnAction((ActionEvent evento) -> {
                        Estudiante estudianteSeleccionado = getTableView().getItems().get(getIndex());
                        cambiarTutores(estudianteSeleccionado);
                    });
                }
                @Override
                public void updateItem(Void item, boolean vacio) {
                    super.updateItem(item, vacio);
                    if (vacio) {
                        setGraphic(null);
                    } else {
                        setGraphic(buttonAsignarTutor);
                    }
                }
            };
            return celda;
        };
        tableColumnAsignar.setCellFactory(constructorCelda);
    }
}

