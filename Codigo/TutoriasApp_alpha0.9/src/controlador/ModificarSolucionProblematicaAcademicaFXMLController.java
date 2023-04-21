package controlador;


import logicaNegocio.SolucionDAO;
import dominio.globales.InformacionSesion;
import dominio.Problematica;
import dominio.Solucion;
import static dominio.globales.InformacionSesion.getInformacionSesion;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ModificarSolucionProblematicaAcademicaFXMLController implements Initializable {
    private Problematica problematicaConsulta = new Problematica();
    private Solucion solucionNueva = new Solucion();
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
    private Button buttonGuardar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private TextArea textAreaDescripcionSolucion;
    
    public void setProblematicaConsulta(Problematica problematicaConsulta){
        this.problematicaConsulta = problematicaConsulta;
        cargarCamposGUI();
    }
    
    public void setSolucionNueva(Solucion solucionNueva){
        this.solucionNueva = solucionNueva;
        cargarCamposGUI(); 
    }

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        cargarCamposGUI();
        
    }    
    
    
    private void cargarCamposGUI(){
        labelTitulo.setText(this.problematicaConsulta.getTitulo());
        labelExperienciaEducativa.setText(
                this.problematicaConsulta.getCurso().getExperienciaEducativa().getNombre());
        labelProfesor.setText(
                this.problematicaConsulta.getCurso().getAcademico().getNombreCompleto());
        labelNRC.setText(Integer.toString(this.problematicaConsulta.getCurso().getNRC()));
        labelEstudiante.setText(this.problematicaConsulta.getEstudiante().getNombreCompleto());
        textAreaDescripcionProblematica.setText(this.problematicaConsulta.getDescripcion());
        textAreaDescripcionSolucion.setText(this.solucionNueva.getDescripcion());
    }
    
    
    @FXML
    private void guardar(ActionEvent evento){
        Object objeto = evento.getSource();
        if(objeto.equals(buttonGuardar)){
            try{
                camposValidos();
                registrarModificacionSolucionProblematica();
                Utilidades.mostrarAlertaConfirmacion("Información guardada",
                "La información se registró correctamente en el sistema.",Alert.AlertType.INFORMATION);
                if (Utilidades.getOpcion().get() == ButtonType.OK) {
                    Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                    escenarioPrincipal.close();
                }
            }catch(SQLException sqlException){
                Utilidades.mensajePerdidaDeConexion();
            }catch(IllegalArgumentException iaException){
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
   
    
    private void registrarModificacionSolucionProblematica() throws SQLException{
        InformacionSesion informacionSesion = getInformacionSesion();
        SolucionDAO solucionDAO = new SolucionDAO();
        Solucion nuevaSolucion = new Solucion();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        nuevaSolucion.setFecha(java.sql.Date.valueOf(formatoFecha.format(LocalDateTime.now())));
        nuevaSolucion.setDescripcion(textAreaDescripcionSolucion.getText());
        nuevaSolucion.setTutor(informacionSesion.getAcademicoRol().getAcademico());
        nuevaSolucion.setIdSolucion(this.solucionNueva.getIdSolucion());
        solucionDAO.modificarSolucionProblematica(nuevaSolucion);
    }
    
    private void camposValidos() throws IllegalArgumentException{
        if(textAreaDescripcionSolucion.getText().isBlank()){
          throw new IllegalArgumentException(); 
        }
    }
    
}
