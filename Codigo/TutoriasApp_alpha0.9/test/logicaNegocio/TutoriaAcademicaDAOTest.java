package logicaNegocio;

import logicaNegocio.TutoriaAcademicaDAO;
import dominio.Academico;
import dominio.FechaTutoria;
import dominio.TutoriaAcademica;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class TutoriaAcademicaDAOTest {
    
    public TutoriaAcademicaDAOTest() {
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

    @Test
    public void testObtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaExito(){
        System.out.println("obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaExito");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        Academico academicoTutor = new Academico();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        academicoTutor.setIdAcademico(5);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademicaEsperada = new TutoriaAcademica();
        tutoriaAcademicaEsperada.setIdTutoriaAcademica(2);
        try{
            TutoriaAcademica tutoriaAcademicaObtenida = 
            tutoriaAcademicaDao.obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademicaBuscar);
            assertEquals(tutoriaAcademicaEsperada, tutoriaAcademicaObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaFallo_IdAcademicoInexistente(){
        System.out.println("obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaFallo__IdAcademicoInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        Academico academicoTutor = new Academico();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        academicoTutor.setIdAcademico(11);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademicaEsperada = new TutoriaAcademica();
        tutoriaAcademicaEsperada.setIdTutoriaAcademica(0);
        try{
            TutoriaAcademica tutoriaAcademicaObtenida = 
            tutoriaAcademicaDao.obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademicaBuscar);
            assertEquals(tutoriaAcademicaEsperada, tutoriaAcademicaObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaFallo_IdFechaTutoriaInexistente(){
        System.out.println("obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaFallo__IdFechaTutoriaInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        Academico academicoTutor = new Academico();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        academicoTutor.setIdAcademico(5);
        fechaDeTutoria.setIdFechaTutoria(35);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademicaEsperada = new TutoriaAcademica();
        tutoriaAcademicaEsperada.setIdTutoriaAcademica(0);
        try{
            TutoriaAcademica tutoriaAcademicaObtenida = 
            tutoriaAcademicaDao.obtenerIdDeTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademicaBuscar);
            assertEquals(tutoriaAcademicaEsperada, tutoriaAcademicaObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testValidarEntregaDeReportesDeTutoriaExito(){
        System.out.println("validarEntregaDeReportesDeTutoriaExito");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setReporteEntregado(true);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.validarEntregaDeReportesDeTutoria(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testValidarEntregaDeReportesDeTutoriaFallo(){
        System.out.println("validarEntregaDeReportesDeTutoriaFallo");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        fechaDeTutoria.setIdFechaTutoria(14);
        tutoriaAcademicaBuscar.setReporteEntregado(true);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.validarEntregaDeReportesDeTutoria(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregadoExito(){
        System.out.println("obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregadoExito");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setReporteEntregado(true);
        int numeroDeTutoriasEsperado = 5;
        try{
            ArrayList<TutoriaAcademica> tutoriasAcademicasObtenidas =
            tutoriaAcademicaDao.obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregado(tutoriaAcademicaBuscar);
            assertEquals(numeroDeTutoriasEsperado, tutoriasAcademicasObtenidas.size());
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
        @Test
    public void testObtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregadoFallo_IdFechaTutoriaInexistente(){
        System.out.println("obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregadoFallo_"
        + "IdFechaTutoriaInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        fechaDeTutoria.setIdFechaTutoria(14);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setReporteEntregado(true);
        int numeroDeTutoriasEsperado = 0;
        try{
            ArrayList<TutoriaAcademica> tutoriasAcademicasObtenidas =
            tutoriaAcademicaDao.obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregado(tutoriaAcademicaBuscar);
            assertEquals(numeroDeTutoriasEsperado, tutoriasAcademicasObtenidas.size());
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregadoFallo_ReporteNoEntregado(){
        System.out.println("obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregadoFallo_"
        + "ReporteNoEntregado");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setReporteEntregado(false);
        int numeroDeTutoriasEsperado = 0;
        try{
            ArrayList<TutoriaAcademica> tutoriasAcademicasObtenidas =
            tutoriaAcademicaDao.obtenerTutoriasAcademicasPorIdFechaTutoriaYReporteEntregado(tutoriaAcademicaBuscar);
            assertEquals(numeroDeTutoriasEsperado, tutoriasAcademicasObtenidas.size());
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarReporteDeTutoriaAcademicaEntregadoExito(){
        System.out.println("buscarReporteDeTutoriaAcademicaEntregadoExito");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(5);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setReporteEntregado(true);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.buscarReporteDeTutoriaAcademicaEntregado(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarReporteDeTutoriaAcademicaEntregadoFallo_ReporteNoEntregado(){
        System.out.println("buscarReporteDeTutoriaAcademicaEntregado_ReporteNoEntregado");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(5);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setReporteEntregado(false);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.buscarReporteDeTutoriaAcademicaEntregado(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarReporteDeTutoriaAcademicaEntregadoFallo_IdAcademicoInexistente(){
        System.out.println("buscarReporteDeTutoriaAcademicaEntregado_IdAcademicoInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(88);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setReporteEntregado(true);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.buscarReporteDeTutoriaAcademicaEntregado(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarReporteDeTutoriaAcademicaEntregadoFallo_IdFechaTutoriaInexistente(){
        System.out.println("buscarReporteDeTutoriaAcademicaEntregado_IdFechaTutoriaInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(3);
        fechaDeTutoria.setIdFechaTutoria(22);
        tutoriaAcademicaBuscar.setReporteEntregado(true);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.buscarReporteDeTutoriaAcademicaEntregado(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaExito(){
        System.out.println("obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaExito");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademicaEsperada = new TutoriaAcademica();
        tutoriaAcademicaEsperada.setIdTutoriaAcademica(4);
        tutoriaAcademicaEsperada.setComentarioGeneral("Todos los tutorados tienen depresión y ansiedad, "
        + "producto del confinamiento. ");
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(1);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        try{
            TutoriaAcademica tutoriaAcademicaObtenida = 
            tutoriaAcademicaDao.obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademicaBuscar);
            assertEquals(tutoriaAcademicaEsperada, tutoriaAcademicaObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaFallo_IdAcademicoInexistente(){
        System.out.println("obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaFallo_IdAcademicoInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademicaEsperada = new TutoriaAcademica();
        tutoriaAcademicaEsperada.setIdTutoriaAcademica(0);
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(99);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        try{
            TutoriaAcademica tutoriaAcademicaObtenida = 
            tutoriaAcademicaDao.obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademicaBuscar);
            assertEquals(tutoriaAcademicaEsperada, tutoriaAcademicaObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaFallo_IdFechaTutoriaInexistente(){
        System.out.println("obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoriaFallo_IdFechaTutoriaInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        TutoriaAcademica tutoriaAcademicaEsperada = new TutoriaAcademica();
        tutoriaAcademicaEsperada.setIdTutoriaAcademica(0);
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(1);
        fechaDeTutoria.setIdFechaTutoria(99);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        try{
            TutoriaAcademica tutoriaAcademicaObtenida = 
            tutoriaAcademicaDao.obtenerTutoriaAcademicaPorIdAcademicoEIdFechaTutoria(tutoriaAcademicaBuscar);
            assertEquals(tutoriaAcademicaEsperada, tutoriaAcademicaObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testBuscarTutoriaAcademicaExistenteExito(){
        System.out.println("buscarTutoriaAcademicaExistenteExito");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(4);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.buscarTutoriaAcademicaExistente(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarTutoriaAcademicaExistenteFallo_IdAcademicoInexistente(){
        System.out.println("buscarTutoriaAcademicaExistenteFallo_IdAcademicoInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(40);
        fechaDeTutoria.setIdFechaTutoria(15);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.buscarTutoriaAcademicaExistente(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
        @Test
    public void testBuscarTutoriaAcademicaExistenteFallo_IdFechaTutoriaInexistente(){
        System.out.println("buscarTutoriaAcademicaExistenteFallo_IdFechaTutoriaInexistente");
        TutoriaAcademica tutoriaAcademicaBuscar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(4);
        fechaDeTutoria.setIdFechaTutoria(99);
        tutoriaAcademicaBuscar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaBuscar.setTutor(academicoTutor);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.buscarTutoriaAcademicaExistente(tutoriaAcademicaBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    //@Test
    public void testRegistrarTutoriaAcademica(){
        System.out.println("registrarTutoriaAcademica");
        TutoriaAcademica tutoriaAcademicaRegistrar = new TutoriaAcademica();
        FechaTutoria fechaDeTutoria = new FechaTutoria();
        Academico academicoTutor = new Academico();
        academicoTutor.setIdAcademico(4);
        fechaDeTutoria.setIdFechaTutoria(14);
        tutoriaAcademicaRegistrar.setFechaTutoria(fechaDeTutoria);
        tutoriaAcademicaRegistrar.setTutor(academicoTutor);
        tutoriaAcademicaRegistrar.setComentarioGeneral("Sin comentario");
        tutoriaAcademicaRegistrar.setReporteEntregado(false);
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = tutoriaAcademicaDao.registrarTutoriaAcademica(tutoriaAcademicaRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    //@Test
    public void testRegistrarComentarioGeneralYEntregaDeReporteDeLaTutoriaExito(){
        System.out.println("registrarComentarioGeneralYEntregaDeReporteDeLaTutoriaExito");
        TutoriaAcademica tutoriaAcademicaRegistrar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        tutoriaAcademicaRegistrar.setIdTutoriaAcademica(5);
        tutoriaAcademicaRegistrar.setComentarioGeneral("Todo bien");
        tutoriaAcademicaRegistrar.setReporteEntregado(true);
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = 
            tutoriaAcademicaDao.registrarComentarioGeneralYEntregaDeReporteDeLaTutoria(tutoriaAcademicaRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testRegistrarComentarioGeneralYEntregaDeReporteDeLaTutoriaFallo_IdTutoriaAcademicaInexistente(){
        System.out.println("registrarComentarioGeneralYEntregaDeReporteDeLaTutoriaFallo"
        + "_IdTutoriaAcademicaInexistente");
        TutoriaAcademica tutoriaAcademicaRegistrar = new TutoriaAcademica();
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        tutoriaAcademicaRegistrar.setIdTutoriaAcademica(99);
        tutoriaAcademicaRegistrar.setComentarioGeneral("Todo bien");
        tutoriaAcademicaRegistrar.setReporteEntregado(true);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = 
            tutoriaAcademicaDao.registrarComentarioGeneralYEntregaDeReporteDeLaTutoria(tutoriaAcademicaRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
}
