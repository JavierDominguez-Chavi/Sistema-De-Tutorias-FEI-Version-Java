package controlador;

import logicaNegocio.EstudianteDAO;
import logicaNegocio.AsignaturaDAO;
import logicaNegocio.ProblematicaDAO;
import dominio.Estudiante;
import dominio.Asignatura;
import dominio.globales.InformacionSesion;
import dominio.Problematica;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ModificarProblematicaAcademicaFXMLController implements Initializable {
    @FXML
    private Label labelTitulo;
    @FXML
    private ComboBox <Estudiante> comboBoxEstudiante;
    @FXML
    private TextField textFieldTitulo;
    @FXML
    private TextArea textAreaDescripcion;
    @FXML
    private Button buttonGuardar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Label labelEstudiante;
    @FXML
    private Label labelExperienciaEducativa;
    @FXML
    private Label labelDescripcion;
    @FXML
    private ComboBox<Asignatura> comboBoxAsignatura;
    private Problematica problematicaModificar = new Problematica();
    
    public void setProblematicaModificar(Problematica problematicaModificar){
        this.problematicaModificar = problematicaModificar;
        cargarCamposGUI();
        seleccionarExperienciaEducativaComboBox();
    }
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        cargarCamposGUI();
    } 
    
    private void cargarCamposGUI(){
        try {
            comboBoxEstudiante.getItems().addAll(listaEstudiantes());
            editarFormatoComboBoxEstudiantes();
            seleccionarEstudianteComboBox();
            listaAsignaturasEstudiante(problematicaModificar.getEstudiante());
            textFieldTitulo.setText(this.problematicaModificar.getTitulo());
            textAreaDescripcion.setText(this.problematicaModificar.getDescripcion());
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    private ArrayList <Estudiante> listaEstudiantes() throws SQLException{
        comboBoxEstudiante.getItems().clear();
        EstudianteDAO daoEstudiante = new EstudianteDAO();
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
        InformacionSesion informacionSesion= InformacionSesion.getInformacionSesion();
        estudiantes = daoEstudiante.obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo(
            informacionSesion.getAcademicoRol().getAcademico(),informacionSesion.getProgramaEducativo()
        );
        return estudiantes;
    }
    
    private void editarFormatoComboBoxEstudiantes(){
        comboBoxEstudiante.setConverter(new StringConverter<Estudiante>(){
            @Override
            public String toString(Estudiante estudiante) {
                return estudiante == null ? null : estudiante.getApellidoPaterno() + " " 
                + estudiante.getApellidoMaterno() + " " + estudiante.getNombre();
            }
            @Override
            public Estudiante fromString(String string) {
              return null;
            }
        });
    }
    
    private void seleccionarEstudianteComboBox(){
        ObservableList<Estudiante> listaEstudiantes = comboBoxEstudiante.getItems();
        boolean bandera = true;
        int indice = 0;
        while(indice < listaEstudiantes.size() && bandera){
            if(listaEstudiantes.get(indice).getMatricula().equals(
                    problematicaModificar.getEstudiante().getMatricula())){
                bandera = false;
                comboBoxEstudiante.getSelectionModel().select(indice);
            }else{
                indice++;
            }
        }
    }
    
    private void seleccionarExperienciaEducativaComboBox(){ 
        ObservableList<Asignatura> 
                listaExperienciasEducativas = comboBoxAsignatura.getItems();
        boolean bandera = true;
        int indice = 0;
        while(indice < listaExperienciasEducativas.size() && bandera){
            if(listaExperienciasEducativas.get(indice).getNRC() 
                    == this.problematicaModificar.getCurso().getNRC()){
                bandera = false;
                comboBoxAsignatura.getSelectionModel().select(indice);
            }else{
                indice++;
            }
        }
    }
    private void listaAsignaturasEstudiante(Estudiante estudianteSeleccionado) throws SQLException{
        comboBoxAsignatura.getItems().clear();
        AsignaturaDAO experienciaEducativaAcademicoDAO = 
        new AsignaturaDAO();
        ArrayList<Asignatura> experienciasEducativasAcademicos = 
            experienciaEducativaAcademicoDAO.obtenerExperienciasEducativasActivasporMatricula
                (estudianteSeleccionado);
        if(!experienciasEducativasAcademicos.isEmpty()){
            comboBoxAsignatura.getItems().addAll(experienciasEducativasAcademicos);
            editarFormatoComboBoxExperienciaEducativa();
        }
    }
      private void editarFormatoComboBoxExperienciaEducativa(){
        comboBoxAsignatura.setConverter(new StringConverter<Asignatura>(){
            @Override
            public String toString(Asignatura experienciaEducativaAcademico) {
                return  experienciaEducativaAcademico == null ? null : 
                experienciaEducativaAcademico.getExperienciaEducativa().getNombre();
            }
            @Override
            public Asignatura fromString(String string) {
                return null;
            }
        });
    }
    
    private void registrarModificacionProlematicaAcademica() throws SQLException{
        ProblematicaDAO problematicaDAO = new ProblematicaDAO();
        this.problematicaModificar.setTitulo(textFieldTitulo.getText());
        this.problematicaModificar.setDescripcion(textAreaDescripcion.getText());
        this.problematicaModificar.getCurso().setNRC(comboBoxAsignatura.getValue().getNRC());
        this.problematicaModificar.setEstudiante(comboBoxEstudiante.getValue());
        problematicaDAO.modificarProblematica(problematicaModificar);
    }
    
    @FXML
    private void obtenerAsignaturasDelEstudiante(ActionEvent evento){
        try{
            listaAsignaturasEstudiante
            (comboBoxEstudiante.getSelectionModel().getSelectedItem());
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    @FXML
    private void guardarProblematicaAcademica(ActionEvent evento){
        try {
            validarCamposVacios();
            registrarModificacionProlematicaAcademica();
            Utilidades.mostrarAlertaConfirmacion("Información guardada",
            "La información se registró correctamente en el sistema.",Alert.AlertType.INFORMATION);
            if (Utilidades.getOpcion().get() == ButtonType.OK) {
                Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
                escenarioPrincipal.close();
            }
        } catch(SQLException sqlExcepcion){
            Utilidades.mensajePerdidaDeConexion();
        }catch(IllegalArgumentException iaException){
            Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos",
            "No puede dejar ningún campo vacío. Por favor, verifique que todos los campos estén llenos",
            Alert.AlertType.WARNING);
        }
    }
    
    
    private void validarCamposVacios() throws IllegalArgumentException{
        if(comboBoxAsignatura.getValue() == null){
            throw new IllegalArgumentException();
        }
        if(textAreaDescripcion.getText().isBlank()){
            throw new IllegalArgumentException();
        }
        if(textFieldTitulo.getText().isBlank()){
            throw new IllegalArgumentException();
        }
        if(comboBoxEstudiante.getValue() ==null){
            throw new IllegalArgumentException();
        }
    }
    
    
    @FXML
    private void cancelarRegistroProblematica(ActionEvent evento){
        Utilidades.mostrarAlertaConfirmacion("Cancelar operación",
        "¿Está seguro de que desea cancelar la operación?", Alert.AlertType.CONFIRMATION);
        if (Utilidades.getOpcion().get() == ButtonType.OK) {
            Stage escenarioPrincipal = (Stage) labelTitulo.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
}
