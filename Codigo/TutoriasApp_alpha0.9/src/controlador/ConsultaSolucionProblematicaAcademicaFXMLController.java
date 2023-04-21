package controlador;

import logicaNegocio.EstudianteDAO;
import logicaNegocio.AsignaturaDAO;
import logicaNegocio.ProblematicaDAO;
import logicaNegocio.SolucionDAO;
import dominio.globales.InformacionSesion;
import dominio.Problematica;
import dominio.Solucion;
import dominio.constantes.Roles;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;

public class ConsultaSolucionProblematicaAcademicaFXMLController implements Initializable {
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
    private TextArea textAreaDescripcionSolucion;
    @FXML
    private Label labelFechaRegistro;
    @FXML
    private Label labelPersonaRegistro;
    @FXML
    private Button buttonModificar;
    @FXML
    private Button buttonRegresar;
    
    private Problematica problematicaSolucionConsulta = new Problematica();
    private Solucion solucionConsulta = new Solucion();
    
    public void setProblematicaSolucionConsulta(Problematica problematicaSolucionConsulta){
        this.problematicaSolucionConsulta = problematicaSolucionConsulta;
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
        SolucionDAO solucionDAO = new SolucionDAO();
        Format formateadorFecha = new SimpleDateFormat("yyyy/MM/dd");
        validarUsuarioFuncionalidadBotones();
        try{
            this.problematicaSolucionConsulta = 
                    problematicaDAO.obtenerProblematicaPorId(
                            this.problematicaSolucionConsulta.getIdProblematica());
            this.problematicaSolucionConsulta.setEstudiante(
                    estudianteDAO.obtenerEstudiantePorMatricula(
                            this.problematicaSolucionConsulta.getEstudiante().getMatricula()));
            this.problematicaSolucionConsulta.setCurso(
                    experienciaEducativaAcademicoDAO.obtenerAsignaturaPorNrc(
                            this.problematicaSolucionConsulta.getCurso().getNRC()));
            this.solucionConsulta = 
                    solucionDAO.obtenerSolucionaProblematicaPorIdProblematicaRelacionAcademico(
                            this.problematicaSolucionConsulta.getIdProblematica());
            labelTitulo.setText(this.problematicaSolucionConsulta.getTitulo());
            labelExperienciaEducativa.setText(
                    this.problematicaSolucionConsulta.getCurso().getExperienciaEducativa().getNombre());
            labelProfesor.setText(
                    this.problematicaSolucionConsulta.getCurso().getAcademico().getNombreCompleto());
            labelNRC.setText(Integer.toString(this.problematicaSolucionConsulta.getCurso().getNRC()));
            labelEstudiante.setText(
                    this.problematicaSolucionConsulta.getEstudiante().getNombreCompleto());
            textAreaDescripcionProblematica.setText(this.problematicaSolucionConsulta.getDescripcion());
            textAreaDescripcionSolucion.setText(this.solucionConsulta.getDescripcion());
            labelFechaRegistro.setText(formateadorFecha.format(this.solucionConsulta.getFecha()));
            labelPersonaRegistro.setText(this.solucionConsulta.getTutor().getNombreCompleto());
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
    private void modificarSolucion(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonModificar)){
                llamarModificarSolucionGUI();
                cargarCamposGUI();
            }
    }
    
    private void validarUsuarioFuncionalidadBotones(){
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        final int ID_ROL_JEFEDECARRERA = Roles.JEFE_DE_CARRERA.getIdRol();
        if(informacionSesion .getAcademicoRol().getRol().getIdRol() != ID_ROL_JEFEDECARRERA){
            buttonModificar.setVisible(false);
        }
    }

    private void llamarModificarSolucionGUI(){ 
        try {
            FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(
                    "/vista/ModificarSolucionProblematicaAcademicaFXML.fxml"));
            Parent raiz = cargadorFXML.load();
            ModificarSolucionProblematicaAcademicaFXMLController controladorGUI 
                    = cargadorFXML.getController();
            controladorGUI.setProblematicaConsulta(this.problematicaSolucionConsulta);
            controladorGUI.setSolucionNueva(this.solucionConsulta);
            Stage escenario = new Stage();
            escenario.setTitle("Modificar Solucion Problematica Academica");
            escenario.setScene(new Scene(raiz, 600, 400));
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch(IOException ioException){
             Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
}
