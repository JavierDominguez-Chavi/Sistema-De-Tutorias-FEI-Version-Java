package controlador;

import DIG.DIGHorarioTabla;
import logicaNegocio.HorarioDAO;
import dominio.Horario;
import dominio.TutoriaAcademica;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HorarioDeLaSesionDeTutoriaFXMLController implements Initializable {
    @FXML
    private Label labelPeriodoEscolar;
    @FXML
    private Label labelFechaTutoria;
    @FXML
    private TableView <DIGHorarioTabla> tableViewHorarios;
    @FXML
    private TableColumn <DIGHorarioTabla, String> tableColumnMatricula;
    @FXML
    private TableColumn <DIGHorarioTabla, String> tableColumnNombreEstudiante;
    @FXML
    private TableColumn  <DIGHorarioTabla, String> tableColumnHora;
    @FXML 
    private TableColumn <DIGHorarioTabla, String> tableColumnMinutos;
    @FXML
    private Button buttonModificarHorario;
    @FXML
    private Button buttonSalir;
    
    private TutoriaAcademica tutoriaAcademica;
    private ArrayList<DIGHorarioTabla> horariosDeSesionDeTutoria;
    
    public void setTutoriaAcademicaSeleccionada(TutoriaAcademica tutoriaAcademicaSeleccionada){
        tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica = tutoriaAcademicaSeleccionada;
        cargarCamposGUIHorarioDeLaSesionDeTutoria();
    }
    
    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        
    }    
    
    private void cargarCamposGUIHorarioDeLaSesionDeTutoria(){
        labelPeriodoEscolar.setText(tutoriaAcademica.getFechaTutoria().getPeriodo().getFechaInicio()
        + " - " + tutoriaAcademica.getFechaTutoria().getPeriodo().getFechaFin());
        labelFechaTutoria.setText(tutoriaAcademica.getFechaTutoria().getFechaTutoria().toString());
        try {
            cargarInformacionTablaHorarios();
        } catch(SQLException sqlException){
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private void cargarInformacionTablaHorarios() throws SQLException{
        ObservableList<DIGHorarioTabla> listaHorariosDeSesionDeTutoria;
        listaHorariosDeSesionDeTutoria = 
        FXCollections.observableArrayList(obtenerHorariosDeSesionDeTutoria());
        tableViewHorarios.setItems(listaHorariosDeSesionDeTutoria);
        inicializarColumnasTablaHorarios();
    }
    
    private ArrayList<DIGHorarioTabla> obtenerHorariosDeSesionDeTutoria() throws SQLException{
        horariosDeSesionDeTutoria = new ArrayList<DIGHorarioTabla>();
        ArrayList<Horario> horariosObtenidos = new ArrayList<Horario>();
        HorarioDAO horarioDao = new HorarioDAO();
        Horario horarioABuscar = new Horario();
        horarioABuscar.setTutoriaAcademica(tutoriaAcademica);
        horariosObtenidos = horarioDao.obtenerHorariosDeLosEstudiantesPorIdTutoria(horarioABuscar);      
        String horaObtenida;
        String[] horaYMinutosObtenidos; 
        String valorHoraObtenida;  
        String valorMinutosObtenidos; 
        for (Horario horarioObtenido : horariosObtenidos){
            DIGHorarioTabla horarioEncontrado = new DIGHorarioTabla();
            horarioEncontrado.setEstudiante(horarioObtenido.getEstudiante());
            horaObtenida = horarioObtenido.getHoraTutoria().toString();
            horaYMinutosObtenidos = horaObtenida.split(":");
            valorHoraObtenida = horaYMinutosObtenidos[0]; 
            valorMinutosObtenidos = horaYMinutosObtenidos[1];
            horarioEncontrado.setHora(valorHoraObtenida);
            horarioEncontrado.setMinutos(valorMinutosObtenidos);
            horarioEncontrado.setIdHorario(horarioObtenido.getIdHorario());
            horariosDeSesionDeTutoria.add(horarioEncontrado);
        }
        return horariosDeSesionDeTutoria;
    }
    
    private void inicializarColumnasTablaHorarios(){
        tableColumnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tableColumnNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        tableColumnHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tableColumnMinutos.setCellValueFactory(new PropertyValueFactory<>("minutos"));
    }
    
    @FXML
    private void cerrarConsultaHorarioDeLaSesionDeTutoria(){
        Stage escenarioPrincipal = (Stage) buttonSalir.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    @FXML
    private void abrirVentanaModificarHorarioDeSesionDeTutoria(){
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource
           ("/vista/ModificarHorarioDeSesionDeTutoriaFXML.fxml"));
            Parent raiz = cargador.load();
            ModificarHorarioDeSesionDeTutoriaFXMLController controladorGUIModificarHorarioDeSesionDeTutoria = 
            cargador.getController();
            controladorGUIModificarHorarioDeSesionDeTutoria.setTutoriaAcademicaYHorariosDeSesion
            (tutoriaAcademica, horariosDeSesionDeTutoria);
            Scene escenaFormulario = new Scene(raiz);
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setResizable(false);
            escenarioFormulario.setScene(escenaFormulario);
            escenarioFormulario.setTitle("Modificar horario de sesión de tutoría");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
            Stage escenarioPrincipal = (Stage) buttonModificarHorario.getScene().getWindow();
            escenarioPrincipal.close();
        } catch(IOException ioException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        } catch(IllegalStateException isException){
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
}
