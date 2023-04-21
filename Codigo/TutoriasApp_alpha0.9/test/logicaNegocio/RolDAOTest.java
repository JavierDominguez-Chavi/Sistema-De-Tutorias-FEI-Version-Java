package logicaNegocio;

import logicaNegocio.RolDAO;
import dominio.Rol;
import dominio.constantes.Roles;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

public class RolDAOTest {
    
    public RolDAOTest() {
    }

    @Test
    public void testObtenerRolPorIdRol() {
        try {
            System.out.println("obtenerRolPorIdRolExito");
            int idRol = Roles.PROFESOR.getIdRol();
            RolDAO instancia = new RolDAO();
            Rol resultadoEsperado = new Rol();
            resultadoEsperado.setIdRol(idRol);
            resultadoEsperado.setNombreRol(Roles.PROFESOR.getNombreRol());
            Rol resultadoObtenido = instancia.obtenerRolPorIdRol(idRol);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerRolPorIdRolFallo() {
        try {
            System.out.println("obtenerRolPorIdRolFallo");
            int idRol = 99;
            RolDAO instancia = new RolDAO();
            Rol resultadoEsperado = new Rol();
            Rol resultadoObtenido = instancia.obtenerRolPorIdRol(idRol);
            assertNull(resultadoEsperado.getNombreRol(), resultadoObtenido.getNombreRol());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
