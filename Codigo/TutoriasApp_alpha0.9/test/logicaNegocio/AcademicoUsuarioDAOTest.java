package logicaNegocio;

import logicaNegocio.AcademicoUsuarioDAO;
import dominio.AcademicoUsuario;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;


public class AcademicoUsuarioDAOTest {
    
    public AcademicoUsuarioDAOTest() {
    }

    @Test
    public void testObtenerMaximoIdUsuarioInsertado() {
        try {
            System.out.println("obtenerMaximoIdUsuarioInsertadoExito");
            AcademicoUsuarioDAO instancia = new AcademicoUsuarioDAO();
            int resultadoEsperado = 24;
            int resultadoObtenido = instancia.obtenerMaximoIdUsuarioInsertado();
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerMaximoIdUsuarioInsertadoFallo() {
        try {
            System.out.println("obtenerMaximoIdUsuarioInsertadoFallo");
            AcademicoUsuarioDAO instancia = new AcademicoUsuarioDAO();
            int resultadoEsperado = 0;
            int resultadoObtenido = instancia.obtenerMaximoIdUsuarioInsertado();
            assertNotEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    

    @Test
    public void testObtenerIdAcademicoUsuarioPorNombreUsuarioyContraseniaExito(){
        System.out.println("obtenerIdAcademicoUsuarioPorNombreUsuarioyContraseniaExito");
        AcademicoUsuario academicoUsuarioBuscar = new AcademicoUsuario();
        AcademicoUsuarioDAO academicoUsuarioDao = new AcademicoUsuarioDAO();
        AcademicoUsuario academicoUsuarioEsperado = new AcademicoUsuario();
        academicoUsuarioEsperado.setIdUsuario(1);
        academicoUsuarioEsperado.setNombreUsuario("lizga");
        academicoUsuarioBuscar.setNombreUsuario("lizga");
        academicoUsuarioBuscar.setContrasenia("0123456789");
        try{
            AcademicoUsuario academicoUsuarioObtenido = 
            academicoUsuarioDao.obtenerIdAcademicoUsuarioPorNombreUsuarioyContrasenia(academicoUsuarioBuscar);
            assertEquals(academicoUsuarioEsperado, academicoUsuarioObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerIdAcademicoUsuarioPorNombreUsuarioyContraseniaFallo_NombreUsuarioInexistente(){
        System.out.println("obtenerIdAcademicoUsuarioPorNombreUsuarioyContraseniaFallo_NombreUsuarioInexistente");
        AcademicoUsuario academicoUsuarioBuscar = new AcademicoUsuario();
        AcademicoUsuarioDAO academicoUsuarioDao = new AcademicoUsuarioDAO();
        AcademicoUsuario academicoUsuarioEsperado = new AcademicoUsuario();
        academicoUsuarioBuscar.setNombreUsuario("lis");
        academicoUsuarioBuscar.setContrasenia("0123456789");
        academicoUsuarioEsperado.setNombreUsuario("lis");
        academicoUsuarioEsperado.setIdUsuario(0);
        try{
            AcademicoUsuario academicoUsuarioObtenido = 
            academicoUsuarioDao.obtenerIdAcademicoUsuarioPorNombreUsuarioyContrasenia(academicoUsuarioBuscar);
            assertEquals(academicoUsuarioEsperado, academicoUsuarioObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerIdAcademicoUsuarioPorNombreUsuarioyContraseniaFallo_ContraseniaInexistente(){
        System.out.println("obtenerIdAcademicoUsuarioPorNombreUsuarioyContraseniaFallo_ContraseniaInexistente");
        AcademicoUsuario academicoUsuarioBuscar = new AcademicoUsuario();
        AcademicoUsuarioDAO academicoUsuarioDao = new AcademicoUsuarioDAO();
        AcademicoUsuario academicoUsuarioEsperado = new AcademicoUsuario();
        academicoUsuarioBuscar.setNombreUsuario("lizga");
        academicoUsuarioBuscar.setContrasenia("0123456789675");
        academicoUsuarioEsperado.setNombreUsuario("lizga");
        academicoUsuarioEsperado.setIdUsuario(0);
        try{
            AcademicoUsuario academicoUsuarioObtenido = 
            academicoUsuarioDao.obtenerIdAcademicoUsuarioPorNombreUsuarioyContrasenia(academicoUsuarioBuscar);
            assertEquals(academicoUsuarioEsperado, academicoUsuarioObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //@Test
    public void testActualizarAcademicoUsuarioExistenteExito(){
        System.out.println("actualizarAcademicoUsuarioExistenteExito");
        AcademicoUsuario academicoUsuarioActualizar = new AcademicoUsuario();
        AcademicoUsuarioDAO academicoUsuarioDao = new AcademicoUsuarioDAO();
        academicoUsuarioActualizar.setNombreUsuario("lizga");
        academicoUsuarioActualizar.setContrasenia("0123456789");
        academicoUsuarioActualizar.setIdUsuario(1);
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = 
            academicoUsuarioDao.actualizarAcademicoUsuarioExistente(academicoUsuarioActualizar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testActualizarAcademicoUsuarioExistenteFallo_IdUsuarioInexistente(){
        System.out.println("actualizarAcademicoUsuarioExistenteFallo_IdUsuarioInexistente");
        AcademicoUsuario academicoUsuarioActualizar = new AcademicoUsuario();
        AcademicoUsuarioDAO academicoUsuarioDao = new AcademicoUsuarioDAO();
        academicoUsuarioActualizar.setNombreUsuario("lizga");
        academicoUsuarioActualizar.setContrasenia("0123456789");
        academicoUsuarioActualizar.setIdUsuario(144);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = 
            academicoUsuarioDao.actualizarAcademicoUsuarioExistente(academicoUsuarioActualizar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerMaximoIdUsuarioInsertadoExito() {
        System.out.println("obtenerMaximoIdUsuarioInsertadoExito");
        AcademicoUsuarioDAO academicoUsuarioDao = new AcademicoUsuarioDAO();
        int maximoIdInsertadoEsperado = 24;
        try {
            int maximoIdInsertadoObtenido = academicoUsuarioDao.obtenerMaximoIdUsuarioInsertado();
            assertEquals(maximoIdInsertadoEsperado, maximoIdInsertadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //@Test
    public void testRegistrarAcademicoUsuario(){
        System.out.println("registrarAcademicoUsuarioExito");
        AcademicoUsuario academicoUsuarioRegistrar = new AcademicoUsuario();
        AcademicoUsuarioDAO academicoUsuarioDao = new AcademicoUsuarioDAO();
        academicoUsuarioRegistrar.setNombreUsuario("RomanUV");
        academicoUsuarioRegistrar.setContrasenia("Romi5");
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = academicoUsuarioDao.registrarAcademicoUsuario(academicoUsuarioRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
