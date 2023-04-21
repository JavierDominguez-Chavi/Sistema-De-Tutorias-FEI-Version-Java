package controlador;

import logicaNegocio.EstudianteDAO;
import logicaNegocio.AsignaturaDAO;
import logicaNegocio.ProblematicaDAO;
import logicaNegocio.SolucionDAO;
import dominio.globales.InformacionSesion;
import dominio.Problematica;
import dominio.Solucion;
import static dominio.globales.InformacionSesion.getInformacionSesion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class RegistroSolucionProblematicaAcademicaFXMLController implements Initializable {
    private Problematica problematica = new Problematica();
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
    private Label labelDescripcion;
    @FXML
    private Button buttonGuardar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private TextArea textAreaDescripcionSolucion;
    public void setProblematica(Problematica problematica){
        this.problematica = problematica;
        cargarCampos();
    };
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        cargarCampos();
    }   
    
    private void cargarCampos(){
        ProblematicaDAO problematicaDAO = new ProblematicaDAO();
        AsignaturaDAO eeAcademicoDAO = new AsignaturaDAO();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        try{
            this.problematica = problematicaDAO.obtenerProblematicaPorId(this.problematica.getIdProblematica());
            this.problematica.setEstudiante(estudianteDAO.obtenerEstudiantePorMatricula(
                problematica.getEstudiante().getMatricula()));
            this.problematica.setCurso(eeAcademicoDAO.obtenerAsignaturaPorNrc(problematica.getCurso().getNRC()));
            labelTitulo.setText(problematica.getTitulo());
            labelExperienciaEducativa.setText(problematica.getCurso().getExperienciaEducativa().getNombre());
            labelProfesor.setText(problematica.getCurso().getAcademico().getNombreCompleto());
            labelNRC.setText(Integer.toString(problematica.getCurso().getNRC()));
            labelEstudiante.setText(problematica.getEstudiante().getNombreCompleto());
            labelDescripcion.setText(problematica.getDescripcion());
        }catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                escenarioPrincipal.close();
            });
        }
    }

    @FXML
    private void guardar(ActionEvent evento){
        Object objeto = evento.getSource();
        if(objeto.equals(buttonGuardar)){
            try{
                camposValidos();
                registrarSolucionProblematica();
                Utilidades.mostrarAlertaConfirmacion("Información guardada",
                "La información se registró correctamente en el sistema.",Alert.AlertType.INFORMATION);
                if (Utilidades.getOpcion().get() == ButtonType.OK) {
                    Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                    escenarioPrincipal.close();
                }
            }catch(SQLException sqlException){
                Utilidades.mostrarAlertaSinConfirmacion("Error de conexion", "No se pudo conectar"
                + " con la base de datos. Por favor, inténtelo más tarde",Alert.AlertType.ERROR);
            }catch(IllegalArgumentException illegalArgumentException){
                Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos",
                "No puede dejar ningún campo vacío. Por favor, verifique que todos los campos estén llenos",
                Alert.AlertType.WARNING);
            }
            
        }
    }
    
    @FXML
    private void cancelar(ActionEvent evento) {
        Object objeto = evento.getSource();
        if(objeto.equals(buttonCancelar)){
            Utilidades.mostrarAlertaConfirmacion("Cancelar Operación", 
                    "¿Desea cancelar la operación", Alert.AlertType.CONFIRMATION);
            if(Utilidades.getOpcion().get() == ButtonType.OK){
                Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                escenarioPrincipal.close();
            }
        }
    }
   
    private void registrarSolucionProblematica() throws SQLException{
        InformacionSesion informacionSesion = getInformacionSesion();
        SolucionDAO solucionDAO = new SolucionDAO();
        Solucion nuevaSolucion = new Solucion();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        nuevaSolucion.setFecha(java.sql.Date.valueOf(formatoFecha.format(LocalDateTime.now())));
        nuevaSolucion.setDescripcion(textAreaDescripcionSolucion.getText());
        nuevaSolucion.setTutor(informacionSesion.getAcademicoRol().getAcademico());
        this.problematica.setSolucion(nuevaSolucion);
        solucionDAO.registrarSolucionaProblematica(this.problematica);
    }
    
    private void camposValidos() throws IllegalArgumentException{
        if(textAreaDescripcionSolucion.getText().isBlank()){
          throw new IllegalArgumentException(); 
        }
    }
    
}
