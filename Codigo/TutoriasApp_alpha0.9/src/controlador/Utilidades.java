package controlador;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Utilidades {
    private static Optional<ButtonType> opcion;
    public static void mostrarAlertaSinConfirmacion(String titulo, String mensaje, Alert.AlertType tipoAlerta){
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    public static void mostrarAlertaConfirmacion(String titulo, String mensaje, Alert.AlertType tipoAlerta){
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        opcion = alerta.showAndWait();
    }

    public static Optional<ButtonType> getOpcion() {
        return opcion;
    }
    
    public static void mensajePerdidaDeConexion(){
        Utilidades.mostrarAlertaSinConfirmacion(
            "Pérdida de conexión", 
            "No se pudo conectar con la base de datos. "+
            "Por favor, inténtelo más tarde.", 
            Alert.AlertType.ERROR
        );
    }
    
    public static void mensajeErrorAlCargarLaInformacionDeLaVentana(){
          Utilidades.mostrarAlertaSinConfirmacion("Error de sistema", "Hubo un error "
          + "al cargar la información. Por favor, inténtelo más tarde",Alert.AlertType.ERROR);
    }
}
