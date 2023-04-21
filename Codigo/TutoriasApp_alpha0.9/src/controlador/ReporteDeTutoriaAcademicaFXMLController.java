package controlador;

import DIG.DIGEstudiantesAsistentesOEnRiesgoTabla;
import DIG.DIGProblematicaTabla;
import logicaNegocio.HorarioDAO;
import logicaNegocio.ProblematicaDAO;
import logicaNegocio.TutoriaAcademicaDAO;
import dominio.Academico;
import dominio.FechaTutoria;
import dominio.globales.InformacionSesion;
import dominio.Horario;
import dominio.Problematica;
import dominio.TutoriaAcademica;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import formatosReporte.FormatoReporteDeTutoriaAcademica;

public class ReporteDeTutoriaAcademicaFXMLController implements Initializable {
    @FXML
    private Label labelNumeroDeTutoria;
    @FXML
    private Label labelProgramaEducativo;
    @FXML
    private Label labelPeriodoEscolar;
    @FXML
    private Label labelFechaDeTutoria;
    @FXML
    private TableView <DIGEstudiantesAsistentesOEnRiesgoTabla>  tableViewEstudiantesAsistentesOEnRiesgo;
    @FXML
    private TableColumn <DIGEstudiantesAsistentesOEnRiesgoTabla, String>  tableColumnNombreEstudiante;
    @FXML
    private TableColumn <DIGEstudiantesAsistentesOEnRiesgoTabla, CheckBox>  tableColumnAsistencia;
    @FXML
    private TableColumn <DIGEstudiantesAsistentesOEnRiesgoTabla, CheckBox>  tableColumnEstudianteEnRiesgo;
    @FXML
    private TableView <DIGProblematicaTabla> tableViewProblematicasAcademicas;
    @FXML
    private TableColumn <DIGProblematicaTabla, String> tableColumnNombreExperienciaEducativa;
    @FXML
    private TableColumn <DIGProblematicaTabla, String> tableColumnNombreProfesor;
    @FXML
    private TableColumn <DIGProblematicaTabla, String> tableColumnDescripcionProblematica;
    @FXML
    private TableColumn <DIGProblematicaTabla, String> tableColumnTituloProblematica;
    @FXML
    private Text textComentarioGeneral;
    @FXML
    private ScrollPane scrollPaneComentarioGeneral;
    private Button buttonImprimirReporteDeTutoriasAcademica;
    @FXML
    private Button buttonDescargarReporteDeTutoriasAcademica;
    @FXML
    private Button buttonSalirReporteGeneralDeTutoriasAcademica;
    
    private Academico tutorAcademico;
    private FechaTutoria fechaTutoria;
             
    public void setFechaTutoriaAcademicaYTutorAcademicoSeleccionado
    (Academico tutorAcademicoSeleccionado, FechaTutoria fechaDeTutoriaAcademicaSeleccionada){
        tutorAcademico = new Academico();
        fechaTutoria = new FechaTutoria();
        tutorAcademico = tutorAcademicoSeleccionado;
        fechaTutoria = fechaDeTutoriaAcademicaSeleccionada;
        cargarCamposGUIReporteDeTutoriaAcademica();
    }
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        
    }   
    
    private void cargarCamposGUIReporteDeTutoriaAcademica(){
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        cargarInformacionDeEtiquetas(informacionSesion );
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setFechaTutoria(fechaTutoria);
        tutoriaAcademica.setTutor(tutorAcademico);
        try {
            tutoriaAcademica = obtenerIdDeLaTutoriaAcademica(tutoriaAcademica);
            cargarInformacionTablaEstudiantesAsistentesyEnRiesgo(tutoriaAcademica);
            cargarInformacionDeLaTablaProblematicasAcademicas(tutoriaAcademica);
             cargarInformacionComentarioGeneral(tutoriaAcademica);
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void cargarInformacionDeEtiquetas(InformacionSesion informacionSesion ){
        labelNumeroDeTutoria.setText(Integer.toString(fechaTutoria.getNumeroSesion()));
        labelProgramaEducativo.setText(informacionSesion .getProgramaEducativo().getNombre());
        labelPeriodoEscolar.setText(fechaTutoria.getPeriodo().getFechaInicio() + " - " +
        fechaTutoria.getPeriodo().getFechaFin());
        labelFechaDeTutoria.setText(fechaTutoria.getFechaTutoria().toString());
    }
    
    private void cargarInformacionTablaEstudiantesAsistentesyEnRiesgo(TutoriaAcademica tutoriaAcademica)
    throws SQLException{
        ObservableList<DIGEstudiantesAsistentesOEnRiesgoTabla> listaDeEstudiantesAsistentesOEnRiesgo;
        listaDeEstudiantesAsistentesOEnRiesgo = 
        FXCollections.observableArrayList(obtenerEstudiantesAsistentesOEnRiesgo(tutoriaAcademica));
        tableViewEstudiantesAsistentesOEnRiesgo.setItems(listaDeEstudiantesAsistentesOEnRiesgo);
        inicializarColumnasTablaEstudiantesAsistentesOEnRiesgo();
    }
    
    private ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla>obtenerEstudiantesAsistentesOEnRiesgo
   (TutoriaAcademica tutoriaAcademica) throws SQLException{
        ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla> listaDeEstudiantesAsistentesOEnRiesgo = 
        new ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla>();
        HorarioDAO horarioDao = new HorarioDAO();
        ArrayList<Horario> horariosEncontrados = new ArrayList<Horario>();
        horariosEncontrados =
        horarioDao.obtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademica
            (tutoriaAcademica.getIdTutoriaAcademica());
        for(Horario horarioEncontrado:horariosEncontrados){
            DIGEstudiantesAsistentesOEnRiesgoTabla estudianteAsistenteOEnRiesgo = 
            new DIGEstudiantesAsistentesOEnRiesgoTabla();
            estudianteAsistenteOEnRiesgo.setEstudiante(horarioEncontrado.getEstudiante());
            estudianteAsistenteOEnRiesgo.setAsistenciaEstudiante(horarioEncontrado.isAsistencia());
            estudianteAsistenteOEnRiesgo.setEstudianteEnRiesgo(horarioEncontrado.isRiesgo());
            listaDeEstudiantesAsistentesOEnRiesgo.add(estudianteAsistenteOEnRiesgo);
        }
        return listaDeEstudiantesAsistentesOEnRiesgo;
    }
    
    private TutoriaAcademica obtenerIdDeLaTutoriaAcademica(TutoriaAcademica tutoriaAcademica) throws SQLException{
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        tutoriaAcademica = tutoriaAcademicaDao.obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademica);
        return tutoriaAcademica;
    }
    
    private void inicializarColumnasTablaEstudiantesAsistentesOEnRiesgo(){
        tableColumnNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        tableColumnAsistencia.setCellValueFactory(new PropertyValueFactory<>("asistenciaEstudiante"));
        tableColumnEstudianteEnRiesgo.setCellValueFactory(new PropertyValueFactory<>("EstudianteEnRiesgo"));
    }
    
    private void cargarInformacionDeLaTablaProblematicasAcademicas(TutoriaAcademica tutoriaAcademica) 
    throws SQLException{
        ObservableList<DIGProblematicaTabla> listaDeProblematicasAcademicas;
        listaDeProblematicasAcademicas = 
        FXCollections.observableArrayList(obtenerProblematicasDetectadasEnLaTutoriaAcademica(tutoriaAcademica));
        tableViewProblematicasAcademicas.setItems(listaDeProblematicasAcademicas);
        inicializarColumnasTablaProblematicas();
    }
    
    private ArrayList<DIGProblematicaTabla> obtenerProblematicasDetectadasEnLaTutoriaAcademica(TutoriaAcademica
    tutoriaAcademica) throws SQLException{
        ProblematicaDAO problematicaDAO = new ProblematicaDAO();
        ArrayList<Problematica> problematicasDetectadas = 
        problematicaDAO.obtenerListadoDeProblematicasPorIdTutoriaAcademica(tutoriaAcademica);
        ArrayList<DIGProblematicaTabla> problematicasEncontradas = new ArrayList<DIGProblematicaTabla>();
        problematicasEncontradas = 
        almacenarProblematicasDetectadas(problematicasDetectadas, problematicasEncontradas);
        return problematicasEncontradas;
    }
    
    private ArrayList<DIGProblematicaTabla> almacenarProblematicasDetectadas
    (ArrayList<Problematica> problematicasDetectadas, ArrayList<DIGProblematicaTabla> problematicasEncontradas){
        for (Problematica problematicaDetectada : problematicasDetectadas){
            DIGProblematicaTabla problematicasEncontrada = new DIGProblematicaTabla();
            problematicasEncontrada.setProblematica(problematicaDetectada);
            problematicasEncontradas.add(problematicasEncontrada);
        }
        return problematicasEncontradas;
    }
    
    private void inicializarColumnasTablaProblematicas(){
        tableColumnNombreExperienciaEducativa.setCellValueFactory
        (new PropertyValueFactory<>("ExperienciaEducativa"));
        tableColumnNombreProfesor.setCellValueFactory(new PropertyValueFactory<>("nombreProfesor"));
        tableColumnTituloProblematica.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumnDescripcionProblematica.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }
    
    private void cargarInformacionComentarioGeneral(TutoriaAcademica tutoriaAcademica){
        textComentarioGeneral.setText(tutoriaAcademica.getComentarioGeneral());
        scrollPaneComentarioGeneral.setFitToWidth(true);
        scrollPaneComentarioGeneral.setContent(textComentarioGeneral);
    }
    
    @FXML 
    private void cerrarReporteDeTutoriaAcademica(ActionEvent evento){
        Stage escenarioPrincipal = (Stage) buttonImprimirReporteDeTutoriasAcademica.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void descargar(ActionEvent evento) {
        try {
            FileChooser selectorDeArchivos = new FileChooser();
            selectorDeArchivos.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("(*.pdf)", "*.pdf")
            );
            File destinoElegido = selectorDeArchivos.showSaveDialog(new Stage());
            if (destinoElegido!= null){
                generarFormatoEnPDF(destinoElegido.getPath());
            }
        } catch (IOException ioException) {
            Utilidades.mostrarAlertaSinConfirmacion(
                "Error de escritura", 
                "No se puede sobreescribir el archivo seleccionado pues está siendo "
                + "usado por otro proceso. Por favor, seleccione un nombre de archivo "
                + "distinto o una ruta distinta.", Alert.AlertType.WARNING
            );
        }
    }
    
    private void generarFormatoEnPDF(String destinoElegido) throws IOException, FileNotFoundException{
        FormatoReporteDeTutoriaAcademica formatoReporteTutoria=  new FormatoReporteDeTutoriaAcademica();
        formatoReporteTutoria.setDestinoDelArchivo(destinoElegido);
        formatoReporteTutoria.setListaDeEstudiantesAsistentesOEnRiesgo(
            this.tableViewEstudiantesAsistentesOEnRiesgo.getItems());
        formatoReporteTutoria.setListaDeProblematicasAcademicas(
            this.tableViewProblematicasAcademicas.getItems());
        formatoReporteTutoria.setNombreTutorAcademico(this.tutorAcademico.getNombreCompleto());
        formatoReporteTutoria.setComentarioGeneral(this.textComentarioGeneral.getText());
        formatoReporteTutoria.setProgramaEducativo(labelProgramaEducativo.getText());
        formatoReporteTutoria.setPeriodoEscolar(this.labelPeriodoEscolar.getText());
        formatoReporteTutoria.setFechaDeTutoria(this.labelFechaDeTutoria.getText());
        formatoReporteTutoria.setNumeroDeTutoria(this.labelNumeroDeTutoria.getText());
        formatoReporteTutoria.generarPDF();
        Utilidades.mostrarAlertaConfirmacion(
            "Mensaje", 
            "El formato del reporte general de tutorías académicas se ha guardado exitosamente.", 
            Alert.AlertType.INFORMATION);
    }
}
