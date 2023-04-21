package controlador;

import dominio.TutoriaAcademica;
import dominio.constantes.Roles;
import dominio.globales.InformacionSesion;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logicaNegocio.HorarioDAO;
import logicaNegocio.TutoriaAcademicaDAO;

public class PantallaPrincipalFXMLController implements Initializable {
    @FXML
    private MenuBar menuBarTutoriasFEI;
    @FXML
    private Menu menuReportesTutoriales;
    @FXML
    private MenuItem menuItemLlenarReporteDeTutoria;
    @FXML
    private MenuItem  menuItemConsultarReporteGeneralDeTutorias;
    @FXML
    private MenuItem  menuItemConsultarReportePorTutorAcademico; 
    @FXML
    private Menu menuProblematicasAcademicas;
    @FXML
    private MenuItem menuItemConsultarProblematicasAcademicas;
    @FXML
    private MenuItem menuItemConsultarSolucionesDeProblematicas;
    @FXML 
    private Menu menuSesionesDeTutoria;
    @FXML
    private MenuItem menuItemRegistrarHorarioDeSesionDeTutoria;
    @FXML
    private MenuItem menuItemConsultarHorarioDeSesionDeTutoria;
    @FXML
    private MenuItem menuItemRegistrarFechasDeSesionDeTutoria;
    @FXML
    private MenuItem menuItemModificarFechasDeTutoria;
    @FXML   
    private Menu menuTutoresAcademicosYEstudiantes;
    @FXML
    private MenuItem menuItemRegistrarTutorAcademico;
    @FXML
    private MenuItem menuItemConsultarEstudiantes;
    @FXML
    private MenuItem menuItemConsultarTutoresAcademicos;
    @FXML
    private MenuItem menuItemModificarAsignacionesDeTutores;   
    @FXML
    private Menu menuAdministracion;
    @FXML
    private MenuItem menuItemConsultarOfertaAcademica;
    @FXML
    private MenuItem menuItemRegistrarPeriodoEscolar;
    @FXML
    private MenuItem menuItemRegistrarAcademico;
    @FXML
    private MenuItem menuItemConsultarProfesores;
    
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) { 
        deshabilitarBotonesDelCoordinadorDeTutoriasAcademicas();
        deshabilitarBotonesDelAdministrador();
        deshabilitarBotonesDelTutorAcademico();
        deshabilitarBotonesDelJefeDeCarrera();
    }
    
    private void llamarSubventana(String titulo, String ventana){ 
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(ventana));
        try {
            Parent raiz = cargador.load();
            Scene escenaFormulario = new Scene(raiz);
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setResizable(false);
            escenarioFormulario.setScene(escenaFormulario);
            escenarioFormulario.setTitle(titulo);
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        } 
    }
  
    private void deshabilitarBotonesDelCoordinadorDeTutoriasAcademicas(){
        int rolCoordinador = Roles.COORDINADOR_DE_TUTORIAS_ACADEMICAS.getIdRol();
        int rolUsuario= 
            InformacionSesion.getInformacionSesion().getAcademicoRol().getRol().getIdRol();
        if (rolCoordinador == rolUsuario){
            menuItemLlenarReporteDeTutoria.setVisible(false);
            menuItemConsultarProblematicasAcademicas.setVisible(false);
            menuItemRegistrarHorarioDeSesionDeTutoria.setVisible(false);
            menuItemConsultarHorarioDeSesionDeTutoria.setVisible(false);
            menuItemRegistrarPeriodoEscolar.setVisible(false);
            menuItemRegistrarAcademico.setVisible(false);
        }
    }
    
    private void deshabilitarBotonesDelAdministrador(){
        int ROL_ADMINISTRADOR = Roles.ADMINISTRADOR.getIdRol();
        int rolUsuario = 
            InformacionSesion.getInformacionSesion().getAcademicoRol().getRol().getIdRol();
        if (ROL_ADMINISTRADOR == rolUsuario){
            menuReportesTutoriales.setVisible(false);
            menuProblematicasAcademicas.setVisible(false);
            menuSesionesDeTutoria.setVisible(false);
            menuTutoresAcademicosYEstudiantes.setVisible(false);
        }
    }
    
    private void deshabilitarBotonesDelTutorAcademico(){
        int rolTutorAcademico = Roles.TUTOR_ACADEMICO.getIdRol();
        int rolUsuario =
            InformacionSesion.getInformacionSesion().getAcademicoRol().getRol().getIdRol();
        if(rolTutorAcademico == rolUsuario){
            menuItemConsultarReporteGeneralDeTutorias.setVisible(false);
            menuItemConsultarReportePorTutorAcademico.setVisible(false);
            menuItemConsultarSolucionesDeProblematicas.setVisible(false);
            menuItemRegistrarFechasDeSesionDeTutoria.setVisible(false);
            menuItemModificarFechasDeTutoria.setVisible(false);
            menuItemRegistrarTutorAcademico.setVisible(false);
            menuItemConsultarTutoresAcademicos.setVisible(false);
            menuItemModificarAsignacionesDeTutores.setVisible(false);
            menuItemRegistrarPeriodoEscolar.setVisible(false);
            menuItemRegistrarAcademico.setVisible(false);
            menuTutoresAcademicosYEstudiantes.setVisible(false);
        }
    }
    
    private void deshabilitarBotonesDelJefeDeCarrera(){
        int rolJefeDeCarrera = Roles.JEFE_DE_CARRERA.getIdRol();
        int rolUsuario =
            InformacionSesion.getInformacionSesion().getAcademicoRol().getRol().getIdRol();
        if(rolJefeDeCarrera == rolUsuario){
            menuItemRegistrarHorarioDeSesionDeTutoria.setVisible(false);
            menuItemConsultarHorarioDeSesionDeTutoria.setVisible(false);
            menuItemLlenarReporteDeTutoria.setVisible(false);
            menuItemConsultarProblematicasAcademicas.setVisible(false);
            menuItemRegistrarFechasDeSesionDeTutoria.setVisible(false);
            menuItemModificarFechasDeTutoria.setVisible(false);
            menuItemRegistrarTutorAcademico.setVisible(false);
            menuItemModificarAsignacionesDeTutores.setVisible(false);
            menuItemRegistrarPeriodoEscolar.setVisible(false);
            menuItemRegistrarAcademico.setVisible(false);
            menuSesionesDeTutoria.setVisible(false);
        }
    }
    
    @FXML
    private void registrarTutor(ActionEvent evento) {
        llamarSubventana("Registrar tutor académico",
        "/vista/RegistrarTutorAcademicoFXML.fxml");
    }
    
    @FXML
    private void registrarHorarioDeSesionDeTutoria(ActionEvent evento) {
        llamarSubventana("Registrar horario de sesión de tutoría",
        "/vista/RegistrarHorarioDeSesionDeTutoriaFXML.fxml");
    }
    
    @FXML
    private void llenarReporteDeTutoríaAcademica(ActionEvent evento) {
        validarQueLaFechaDeTutoriaExista();
    }
    
    @FXML
    private void consultarReporteGeneralDeTutoriasAcademicas(ActionEvent evento){
        llamarSubventana("Consultar reporte general de tutorías académica",
        "/vista/ConsultarReporteGeneralDeTutoriasAcademicasFXML.fxml");   
    }
    
    @FXML
    private void consultarReportePorTutorAcademico(ActionEvent evento){
        llamarSubventana("Consultar reporte por tutor académico",
        "/vista/ConsultarReportePorTutorAcademicoFXML.fxml");   
    }
    
    @FXML
    private void consultarHorarioDeLaSesionDeTutoria(ActionEvent evento){
        llamarSubventana("Consultar horario de la sesión de tutoría",
        "/vista/ConsultarHorarioDeLaSesionDeTutoriaFXML.fxml");   
    }

    @FXML
    private void modificarAsignacionesDeTutores(ActionEvent evento) {
        llamarSubventana("Asignación de tutores académicos",
        "/vista/ModificarAsignacionDeTutorFXML.fxml");   
    }

    @FXML
    private void registrarFechasDeTutoria(ActionEvent evento) {
        llamarSubventana("Registro de fechas de tutoría",
        "/vista/RegistrarFechasTutoriaFXML.fxml");  
    }

    @FXML
    private void modificarFechasDeTutoria(ActionEvent evento) {
        llamarSubventana("Modificación de fechas de tutoría",
        "/vista/ModificarFechasTutoriaFXML.fxml");
    }

    @FXML
    private void consultarOfertaAcademica(ActionEvent evento) {
        llamarSubventana("Consulta de oferta académica",
        "/vista/ConsultarOfertaAcademicaFXML.fxml");
    }

    @FXML
    private void registrarAcademico(ActionEvent evento) {
        llamarSubventana("Registro de académicos",
        "/vista/RegistrarAcademicoFXML.fxml");  
    }

    @FXML
    private void registrarPeriodoEscolar(ActionEvent evento) {
        llamarSubventana("Registro de periodos escolares",
        "/vista/RegistrarPeriodoEscolarFXML.fxml");  
    }
    
    @FXML
    private void consultarListaEstudiantesProgramaEducativo(ActionEvent evento) {
        llamarSubventana("Consultar Listado Estudiantes del Programa Educativo",
            "/vista/ListaEstudiantesFXML.fxml");
    }

    @FXML
    private void consultarListaProblematicasAcademicas(ActionEvent evento) {
        llamarSubventana("Consulta Listado de Problematicas",
            "/vista/ListaProblematicasAcademicasFXML.fxml");
    }

    @FXML
    private void consultarListaSolucionesAProblematicas(ActionEvent evento) {
        llamarSubventana("Consulta Listado de Soluciones a Problemática Académica",
            "/vista/ListaSolucionesProblematicasAcademicasFXML.fxml");
    }

    @FXML
    private void consultarListaTutores(ActionEvent evento) {
        llamarSubventana("Consulta Listado de Tutores",
            "/vista/ListaTutoresAcademicosFXML.fxml");
    }

    @FXML
    private void consultarProfesores(ActionEvent evento) {
        llamarSubventana("Consulta de profesores",
        "/vista/ConsultaDeProfesoresFXML.fxml"); 
    }
    
    private void validarQueLaFechaDeTutoriaExista(){
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        if(informacionSesion.getFechaTutoria().getNumeroSesion() != 0){
            try {
                validarQueExistaElHorarioDeLaSesionDeTutoria(informacionSesion);
            } catch (SQLException sqlException) {
                Utilidades.mensajePerdidaDeConexion();
            }
        }else{
            Utilidades.mostrarAlertaSinConfirmacion("Fecha de tutoría académica concluida", "La fecha para la "
            + "entrega del reporte de tutoría académica ha concluido", Alert.AlertType.WARNING);
        }
    }
    
    private void validarQueExistaElHorarioDeLaSesionDeTutoria(InformacionSesion informacionSesion) 
    throws SQLException{
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        HorarioDAO horarioDao = new HorarioDAO();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        tutoriaAcademicaBuscar.setTutor(informacionSesion.getAcademicoRol().getAcademico());
        tutoriaAcademicaBuscar.setFechaTutoria(informacionSesion.getFechaTutoria());
        tutoriaAcademicaBuscar.getFechaTutoria().setPeriodo(informacionSesion.getFechaTutoria().getPeriodo());
        tutoriaAcademicaBuscar = 
        tutoriaAcademicaDao.obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademicaBuscar);
        if(horarioDao.buscarHorarioExistentePorIdTutoriaAcademica(tutoriaAcademicaBuscar)){
            llamarSubventana("Llenar reporte de tutoría académica", 
            "/vista/LlenarReporteDeTutoriaAcademicaFXML.fxml");
        } else{
            Utilidades.mostrarAlertaSinConfirmacion("Horario no registrado", "El horario de la sesión de tutoría "
            + "necesario para el llenado de este reporte aún no se encuentra registrado", 
            Alert.AlertType.WARNING);
        }
    }
}
