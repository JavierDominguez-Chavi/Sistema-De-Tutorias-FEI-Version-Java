package accesoADatos;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseConnection {
    private Connection conexion;   
    private static Logger logger = LoggerFactory.getLogger(DataBaseConnection.class);
    
    public Connection getConexion() throws SQLException{
        if (conectar()){
            return conexion;
        }
        throw new SQLException();
    }
    
    public boolean conectar() throws SQLException {
        boolean conexionExitosa = false;
        try {
            InputStream archivoConfiguracion = 
                getClass().getResourceAsStream("/accesoADatos/archivoConfiguracionBaseDeDatos.txt");
            validarArchivoConfiguracion(archivoConfiguracion);
            Properties atributos = new Properties();
            atributos.load(archivoConfiguracion);
            archivoConfiguracion.close();
            String direccionBD = atributos.getProperty("DireccionBD");
            String usuario = atributos.getProperty("Usuario");
            String contrasenia = atributos.getProperty("Contrasenia");
            conexion = DriverManager.getConnection(direccionBD, usuario, contrasenia);
            conexionExitosa = true;
        } catch (FileNotFoundException fnfException) {
           logger.error(fnfException.getClass()+": "+fnfException.getMessage());
        } catch (IOException ioException){
           logger.error(ioException.getClass()+": "+ioException.getMessage());
        } catch(CommunicationsException cException){
           logger.error(cException.getClass()+": "+cException.getMessage());
        }
        return conexionExitosa;
    }
    
    public void validarArchivoConfiguracion(InputStream archivoConfiguracion) 
    throws FileNotFoundException{
        if (archivoConfiguracion==null){
            throw new FileNotFoundException(
                "No se encontró el archivo de configuración para conectar con la base de datos."
            );
        }
    }
    
    public void desconectar() throws SQLException{
        if (conexion!=null){
            if (!conexion.isClosed()){
                conexion.close();
            }
        }
    }
}


