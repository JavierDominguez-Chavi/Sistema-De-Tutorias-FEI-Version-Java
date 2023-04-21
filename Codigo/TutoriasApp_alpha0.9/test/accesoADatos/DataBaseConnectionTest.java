package accesoADatos;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataBaseConnectionTest {
    private final DataBaseConnection db;
    public DataBaseConnectionTest() {
        db = new DataBaseConnection();
    }

    @Test
    public void testGetConexionExito() throws SQLException {
        System.out.println("testGetConexionExito");
        Connection instancia = db.getConexion();
        assertNotNull(instancia);
    }

    @Test
    public void testDesconectarExito() throws SQLException {
        System.out.println("testDesconectarExito");
        Connection instancia = db.getConexion();
        db.desconectar();
        assertTrue(instancia.isClosed());
    }
}
