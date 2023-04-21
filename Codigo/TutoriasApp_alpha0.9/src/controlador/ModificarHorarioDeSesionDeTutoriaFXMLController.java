package controlador;

import DIG.DIGHorarioTabla;
import logicaNegocio.HorarioDAO;
import dominio.Horario;
import dominio.TutoriaAcademica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModificarHorarioDeSesionDeTutoriaFXMLController implements Initializable {
    @FXML
    private TableView<DIGHorarioTabla> tableViewHorarios;
    @FXML
    private TableColumn<DIGHorarioTabla, String> tableColumnMatricula;
    @FXML
    private TableColumn<DIGHorarioTabla, String> tableColumnNombreEstudiante;
    @FXML
    private TableColumn<DIGHorarioTabla, Spinner> tableColumnHora;
    @FXML
    private TableColumn<DIGHorarioTabla, Spinner> tableColumnMinutos;
    @FXML
    private Label labelPeriodoEscolar;
    @FXML
    private Label labelFechaTutoria;
    @FXML
    private Button buttonGuardarCambios;
    @FXML
    private Button buttonCancelar;
    
    private TutoriaAcademica tutoriaAcademica;
    private ArrayList<DIGHorarioTabla> horariosDeSesionDeTutoria;
    
    public void setTutoriaAcademicaYHorariosDeSesion(TutoriaAcademica tutoriaAcademicaSeleccionada,
    ArrayList<DIGHorarioTabla> listaHorariosDeSesionDeTutoria){
        tutoriaAcademica = new TutoriaAcademica();
        horariosDeSesionDeTutoria = new ArrayList<DIGHorarioTabla>();
        tutoriaAcademica = tutoriaAcademicaSeleccionada;
        horariosDeSesionDeTutoria = listaHorariosDeSesionDeTutoria;
        cargarCamposGUIModificarHorarioDeSesionDeTutoria();
    }
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        
    }    
    
    private void cargarCamposGUIModificarHorarioDeSesionDeTutoria(){
        labelPeriodoEscolar.setText(tutoriaAcademica.getFechaTutoria().getPeriodo().getFechaInicio() + " - " + 
        tutoriaAcademica.getFechaTutoria().getPeriodo().getFechaFin());
        labelFechaTutoria.setText(tutoriaAcademica.getFechaTutoria().getFechaTutoria().toString());
        try {
            cargarInformacionTablaHorarios();
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void cargarInformacionTablaHorarios() throws SQLException{
        cambiarFormatoInformacionTablaHorarios();
        ObservableList<DIGHorarioTabla> listaHorariosDeSesionDeTutoria = 
        FXCollections.observableArrayList(cambiarFormatoInformacionTablaHorarios());
        tableViewHorarios.setItems(listaHorariosDeSesionDeTutoria);
        inicializarColumnasTablaHorarios();
    }
    
    private ArrayList<DIGHorarioTabla> cambiarFormatoInformacionTablaHorarios(){
        ArrayList<DIGHorarioTabla> horariosDeSesionDeTutoriaConNuevoFormato = new ArrayList<DIGHorarioTabla>();
        for(DIGHorarioTabla horarioDeLaSesionDeTutoria : horariosDeSesionDeTutoria){
            DIGHorarioTabla horarioModificado = new DIGHorarioTabla();
            horarioModificado.setEstudiante(horarioDeLaSesionDeTutoria.getEstudiante());
            horarioModificado.setValorInicialSpinners(Integer.parseInt(horarioDeLaSesionDeTutoria.getHora()),
            Integer.parseInt(horarioDeLaSesionDeTutoria.getMinutos()));
            horarioModificado.setIdHorario(horarioDeLaSesionDeTutoria.getIdHorario());
            horariosDeSesionDeTutoriaConNuevoFormato.add(horarioModificado);
        }
        return horariosDeSesionDeTutoriaConNuevoFormato;
    }
    
    private void inicializarColumnasTablaHorarios(){
        tableColumnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tableColumnNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        tableColumnHora.setCellValueFactory(new PropertyValueFactory<>("spinnerHoras"));
        tableColumnMinutos.setCellValueFactory(new PropertyValueFactory<>("spinnerMinutos"));
    }
    
    @FXML
    private void cancelarModificacionHorarioDeLaSesionDeTutoria(){
        Utilidades.mostrarAlertaConfirmacion("Cancelar operación",
        "¿Está seguro de que desea cancelar la operación?", Alert.AlertType.CONFIRMATION);
        if (Utilidades.getOpcion().get() == ButtonType.OK) {
            Stage escenarioPrincipal = (Stage) buttonCancelar.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
    
    @FXML
    private void guardarCambiosHorariosDeLaSesionDeTutoria(){
        HorarioDAO horarioDao = new HorarioDAO();
        try {
            boolean horarioConCambios =  validarHorarioMoficado();
            if(horarioConCambios){
                confirmarHorariosModificadosRegistrados();
            }else{
                Utilidades.mostrarAlertaSinConfirmacion("Horarios sin modificar", "No ha modificado "
                + "ningún horario. Por favor realice por lo menos un cambio", Alert.AlertType.WARNING);
            }
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private boolean validarHorarioMoficado()throws SQLException{
        boolean horarioConCambios = false;
        ArrayList <DIGHorarioTabla> horariosModificados = new ArrayList<DIGHorarioTabla>();
        horariosModificados.addAll(tableViewHorarios.getItems());
        int numeroDeHorarios = 0;
        while(numeroDeHorarios<horariosDeSesionDeTutoria.size()){
            horarioConCambios = 
            buscarHorariosModificados(horarioConCambios, numeroDeHorarios, horariosModificados);
            numeroDeHorarios++;
        }
        return horarioConCambios;
    }
    
    private boolean buscarHorariosModificados(boolean horarioConCambios, int numeroDeHorarios,
    ArrayList <DIGHorarioTabla> horariosModificados){
        DIGHorarioTabla horarioOriginal = new DIGHorarioTabla();
        horarioOriginal = horariosDeSesionDeTutoria.get(numeroDeHorarios);
        DIGHorarioTabla horarioModificado = new DIGHorarioTabla();
        horarioModificado = horariosModificados.get(numeroDeHorarios);
        if(!horarioModificado.getSpinnerHoras().getValue().equals(Integer.parseInt(horarioOriginal.getHora())) || 
        !horarioModificado.getSpinnerMinutos().getValue().equals(Integer.parseInt(horarioOriginal.getMinutos()))){
            horarioConCambios = true;
        }
        return horarioConCambios;
    }
    
    private void confirmarHorariosModificadosRegistrados() throws SQLException{
        if(registrarHorariosModificados()){
            Utilidades.mostrarAlertaSinConfirmacion("Información guardada", "Los cambios se guardaron"
            + " correctamente en el sistema", Alert.AlertType.INFORMATION);
            Stage escenarioPrincipal = (Stage) buttonGuardarCambios.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }
    
    private boolean registrarHorariosModificados() throws SQLException{
        boolean horariosModificadosRegistrados = false;
        HorarioDAO horarioDao = new HorarioDAO();
        ArrayList <DIGHorarioTabla> horariosModificados = new ArrayList<DIGHorarioTabla>();
        horariosModificados.addAll(tableViewHorarios.getItems());
        for(DIGHorarioTabla horarioModificado:horariosModificados){
                Horario horarioARegistrar = new Horario();
                horarioARegistrar.setEstudiante(horarioModificado.getEstudiante());
                horarioARegistrar.setHoraTutoria(java.sql.Time.valueOf
                (Integer.toString((int) horarioModificado.getSpinnerHoras().getValue()) + ":" 
                + Integer.toString((int) horarioModificado.getSpinnerMinutos().getValue()) + ":" + "0"));
                horarioARegistrar.setIdHorario(horarioModificado.getIdHorario());
                
                horariosModificadosRegistrados = 
                horarioDao.modificarHorarioDeTutoriaPoIdHorario(horarioARegistrar);
            }
        
        return horariosModificadosRegistrados;
    }
}
