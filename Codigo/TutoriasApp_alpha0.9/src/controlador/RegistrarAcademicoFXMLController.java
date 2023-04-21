package controlador;

import logicaNegocio.AcademicoDAO;
import logicaNegocio.AcademicoRolDAO;
import logicaNegocio.AcademicoUsuarioDAO;
import logicaNegocio.ProgramaEducativoDAO;
import dominio.Academico;
import dominio.AcademicoRol;
import dominio.AcademicoUsuario;
import dominio.ProgramaEducativo;
import dominio.Rol;
import dominio.constantes.Roles;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class RegistrarAcademicoFXMLController extends RegistrarPersonaFXMLController  
implements Initializable {

    @FXML
    private TableView<ProgramaEducativo> tableViewProgramasEducativos;
    @FXML
    private TableColumn<ProgramaEducativo, String> tableColumnNombreProgramaEducativo;
    @FXML
    private TableColumn<ProgramaEducativo, Void> tableColumnSeleccionProgramaEducativo;
    
    private HashSet<Integer> idProgramasEducativosSeleccionados;

    @Override
    public void initialize(URL localizadorRecursos, ResourceBundle paqueteRecursos) {
        try {
            super.initialize(localizadorRecursos, paqueteRecursos);
            this.idProgramasEducativosSeleccionados = new HashSet<Integer>();
            inicializarTablaProgramasEducativos();
            inicializarColumnasDeTablaProgramasEducativos();
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
            Platform.runLater(() -> {
                cerrar(true);
            });
        }
    }    

    @FXML
    private void guardar(ActionEvent evento) {
        try {
            validarCamposLlenos();
            super.validarLongitudCampos();
            validarCaracteresEntradas();
            
            Academico nuevoAcademico = crearAcademico();
            
            validarAcademicoPorNombreCompleto(nuevoAcademico);
            validarAcademicoPorCorreoElectronicoPersonal(nuevoAcademico);
            validarAcademicoPorCorreoElectronicoInstitucional(nuevoAcademico);
            
            nuevoAcademico = registrarAcademico(nuevoAcademico);
            registrarAcademicosUsuarios(nuevoAcademico);
            terminar();
        } catch (IllegalArgumentException iaException) {
            Utilidades.mostrarAlertaSinConfirmacion("Datos inválidos", iaException.getMessage(), 
                Alert.AlertType.WARNING);
        } catch (SQLException sqlException) {
            Utilidades.mensajePerdidaDeConexion();
        }
    }
    
    private Academico registrarAcademico(Academico nuevoAcademico) throws SQLException{
        AcademicoDAO academicoDAO = new AcademicoDAO();
        academicoDAO.registrarAcademico(nuevoAcademico);
        int idAcademicoInsertado = academicoDAO.obtenerMaximoIdAcademicoInsertado();
        nuevoAcademico.setIdAcademico(idAcademicoInsertado);
        return nuevoAcademico;
    }
    
    private void inicializarTablaProgramasEducativos() throws SQLException{
        ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
        ObservableList<ProgramaEducativo> programasEducativosObtenidos = FXCollections.observableArrayList(
            programaEducativoDAO.obtenerProgramasEducativos()
        );
        programasEducativosObtenidos.removeIf(
            programaEducativo -> "Sin programa".equals(programaEducativo.getNombre())
        );
        this.tableViewProgramasEducativos.setItems(programasEducativosObtenidos);
    }

    private void registrarAcademicosUsuarios(Academico academico) throws SQLException{
        String nombreAcademico = 
            academico.getNombre()+
            academico.getApellidoPaterno();
        AcademicoUsuarioDAO academicoUsuarioDAO = new AcademicoUsuarioDAO();
        HashMap<Integer,AcademicoUsuario> idProgramaEducativoYAcademicoUsuario = new HashMap<>();
        for (Integer idProgramaEducativo : this.idProgramasEducativosSeleccionados) {
            
            String nombreUsuario = idProgramaEducativo+"_"+nombreAcademico;
            String contrasenia = "0123456789";
            
            AcademicoUsuario nuevoAcademicoUsuario = new AcademicoUsuario();
            nuevoAcademicoUsuario.setNombreUsuario(nombreUsuario);
            nuevoAcademicoUsuario.setContrasenia(contrasenia);
            
            
            academicoUsuarioDAO.registrarAcademicoUsuario(nuevoAcademicoUsuario);
            Integer idUsuarioInsertado = academicoUsuarioDAO.obtenerMaximoIdUsuarioInsertado();
            nuevoAcademicoUsuario.setIdUsuario(idUsuarioInsertado);
            
            idProgramaEducativoYAcademicoUsuario.put(idProgramaEducativo, nuevoAcademicoUsuario);
        }
        registrarAcademicosRoles(academico, idProgramaEducativoYAcademicoUsuario);
    }
    
    private void registrarAcademicosRoles
    (Academico nuevoAcademico, HashMap<Integer,AcademicoUsuario> idProgramaEducativo_AcademicoUsuario)
    throws SQLException{
        Rol sinRol = new Rol();
        final int SIN_ROL = Roles.SIN_ROL.getIdRol();
        sinRol.setIdRol(SIN_ROL);
        AcademicoRolDAO academicoRolDAO = new AcademicoRolDAO();
        for(Map.Entry<Integer,AcademicoUsuario> registro : idProgramaEducativo_AcademicoUsuario.entrySet()) {
          Integer idProgramaEducativoDelRol = registro.getKey();
          AcademicoUsuario usuarioDelRol = registro.getValue();
          
          ProgramaEducativo programaEducativoDelRol = new ProgramaEducativo();
          programaEducativoDelRol.setIdProgramaEducativo(idProgramaEducativoDelRol);
          
          AcademicoRol nuevoAcademicoRol = new AcademicoRol();
          nuevoAcademicoRol.setAcademico(nuevoAcademico);
          nuevoAcademicoRol.setRol(sinRol);
          nuevoAcademicoRol.setProgramaEducativo(programaEducativoDelRol);
          nuevoAcademicoRol.setAcademicoUsuario(usuarioDelRol);
          academicoRolDAO.registrarAcademicoRol(nuevoAcademicoRol);
        }
    }
    
    void terminar(){
        this.inicializarColumnasDeTablaProgramasEducativos();
        this.idProgramasEducativosSeleccionados.clear();
        super.terminar();
    }
    
    private void modificarProgramasEducativosEnHashSet
    (Integer idProgramaEducativoSeleccionado){
        boolean agregarProgramaEducativo = 
            !this.idProgramasEducativosSeleccionados.contains(idProgramaEducativoSeleccionado);
        if (agregarProgramaEducativo==true){
            this.idProgramasEducativosSeleccionados.add(idProgramaEducativoSeleccionado);
        }else{
            this.idProgramasEducativosSeleccionados.remove(idProgramaEducativoSeleccionado);
        }
    }
    
    private Academico crearAcademico(){
        Academico nuevoAcademico = new Academico();
        nuevoAcademico.setNombre(super.getTextFieldNombre().getText());
        nuevoAcademico.setApellidoPaterno(super.getTextFieldApellidoPaterno().getText());
        nuevoAcademico.setApellidoMaterno(super.getTextFieldApellidoMaterno().getText());
        nuevoAcademico.setCorreoElectronicoPersonal(super.getTextFieldEmailPersonal().getText());
        nuevoAcademico.setCorreoElectronicoInstitucional(this.getTextFieldEmailInstitucional().getText());
        return nuevoAcademico;
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
    
    void validarCamposLlenos() throws IllegalArgumentException{
        if (this.idProgramasEducativosSeleccionados.isEmpty()){
            throw new IllegalArgumentException("No puede dejar ningún campo vacío");
        }
        super.validarCamposLlenos();
    }
    
    private void validarCaracteresEntradas() throws IllegalArgumentException{
        super.validarCaracteresNombreCompleto();
        super.validarCaracteresCorreoElectronicoPersonal();
        super.validarCaracteresCorreoElectronicoInstitucional();
    }
    
    private void inicializarColumnasDeTablaProgramasEducativos(){
        tableColumnNombreProgramaEducativo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        Callback<TableColumn<ProgramaEducativo, Void>, TableCell<ProgramaEducativo, Void>> constructorCelda = 
        (final TableColumn<ProgramaEducativo, Void> parametro) -> {
            final TableCell<ProgramaEducativo, Void> celda = new TableCell<ProgramaEducativo, Void>() {
                private final CheckBox checkBoxSeleccionarProgramaEducativo = new CheckBox();
                
                {
                    checkBoxSeleccionarProgramaEducativo.setOnAction((ActionEvent evento) -> {
                        Integer idProgramaEducativoSeleccionado = 
                            getTableView().getItems().get(getIndex()).getIdProgramaEducativo();
                        modificarProgramasEducativosEnHashSet(idProgramaEducativoSeleccionado);
                    });
                }
                @Override
                public void updateItem(Void item, boolean vacio) {
                    super.updateItem(item, vacio);
                    if (vacio) {
                        setGraphic(null);
                    } else {
                        setGraphic(checkBoxSeleccionarProgramaEducativo);
                    }
                }
            };
            return celda;
        };
        tableColumnSeleccionProgramaEducativo.setCellFactory(constructorCelda);
    } 
}
