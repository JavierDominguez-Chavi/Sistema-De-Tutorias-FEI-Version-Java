package controlador;

import logicaNegocio.EstudianteDAO;
import logicaNegocio.AsignaturaDAO;
import logicaNegocio.ProblematicaDAO;
import dominio.globales.InformacionSesion;
import dominio.Problematica;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

public class ConsultaProblematicaAcademicaFXMLController implements Initializable {
    @FXML
    private Label labelTitulo;
    @FXML
    private Label labelExperienciaEducativa;
    @FXML
    private Label labelProfesor;
    @FXML
    private Label labelNRC;
    @FXML
    private Label labelEstudiante;
    @FXML
    private TextArea textAreaDescripcionProblematica;
    @FXML
    private Button buttonRegresar;
    @FXML
    private Button buttonModificar;
    @FXML
    private Button buttonEliminar;
    
    private Problematica problematicaConsulta = new Problematica();
    
    public void setProblematicaConsulta(Problematica problematicaConsulta){
        this.problematicaConsulta = problematicaConsulta;
        cargarCamposGUI();
    }

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        cargarCamposGUI();
    } 
    
    private void cargarCamposGUI(){
        ProblematicaDAO problematicaDAO = new ProblematicaDAO();
        AsignaturaDAO experienciaEducativaAcademicoDAO = 
                new AsignaturaDAO();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        try{
            this.problematicaConsulta = problematicaDAO.obtenerProblematicaPorId(
                    this.problematicaConsulta.getIdProblematica());
            this.problematicaConsulta.setEstudiante(
                    estudianteDAO.obtenerEstudiantePorMatricula(
                            this.problematicaConsulta.getEstudiante().getMatricula()));
            this.problematicaConsulta.setCurso(
                    experienciaEducativaAcademicoDAO.obtenerAsignaturaPorNrc(
                            this.problematicaConsulta.getCurso().getNRC()));
            labelTitulo.setText(this.problematicaConsulta.getTitulo());
            labelExperienciaEducativa.setText(
                    this.problematicaConsulta.getCurso().getExperienciaEducativa().getNombre());
            labelProfesor.setText(
                    this.problematicaConsulta.getCurso().getAcademico().getNombreCompleto());
            labelNRC.setText(Integer.toString(this.problematicaConsulta.getCurso().getNRC()));
            labelEstudiante.setText(this.problematicaConsulta.getEstudiante().getNombreCompleto());
            textAreaDescripcionProblematica.setText(this.problematicaConsulta.getDescripcion());
            validarFechasFuncionalidadBotones();
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                escenarioPrincipal.close();
            });
        }
    }

    @FXML
    private void regresar(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonRegresar)){
                Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                escenarioPrincipal.close();
            }
    }

    @FXML
    private void modificarProblematica(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonModificar)){
            llamarModificarProblematicaGUI();
            cargarCamposGUI();
        }
    }

    @FXML
    private void eliminarProblematica(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonEliminar)){
            try{
                eliminarProblematicaDeBaseDatos();
            }catch(SQLException sqlException){
                Utilidades.mensajePerdidaDeConexion();
            }
        }
    }
    
    private void eliminarProblematicaDeBaseDatos() throws SQLException{
        Utilidades.mostrarAlertaConfirmacion("Eliminar Problemática Académica",
                "¿Desea eliminar esta problemática ?",Alert.AlertType.CONFIRMATION);
            if (Utilidades.getOpcion().get() == ButtonType.OK) {
                ProblematicaDAO problematicaDAO = new ProblematicaDAO();
                problematicaDAO.eliminarProblematica(problematicaConsulta.getIdProblematica());
                Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                escenarioPrincipal.close();
                Utilidades.mostrarAlertaConfirmacion(
                    "Registro Eliminado de Forma Exitosa",
                    "El registro de la problemática académica ha sido eliminada.",
                    Alert.AlertType.INFORMATION);
            }
    }
    
    private void validarFechasFuncionalidadBotones(){
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date fechaActual = java.sql.Date.valueOf(formateadorFecha.format(LocalDateTime.now()));
        if(fechaActual.before(informacionSesion.getFechaTutoria().getFechaCierreDeReporte())){
            buttonModificar.setVisible(false);
            buttonEliminar.setVisible(false);
        }
    }
    
    private void llamarModificarProblematicaGUI(){
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                    "/vista/ModificarProblematicaAcademicaFXML.fxml"));
            Parent raiz = cargador.load();
            ModificarProblematicaAcademicaFXMLController controller = cargador.getController();
            controller.setProblematicaModificar(this.problematicaConsulta);
            Stage escenario = new Stage();
            escenario.setTitle("Modificar Solucion Problematica Academica");
            escenario.setScene(new Scene(raiz, 600, 400));
            escenario.showAndWait();
        }catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
    
}
