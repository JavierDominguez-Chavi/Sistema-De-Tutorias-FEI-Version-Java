package controlador;

import DIG.DIGHorarioTabla;
import logicaNegocio.EstudianteDAO;
import logicaNegocio.FechaTutoriaDAO;
import logicaNegocio.HorarioDAO;
import logicaNegocio.PeriodoEscolarDAO;
import logicaNegocio.TutoriaAcademicaDAO;
import dominio.Estudiante;
import dominio.FechaTutoria;
import dominio.globales.InformacionSesion;
import dominio.Horario;
import dominio.PeriodoEscolar;
import dominio.TutoriaAcademica;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RegistrarHorarioDeSesionDeTutoriaFXMLController implements Initializable {
    @FXML
    private Label labelPeriodoEscolar;
    @FXML
    private ComboBox <PeriodoEscolar> comboBoxPeriodoEscolar;
    @FXML
    private ComboBox <FechaTutoria> comboBoxFechaTutoria;
    @FXML
    private TableView  <DIGHorarioTabla>  tableViewHorarios;
    @FXML
    private TableColumn <DIGHorarioTabla, String>  tableColumnMatricula;
    @FXML 
    private TableColumn <DIGHorarioTabla, String> tableColumnNombreEstudiante;
    @FXML 
    private TableColumn <DIGHorarioTabla, Spinner> tableColumnHora;
    @FXML 
    private TableColumn <DIGHorarioTabla, Spinner> tableColumnMinutos;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonGuardar;

    @Override 
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        ObservableList <DIGHorarioTabla> listaEstudiantes;
        try { 
            listaEstudiantes = FXCollections.observableArrayList(obtenerEstudiantesDelTutorAcademico());
            tableViewHorarios.setItems(listaEstudiantes);
            inicializarColumnasTablaHorarios();
            comboBoxPeriodoEscolar.getItems().addAll(obtenerListaPeriodosEscolares());
            editarFormatoComboBoxPeriodoEscolar();
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                cerrar(true);
            });
        }
    } 
    
    private void cerrar(boolean confirmacion){
        if (confirmacion == true){
            Stage escenario = (Stage)buttonGuardar.getScene().getWindow();
            escenario.close();   
        }
    }
    
    private void editarFormatoComboBoxPeriodoEscolar(){
        comboBoxPeriodoEscolar.setConverter(new StringConverter<PeriodoEscolar>() {
            @Override
            public String toString(PeriodoEscolar periodoEscolar) {
                return periodoEscolar == null ? null : periodoEscolar.getFechaInicio().toString() 
                + " - " + periodoEscolar.getFechaFin().toString();
            }
            @Override
            public PeriodoEscolar fromString(String string) {
              return null;
            }
        });
    }
    
    private ArrayList<DIGHorarioTabla> obtenerEstudiantesDelTutorAcademico() throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ArrayList<Estudiante> estudiantesEncontrados = 
        estudianteDAO.obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo(
            informacionSesion.getAcademicoRol().getAcademico(),
            informacionSesion.getProgramaEducativo()
        );
        ArrayList<DIGHorarioTabla> listaEstudiantesObtenidos = new ArrayList<>();
        for (Estudiante estudianteEncontrado : estudiantesEncontrados){
            DIGHorarioTabla horarioEstudiante = new DIGHorarioTabla();
            horarioEstudiante.setEstudiante(estudianteEncontrado);
            listaEstudiantesObtenidos.add(horarioEstudiante);
        }
        Comparator<DIGHorarioTabla> comparador = Comparator.comparing(DIGHorarioTabla::getNombreCompleto);
        List<DIGHorarioTabla> listaEstudiantesOrdenada=
        listaEstudiantesObtenidos.stream().sorted(comparador).collect(Collectors.toList());
        return (ArrayList<DIGHorarioTabla>) listaEstudiantesOrdenada;
    }
      
    private void inicializarColumnasTablaHorarios(){
        tableColumnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tableColumnNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        tableColumnHora.setCellValueFactory(new PropertyValueFactory <>("spinnerHoras"));
        tableColumnMinutos.setCellValueFactory(new PropertyValueFactory <>("spinnerMinutos"));
    }
    
    private ArrayList <PeriodoEscolar> obtenerListaPeriodosEscolares() throws SQLException{
        PeriodoEscolarDAO periodoEscolarDao =  new PeriodoEscolarDAO();
        ArrayList <PeriodoEscolar> periodosEscolares =
        periodoEscolarDao.obtenerTodosLosPeriodosEscolaresRegistrados();
        return periodosEscolares;
    }
    
    private void obtenerListaFechasTutoria(PeriodoEscolar periodoSeleccionado) throws SQLException{
        comboBoxFechaTutoria.getItems().clear();
        FechaTutoriaDAO fechaTutoriaDAO =  new FechaTutoriaDAO();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        ArrayList <FechaTutoria> fechasTutoria = 
        fechaTutoriaDAO.obtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo(periodoSeleccionado, 
        informacionSesion .getProgramaEducativo().getIdProgramaEducativo());
        if(!fechasTutoria.isEmpty()){
            comboBoxFechaTutoria.getItems().addAll(fechasTutoria);
            editarFormatoComboBoxFechaTutoria();
        }
    }
    
    private void editarFormatoComboBoxFechaTutoria(){
        comboBoxFechaTutoria.setConverter(new StringConverter<FechaTutoria>() {
            @Override
            public String toString(FechaTutoria fechaTutoria) {
                return  fechaTutoria == null ? null : fechaTutoria.getFechaTutoria().toString();
            }
            @Override
            public FechaTutoria fromString(String string) {
                return null;
            }
        });
    }
    
    @FXML
    private void obtenerFechasDelPeriodoEscolarSeleccionado(ActionEvent evento){
        try {
            obtenerListaFechasTutoria(comboBoxPeriodoEscolar.getSelectionModel().getSelectedItem());
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private boolean registrarHorarioDeSesionDeTutoria() throws SQLException{
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica = obtenerIdDeLaTutoriaAcademica(tutoriaAcademica);
        HorarioDAO horarioDao = new HorarioDAO();
        ArrayList <DIGHorarioTabla> horariosEstudiantes= new ArrayList<>();
        horariosEstudiantes.addAll(tableViewHorarios.getItems());
        boolean horarioExistente =  validarHorarioComoNuevo(tutoriaAcademica, horarioDao);
        if(!horarioExistente){
            for(DIGHorarioTabla horarioEstudiante:horariosEstudiantes){
                Horario horario = new Horario();
                horario.setEstudiante(horarioEstudiante.getEstudiante());
                horario.setHoraTutoria(java.sql.Time.valueOf
                (Integer.toString((int) horarioEstudiante.getSpinnerHoras().getValue()) + ":" 
                + Integer.toString((int) horarioEstudiante.getSpinnerMinutos().getValue()) + ":" + "0"));
                horario.getTutoriaAcademica().setFechaTutoria(comboBoxFechaTutoria.getValue());
                horario.setTutoriaAcademica(tutoriaAcademica);
                horario.setAsistencia(false);
                horario.setRiesgo(false);  
                horarioDao.registrarHorarioDeLaTutoria(horario); 
            }
        }
        return horarioExistente;
    }
    
    private boolean validarHorarioComoNuevo(TutoriaAcademica tutoriaAcademica,  HorarioDAO horarioDao)
    throws SQLException{
        boolean horarioExistente = false;
        ArrayList <DIGHorarioTabla> horariosEstudiantes= new ArrayList<>();
        horariosEstudiantes.addAll(tableViewHorarios.getItems());
        for(DIGHorarioTabla horarioBuscar:horariosEstudiantes){
            if(horarioDao.buscarHorarioExistentePorIdTutoriaAcademicaYMatricula(tutoriaAcademica, 
            horarioBuscar.getMatricula())){
                horarioExistente = true;
            }
        }
        return horarioExistente;
    }
    
    private TutoriaAcademica obtenerIdDeLaTutoriaAcademica(TutoriaAcademica tutoriaAcademica)
    throws SQLException{
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        tutoriaAcademica.setTutor(informacionSesion .getAcademicoRol().getAcademico());
        tutoriaAcademica.setFechaTutoria(comboBoxFechaTutoria.getValue());
        tutoriaAcademica.setComentarioGeneral("Sin comentario");
        tutoriaAcademica.setReporteEntregado(false);
        if(!tutoriaAcademicaDao.buscarTutoriaAcademicaExistente(tutoriaAcademica)){
            tutoriaAcademicaDao.registrarTutoriaAcademica(tutoriaAcademica);     
        }
        tutoriaAcademicaDao.obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademica);
        return tutoriaAcademica;
    }
    
    @FXML
    private void guardarHorarioDeSesionDeTutoria(ActionEvent evento){
        if(comboBoxPeriodoEscolar.getValue()!=null && comboBoxFechaTutoria.getValue() != null
        && !tableViewHorarios.getItems().isEmpty()){
            validarHorarioDeSesionDeTutoria();
        }else{
           Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos",
           "No puede dejar ningún campo vacío. Por favor, verifique que todos los campos estén llenos",
           Alert.AlertType.WARNING);
        }     
    }
    
    private void validarHorarioDeSesionDeTutoria(){
        try{
            if(!registrarHorarioDeSesionDeTutoria()){
                Utilidades.mostrarAlertaConfirmacion("Información guardada", "La información"
                + " se registró correctamente en el sistema.",Alert.AlertType.INFORMATION);
                if (Utilidades.getOpcion().get() == ButtonType.OK) {
                    Stage escenarioPrincipal = (Stage) labelPeriodoEscolar.getScene().getWindow();
                    escenarioPrincipal.close();
                }
            }else{
                Utilidades.mostrarAlertaSinConfirmacion("Horario existente", "El horario de"
                + " sesión de tutoría para la fecha seleccionada ya se encuentra registrado"
                + " en el sistema.", Alert.AlertType.INFORMATION);
            } 
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    @FXML
    private void cancelarRegistroHorario(ActionEvent evento){
        Utilidades.mostrarAlertaConfirmacion("Cancelar operación",
        "¿Está seguro de que desea cancelar la operación?", Alert.AlertType.CONFIRMATION);
        if (Utilidades.getOpcion().get() == ButtonType.OK) {
            Stage escenarioPrincipal = (Stage) labelPeriodoEscolar.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
}
