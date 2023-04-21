package logicaNegocio;

import logicaNegocio.ExperienciaEducativaDAO;
import dominio.ExperienciaEducativa;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class ExperienciaEducativaDAOTest {
    
    public ExperienciaEducativaDAOTest() {
    }

    @Test
    public void testObtenerExperienciaEducativa()  {
        try {
            System.out.println("obtenerExperienciaEducativaExito");
            int idExperienciaEducativa = 1;
            ExperienciaEducativaDAO instancia = new ExperienciaEducativaDAO();
            ExperienciaEducativa resultadoEsperado = new ExperienciaEducativa();
            resultadoEsperado.setIdExperienciaEducativa(idExperienciaEducativa);
            resultadoEsperado.setNombre("Programacion");
            ExperienciaEducativa resultadoObtenido = instancia.obtenerExperienciaEducativa(idExperienciaEducativa);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @Test
    public void testObtenerExperienciaEducativaFallo() {
        try {
            System.out.println("obtenerExperienciaEducativaFallo");
            int idExperienciaEducativa = 0;
            ExperienciaEducativaDAO instancia = new ExperienciaEducativaDAO();
            ExperienciaEducativa resultadoEsperado = new ExperienciaEducativa();
            ExperienciaEducativa resultadoObtenido = instancia.obtenerExperienciaEducativa(idExperienciaEducativa);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
}
