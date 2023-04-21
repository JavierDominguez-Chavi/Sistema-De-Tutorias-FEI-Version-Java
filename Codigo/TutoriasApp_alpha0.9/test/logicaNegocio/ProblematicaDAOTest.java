package logicaNegocio;

import logicaNegocio.ProblematicaDAO;
import dominio.AcademicoRol;
import dominio.Problematica;
import dominio.TutoriaAcademica;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProblematicaDAOTest {
    
    public ProblematicaDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    //@Test
    public void testRegistrarProblematica() throws Exception {
        System.out.println("registrarProblematicaExito");
        Problematica problematica = new Problematica();
        problematica.setTitulo("TituloTest");
        problematica.setDescripcion("DescripcionTest");
        problematica.getCurso().setNRC(12345);
        problematica.getEstudiante().setMatricula("S20015736");
        problematica.getTutoriaAcademica().setIdTutoriaAcademica(4);
        ProblematicaDAO instancia = new ProblematicaDAO();
        boolean resultadoEsperado = true;
        boolean result = instancia.registrarProblematica(problematica);
        assertEquals(resultadoEsperado, result);
    }

    //@Test
    public void testEliminarProblematicaExito() throws Exception { 
        System.out.println("eliminarProblematicaExito");
        int idProblematica = 5;
        ProblematicaDAO instancia = new ProblematicaDAO();
        boolean resultadoEsperado = true;
        boolean result = instancia.eliminarProblematica(idProblematica);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void testObtenerProblematicaPorIdExito() throws Exception {
        System.out.println("obtenerProblematicaPorIdExito");
        int idProblematica = 1;
        ProblematicaDAO instancia = new ProblematicaDAO();
        Problematica resultadoEsperado = new Problematica();
        resultadoEsperado.setTitulo("No me dejan programar");
        resultadoEsperado.setDescripcion("Mis compañeros no saben programar y "
                + "aparte no me dejan programar. "
                + "Hicieron todo mal, y me explicaron peor.");
        Problematica result = instancia.obtenerProblematicaPorId(idProblematica);
        assertEquals(resultadoEsperado, result);
    }
    
    @Test
    public void testObtenerProblematicaPorIdFallido() throws Exception {
        System.out.println("obtenerProblematicaPorIdFallido");
        int idProblematica = 1;
        ProblematicaDAO instancia = new ProblematicaDAO();
        Problematica resultadoEsperado = new Problematica();
        resultadoEsperado.setTitulo("No");
        resultadoEsperado.setDescripcion("Mis compañeros no saben programar y "
                + "aparte no me dejan programar. "
                + "Hicieron todo mal, y me explicaron peor.");
        Problematica result = instancia.obtenerProblematicaPorId(idProblematica);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void testObtenerFechaExperienciaEducativaProblematicaPorProgramaEducativoExitoso() throws Exception {
        System.out.println("obtenerFechaExperienciaEducativaProblematicaPorProgramaEducativoExitoso");
        AcademicoRol academicoRol = new AcademicoRol();
        academicoRol.getProgramaEducativo().setIdProgramaEducativo(0);
        ProblematicaDAO instancia = new ProblematicaDAO();
        List<Problematica> result = instancia.obtenerFechaExperienciaEducativaProblematicaPorProgramaEducativo(academicoRol);
        assertEquals(3, result.size());
    }
    
    @Test
    public void testObtenerFechaExperienciaEducativaProblematicaPorProgramaEducativoFallido() throws Exception {
        System.out.println("obtenerFechaExperienciaEducativaProblematicaPorProgramaEducativoFallido");
        AcademicoRol academicoRol = new AcademicoRol();
        academicoRol.getProgramaEducativo().setIdProgramaEducativo(200);
        ProblematicaDAO instancia = new ProblematicaDAO();
        List<Problematica> result = instancia.obtenerFechaExperienciaEducativaProblematicaPorProgramaEducativo(academicoRol);
        assertEquals(0, result.size());
    }

    @Test
    public void testObtenerFechaExperienciaEducativaProblematicaPorRolAcademicoExitoso() throws Exception {
        System.out.println("obtenerFechaExperienciaEducativaProblematicaPorRolAcademicoExito");
        AcademicoRol academicoRol = new AcademicoRol();
        academicoRol.getAcademico().setIdAcademico(3);
        academicoRol.getProgramaEducativo().setIdProgramaEducativo(0);
        ProblematicaDAO instancia = new ProblematicaDAO();
        List<Problematica> result = instancia.obtenerFechaExperienciaEducativaProblematicaPorRolAcademico(academicoRol);
        assertEquals(1, result.size());
    }
    @Test
    public void testObtenerFechaExperienciaEducativaProblematicaPorRolAcademicoFallido() throws Exception {
        System.out.println("obtenerFechaExperienciaEducativaProblematicaPorRolAcademicoFallido");
        AcademicoRol academicoRol = new AcademicoRol();
        academicoRol.getAcademico().setIdAcademico(10);
        academicoRol.getProgramaEducativo().setIdProgramaEducativo(0);
        ProblematicaDAO instancia = new ProblematicaDAO();
        List<Problematica> result = instancia.obtenerFechaExperienciaEducativaProblematicaPorRolAcademico(academicoRol);
        assertEquals(0, result.size());
    }

    //@Test
    public void testModificarProblematicaExito() throws Exception {
        System.out.println("modificarProblematicaExitoso");
        Problematica problematica = new Problematica();
        problematica.setIdProblematica(6);
        problematica.setTitulo("TituloTestModificacion");
        problematica.setDescripcion("DescripcionTest");
        problematica.getCurso().setNRC(12345);
        problematica.getEstudiante().setMatricula("S20015736");
        problematica.getTutoriaAcademica().setIdTutoriaAcademica(4);
        ProblematicaDAO instancia = new ProblematicaDAO();
        boolean resultadoEsperado = true;
        boolean result = instancia.modificarProblematica(problematica);
        assertEquals(resultadoEsperado, result);
    }
    
    @Test
    public void testModificarProblematicaFallido() throws Exception {
        System.out.println("modificarProblematicaFallido");
        Problematica problematica = new Problematica();
        problematica.setIdProblematica(100000);
        problematica.setTitulo("TituloTestModificacion");
        problematica.setDescripcion("DescripcionTest");
        problematica.getCurso().setNRC(12345);
        problematica.getEstudiante().setMatricula("S20015736");
        problematica.getTutoriaAcademica().setIdTutoriaAcademica(4);
        ProblematicaDAO instancia = new ProblematicaDAO();
        boolean resultadoEsperado = false;
        boolean result = instancia.modificarProblematica(problematica);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void testObtenerListadoDeProblematicasPorIdTutoriaAcademicaExitoso() throws Exception {
        System.out.println("obtenerListadoDeProblematicasPorIdTutoriaAcademicaExitoso");
        TutoriaAcademica tutoria = new TutoriaAcademica();
        tutoria.setIdTutoriaAcademica(4);
        ProblematicaDAO instancia = new ProblematicaDAO();
        ArrayList<Problematica> result = instancia.obtenerListadoDeProblematicasPorIdTutoriaAcademica(tutoria);
        assertEquals(1, result.size());
    }
    
    @Test
    public void testObtenerListadoDeProblematicasPorIdTutoriaAcademicaFallido() throws Exception {
        System.out.println("obtenerListadoDeProblematicasPorIdTutoriaAcademicaFallido");
        TutoriaAcademica tutoria = new TutoriaAcademica();
        tutoria.setIdTutoriaAcademica(200);
        ProblematicaDAO instancia = new ProblematicaDAO();
        ArrayList<Problematica> result = instancia.obtenerListadoDeProblematicasPorIdTutoriaAcademica(tutoria);
        assertEquals(0, result.size());
    }
    
}
