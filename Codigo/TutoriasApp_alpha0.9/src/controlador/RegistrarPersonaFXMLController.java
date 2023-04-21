package controlador;

import logicaNegocio.helpers.StringHelper;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public abstract class RegistrarPersonaFXMLController implements Initializable {
    
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellidoPaterno;
    @FXML
    private TextField textFieldApellidoMaterno;
    @FXML
    private Button buttonGuardar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private TextField textFieldEmailInstitucional;
    @FXML
    private TextField textFieldEmailPersonal;
    
    private final int LONGITUD_NOMBRES = 50;
    private final int LONGITUD_CORREO_PERSONAL=100;
    private final int LONGITUD_CORREO_INSTITUCIONAL=30;
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        limitarLongitudDeEntradasEnTextFields();
        limitarCaracteresDeEntradasEnTextFields();
    }   
    
    void validarCaracteresNombreCompleto() throws IllegalArgumentException{
        String nombreCompleto =
            this.textFieldNombre.getText()
            + this.textFieldApellidoPaterno.getText()
            + this.textFieldApellidoMaterno.getText();
        
        StringHelper stringHelper = new StringHelper();
        boolean nombreCompletoEsValido = stringHelper.validarCaracteresDeCadena(
            nombreCompleto, stringHelper.getCaracteresAlfabeticos()
        );
        if (nombreCompletoEsValido==false){
            throw new IllegalArgumentException("Sólo se permiten caracteres alfabéticos para los"
                + " nombres y apellidos. Por favor, verifique su información e intente de nuevo.");
        }    
    }
    
    void validarLongitudCampos() throws IllegalArgumentException{
        StringHelper stringHelper = new StringHelper();
        if (this.textFieldNombre.getText().length()>LONGITUD_NOMBRES){
            generarExceptionPorLongitudExcedida("Nombre", LONGITUD_NOMBRES);
        }
        if (this.textFieldApellidoPaterno.getText().length()>LONGITUD_NOMBRES){
            generarExceptionPorLongitudExcedida("Apellido paterno", LONGITUD_NOMBRES);
        }
        if (this.textFieldApellidoMaterno.getText().length()>LONGITUD_NOMBRES){
            generarExceptionPorLongitudExcedida("Apellido materno", LONGITUD_NOMBRES);
        }
        if (this.textFieldEmailPersonal.getText().length()>LONGITUD_CORREO_PERSONAL){
            generarExceptionPorLongitudExcedida("Correo electrónico personal", LONGITUD_CORREO_PERSONAL);
        }
        if (this.textFieldEmailInstitucional!=null){
            validarLongitudDeCorreoElectronicoInstitucional();
        }
    }
    
    void validarLongitudDeCorreoElectronicoInstitucional() throws IllegalArgumentException {
        if (this.textFieldEmailInstitucional.getText().length()>LONGITUD_CORREO_INSTITUCIONAL){
            generarExceptionPorLongitudExcedida("Correo electrónico institucional", 
                    LONGITUD_CORREO_INSTITUCIONAL);
        }
    }
    
    private void generarExceptionPorLongitudExcedida(String nombreDelCampo, int longitudLimite) 
    throws IllegalArgumentException{
        throw new IllegalArgumentException("El campo "+nombreDelCampo+
            " no exceder los "+longitudLimite+" caracteres.");
    }
    
    void validarCaracteresCorreoElectronicoInstitucional() throws IllegalArgumentException{
        String correoElectronicoIntitucional = this.textFieldEmailInstitucional.getText();
        
        StringHelper stringHelper = new StringHelper();
        boolean correoElectronicoInstitucionalValido = 
            stringHelper.validarCaracteresDeCadena(
                correoElectronicoIntitucional, stringHelper.getCaracteresCorreoElectronico()
            );
        if (correoElectronicoInstitucionalValido==false){
            throw new IllegalArgumentException("El correo electrónico institucional ingresado es inválido."
                    + " Por favor, verifique su información e intente de nuevo.");
        }
    }
    
    void validarCaracteresCorreoElectronicoPersonal() throws IllegalArgumentException{
        String correoElectronicoPersonal = this.textFieldEmailPersonal.getText();
        
        StringHelper stringHelper = new StringHelper();
        boolean correoElectronicoPersonaValido = 
            stringHelper.validarCaracteresDeCadena(
                correoElectronicoPersonal, stringHelper.getCaracteresCorreoElectronico());
        if (correoElectronicoPersonaValido==false){
            throw new IllegalArgumentException("El correo electrónico personal ingresado es inválido."
                    + " Por favor, verifique su información e intente de nuevo.");
        }
    }
    
    void terminar(){
        Utilidades.mostrarAlertaSinConfirmacion(
            "Mensaje", 
            "La información se ha actualizado correctamente", 
            Alert.AlertType.INFORMATION);
        this.textFieldNombre.setText("");
        this.textFieldApellidoPaterno.setText("");
        this.textFieldApellidoMaterno.setText("");
        this.textFieldEmailPersonal.setText("");
        if (this.textFieldEmailInstitucional!=null){
            this.textFieldEmailInstitucional.setText("");
        }
    }
    
    void validarCamposLlenos() throws IllegalArgumentException{
        ArrayList<TextField> campos = new ArrayList<TextField>();
        campos.add(this.textFieldNombre);
        campos.add(this.textFieldApellidoPaterno);
        campos.add(this.textFieldApellidoMaterno);
        campos.add(this.textFieldEmailPersonal);
        if (this.textFieldEmailInstitucional!=null){
            campos.add(this.textFieldEmailInstitucional);    
        }
        campos.forEach(textField -> {
            if (textField.getText().isBlank()){
                throw new IllegalArgumentException("No puede dejar ningún campo vacío");
            }
        });
    }
    
    private void limitarLongitudDeEntradasEnTextFields(){
        this.textFieldNombre.textProperty().addListener(
            (ObservableValue<? extends String> observable, String prievioValor, String nuevoValor) -> {
                if (textFieldNombre.getText().length()>=LONGITUD_NOMBRES){
                    String cadena = textFieldNombre.getText().substring(0,LONGITUD_NOMBRES);
                    textFieldNombre.setText(cadena);
                }
            }
        );
        this.textFieldApellidoPaterno.textProperty().addListener(
            (ObservableValue<? extends String> observable, String prievioValor, String nuevoValor) -> {
                if (textFieldApellidoPaterno.getText().length()>=LONGITUD_NOMBRES){
                    String cadena = textFieldApellidoPaterno.getText().substring(0,LONGITUD_NOMBRES);
                    textFieldApellidoPaterno.setText(cadena);
                }
            }
        );
        this.textFieldApellidoMaterno.textProperty().addListener(
            (ObservableValue<? extends String> observable, String prievioValor, String nuevoValor) -> {
                if (textFieldApellidoMaterno.getText().length()>=LONGITUD_NOMBRES){
                    String cadena = textFieldApellidoMaterno.getText().substring(0,LONGITUD_NOMBRES);
                    textFieldApellidoMaterno.setText(cadena);
                }
            }
        );
        this.textFieldEmailPersonal.textProperty().addListener(
            (ObservableValue<? extends String> observable, String prievioValor, String nuevoValor) -> {
                if (textFieldEmailPersonal.getText().length()>=LONGITUD_CORREO_PERSONAL){
                    String cadena = textFieldEmailPersonal.getText().substring(0,LONGITUD_CORREO_PERSONAL);
                    textFieldEmailPersonal.setText(cadena);
                }
            }
        );
        if (this.textFieldEmailInstitucional!=null){
            limitarLongitudDeEntradaEnTextFieldEmailInstitucional();
        }
    }
    
    private void limitarLongitudDeEntradaEnTextFieldEmailInstitucional(){
        this.textFieldEmailInstitucional.textProperty().addListener(
            (ObservableValue<? extends String> observable, String prievioValor, String nuevoValor) -> {
                if (textFieldEmailInstitucional.getText().length()>=LONGITUD_CORREO_INSTITUCIONAL){
                    String cadena = 
                        textFieldEmailInstitucional.getText().substring(0,LONGITUD_CORREO_INSTITUCIONAL);
                    textFieldEmailInstitucional.setText(cadena);
                }
            }
        );
    }
    
    private void limitarCaracteresDeEntradasEnTextFields(){
        StringHelper stringHelper = new StringHelper();
        this.textFieldNombre.textProperty().addListener(
        (ObservableValue<? extends String> observable, String valorPrevio, String valorNuevo) -> {
            if (!valorNuevo.matches(stringHelper.getCaracteresAlfabeticos()) ){
                if (textFieldNombre.getText().length()!=LONGITUD_NOMBRES){
                    textFieldNombre.setText(valorNuevo.replaceAll(
                        "[^"+stringHelper.getCaracteresAlfabeticos()+"]","")
                    );
                }else{
                    textFieldNombre.setText(valorNuevo.substring(0,LONGITUD_NOMBRES));
                }
            }
        });
        this.textFieldApellidoPaterno.textProperty().addListener(
        (ObservableValue<? extends String> observable, String valorPrevio, String valorNuevo) -> {
            if (!valorNuevo.matches(stringHelper.getCaracteresAlfabeticos()) ){
                if (textFieldApellidoPaterno.getText().length()!=LONGITUD_NOMBRES){
                    textFieldApellidoPaterno.setText(valorNuevo.replaceAll(
                        "[^"+stringHelper.getCaracteresAlfabeticos()+"]","")
                    );
                }else{
                    textFieldApellidoPaterno.setText(valorNuevo.substring(0,LONGITUD_NOMBRES));
                }
            }
        });
        this.textFieldApellidoMaterno.textProperty().addListener(
        (ObservableValue<? extends String> observable, String valorPrevio, String valorNuevo) -> {
            if (!valorNuevo.matches(stringHelper.getCaracteresAlfabeticos()) ){
                if (textFieldApellidoMaterno.getText().length()!=LONGITUD_NOMBRES){
                    textFieldApellidoMaterno.setText(valorNuevo.replaceAll(
                        "[^"+stringHelper.getCaracteresAlfabeticos()+"]","")
                    );
                }else{
                    textFieldApellidoMaterno.setText(valorNuevo.substring(0,LONGITUD_NOMBRES));
                }
            }
        });
    }
  
    @FXML
    private void cancelar(ActionEvent evento) {
        Utilidades.mostrarAlertaConfirmacion(
            "Cancelar operación", 
            "¿Está seguro de que desea cancelar la operación?", 
            Alert.AlertType.CONFIRMATION);
        boolean confirmarCancelacion =
            Utilidades.getOpcion().orElse(ButtonType.CANCEL).getButtonData().isDefaultButton();
        cerrar(confirmarCancelacion);
    }

    void cerrar(boolean confirmacion){
        if (confirmacion == true){
            Stage escenario = (Stage)buttonCancelar.getScene().getWindow();
            escenario.close();   
        }
    }
    
    public TextField getTextFieldNombre() {
        return textFieldNombre;
    }

    public TextField getTextFieldApellidoPaterno() {
        return textFieldApellidoPaterno;
    }

    public TextField getTextFieldApellidoMaterno() {
        return textFieldApellidoMaterno;
    }

    public TextField getTextFieldEmailInstitucional() {
        return textFieldEmailInstitucional;
    }

    public TextField getTextFieldEmailPersonal() {
        return textFieldEmailPersonal;
    }
}
