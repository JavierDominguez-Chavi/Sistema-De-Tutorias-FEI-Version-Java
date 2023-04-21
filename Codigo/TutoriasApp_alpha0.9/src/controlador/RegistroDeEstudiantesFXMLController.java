package controlador;

import logicaNegocio.EstudianteDAO;
import logicaNegocio.helpers.StringHelper;
import dominio.Estudiante;
import dominio.globales.InformacionSesion;
import dominio.ProgramaEducativo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class RegistroDeEstudiantesFXMLController extends RegistrarPersonaFXMLController 
implements Initializable {

    @FXML
    private TextField textFieldMatricula;
    @FXML
    private ComboBox<Integer> comboBoxPeriodoQueCursa;
    @FXML
    private Label labelCorreoInstitucional;
    @FXML
    private Button buttonRegistrarYAsignar;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        super.initialize(localizadorRecursos, paqueteRecursos);
        inicializarComboBoxPeriodosQueCursa();
        inicializarListenersDeMatricula();
    }    
    
    private void validarCaracteresEntradas() throws IllegalArgumentException{
        super.validarCaracteresNombreCompleto();
        super.validarCaracteresCorreoElectronicoPersonal();
        validarMatricula();
    }
    
    private void inicializarComboBoxPeriodosQueCursa(){
        final Integer[] PERIODOS = {1,2,3,4,5,6,7,8,9,10,11,12};
        ObservableList<Integer> listaPeriodos = FXCollections.observableArrayList(PERIODOS);
        this.comboBoxPeriodoQueCursa.setItems(listaPeriodos);
    }
    
    @Override
    void validarCamposLlenos() throws IllegalArgumentException {
        super.validarCamposLlenos();
        if (this.textFieldMatricula.getText().isBlank() 
            || this.comboBoxPeriodoQueCursa.getSelectionModel().isEmpty()){
            throw new IllegalArgumentException("No puede dejar ningún campo vacío");
        }
    }
    
    private void validarMatricula() throws NumberFormatException{
        Integer.parseInt(this.textFieldMatricula.getText());
        if (textFieldMatricula.getText().length()<8){
            throw new IllegalArgumentException("La matrícula debe contener 8 números.");
        }
    }   
    
    @FXML
    private boolean guardar(ActionEvent evento) {
        boolean registroCorrecto = false;
        try{
            this.validarCamposLlenos();
            super.validarLongitudCampos();
            this.validarCaracteresEntradas();
            Estudiante nuevoEstudiante = crearEstudiante();
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            registroCorrecto = estudianteDAO.registrarEstudiante(nuevoEstudiante);
            terminar();
        }catch(NumberFormatException nfException){
            Utilidades.mostrarAlertaSinConfirmacion(
                "Datos inválidos", 
                "La matrícula sólo puede contener caracteres numéricos. Por favor, verifique su"
                    + " información e intentelo de nuevo.", Alert.AlertType.WARNING);
        }catch(IllegalArgumentException iaException){
            Utilidades.mostrarAlertaSinConfirmacion(
                "Datos inválidos", iaException.getMessage(), Alert.AlertType.WARNING);
        } catch(SQLIntegrityConstraintViolationException sqlicvException){
            Utilidades.mostrarAlertaSinConfirmacion(
                "Datos inválidos", "Esa matrícula ya está registrada. Por favor, verifique ese dato.", 
                Alert.AlertType.WARNING);
        } catch (SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
        return registroCorrecto;
    }
    
    @FXML
    private void extenderAAsignacionDeTutor(ActionEvent evento){
        if (guardar(evento)==true){
            llamarAsignacionDeTutorGUI(new Stage());
        }
    }
    
    private void llamarAsignacionDeTutorGUI(Stage primaryStage) {
        try {
            Parent raiz = FXMLLoader.load(getClass().getResource(
                    "/vista/AsignacionTutorAEstudianteNuevoFXML.fxml"));
            Scene escena = new Scene(raiz, 743, 560);
            primaryStage.setTitle("Asignación de tutores académicos");
            primaryStage.setResizable(false);
            primaryStage.setScene(escena);
            primaryStage.show();
        } catch (IOException ioException) {
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        } catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
    @Override
    void terminar(){
        this.labelCorreoInstitucional.setText("");
        this.textFieldMatricula.setText("");
        this.comboBoxPeriodoQueCursa.getSelectionModel().select(0);
        super.terminar();
    }
    
    private Estudiante crearEstudiante(){
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setMatricula("S"+textFieldMatricula.getText());
        nuevoEstudiante.setNombre(getTextFieldNombre().getText());
        nuevoEstudiante.setApellidoPaterno(getTextFieldApellidoPaterno().getText());
        nuevoEstudiante.setApellidoMaterno(getTextFieldApellidoMaterno().getText());
        nuevoEstudiante.setCorreoElectronicoPersonal(getTextFieldEmailPersonal().getText());
        nuevoEstudiante.setCorreoElectronicoInstitucional(
                "zs"+labelCorreoInstitucional.getText()+"@estudiantes.uv.mx");
        nuevoEstudiante.setSemestreQueCursa(comboBoxPeriodoQueCursa.getSelectionModel().getSelectedItem());
        ProgramaEducativo programaEducativo = 
            InformacionSesion.getInformacionSesion().getAcademicoRol().getProgramaEducativo();
        nuevoEstudiante.setProgramaEducativo(programaEducativo);
        return nuevoEstudiante;
    }

    private void inicializarListenersDeMatricula(){
        StringHelper stringHelper = new StringHelper();
        this.textFieldMatricula.textProperty().addListener(
            (ObservableValue<? extends String> observable, String valorPrevio, String nuevoValor) -> {
                if (textFieldMatricula.getText().length()>=8){
                    String cadena = textFieldMatricula.getText().substring(0,8);
                    textFieldMatricula.setText(cadena);
                }
            }
        );
        this.textFieldMatricula.textProperty().addListener(
        (ObservableValue<? extends String> observable, String valorPrevio, String valorNuevo) -> {
            if (!valorNuevo.matches(stringHelper.getCaracteresNumericos()) ){
                if (textFieldMatricula.getText().length()!=8){
                    textFieldMatricula.setText(valorNuevo.replaceAll(
                        "[^"+stringHelper.getCaracteresNumericos()+"]","")
                    );
                }else{
                    textFieldMatricula.setText(valorNuevo.substring(0,8));
                }
            }
            this.labelCorreoInstitucional.setText(valorNuevo);
        });
    }
    
}
