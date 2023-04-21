package controlador;

import logicaNegocio.AcademicoDAO;
import logicaNegocio.AsignaturaDAO;
import dominio.Academico;
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
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModificarProfesorFXMLController extends RegistrarPersonaFXMLController 
implements Initializable {

    private Academico profesorSeleccionado;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonAsignarExperienciasEducativas;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        super.initialize(localizadorRecursos, paqueteRecursos);
    }

    @FXML
    private void guardar(ActionEvent evento) {
        try {
            validarEntradas();
            Academico profesorModificado = modificarProfesor();
            validarDatosDelProfesorNoSeRepitenEnBaseDeDatos(profesorModificado);
            AcademicoDAO academicoDAO = new AcademicoDAO();
            academicoDAO.modificarAcademico(profesorModificado);
            super.terminar();
            super.cerrar(true);
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        } catch (IllegalArgumentException iaException) {
            Utilidades.mostrarAlertaSinConfirmacion(
                "Datos inválidos", iaException.getMessage(), Alert.AlertType.WARNING
            );
        }
    }
    
    private void validarEntradas(){
        super.validarCamposLlenos();
        super.validarCaracteresNombreCompleto();
        super.validarCaracteresCorreoElectronicoPersonal();
        super.validarCaracteresCorreoElectronicoInstitucional();
        super.validarLongitudCampos();
    }
    
    private void validarDatosDelProfesorNoSeRepitenEnBaseDeDatos(Academico profesorModificado) throws SQLException{
        if (profesorModificado.getNombre().
                equals(profesorSeleccionado.getNombre())==false){
            validarAcademicoPorNombreCompleto(profesorModificado);
        }
        if (profesorModificado.getApellidoPaterno().
                equals(profesorSeleccionado.getApellidoPaterno())==false){
            validarAcademicoPorNombreCompleto(profesorModificado);
        }
        if (profesorModificado.getApellidoMaterno().
                equals(profesorSeleccionado.getApellidoMaterno())==false){
            validarAcademicoPorNombreCompleto(profesorModificado);
        }
        if (profesorModificado.getCorreoElectronico().
                equals(profesorSeleccionado.getCorreoElectronico())==false){
            validarAcademicoPorCorreoElectronicoPersonal(profesorModificado);
        }
        if (profesorModificado.getCorreoElectronicoInstitucional().
                equals(profesorSeleccionado.getCorreoElectronicoInstitucional())==false){
            validarAcademicoPorCorreoElectronicoInstitucional(profesorModificado);
        }
    }
    
    private Academico modificarProfesor() {
        Academico profesorModificado = new Academico();
        profesorModificado.setIdAcademico(profesorSeleccionado.getIdAcademico());
        profesorModificado.setNombre(super.getTextFieldNombre().getText());
        profesorModificado.setApellidoPaterno(super.getTextFieldApellidoPaterno().getText());
        profesorModificado.setApellidoMaterno(super.getTextFieldApellidoMaterno().getText());
        profesorModificado.setCorreoElectronicoPersonal(super.getTextFieldEmailPersonal().getText());
        profesorModificado.setCorreoElectronicoInstitucional(
            super.getTextFieldEmailInstitucional().getText());
        
        this.profesorSeleccionado.setNombre(profesorModificado.getNombre());
        this.profesorSeleccionado.setApellidoPaterno(profesorModificado.getApellidoPaterno());
        this.profesorSeleccionado.setApellidoMaterno(profesorModificado.getApellidoMaterno());
        this.profesorSeleccionado.setCorreoElectronicoPersonal(profesorModificado.getCorreoElectronico());
        this.profesorSeleccionado.setCorreoElectronicoInstitucional(
                profesorModificado.getCorreoElectronicoInstitucional()
        );
        return profesorModificado;
    }

    private void validarAcademicoPorNombreCompleto(Academico nuevoAcademico) 
    throws SQLException{
        AcademicoDAO academicoDAO = new AcademicoDAO();
        boolean nombreDelAcademicoNoSeRepite = !academicoDAO.buscarNombreDeAcademicoExistente(nuevoAcademico);
        if (nombreDelAcademicoNoSeRepite == false){
            throw new IllegalArgumentException(
                "Ya existe un académico con ese nombre registrado en el sistema. "
                + "Por favor, verifique su información e intente de nuevo.");
        }
    }
    
    private void validarAcademicoPorCorreoElectronicoPersonal(Academico nuevoAcademico) 
    throws SQLException{
        AcademicoDAO academicoDAO = new AcademicoDAO();
        boolean correoElectronicoPersonalDelAcademicoNoSeRepite = 
            !academicoDAO.buscarCorreoElectronicoPersonalDeAcademicoExistente(nuevoAcademico);
        if (correoElectronicoPersonalDelAcademicoNoSeRepite == false){
            throw new IllegalArgumentException(
                "Ya existe un académico con ese correo electrónico personal registrado en el sistema. "
                + "Por favor, verifique su información e intente de nuevo.");
        }
    }
    
    private void validarAcademicoPorCorreoElectronicoInstitucional (Academico nuevoAcademico) 
    throws SQLException{
        AcademicoDAO academicoDAO = new AcademicoDAO();
        boolean correoElectronicoInstitucionalDelAcademicoNoSeRepite = 
            !academicoDAO.buscarCorreoElectronicoInstitucionalDeAcademicoExistente(nuevoAcademico);
        if (correoElectronicoInstitucionalDelAcademicoNoSeRepite == false){
            throw new IllegalArgumentException(
                "Ya existe un académico con ese correo electrónico institucional registrado en el sistema. "
                + "Por favor, verifique su información e intente de nuevo.");
        }
    }
    
    private void llamarAsignacionDeExperienciasEducativasGUI() {
        Parent raiz;
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                    "/vista/AsignacionDeExperienciasEducativasAProfesoresFXML.fxml"));
            raiz = cargador.load();
            AsignacionDeExperienciasEducativasAProfesoresFXMLController controlador
                    = cargador.getController();
            controlador.setProfesor(profesorSeleccionado);
            Stage escenarioActual = (Stage) buttonCancelar.getScene().getWindow();
            Stage escenarioSecundario = new Stage();
            escenarioSecundario.initOwner(escenarioActual);
            escenarioSecundario.initModality(Modality.APPLICATION_MODAL);
            escenarioSecundario.setTitle("Asignación de Experiencias Educativas");
            escenarioSecundario.setScene(new Scene(raiz, 470, 410));
            escenarioSecundario.showAndWait();
        } catch (IOException ioException) {
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }

    public void setProfesorSeleccionado(Academico profesorSeleccionado) {
        this.profesorSeleccionado = new Academico();
        this.profesorSeleccionado = profesorSeleccionado;
        inicializarTextFields();
    }

    private void inicializarTextFields() {
        super.getTextFieldNombre().setText(profesorSeleccionado.getNombre());
        super.getTextFieldApellidoPaterno().setText(profesorSeleccionado.getApellidoPaterno());
        super.getTextFieldApellidoMaterno().setText(profesorSeleccionado.getApellidoMaterno());
        super.getTextFieldEmailPersonal().setText(profesorSeleccionado.getCorreoElectronico());
        super.getTextFieldEmailInstitucional().setText(profesorSeleccionado.getCorreoElectronicoInstitucional());
    }

    @FXML
    private void verificarExistenciaDeExperienciasEducativas(ActionEvent evento) {
        try {
            AsignaturaDAO asignaturaDAO = new AsignaturaDAO();
            if (asignaturaDAO.verificarExistenciaDeAsignaturas()==false){
                Utilidades.mostrarAlertaConfirmacion(
                    "Mensaje", "No hay ninguna experiencia educativa registrada en el sistema."
                    + " Por favor, intentelo más tarde.", Alert.AlertType.INFORMATION);
            } else if (asignaturaDAO.obtenerAsignaturasSinAcademico().isEmpty()){
                Utilidades.mostrarAlertaConfirmacion(
                    "Mensaje", "No hay ninguna experiencia educativa sin facilitador en el sistema."
                    + " Por favor, intentelo más tarde.", Alert.AlertType.INFORMATION);
            }else {
                llamarAsignacionDeExperienciasEducativasGUI();
            }
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        }
    }
}
