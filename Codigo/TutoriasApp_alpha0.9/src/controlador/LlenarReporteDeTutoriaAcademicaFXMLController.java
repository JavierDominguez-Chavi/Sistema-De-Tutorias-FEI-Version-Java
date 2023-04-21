package controlador;

import DIG.DIGEstudiantesAsistentesOEnRiesgoTabla;
import logicaNegocio.EstudianteDAO;
import logicaNegocio.HorarioDAO;
import logicaNegocio.PeriodoEscolarDAO;
import logicaNegocio.TutoriaAcademicaDAO;
import dominio.Estudiante;
import dominio.globales.InformacionSesion;
import dominio.Horario;
import dominio.PeriodoEscolar;
import dominio.TutoriaAcademica;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LlenarReporteDeTutoriaAcademicaFXMLController implements Initializable {
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
    private Label labelPorcentajeDeAsistencias;
    @FXML
    private Label labelPorcentajeDeEstudiantesEnRiesgo;
    @FXML
    private TableView <DIGEstudiantesAsistentesOEnRiesgoTabla>  tableViewEstudiantesAsistentesOEnRiesgo;
    @FXML
    private TableColumn <DIGEstudiantesAsistentesOEnRiesgoTabla, String>  tableColumnNombreEstudiante;
    @FXML
    private TableColumn <DIGEstudiantesAsistentesOEnRiesgoTabla, CheckBox>  tableColumnAsistencia;
    @FXML
    private TableColumn <DIGEstudiantesAsistentesOEnRiesgoTabla, CheckBox>  tableColumnEstudianteEnRiesgo;
    @FXML
    private Button buttonRegistrarProblematica;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonEnviar;
    @FXML
    private TextArea textAreaComentarioGeneral;
    
    private int totalDeAsistencias = 0;
    private int totalDeEstudiantesEnRiesgo = 0;
    private float porcentajeDeAsistencias = 0;
    private float porcentajeDeEstudiantesEnRiesgo = 0;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        ObservableList <DIGEstudiantesAsistentesOEnRiesgoTabla> listaEstudiantes;
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        try { 
            listaEstudiantes = 
                FXCollections.observableArrayList(obtenerEstudiantesDelTutorAcademico(
                        informacionSesion));
            tableViewEstudiantesAsistentesOEnRiesgo.setItems(listaEstudiantes);
            inicializarColumnasTablaEstudiantes();  
            ArrayList <DIGEstudiantesAsistentesOEnRiesgoTabla> horariosEstudiantes= new ArrayList<>();
            horariosEstudiantes.addAll(tableViewEstudiantesAsistentesOEnRiesgo.getItems());
            calcularTotalyPorcentajeDeAsistencias(horariosEstudiantes);
            calcularTotalyPorcentajeDeEstudiantesEnRiesgo(horariosEstudiantes);
            labelNumeroDeTutoria.setText
            (Integer.toString(informacionSesion .getFechaTutoria().getNumeroSesion()));
            labelProgramaEducativo.setText(informacionSesion .getProgramaEducativo().getNombre());
            PeriodoEscolar periodoEscolar = obtenerPeriodoEscolar(informacionSesion);
            labelPeriodoEscolar.setText(periodoEscolar.getFechaInicio() 
            + " - " + periodoEscolar.getFechaFin());
            labelFechaDeTutoria.setText
            (informacionSesion .getFechaTutoria().getFechaTutoria().toString());
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla> obtenerEstudiantesDelTutorAcademico
    (InformacionSesion informacionSesion ) throws SQLException{
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ArrayList<Estudiante> estudiantesEncontrados = 
        estudianteDAO.obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo
        (informacionSesion .getAcademicoRol().getAcademico(),informacionSesion.getProgramaEducativo());
        ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla> listaEstudiantesObtenidos = new ArrayList<>();
        for (Estudiante estudianteEncontrado : estudiantesEncontrados){
            DIGEstudiantesAsistentesOEnRiesgoTabla horarioEstudiante = 
            new DIGEstudiantesAsistentesOEnRiesgoTabla();
            horarioEstudiante.setEstudiante(estudianteEncontrado);
            listaEstudiantesObtenidos.add(horarioEstudiante);
        }
        Comparator<DIGEstudiantesAsistentesOEnRiesgoTabla> comparador = 
        Comparator.comparing(DIGEstudiantesAsistentesOEnRiesgoTabla::getNombreCompleto);
        List<DIGEstudiantesAsistentesOEnRiesgoTabla>listaPrincipal=
        listaEstudiantesObtenidos.stream().sorted(comparador).collect(Collectors.toList());
        return (ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla>) listaPrincipal;
    }
    
    private void inicializarColumnasTablaEstudiantes(){
        tableColumnNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        tableColumnAsistencia.setCellValueFactory(new PropertyValueFactory <>("checkBoxAsistencia"));
        tableColumnEstudianteEnRiesgo.setCellValueFactory(new PropertyValueFactory <>("checkBoxRiesgo"));
    }
    
    private void calcularTotalyPorcentajeDeAsistencias(ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla>
    horariosEstudiantes){
        for(DIGEstudiantesAsistentesOEnRiesgoTabla horarioBuscar:horariosEstudiantes){
            horarioBuscar.getCheckBoxAsistencia().selectedProperty().addListener
            ((propiedadSeleccionada, valorAnterior, valorNuevo) -> {
                if (valorNuevo) {
                    totalDeAsistencias++;
                    labelTotalDeAsistencias.setText(Integer.toString(totalDeAsistencias));
                    porcentajeDeAsistencias = (totalDeAsistencias*100)/(horariosEstudiantes.size());
                    labelPorcentajeDeAsistencias.setText(Float.toString(porcentajeDeAsistencias) + '%');
                } else{
                    totalDeAsistencias--;
                    labelTotalDeAsistencias.setText(Integer.toString(totalDeAsistencias));
                    porcentajeDeAsistencias = (totalDeAsistencias*100)/(horariosEstudiantes.size());
                    labelPorcentajeDeAsistencias.setText(Float.toString(porcentajeDeAsistencias) + '%');
                }
            });
        }
    }
    
    private void calcularTotalyPorcentajeDeEstudiantesEnRiesgo(ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla> 
    horariosEstudiantes){
        for(DIGEstudiantesAsistentesOEnRiesgoTabla horarioBuscar:horariosEstudiantes){
            horarioBuscar.getCheckBoxRiesgo().selectedProperty().addListener
            ((propiedadSeleccionada, valorAnterior, valorNuevo) -> {
                if (valorNuevo) {
                    totalDeEstudiantesEnRiesgo++;
                    labelTotalDeEstudiantesEnRiesgo.setText(Integer.toString(totalDeEstudiantesEnRiesgo));
                    porcentajeDeEstudiantesEnRiesgo = 
                    (totalDeEstudiantesEnRiesgo*100)/(horariosEstudiantes.size());
                    labelPorcentajeDeEstudiantesEnRiesgo.setText
                    (Float.toString(porcentajeDeEstudiantesEnRiesgo) + '%');
                } else{
                    totalDeEstudiantesEnRiesgo--;
                    labelTotalDeEstudiantesEnRiesgo.setText(Integer.toString(totalDeEstudiantesEnRiesgo));
                    porcentajeDeEstudiantesEnRiesgo = 
                    (totalDeEstudiantesEnRiesgo*100)/(horariosEstudiantes.size());
                    labelPorcentajeDeEstudiantesEnRiesgo.setText
                    (Float.toString(porcentajeDeEstudiantesEnRiesgo) + '%');
                }
            });      
        }
    }
    
    private PeriodoEscolar obtenerPeriodoEscolar(InformacionSesion informacionSesion )
    throws SQLException{
        PeriodoEscolarDAO periodoEscolarDao = new PeriodoEscolarDAO();
        PeriodoEscolar periodoEscolar = 
        periodoEscolarDao.obtenerPeriodoEscolarPorId(informacionSesion .getFechaTutoria().
            getPeriodo().getIdPeriodoEscolar());
        
        return periodoEscolar;
    }
    
    @FXML
    private void registrarProblematicaAcademica(ActionEvent evento){
        try {
            if(!buscarReporteDeTutoriaAcademicaEntregado()){
                FXMLLoader cargador = new FXMLLoader(getClass().getResource
                ("/vista/RegistrarProblematicaAcademicaFXML.fxml"));
                Parent raiz = cargador.load();
                Scene escenaFormulario = new Scene(raiz);
                Stage escenarioFormulario = new Stage();
                escenarioFormulario.setResizable(false);
                escenarioFormulario.setScene(escenaFormulario);
                escenarioFormulario.setTitle("Registrar problemática académica");
                escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
                escenarioFormulario.showAndWait();
            } else{
                Utilidades.mostrarAlertaSinConfirmacion("Reporte entregado", "El reporte para esta fecha "
                + "de tutoria ya ha sido entregado, no puede registrar más problemáticas", Alert.AlertType.WARNING);
            }
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        } catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        } catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
    private boolean buscarReporteDeTutoriaAcademicaEntregado() throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        boolean reporteEntregado = false;
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setReporteEntregado(true);
        tutoriaAcademica.setFechaTutoria(informacionSesion .getFechaTutoria());
        tutoriaAcademica.setTutor(informacionSesion .getAcademicoRol().getAcademico());
        reporteEntregado = tutoriaAcademicaDao.buscarReporteDeTutoriaAcademicaEntregado(tutoriaAcademica);
        return reporteEntregado;
    }
    
    @FXML
    private void guardarReporteDeTutoríaAcademica(ActionEvent evento) throws SQLException{
        if(!textAreaComentarioGeneral.getText().isBlank()){
            validarReporteComoNuevo();
        } else{
            Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos",
            "Cometario general no detectado. Por favor, verifique "
            + "que haya ingresado el comentario general del reporte de tutoría académica.",
            Alert.AlertType.WARNING);
        }
    }
    
    private void validarReporteComoNuevo(){
        InformacionSesion informacionSesion =InformacionSesion.getInformacionSesion();
        try {
            TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
            TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
            tutoriaAcademica.setReporteEntregado(true);
            tutoriaAcademica.setFechaTutoria(informacionSesion .getFechaTutoria());
            tutoriaAcademica.setTutor(informacionSesion .getAcademicoRol().getAcademico());
            if(!tutoriaAcademicaDao.buscarReporteDeTutoriaAcademicaEntregado(tutoriaAcademica)){
                tutoriaAcademica = obtenerIdDeLaTutoriaAcademica(tutoriaAcademica, tutoriaAcademicaDao);
                registrarInformacionDelReporteDeTutoriaAcademica(tutoriaAcademica,tutoriaAcademicaDao);
                mostrarMensajeInformacionGuardada();
            }else{
                Utilidades.mostrarAlertaSinConfirmacion("Reporte existente",
                "El reporte que desea registrar ya se encuentra almacenado en el sistema",
                Alert.AlertType.WARNING);
            }
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void mostrarMensajeInformacionGuardada(){
        Utilidades.mostrarAlertaConfirmacion("Información guardada",
        "La información se registró correctamente en el sistema", Alert.AlertType.CONFIRMATION);
        if (Utilidades.getOpcion().get() == ButtonType.OK){
            Stage escenarioPrincipal = (Stage) labelPeriodoEscolar.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
    
    private TutoriaAcademica obtenerIdDeLaTutoriaAcademica(TutoriaAcademica tutoriaAcademica,  
    TutoriaAcademicaDAO tutoriaAcademicaDao) throws SQLException{
        tutoriaAcademica = 
            tutoriaAcademicaDao.obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademica);
        return tutoriaAcademica;
    }
    
    private void registrarInformacionDelReporteDeTutoriaAcademica(TutoriaAcademica tutoriaAcademica,
    TutoriaAcademicaDAO tutoriaAcademicaDao){
        ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla> estudiantesAsistentesOEnRiesgo = 
        new ArrayList<DIGEstudiantesAsistentesOEnRiesgoTabla>();
        estudiantesAsistentesOEnRiesgo.addAll(tableViewEstudiantesAsistentesOEnRiesgo.getItems());
        HorarioDAO horarioDao = new HorarioDAO();
        try{
            for(DIGEstudiantesAsistentesOEnRiesgoTabla estudianteAsistenteOEnRiesgo:estudiantesAsistentesOEnRiesgo){
                Horario horario = new Horario();
                horario.setEstudiante(estudianteAsistenteOEnRiesgo.getEstudiante());
                horario.setAsistencia(estudianteAsistenteOEnRiesgo.getCheckBoxAsistencia().isSelected());
                horario.setRiesgo(estudianteAsistenteOEnRiesgo.getCheckBoxRiesgo().isSelected());
                horario.setTutoriaAcademica(tutoriaAcademica);
                horarioDao.registrarAsistenciaySiEstaEnRiesgoElEstudiante(horario);
            }
            tutoriaAcademica.setComentarioGeneral(textAreaComentarioGeneral.getText());
            tutoriaAcademicaDao.registrarComentarioGeneralYEntregaDeReporteDeLaTutoria(tutoriaAcademica);
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    @FXML
    private void cancelarLlenarReporteDeTutoriaAcademica(ActionEvent evento){
        Utilidades.mostrarAlertaConfirmacion("Cancelar operación",
        "¿Está seguro de que desea cancelar la operación?", Alert.AlertType.CONFIRMATION);
        if (Utilidades.getOpcion().get() == ButtonType.OK) {
            Stage escenarioPrincipal = (Stage) labelPeriodoEscolar.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
}
