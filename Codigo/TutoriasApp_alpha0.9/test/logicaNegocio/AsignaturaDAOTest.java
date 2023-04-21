package logicaNegocio;

import logicaNegocio.AsignaturaDAO;
import dominio.ExperienciaEducativa;
import dominio.Asignatura;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class AsignaturaDAOTest {
    
    private ExperienciaEducativa experienciaEducativaProgramacion;
    private ExperienciaEducativa experienciaEducativaMatematicasDiscretas;
    private ExperienciaEducativa experienciaEducativaRedes;
    private ExperienciaEducativa experienciaEducativaEstructurasDeDatos;
    private ExperienciaEducativa experienciaEducativaBasesDeDatos;
    
    public AsignaturaDAOTest() {
        this.experienciaEducativaProgramacion = new ExperienciaEducativa();
        this.experienciaEducativaMatematicasDiscretas = new ExperienciaEducativa();
        this.experienciaEducativaRedes = new ExperienciaEducativa();
        this.experienciaEducativaEstructurasDeDatos = new ExperienciaEducativa();
        this.experienciaEducativaBasesDeDatos = new ExperienciaEducativa();
        this.experienciaEducativaProgramacion.setNombre("Programacion");
        this.experienciaEducativaProgramacion.setIdExperienciaEducativa(1);
        this.experienciaEducativaMatematicasDiscretas.setNombre("Matematicas Discretas");
        this.experienciaEducativaMatematicasDiscretas.setIdExperienciaEducativa(2);
        this.experienciaEducativaRedes.setNombre("Redes");
        this.experienciaEducativaRedes.setIdExperienciaEducativa(3);
        this.experienciaEducativaEstructurasDeDatos.setNombre("Estructuras de Datos");
        this.experienciaEducativaEstructurasDeDatos.setIdExperienciaEducativa(4);
        this.experienciaEducativaBasesDeDatos.setNombre("Bases de Datos");
        this.experienciaEducativaBasesDeDatos.setIdExperienciaEducativa(5);
    }

    @Test
    public void testObtenerAsignaturaPorNrc() {
        try {
            System.out.println("obtenerAsignaturaPorNrcExito");
            int NRC = 12345;
            Asignatura resultadoEsperado = new Asignatura();
            resultadoEsperado.setNRC(NRC);
            resultadoEsperado.setExperienciaEducativa(experienciaEducativaMatematicasDiscretas);
            AsignaturaDAO instancia = new AsignaturaDAO();
            Asignatura resultadoObtenido = instancia.obtenerAsignaturaPorNrc(NRC);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerAsignaturaPorNrcFallo() {
        try {
            System.out.println("obtenerAsignaturaPorNrcFallo");
            int NRC = -1;
            Asignatura resultadoEsperado = new Asignatura();
            AsignaturaDAO instancia = new AsignaturaDAO();
            Asignatura resultadoObtenido = instancia.obtenerAsignaturaPorNrc(NRC);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerOfertaAcademicPorProgramaEducativo() {
        try {
            System.out.println("obtenerOfertaAcademicaPorProgramaEducativoYPeriodoEscolarExito");
            
            Asignatura asignaturaEsperada1 = new Asignatura();
                asignaturaEsperada1.setNRC(12345);
                asignaturaEsperada1.setExperienciaEducativa(experienciaEducativaMatematicasDiscretas);
            Asignatura asignaturaEsperada3= new Asignatura();
                asignaturaEsperada3.setNRC(21423);
                asignaturaEsperada3.setExperienciaEducativa(experienciaEducativaRedes);
            Asignatura asignaturaEsperada6 = new Asignatura();
                asignaturaEsperada6.setNRC(45678);
                asignaturaEsperada6.setExperienciaEducativa(experienciaEducativaProgramacion);
                
            ArrayList<Asignatura> resultadoEsperado = new ArrayList<>();
            
            resultadoEsperado.add(asignaturaEsperada6);
            resultadoEsperado.add(asignaturaEsperada1);
            resultadoEsperado.add(asignaturaEsperada3);
            
            int idProgramaEducativo = 0;
            int idPeriodo = 2;
            AsignaturaDAO instancia = new AsignaturaDAO();
            ArrayList<Asignatura> resultadoObtenido = 
                instancia.obtenerOfertaAcademicaPorProgramaEducativoYPeriodoEscolar(
                    idProgramaEducativo, idPeriodo);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerOfertaAcademicaPorProgramaEducativoFallo_ProgramaEducativoNoExiste() {
        try {
            System.out.println("obtenerOfertaAcademicaPorProgramaEducativoFallo_ProgramaEducativoNoExiste");
            int idProgramaEducativo = 7;
            int idPeriodo = 2;
            AsignaturaDAO instancia = new AsignaturaDAO();
            ArrayList<Asignatura> resultadoObtenido =
                instancia.obtenerOfertaAcademicaPorProgramaEducativoYPeriodoEscolar(
                    idProgramaEducativo, idPeriodo);
            assertTrue(resultadoObtenido.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerOfertaAcademicaPorProgramaEducativoFallo_PeriodoNoExiste() {
        try {
            System.out.println("obtenerOfertaAcademicaPorProgramaEducativoFallo_PeriodoNoExiste");
            int idProgramaEducativo = 0;
            int idPeriodo = 99;
            AsignaturaDAO instancia = new AsignaturaDAO();
            ArrayList<Asignatura> resultadoObtenido =
                instancia.obtenerOfertaAcademicaPorProgramaEducativoYPeriodoEscolar(
                    idProgramaEducativo, idPeriodo);
            assertTrue(resultadoObtenido.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerAsignaturasSinAcademico() {
        try {
            System.out.println("obtenerAsignaturaSinAcademicoExito");
            ArrayList<Asignatura> resultadoEsperado = new ArrayList<>();
            Asignatura asignaturaObtenida1 = new Asignatura();
            asignaturaObtenida1.setNRC(45678);
            asignaturaObtenida1.setExperienciaEducativa(experienciaEducativaProgramacion);
            resultadoEsperado.add(asignaturaObtenida1);
            AsignaturaDAO instancia = new AsignaturaDAO();
            ArrayList<Asignatura> resultadoObtenido = 
                instancia.obtenerAsignaturasSinAcademico();
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerAsignaturasSinAcademicoFallo() {
        try {
            System.out.println("obtenerAsignaturasSinAcademicoFallo");
            AsignaturaDAO instancia = new AsignaturaDAO();
            int resultadoEsperado = 1;
            ArrayList<Asignatura> resultadoObtenido = 
                instancia.obtenerAsignaturasSinAcademico();
            assertEquals(resultadoEsperado, resultadoObtenido.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //@Test
    public void testAsignarExperienciaEducativaAcademico() {
        try {
            System.out.println("asignarExperienciaEducativaAcademicoExito");
            int idAcademico = 2;
            int NRC = 12345;
            AsignaturaDAO instancia = new AsignaturaDAO();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.asignarAsignaturaAProfesor(idAcademico, NRC);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @Test
    public void testAsignarExperienciaEducativaAcademicoFallo_idAcademico() {
        System.out.println("asignarExperienciaEducativaAcademicoFallo_idAcademico");
        int idAcademico = -5;
        int nrc = 12345;
        AsignaturaDAO instancia = new AsignaturaDAO();
        Exception excepcion = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            instancia.asignarAsignaturaAProfesor(idAcademico, nrc);
        });
        String mensajeEsperado = "Cannot add or update a child row: a foreign key constraint fails";
        String mensajeObtenido = excepcion.getMessage();
        assertEquals(mensajeEsperado,mensajeObtenido);            
    }
    
    @Test
    public void testAsignarExperienciaEducativaAcademicoFallo_NRC() {
        try {
            System.out.println("asignarExperienciaEducativaAcademicoFallo_NRC");
            int idAcademico = 1;
            int nrc = 0;
            AsignaturaDAO instancia = new AsignaturaDAO();
            boolean resultadoEsperado = false;
            boolean resultadoObtenido = instancia.asignarAsignaturaAProfesor(idAcademico, nrc);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testVerificarExistenciaDeAsignaturas() {
        try {
            System.out.println("verificarExistenciaDeAsignaturasExito");
            AsignaturaDAO instancia = new AsignaturaDAO();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.verificarExistenciaDeAsignaturas();
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

