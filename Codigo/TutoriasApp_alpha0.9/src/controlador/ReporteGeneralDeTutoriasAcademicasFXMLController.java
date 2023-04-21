package controlador;

import DIG.DIGProblematicaTabla;
import DIG.DIGTutoriaAcademicaComentarioGeneralTabla;
import logicaNegocio.HorarioDAO;
import logicaNegocio.ProblematicaDAO;
import logicaNegocio.TutoriaAcademicaDAO;
import dominio.FechaTutoria;
import dominio.Horario;
import dominio.Problematica;
import dominio.TutoriaAcademica;
import dominio.globales.InformacionSesion;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import formatosReporte.FormatoReporteGeneralDeTutoríasAcadémicas;

public class ReporteGeneralDeTutoriasAcademicasFXMLController implements Initializable {
    @FXML
    private Label labelNumeroDeTutoria;
    @FXML
    private Label labelProgramaEducativo;
    @FXML
    private Label labelPeriodoEscolar;
    @FXML
    private Label labelFechaDeTutoria;
    @FXML
    private Label labelTotalDeAsistencias;
    @FXML
    private Label labelTotalDeEstudiantesEnRiesgo;
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
    private TableView <DIGTutoriaAcademicaComentarioGeneralTabla> tableViewComentariosGenerales;
    @FXML
    private TableColumn <DIGTutoriaAcademicaComentarioGeneralTabla, String> tableColumnNombreTutor;
    @FXML
    private TableColumn <DIGTutoriaAcademicaComentarioGeneralTabla, String> tableColumnComentarioGeneral;
    @FXML
    private Button buttonCerrarReporteGeneralDeTutoriasAcademica;
    @FXML
    private Button buttonImprimirReporteGeneralDeTutoriasAcademica;
    @FXML
    private Button buttonDescargarReporteGeneralDeTutoriasAcademica;
    
    private FechaTutoria fechaTutoriaAcademica;
    
    public void setFechaTutoriaAcademicaSeleccionada(FechaTutoria fechaTutoriaAcademicaSeleccionada){
        fechaTutoriaAcademica = new FechaTutoria();
        fechaTutoriaAcademica = fechaTutoriaAcademicaSeleccionada;
        cargarCamposGUIReporteGeneralDeTutoriasAcademicas();
    }
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        
    }    
    
    private void cargarCamposGUIReporteGeneralDeTutoriasAcademicas(){
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        try {
            ArrayList<TutoriaAcademica> tutoriasAcademicasConcluidas = 
            obtenerTutoriasAcademicasConcluidas(informacionSesion );
            cargarInformacionDeEtiquetas(informacionSesion , tutoriasAcademicasConcluidas);
            cargarInformacionDeLaTablaProblematicasAcademicas(tutoriasAcademicasConcluidas);
            cargarInformacionDeLaTablaComentariosGenerales(tutoriasAcademicasConcluidas);
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void cargarInformacionDeEtiquetas(InformacionSesion informacionSesion , 
    ArrayList<TutoriaAcademica>tutoriasAcademicasConcluidas) throws SQLException{
        labelNumeroDeTutoria.setText(Integer.toString(fechaTutoriaAcademica.getNumeroSesion()));
        labelProgramaEducativo.setText(informacionSesion .getProgramaEducativo().getNombre());
        labelPeriodoEscolar.setText(fechaTutoriaAcademica.getPeriodo().getFechaInicio() + " - "
        + fechaTutoriaAcademica.getPeriodo().getFechaFin());
        labelFechaDeTutoria.setText(fechaTutoriaAcademica.getFechaTutoria().toString());
        labelTotalDeAsistencias.setText(Integer.toString
        (obtenerEstudiantesAsistentesEnLasTutoriasAcademicas(tutoriasAcademicasConcluidas).size()));
        labelTotalDeEstudiantesEnRiesgo.setText(Integer.toString
        (obtenerEstudiantesEnRiesgoDetectadosEnLasTutoriasAcademicas(tutoriasAcademicasConcluidas).size()));
    }
    
    private void cargarInformacionDeLaTablaProblematicasAcademicas(ArrayList<TutoriaAcademica> 
    tutoriasAcademicasConcluidas) throws SQLException{
        ObservableList<DIGProblematicaTabla> listaDeProblematicasAcademicas;
        listaDeProblematicasAcademicas = 
            FXCollections.observableArrayList(obtenerProblematicasDetectadasEnLasTutoriasAcademicas
                (tutoriasAcademicasConcluidas));
        tableViewProblematicasAcademicas.setItems(listaDeProblematicasAcademicas);
        inicializarColumnasTablaProblematicas();
    }
    
    private void inicializarColumnasTablaProblematicas(){
        tableColumnNombreExperienciaEducativa.setCellValueFactory
        (new PropertyValueFactory<>("experienciaEducativa"));
        tableColumnNombreProfesor.setCellValueFactory(new PropertyValueFactory<>("nombreProfesor"));
        tableColumnTituloProblematica.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumnDescripcionProblematica.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }
    
    private void cargarInformacionDeLaTablaComentariosGenerales(ArrayList<TutoriaAcademica> 
    tutoriasAcademicasConcluidas){
        ObservableList<DIGTutoriaAcademicaComentarioGeneralTabla> listaDeComentariosGenerales;
        listaDeComentariosGenerales = 
        FXCollections.observableArrayList(almacenarComentariosGeneralesDeLasTutoriasAcademicas
        (tutoriasAcademicasConcluidas));
        tableViewComentariosGenerales.setItems(listaDeComentariosGenerales);
        inicializarColumnasTablaComentariosGenerales();
    }
    
    private void inicializarColumnasTablaComentariosGenerales(){
        tableColumnNombreTutor.setCellValueFactory(new PropertyValueFactory<>("nombreTutorAcademico"));
        tableColumnComentarioGeneral.setCellValueFactory(new PropertyValueFactory<>("comentarioGeneral"));
    }
    
    private ArrayList <TutoriaAcademica> obtenerTutoriasAcademicasConcluidas(InformacionSesion informacionSesion )

    throws SQLException{
        ArrayList <TutoriaAcademica> tutoriasAcademicasConcluidas = new ArrayList<TutoriaAcademica>();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademicaABuscar = new TutoriaAcademica();
        tutoriaAcademicaABuscar.setFechaTutoria(fechaTutoriaAcademica);
        tutoriaAcademicaABuscar.setReporteEntregado(true);
        tutoriasAcademicasConcluidas =
        tutoriaAcademicaDao.obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregado(tutoriaAcademicaABuscar);
        return tutoriasAcademicasConcluidas;
    }
      
    private ArrayList<Horario> obtenerEstudiantesAsistentesEnLasTutoriasAcademicas
    (ArrayList<TutoriaAcademica> tutoriasAcademicasConcluidas) throws SQLException{
        HorarioDAO horarioDao = new HorarioDAO();
        ArrayList<Horario> asistenciasEnLasTutoriasAcademicas = new ArrayList<Horario>();
        Horario horarioABuscar = new Horario();
        horarioABuscar.setAsistencia(true);
        for(TutoriaAcademica tutoriaAcademicaConcluida:tutoriasAcademicasConcluidas){
            horarioABuscar.setTutoriaAcademica(tutoriaAcademicaConcluida);
            asistenciasEnLasTutoriasAcademicas.addAll
            (horarioDao.obtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoria(horarioABuscar));
        }
        return asistenciasEnLasTutoriasAcademicas;
    }
    
    private ArrayList<Horario> obtenerEstudiantesEnRiesgoDetectadosEnLasTutoriasAcademicas
    (ArrayList<TutoriaAcademica> tutoriasAcademicasConcluidas) throws SQLException{
        HorarioDAO horarioDao = new HorarioDAO();
        ArrayList<Horario> totalDeEstudiantesEnRiesgo = new ArrayList<Horario>();
        Horario horarioABuscar = new Horario();
        horarioABuscar.setRiesgo(true);
        for(TutoriaAcademica tutoriaAcademicaConcluida:tutoriasAcademicasConcluidas){
            horarioABuscar.setTutoriaAcademica(tutoriaAcademicaConcluida);
            totalDeEstudiantesEnRiesgo.addAll
            (horarioDao.obtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoria(horarioABuscar));
        }
        return totalDeEstudiantesEnRiesgo;
    }  
    
    private ArrayList<DIGProblematicaTabla> obtenerProblematicasDetectadasEnLasTutoriasAcademicas
    (ArrayList<TutoriaAcademica> tutoriasAcademicasConcluidas) throws SQLException{
        ProblematicaDAO problematicaDAO = new ProblematicaDAO();
        ArrayList<Problematica> problematicasDetectadas = new ArrayList<Problematica>();
        for(TutoriaAcademica tutoriaAcademicaConcluida:tutoriasAcademicasConcluidas){
            problematicasDetectadas.addAll(problematicaDAO.obtenerListadoDeProblematicasPorIdTutoriaAcademica
            (tutoriaAcademicaConcluida));
        }
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
    
    private ArrayList<DIGTutoriaAcademicaComentarioGeneralTabla> 
    almacenarComentariosGeneralesDeLasTutoriasAcademicas(ArrayList<TutoriaAcademica> tutoriasAcademicasConcluidas){
        ArrayList<DIGTutoriaAcademicaComentarioGeneralTabla> listaComentariosGeneralesDeTutoriaAcademica = 
        new ArrayList<DIGTutoriaAcademicaComentarioGeneralTabla>();      
        for (TutoriaAcademica tutoriaAcademicaConcluida : tutoriasAcademicasConcluidas){
            DIGTutoriaAcademicaComentarioGeneralTabla tutoriaAcademicaComentarioGeneral = 
            new DIGTutoriaAcademicaComentarioGeneralTabla();
            tutoriaAcademicaComentarioGeneral.setTutoriaAcademica(tutoriaAcademicaConcluida);
            listaComentariosGeneralesDeTutoriaAcademica.add(tutoriaAcademicaComentarioGeneral);
        }  
        return listaComentariosGeneralesDeTutoriaAcademica;
    }
    
    @FXML
    private void cerrarReporteGeneralDeTutoriasAcademicas(ActionEvent evento){
        Stage escenarioPrincipal = 
        (Stage) buttonCerrarReporteGeneralDeTutoriasAcademica.getScene().getWindow();
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
        FormatoReporteGeneralDeTutoríasAcadémicas formatoReporteGeneral = 
            new FormatoReporteGeneralDeTutoríasAcadémicas();
        formatoReporteGeneral.setDestinoDelArchivo(destinoElegido);
        formatoReporteGeneral.setProblematicas(this.tableViewProblematicasAcademicas.getItems());
        formatoReporteGeneral.setComentarios(this.tableViewComentariosGenerales.getItems());
        formatoReporteGeneral.setProgramaEducativo(labelProgramaEducativo.getText());
        formatoReporteGeneral.setPeriodoEscolar(fechaTutoriaAcademica.getPeriodo().getNombrePeriodo());
        formatoReporteGeneral.setFecha(labelFechaDeTutoria.getText());
        formatoReporteGeneral.setNumeroSesion(labelNumeroDeTutoria.getText());
        formatoReporteGeneral.setAsistentes(labelTotalDeAsistencias.getText());
        formatoReporteGeneral.setEnRiesgo(labelTotalDeEstudiantesEnRiesgo.getText());
        formatoReporteGeneral.generarPDF();
        Utilidades.mostrarAlertaConfirmacion(
            "Mensaje", 
            "El formato del reporte general de tutorías académicas se ha guardado exitosamente.", 
            Alert.AlertType.INFORMATION);
    }
}
