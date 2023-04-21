package controlador;

import logicaNegocio.AcademicoRolDAO;
import logicaNegocio.AcademicoUsuarioDAO;
import logicaNegocio.FechaTutoriaDAO;
import logicaNegocio.PeriodoEscolarDAO;
import dominio.AcademicoRol;
import dominio.AcademicoUsuario;
import dominio.FechaTutoria;
import dominio.globales.InformacionSesion;
import dominio.PeriodoEscolar;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class InicioDeSesionFXMLController implements Initializable {

    @FXML
    private TextField textFieldUsuario;
    @FXML
    private PasswordField passwordFieldContrasenia;
    @FXML
    private Label labelErrorUsuario;
    @FXML
    private Label labelErrorContrasenia;
    @FXML
    private Button buttonIniciarSesion;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        
    }    

    @FXML
    private void iniciarSesion(ActionEvent evento) {
        labelErrorContrasenia.setText("");
        labelErrorUsuario.setText("");
        boolean campoValido = true;
        if(textFieldUsuario.getText().isBlank()){
            labelErrorUsuario.setText("Campo Usuario Requerido");
            campoValido = false;
        }
        if(passwordFieldContrasenia.getText().isBlank()){
            labelErrorContrasenia.setText("Campo Contraseña Requerido");
            campoValido = false;
        }
        if (campoValido) {
            validarUsuario(textFieldUsuario.getText(), passwordFieldContrasenia.getText()); 
        }
    }
    
    private void validarUsuario(String nombreUsuario, String contrasenia){
        AcademicoUsuarioDAO  academicoUsuarioDao =  new AcademicoUsuarioDAO();
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setNombreUsuario(nombreUsuario);
        academicoUsuario.setContrasenia(contrasenia);
        try{
            academicoUsuario = 
            academicoUsuarioDao.obtenerIdAcademicoUsuarioPorNombreUsuarioyContrasenia(academicoUsuario);
            if(academicoUsuario.getCredencial()){
                Utilidades.mostrarAlertaSinConfirmacion("Usuario verificado","Bienvenido(a) al sistema", 
                Alert.AlertType.INFORMATION);
                almacenarDatosDeUsuarioValidado(academicoUsuario);
                abrirPantallaPrincipal();
            }else{
                if(!academicoUsuario.getCredencial()){
                Utilidades.mostrarAlertaSinConfirmacion("Credenciales Incorrectas", 
                "Usuario y/o contraseña incorrectos favor de verificarlos", Alert.AlertType.WARNING);
                }
            }
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        } 
    }
    
    private void almacenarDatosDeUsuarioValidado(AcademicoUsuario academicoUsuario) throws SQLException{
        InformacionSesion informacionSesion = InformacionSesion.getInformacionSesion();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        AcademicoRol academicoRol = new AcademicoRol();
        academicoRol.setAcademicoUsuario(academicoUsuario);
        academicoRol = academicoRolDao.obtenerAcademicoRolPorIdUsuario(academicoRol);
        informacionSesion .setProgramaEducativo(academicoRol.getProgramaEducativo());
        informacionSesion .setAcademicoRol(academicoRol);
        cargarPeriodoEscolarDeInicioDeSesion(informacionSesion );         
    }
    
    private void cargarPeriodoEscolarDeInicioDeSesion(InformacionSesion informacionSesion )
    throws SQLException{
        PeriodoEscolarDAO periodoEscolarDao = new PeriodoEscolarDAO();
        PeriodoEscolar periodoActual =
        periodoEscolarDao.obtenerPeriodoEscolarActualPorFechaDelSistema(LocalDate.now());
        FechaTutoriaDAO fechaTutoriaDao = new FechaTutoriaDAO();
        ArrayList<FechaTutoria> fechasDeTutoriaActuales =
        fechaTutoriaDao.obtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo
        (periodoActual, informacionSesion .getProgramaEducativo().getIdProgramaEducativo());
        cargarFechaTutoríaDeInicioDeSesion(fechasDeTutoriaActuales, informacionSesion );  
    }
    
    private void cargarFechaTutoríaDeInicioDeSesion(ArrayList<FechaTutoria> fechasDeTutoriaActuales, 
    InformacionSesion informacionSesion ){
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date fechaActual = (java.sql.Date.valueOf(formatoFecha.format(LocalDateTime.now())));
        for(FechaTutoria fechaEncontrada:fechasDeTutoriaActuales){
            if(fechaEncontrada.getFechaTutoria().before(fechaActual) && 
            fechaEncontrada.getFechaCierreDeReporte().after(fechaActual) || 
            fechaEncontrada.getFechaCierreDeReporte().equals(fechaActual) ||
            fechaEncontrada.getFechaTutoria().equals(fechaActual)){
                informacionSesion .setFechaTutoria(fechaEncontrada);
            }
        }
    }
    
    private void abrirPantallaPrincipal(){
        Stage escenarioPrincipal = (Stage) textFieldUsuario.getScene().getWindow();
        try {
            Scene pantallaPrincipal =
            new Scene(FXMLLoader.load(getClass().getResource("/vista/PantallaPrincipalFXML.fxml")));
            escenarioPrincipal.setScene(pantallaPrincipal);
            escenarioPrincipal.setTitle("Sistema de tutorias FEI");
            escenarioPrincipal.show();
        } catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }   catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
}
