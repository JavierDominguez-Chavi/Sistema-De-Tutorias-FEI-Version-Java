package logicaNegocio;

import logicaNegocio.HorarioDAO;
import dominio.Estudiante;
import dominio.Horario;
import dominio.TutoriaAcademica;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class HorarioDAOTest {
    
    public HorarioDAOTest() {
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
    public void testObtenerHorariosDeLosEstudiantesPorIdTutoriaExito(){
        System.out.println("obtenerHorariosDeLosEstudiantesPorIdTutoriaExito");
        Horario horarioABuscar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        int numeroDeHorarioEsperados = 7;
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setIdTutoriaAcademica(1);
        horarioABuscar.setTutoriaAcademica(tutoriaAcademica);
        try{
            ArrayList<Horario> horariosObtenidos =
            horarioDao.obtenerHorariosDeLosEstudiantesPorIdTutoria(horarioABuscar);
            assertEquals(numeroDeHorarioEsperados, horariosObtenidos.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerHorariosDeLosEstudiantesPorIdTutoriaFallo_IdTutoriaInexistente(){
        System.out.println("obtenerHorariosDeLosEstudiantesPorIdTutoriaFallo_IdTutoriaInexistente");
        Horario horarioABuscar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        int numeroDeHorarioEsperados = 0;
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setIdTutoriaAcademica(78);
        horarioABuscar.setTutoriaAcademica(tutoriaAcademica);
        try{
            ArrayList<Horario> horariosObtenidos =
            horarioDao.obtenerHorariosDeLosEstudiantesPorIdTutoria(horarioABuscar);
            assertEquals(numeroDeHorarioEsperados, horariosObtenidos.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoriaExito(){
        System.out.println("obtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoriaExito");
        Horario horarioBuscar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        int numeroDeEstudiantesEsperados = 3;
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setIdTutoriaAcademica(2);
        horarioBuscar.setTutoriaAcademica(tutoriaAcademica);
        horarioBuscar.setAsistencia(true);
        try{
            ArrayList<Horario> estudiantesObtenidos = 
            horarioDao.obtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoria(horarioBuscar);
            assertEquals(numeroDeEstudiantesEsperados, estudiantesObtenidos.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoriaFallo_IdTutoriaInexistente(){
        System.out.println("obtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoriaFallo_IdTutoriaInexistente");
        Horario horarioBuscar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        int numeroDeEstudiantesEsperado = 0;
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setIdTutoriaAcademica(78);
        horarioBuscar.setTutoriaAcademica(tutoriaAcademica);
        horarioBuscar.setAsistencia(true);
        try{
            ArrayList<Horario> estudiantesObtenidos = 
            horarioDao.obtenerTotalDeEstudiantesAsistentesEnLaSesionDeTutoria(horarioBuscar);
            assertEquals(numeroDeEstudiantesEsperado, estudiantesObtenidos.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoriaExito(){
        System.out.println("obtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoriaExito");
        Horario horarioBuscar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        int numeroDeEstudiantesEsperado = 3;
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setIdTutoriaAcademica(1);
        horarioBuscar.setTutoriaAcademica(tutoriaAcademica);
        horarioBuscar.setRiesgo(true);
        try{
            ArrayList<Horario> estudiantesObtenidos = 
            horarioDao.obtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoria(horarioBuscar);
            assertEquals(numeroDeEstudiantesEsperado, estudiantesObtenidos.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoriaFallo_IdTutoriaInexistente(){
        System.out.println("obtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoriaFallo_IdTutoriaInexistente");
        Horario horarioBuscar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        int numeroDeEstudiantesEsperado = 0;
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        tutoriaAcademica.setIdTutoriaAcademica(76);
        horarioBuscar.setTutoriaAcademica(tutoriaAcademica);
        horarioBuscar.setRiesgo(true);
        try{
            ArrayList<Horario> estudiantesObtenidos = 
            horarioDao.obtenerTotalDeEstudiantesEnRiesgoEnLasSesionesDeTutoria(horarioBuscar);
            assertEquals(numeroDeEstudiantesEsperado, estudiantesObtenidos.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademicaExito(){
        System.out.println("obtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademicaExito");
        int idTutoriaAcademica = 2;
        HorarioDAO horarioDao = new HorarioDAO();
        int numeroDeEstudiantesEsperado = 5;
        try{
            ArrayList<Horario> estudiantesObtenidos = 
            horarioDao.obtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademica(idTutoriaAcademica);
            assertEquals(numeroDeEstudiantesEsperado, estudiantesObtenidos.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademicaFallo_IdTutoriaAcademicaInexistente(){
        System.out.println("obtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademicaFallo"
        + "_IdTutoriaAcademicaInexistente");
        int idTutoriaAcademica = 87;
        HorarioDAO horarioDao = new HorarioDAO();
        int numeroDeEstudiantesEsperado = 0;
        try{
            ArrayList<Horario> estudiantesObtenidos = 
            horarioDao.obtenerEstudiantesAsistentesOEnRiesgoPorIdTutoriaAcademica(idTutoriaAcademica);
            assertEquals(numeroDeEstudiantesEsperado, estudiantesObtenidos.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testBuscarHorarioExistentePorIdTutoriaAcademicaExito(){
        System.out.println("buscarHorarioExistentePorIdTutoriaAcademicaExito");
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        HorarioDAO horarioDao = new HorarioDAO();
        boolean validacionEsperada = true;
        tutoriaAcademica.setIdTutoriaAcademica(4);
        try{
            boolean validacionObtenida = horarioDao.buscarHorarioExistentePorIdTutoriaAcademica(tutoriaAcademica);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarHorarioExistentePorIdTutoriaAcademicaFallo_IdTutoriaAcademicaInexistente(){
        System.out.println("buscarHorarioExistentePorIdTutoriaAcademicaFallo_IdTutoriaAcademicaInexistente");
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        HorarioDAO horarioDao = new HorarioDAO();
        boolean validacionEsperada = false;
        tutoriaAcademica.setIdTutoriaAcademica(144);
        try{
            boolean validacionObtenida = horarioDao.buscarHorarioExistentePorIdTutoriaAcademica(tutoriaAcademica);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //@Test
    public void testModificarHorarioDeTutoriaPoIdHorarioExito(){
        System.out.println("modificarHorarioDeTutoriaPoIdHorarioExito");
        Horario horarioModificar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        boolean verificacionEsperada = true;
        horarioModificar.setIdHorario(1);
        horarioModificar.setHoraTutoria(java.sql.Time.valueOf("1:30:00"));
        try{
            boolean verificacionObtenida = horarioDao.modificarHorarioDeTutoriaPoIdHorario(horarioModificar);
            assertEquals(verificacionEsperada, verificacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testModificarHorarioDeTutoriaPoIdHorarioFallo_IdHorarioInexistente(){
        System.out.println("modificarHorarioDeTutoriaPoIdHorarioFallo_IdHorarioInexistente");
        Horario horarioModificar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        boolean validacionEsperada = false;
        horarioModificar.setIdHorario(86);
        horarioModificar.setHoraTutoria(java.sql.Time.valueOf("1:00:00"));
        try{
            boolean validacionObtenida = horarioDao.modificarHorarioDeTutoriaPoIdHorario(horarioModificar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testBuscarHorarioExistentePorIdTutoriaAcademicaYMatriculaExito(){
        System.out.println("buscarHorarioExistentePorIdTutoriaAcademicaYMatriculaExito");
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        String matricula = "S20015728";
        HorarioDAO horarioDao = new HorarioDAO();
        boolean validacionEsperada = true;
        tutoriaAcademica.setIdTutoriaAcademica(1);
        try{
            boolean validacionObtenida = 
            horarioDao.buscarHorarioExistentePorIdTutoriaAcademicaYMatricula(tutoriaAcademica, matricula);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarHorarioExistentePorIdTutoriaAcademicaYMatriculaFallo_IdTutoriaAcademicaInexistente(){
        System.out.println("buscarHorarioExistentePorIdTutoriaAcademicaYMatriculaFallo"
        + "_IdTutoriaAcademicaInexistente");
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        String matricula = "S20015728";
        HorarioDAO horarioDao = new HorarioDAO();
        boolean validacionEsperada = false;
        tutoriaAcademica.setIdTutoriaAcademica(144);
        try{
            boolean validacionObtenida = 
            horarioDao.buscarHorarioExistentePorIdTutoriaAcademicaYMatricula(tutoriaAcademica, matricula);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarHorarioExistentePorIdTutoriaAcademicaYMatriculaFallo_MatriculaInexistente(){
        System.out.println("buscarHorarioExistentePorIdTutoriaAcademicaYMatriculaFallo_MatriculaInexistente");
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        String matricula = "S20555555";
        HorarioDAO horarioDao = new HorarioDAO();
        boolean validacionEsperada = false;
        tutoriaAcademica.setIdTutoriaAcademica(1);
        try{
            boolean validacionObtenida = 
            horarioDao.buscarHorarioExistentePorIdTutoriaAcademicaYMatricula(tutoriaAcademica, matricula);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //@Test
    public void testRegistrarHorarioDeLaTutoria(){
        System.out.println("registrarHorarioDeLaTutoriaExito");
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        Estudiante estudiante = new Estudiante();
        Horario horarioRegistrar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        boolean validacionEsperada = true;
        horarioRegistrar.setAsistencia(false);
        horarioRegistrar.setRiesgo(false);
        horarioRegistrar.setHoraTutoria(java.sql.Time.valueOf("4:00:00"));
        tutoriaAcademica.setIdTutoriaAcademica(5);
        horarioRegistrar.setTutoriaAcademica(tutoriaAcademica);
        estudiante.setMatricula("S20015728");
        horarioRegistrar.setEstudiante(estudiante);
        try{
            boolean validacionObtenida = horarioDao.registrarHorarioDeLaTutoria(horarioRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testRegistrarAsistenciaySiEstaEnRiesgoElEstudianteExito(){
        System.out.println("registrarAsistenciaySiEstaEnRiesgoElEstudianteExito");
        Horario horarioRegistrar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        Estudiante estudiante = new Estudiante();
        tutoriaAcademica.setIdTutoriaAcademica(1);
        estudiante.setMatricula("S20015728");
        horarioRegistrar.setEstudiante(estudiante);
        horarioRegistrar.setTutoriaAcademica(tutoriaAcademica);
        horarioRegistrar.setAsistencia(true);
        horarioRegistrar.setRiesgo(true);
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = horarioDao.registrarAsistenciaySiEstaEnRiesgoElEstudiante(horarioRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testRegistrarAsistenciaySiEstaEnRiesgoElEstudianteFallo_IdTutoriaAcademicaInexistente(){
        System.out.println("registrarAsistenciaySiEstaEnRiesgoElEstudianteFallo_IdTutoriaAcademicaInexistente");
        Horario horarioRegistrar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        Estudiante estudiante = new Estudiante();
        tutoriaAcademica.setIdTutoriaAcademica(144);
        estudiante.setMatricula("S20015728");
        horarioRegistrar.setEstudiante(estudiante);
        horarioRegistrar.setTutoriaAcademica(tutoriaAcademica);
        horarioRegistrar.setAsistencia(true);
        horarioRegistrar.setRiesgo(true);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = horarioDao.registrarAsistenciaySiEstaEnRiesgoElEstudiante(horarioRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testRegistrarAsistenciaySiEstaEnRiesgoElEstudianteFallo_MatriculaInexistente(){
        System.out.println("registrarAsistenciaySiEstaEnRiesgoElEstudianteFallo_MatriculaInexistente");
        Horario horarioRegistrar = new Horario();
        HorarioDAO horarioDao = new HorarioDAO();
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        Estudiante estudiante = new Estudiante();
        tutoriaAcademica.setIdTutoriaAcademica(1);
        estudiante.setMatricula("S20015729");
        horarioRegistrar.setEstudiante(estudiante);
        horarioRegistrar.setTutoriaAcademica(tutoriaAcademica);
        horarioRegistrar.setAsistencia(true);
        horarioRegistrar.setRiesgo(true);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = horarioDao.registrarAsistenciaySiEstaEnRiesgoElEstudiante(horarioRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
}
