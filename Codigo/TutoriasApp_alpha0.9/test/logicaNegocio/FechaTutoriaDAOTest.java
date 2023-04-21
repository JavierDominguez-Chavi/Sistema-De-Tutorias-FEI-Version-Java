package logicaNegocio;

import dominio.FechaTutoria;
import dominio.PeriodoEscolar;
import dominio.ProgramaEducativo;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class FechaTutoriaDAOTest {
    
    private FechaTutoria fechaTutoriaPrueba;
    private PeriodoEscolar primerPeriodo;
    private ProgramaEducativo programaIngenieriaDeSoftware;
    public FechaTutoriaDAOTest() {
        this.primerPeriodo = new PeriodoEscolar();
        this.primerPeriodo.setIdPeriodoEscolar(2);
        this.primerPeriodo.setFechaInicio(java.sql.Date.valueOf("2022-02-01"));
        this.primerPeriodo.setFechaFin(java.sql.Date.valueOf("2022-08-01"));
        
        this.programaIngenieriaDeSoftware = new ProgramaEducativo();
        this.programaIngenieriaDeSoftware.setIdProgramaEducativo(0);
        
        this.fechaTutoriaPrueba = new FechaTutoria();
        this.fechaTutoriaPrueba.setIdFechaTutoria(19);
        this.fechaTutoriaPrueba.setFechaTutoria(java.sql.Date.valueOf("2022-02-01"));
        this.fechaTutoriaPrueba.setFechaCierreDeReporte(java.sql.Date.valueOf("2022-02-01"));
        this.fechaTutoriaPrueba.setPeriodo(primerPeriodo);
    }

    //@Test
    public void testRegistrarFechaTutoria() {
        try {
            System.out.println("registrarFechaTutoriaExito");
            FechaTutoriaDAO instancia = new FechaTutoriaDAO();
            FechaTutoria fechaTutoriaPrueba = new FechaTutoria();
            ProgramaEducativo programaEducativo = new ProgramaEducativo();
            int idProgramaEducativo = 4;
            programaEducativo.setIdProgramaEducativo(idProgramaEducativo);
            fechaTutoriaPrueba.setProgramaEducativo(programaEducativo);
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.registrarFechaTutoria(fechaTutoriaPrueba);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testRegistrarFechaTutoriaFallo() {
        System.out.println("registrarFechaTutoriaFallo");
        FechaTutoriaDAO instancia = new FechaTutoriaDAO();
        FechaTutoria fechaTutoria = new FechaTutoria();

        fechaTutoria.setFechaTutoria(java.sql.Date.valueOf("0000-02-01"));
        fechaTutoria.setFechaCierreDeReporte(java.sql.Date.valueOf("2022-02-01"));
        fechaTutoria.setPeriodo(primerPeriodo);

        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        int idProgramaEducativo = 9;
        programaEducativo.setIdProgramaEducativo(idProgramaEducativo);
        fechaTutoria.setProgramaEducativo(programaEducativo);
        Exception excepcion = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            instancia.registrarFechaTutoria(fechaTutoria);
        });
        String mensajeEsperado = "Cannot add or update a child row: a foreign key constraint fails";
        String mensajeObtenido = excepcion.getMessage();
        assertEquals(mensajeEsperado,mensajeObtenido);
    }

    @Test
    public void testObtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo(){
        try {
            System.out.println("obtenerFechasTutoriasPorPeriodoEscolarExito");
            int idProgramaEducativo = 0;
            FechaTutoriaDAO instancia = new FechaTutoriaDAO();
            
            ArrayList<FechaTutoria> resultadoEsperado = new ArrayList<>();
            FechaTutoria fechaTutoria1 = new FechaTutoria();
            fechaTutoria1.setIdFechaTutoria(13);
            fechaTutoria1.setFechaTutoria(java.sql.Date.valueOf("2022-03-01"));
            fechaTutoria1.setFechaCierreDeReporte(java.sql.Date.valueOf("2022-03-05"));
            fechaTutoria1.setProgramaEducativo(programaIngenieriaDeSoftware);
            fechaTutoria1.setPeriodo(primerPeriodo);
            fechaTutoria1.setNumeroSesion(1);
            FechaTutoria fechaTutoria2 = new FechaTutoria();
            fechaTutoria2.setIdFechaTutoria(14);
            fechaTutoria2.setFechaTutoria(java.sql.Date.valueOf("2022-05-25"));
            fechaTutoria2.setFechaCierreDeReporte(java.sql.Date.valueOf("2022-05-29"));
            fechaTutoria2.setProgramaEducativo(programaIngenieriaDeSoftware);
            fechaTutoria2.setPeriodo(primerPeriodo);
            fechaTutoria2.setNumeroSesion(2);
            FechaTutoria fechaTutoria3 = new FechaTutoria();
            fechaTutoria3.setIdFechaTutoria(15);
            fechaTutoria3.setFechaTutoria(java.sql.Date.valueOf("2022-06-01"));
            fechaTutoria3.setFechaCierreDeReporte(java.sql.Date.valueOf("2022-07-16"));
            fechaTutoria3.setProgramaEducativo(programaIngenieriaDeSoftware);
            fechaTutoria3.setPeriodo(primerPeriodo);
            fechaTutoria3.setNumeroSesion(3);
            resultadoEsperado.add(fechaTutoria1);
            resultadoEsperado.add(fechaTutoria2);
            resultadoEsperado.add(fechaTutoria3);
            
            ArrayList<FechaTutoria> resultadoObtenido = 
                instancia.obtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo(
                    primerPeriodo,idProgramaEducativo
                );
            resultadoObtenido.forEach(fechaTutoria->fechaTutoria.setPeriodo(primerPeriodo));           
            
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativoFallo(){
        try {
            System.out.println("obtenerFechasTutoriasPorPeriodoEscolarFallo");
            PeriodoEscolar periodo = new PeriodoEscolarDAO().obtenerPeriodoEscolarPorId(1);
            int idProgramaEducativo = -1;
            FechaTutoriaDAO instancia = new FechaTutoriaDAO();
            ArrayList<FechaTutoria> resultadoObtenido = 
                instancia.obtenerFechasTutoriasPorIdPeriodoEscolarEIdProgramaEducativo(periodo,idProgramaEducativo);
            assertTrue(resultadoObtenido.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //@Test
    public void testModificarFechasCierre(){
        try {
            System.out.println("testModificarFechasCierreExito");
            FechaTutoriaDAO instancia = new FechaTutoriaDAO();
            FechaTutoria fechaAModificar = this.fechaTutoriaPrueba;
            fechaAModificar.setFechaCierreDeReporte(java.sql.Date.valueOf("2999-06-6"));
            assertTrue(
                instancia.modificarFechaCierre(fechaAModificar)
            );
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testModificarFechasCierreFallo(){
        try {
            System.out.println("testModificarFechasCierreFallo");
            FechaTutoriaDAO instancia = new FechaTutoriaDAO();
            FechaTutoria fechaAModificar = this.fechaTutoriaPrueba;
            fechaAModificar.setFechaCierreDeReporte(java.sql.Date.valueOf("2999-06-6"));
            fechaAModificar.setIdFechaTutoria(999);
            assertFalse(
                instancia.modificarFechaCierre(fechaAModificar)
            );
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //@Test
    public void testModificarFechaTutoria(){
        try {
            System.out.println("testModificarFechasCierreExito");
            FechaTutoriaDAO instancia = new FechaTutoriaDAO();
            FechaTutoria fechaAModificar = this.fechaTutoriaPrueba;
            fechaAModificar.setFechaTutoria(java.sql.Date.valueOf("1999-06-6"));
            assertTrue(
                instancia.modificarFechaTutoria(fechaAModificar)
            );
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testModificarFechaTutoriaFallo(){
        try {
            System.out.println("testModificarFechasTutoriasFallo");
            FechaTutoriaDAO instancia = new FechaTutoriaDAO();
            FechaTutoria fechaAModificar = this.fechaTutoriaPrueba;
            fechaAModificar.setFechaTutoria(java.sql.Date.valueOf("2999-06-6"));
            fechaAModificar.setIdFechaTutoria(999);
            boolean resultadoEsperado = false;
            boolean resultadoObtenido = instancia.modificarFechaCierre(fechaAModificar);
            assertEquals(resultadoEsperado,resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
