package logicaNegocio;

import logicaNegocio.ProgramaEducativoDAO;
import dominio.ProgramaEducativo;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProgramaEducativoDAOTest {
    
    public ProgramaEducativoDAOTest() {
    
    } 

    @Test
    public void testObtenerProgramaEducativoPorId() {
        try {
            System.out.println("obtenerProgramaEducativoExito");
            int idProgramaEducativo = 0;
            ProgramaEducativo resultadoEsperado = new ProgramaEducativo();
            resultadoEsperado.setIdProgramaEducativo(idProgramaEducativo);
            resultadoEsperado.setNombre("Ingeniería de Software");
            ProgramaEducativoDAO instancia = new ProgramaEducativoDAO();
            ProgramaEducativo resultadoObtenido = instancia.obtenerProgramaEducativoPorId(idProgramaEducativo);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerProgramaEducativoPorIdFallo() {
        try {
            System.out.println("obtenerProgramaEducativoFallo");
            int idProgramaEducativo = 10;
            ProgramaEducativoDAO instancia = new ProgramaEducativoDAO();
            ProgramaEducativo resultadoEsperado = instancia.obtenerProgramaEducativoPorId(idProgramaEducativo);  
            assertNull(resultadoEsperado);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerProgramasEducativos() {
        try {
            System.out.println("obtenerProgramasEducativosExito");
            ProgramaEducativoDAO instancia = new ProgramaEducativoDAO();
            ProgramaEducativo programaEducativoIngenieria = new ProgramaEducativo();
                programaEducativoIngenieria.setIdProgramaEducativo(0);
                programaEducativoIngenieria.setNombre("Ingeniería de Software");
            ProgramaEducativo programaEducativoRedes = new ProgramaEducativo();
                programaEducativoRedes.setIdProgramaEducativo(2);
                programaEducativoRedes.setNombre("Redes y Servicios de Cómputo");
            ProgramaEducativo programaEducativoTecnologias = new ProgramaEducativo();
                programaEducativoTecnologias.setIdProgramaEducativo(3);
                programaEducativoTecnologias.setNombre("Tecnologías Computacionales");
            ProgramaEducativo programaEducativoEstadistica = new ProgramaEducativo();
                programaEducativoEstadistica.setIdProgramaEducativo(4);
                programaEducativoEstadistica.setNombre("Ciencias y Técnicas Estadísticas");
            ArrayList<ProgramaEducativo> resultadosEsperados = new ArrayList<>();
            resultadosEsperados.add(programaEducativoIngenieria);
            resultadosEsperados.add(programaEducativoRedes);
            resultadosEsperados.add(programaEducativoTecnologias);
            resultadosEsperados.add(programaEducativoEstadistica);
            
            ArrayList<ProgramaEducativo> resultadoObtenidos = instancia.obtenerProgramasEducativos();
            assertEquals(resultadosEsperados, resultadoObtenidos);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerProgramasEducativosFallo() {
        try {
            System.out.println("obtenerProgramasEducativosFallo");
            ProgramaEducativoDAO instancia = new ProgramaEducativoDAO();
            ProgramaEducativo programaEducativoIngenieria = new ProgramaEducativo();
                programaEducativoIngenieria.setIdProgramaEducativo(0);
                programaEducativoIngenieria.setNombre("Ingeniería de Software");
            ProgramaEducativo programaEducativoRedes = new ProgramaEducativo();
                programaEducativoRedes.setIdProgramaEducativo(1);
                programaEducativoRedes.setNombre("Redes y Servicios de Cómputo");
            ProgramaEducativo programaEducativoTecnologias = new ProgramaEducativo();
                programaEducativoTecnologias.setIdProgramaEducativo(3);
                programaEducativoTecnologias.setNombre("Tecnologías Computacionales");
            ProgramaEducativo programaEducativoEstadistica = new ProgramaEducativo();
                programaEducativoEstadistica.setIdProgramaEducativo(4);
                programaEducativoEstadistica.setNombre("Ciencias y Técnicas Estadísticas");
            ArrayList<ProgramaEducativo> resultadosEsperados = new ArrayList<>();
            resultadosEsperados.add(programaEducativoIngenieria);
            resultadosEsperados.add(programaEducativoRedes);
            resultadosEsperados.add(programaEducativoTecnologias);
            resultadosEsperados.add(programaEducativoEstadistica);
            
            ArrayList<ProgramaEducativo> resultadoObtenido = instancia.obtenerProgramasEducativos();
            assertNotEquals(resultadosEsperados, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
