package aplicacionTutorias;

import controlador.Utilidades;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
public class clasePrincipal extends Application {

    public void start(Stage primaryStage) {
        try {
            cargarInicioDeSesion(new Stage());
        } catch (IOException ioException) {
            Utilidades.mensajeErrorAlCargarLaInformacionDeLaVentana();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private void cargarInicioDeSesion(Stage primaryStage) throws IOException{
        Parent raiz = FXMLLoader.load(getClass().getResource(
            "/vista/InicioDeSesionFXML.fxml"));
        Scene escena = new Scene(raiz, 720, 480);
        primaryStage.getIcons().add(new Image("/imagenes/MiniaturaSistema.png"));

        primaryStage.setTitle("Inicio de sesión");
        primaryStage.setResizable(false);
        primaryStage.setScene(escena);
        primaryStage.show();
    }    
}


