package logicaNegocio;

import logicaNegocio.SolucionDAO;
import dominio.Problematica;
import dominio.Solucion;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SolucionDAOTest {
    
    public SolucionDAOTest() {
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
    public void testRegistrarSolucionaProblematicaExito() throws Exception {
        System.out.println("registrarSolucionaProblematicaExito");
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Problematica problematicaSolucion = new Problematica();
        SolucionDAO instancia = new SolucionDAO();
        problematicaSolucion.setIdProblematica(1);
        problematicaSolucion.getSolucion().setDescripcion("Test Descripcion");
        problematicaSolucion.getSolucion().setFecha(
                java.sql.Date.valueOf(formatoFecha.format(LocalDateTime.now())));
        problematicaSolucion.getSolucion().setIdSolucion(10);
        problematicaSolucion.getSolucion().getTutor().setIdAcademico(3);
        boolean resultadoEsperado = true;
        boolean result = instancia.registrarSolucionaProblematica(problematicaSolucion);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void testObtenerSolucionaProblematicaPorIdProblematicaRelacionAcademicoCorrecto() throws Exception {
        System.out.println("obtenerSolucionaProblematicaPorIdProblematicaRelacionAcademicoCorrecto");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int idProblematica = 1;
        SolucionDAO instancia = new SolucionDAO();
        Solucion resultadoEsperado = new Solucion();
        resultadoEsperado.setIdSolucion(1);
        resultadoEsperado.setFecha(sdf.parse("2022-06-07"));
        resultadoEsperado.getTutor().setIdAcademico(3);
        resultadoEsperado.setDescripcion("Se le ha llamado la atención a los compañeros de la alumna, y prometieron que "
                + "la dejarán programar más, y que le explicarán mejor como trabajar en el proyecto.");
        Solucion result = instancia.obtenerSolucionaProblematicaPorIdProblematicaRelacionAcademico(idProblematica);
        assertEquals(resultadoEsperado, result);
    }
    
    @Test
    public void testObtenerSolucionaProblematicaPorIdProblematicaRelacionAcademicoIncorrecto() 
    throws Exception {
        System.out.println("obtenerSolucionaProblematicaPorIdProblematicaRelacionAcademicoIncorrecto");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int idProblematica = 1;
        SolucionDAO instancia = new SolucionDAO();
        Solucion resultadoEsperado = new Solucion();
        resultadoEsperado.setIdSolucion(1);
        resultadoEsperado.setFecha(simpleDateFormat.parse("2022-06-07"));
        resultadoEsperado.getTutor().setIdAcademico(3);
        resultadoEsperado.setDescripcion("Explicarán mejor como trabajar en el proyecto.");
        Solucion resultadoObtenido = instancia.obtenerSolucionaProblematicaPorIdProblematicaRelacionAcademico(idProblematica);
        assertNotEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testVerificarSolucionaProblematicaPorIdProblematicaExito() throws Exception {
        System.out.println("verificarSolucionaProblematicaPorIdProblematicaExito");
        int idProblematica = 1;
        SolucionDAO instancia = new SolucionDAO();
        boolean resultadoEsperado = true;
        boolean result = instancia.verificarSolucionaProblematicaPorIdProblematica(idProblematica);
        assertEquals(resultadoEsperado, result);
    }
    
    @Test
    public void testVerificarSolucionaProblematicaPorIdProblematicaFallo() throws Exception {
        System.out.println("verificarSolucionaProblematicaPorIdProblematicaFallo");
        int idProblematica = 0;
        SolucionDAO instancia = new SolucionDAO();
        boolean resultadoEsperado = false;
        boolean result = instancia.verificarSolucionaProblematicaPorIdProblematica(idProblematica);
        assertEquals(resultadoEsperado, result);
    }

    //@Test
    public void testModificarSolucionProblematicaExito() throws Exception {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("modificarSolucionProblematicaExito");
        Solucion solucion = new Solucion();
        solucion.setDescripcion("Test2 Modificacion Solucion");
        solucion.getTutor().setIdAcademico(1);
        solucion.setFecha(java.sql.Date.valueOf(formatoFecha.format(LocalDateTime.now())));
        solucion.setIdSolucion(4);
        SolucionDAO instancia = new SolucionDAO();
        boolean resultadoEsperado = true;
        boolean result = instancia.modificarSolucionProblematica(solucion);
        assertEquals(resultadoEsperado, result);
    }
    
    @Test
    public void testModificarSolucionProblematicaFallo() throws Exception {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("modificarSolucionProblematicaFallo");
        Solucion solucion = new Solucion();
        solucion.setDescripcion("Test2 Modificacion Solucion");
        solucion.getTutor().setIdAcademico(10);
        solucion.setFecha(java.sql.Date.valueOf(formatoFecha.format(LocalDateTime.now())));
        solucion.setIdSolucion(10);
        SolucionDAO instancia = new SolucionDAO();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.modificarSolucionProblematica(solucion);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
}
