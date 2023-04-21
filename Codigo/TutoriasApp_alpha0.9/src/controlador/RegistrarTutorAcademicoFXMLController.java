package controlador;

import logicaNegocio.AcademicoDAO;
import logicaNegocio.AcademicoRolDAO;
import logicaNegocio.AcademicoUsuarioDAO;
import dominio.Academico;
import dominio.AcademicoRol;
import dominio.AcademicoUsuario;
import dominio.constantes.Roles;
import dominio.globales.InformacionSesion;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RegistrarTutorAcademicoFXMLController implements Initializable {
    @FXML
    private Label labelAcademico;
    @FXML
    private ComboBox <Academico> comboBoxAcademico;
    @FXML
    private TextField textFieldNombreUsuario;
    @FXML
    private TextField textFieldContrasenia;
    @FXML
    private Button buttonAceptar;
    @FXML
    private Button buttonCancelar;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            comboBoxAcademico.getItems().addAll(obtenerListaAcademicosDelCoordinadorDeTutoriasAcademicas());
            editarFormatoComboBoxAcademico();
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                cerrar(true);
            });
        }
    }    
    
    private void cerrar(boolean confirmacion){
        if (confirmacion == true){
            Stage escenario = (Stage)buttonAceptar.getScene().getWindow();
            escenario.close();   
        }
    }
    
    private ArrayList <Academico> obtenerListaAcademicosDelCoordinadorDeTutoriasAcademicas() 
    throws SQLException{
        AcademicoDAO academicoDao = new AcademicoDAO();
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        ArrayList <Academico> academicos = 
        academicoDao.obtenerAcademicosPorProgramaEducativo
            (informacionSesion .getProgramaEducativo().getIdProgramaEducativo());
        return academicos;
    }
    
    private void editarFormatoComboBoxAcademico(){
        comboBoxAcademico.setConverter(new StringConverter<Academico>() {
            @Override
            public String toString(Academico academico) {
                return academico == null ? null : academico.getApellidoPaterno() + " " 
                + academico.getApellidoMaterno() + " "  + academico.getNombre();
            }
            @Override
            public Academico fromString(String string) {
              return null;
            }
        });
    }
    
    @FXML
    private void cancelarRegistroDeTutorAcademico(ActionEvent evento){
        Utilidades.mostrarAlertaConfirmacion("Cancelar operación",
        "¿Está seguro de que desea cancelar la operación?", Alert.AlertType.CONFIRMATION);
        if (Utilidades.getOpcion().get() == ButtonType.OK) {
            Stage escenarioPrincipal = (Stage) labelAcademico.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
    
    @FXML
    private void guardarTutorAcademico(ActionEvent evento){
        if(comboBoxAcademico.getValue()!=null && !textFieldNombreUsuario.getText().isBlank()
        && !textFieldContrasenia.getText().isBlank()){
            validarTutorAcademico();
        }else{
            Utilidades.mostrarAlertaSinConfirmacion("Campos vacíos",
            "No puede dejar ningún campo vacío. Por favor, verifique que todos los campos estén llenos",
            Alert.AlertType.WARNING);
        }
        
    }
    private void validarTutorAcademico(){
        try {
            InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();


            AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
            AcademicoRol academicoRol = new AcademicoRol();
            academicoRol.setProgramaEducativo(informacionSesion .getProgramaEducativo());
            int rolTutorAcademico = Roles.TUTOR_ACADEMICO.getIdRol();
            academicoRol.getRol().setIdRol(rolTutorAcademico);
            academicoRol.setAcademico(comboBoxAcademico.getSelectionModel().getSelectedItem());
            if(!academicoRolDao.buscarAcademicoRolExistente(academicoRol)){
                registrarTutorAcademico(academicoRolDao, academicoRol);
                Utilidades.mostrarAlertaConfirmacion("Información guardada", "La información"
                + " se registró correctamente en el sistema.",Alert.AlertType.INFORMATION);
                if (Utilidades.getOpcion().get() == ButtonType.OK) {
                    Stage escenarioPrincipal = (Stage) labelAcademico.getScene().getWindow();
                    escenarioPrincipal.close();
                }
            }else{
                Utilidades.mostrarAlertaSinConfirmacion("Tutor académico existente",
                "El académico que selecciono ya se encuentra registrado como tutor académico",
                Alert.AlertType.WARNING);
            }
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void registrarTutorAcademico(AcademicoRolDAO academicoRolDao, AcademicoRol academicoRol)
    throws SQLException{
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        AcademicoUsuarioDAO academicoUsuarioDao = new AcademicoUsuarioDAO();
        academicoUsuario.setNombreUsuario(textFieldNombreUsuario.getText());
        academicoUsuario.setContrasenia(textFieldContrasenia.getText());
        int sinRol = Roles.SIN_ROL.getIdRol();
        academicoRol.getRol().setIdRol(sinRol);
        if(academicoRolDao.buscarAcademicoRolExistente(academicoRol)){
            academicoRol.setAcademicoUsuario(academicoUsuario);
            registrarPrimerRolDeAcademicoComoTutor(academicoRol, academicoRolDao, academicoUsuarioDao);       
        }else{
            academicoRol.setAcademicoUsuario(academicoUsuario);
            registrarNuevoRolDeAcademicoComoTutor(academicoUsuarioDao, academicoRol, academicoRolDao);
        }
    }
    
    private void registrarPrimerRolDeAcademicoComoTutor(AcademicoRol academicoRol,
    AcademicoRolDAO academicoRolDao, AcademicoUsuarioDAO academicoUsuarioDao) throws SQLException{
        academicoRol = academicoRolDao.obtenerIdUsuarioDeAcademicoRolExistente(academicoRol);
        
        final int tutorAcademicoRol = Roles.TUTOR_ACADEMICO.getIdRol();
        academicoRol.getRol().setIdRol(tutorAcademicoRol);
        academicoRolDao.actualizarAcademicoRolExistente(academicoRol);
        academicoRol.getAcademicoUsuario().setIdUsuario(academicoRol.getAcademicoUsuario().getIdUsuario());
        academicoUsuarioDao.actualizarAcademicoUsuarioExistente(academicoRol.getAcademicoUsuario());     
    }
    
    private void registrarNuevoRolDeAcademicoComoTutor(AcademicoUsuarioDAO academicoUsuarioDao, 
    AcademicoRol academicoRol, AcademicoRolDAO academicoRolDao) throws SQLException{
        academicoUsuarioDao.registrarAcademicoUsuario(academicoRol.getAcademicoUsuario());
        academicoRol.setAcademicoUsuario(academicoUsuarioDao.
        obtenerIdAcademicoUsuarioPorNombreUsuarioyContrasenia(academicoRol.getAcademicoUsuario()));
        academicoRol.getAcademicoUsuario().setIdUsuario(academicoRol.getAcademicoUsuario().getIdUsuario());
        
        final int tutorAcademicoRol = Roles.TUTOR_ACADEMICO.getIdRol();
        academicoRol.getRol().setIdRol(tutorAcademicoRol);
        academicoRolDao.registrarAcademicoRol(academicoRol);
    }
}
