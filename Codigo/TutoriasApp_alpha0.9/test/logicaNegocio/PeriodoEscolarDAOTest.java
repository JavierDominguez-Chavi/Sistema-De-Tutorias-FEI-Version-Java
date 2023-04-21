package logicaNegocio;

import logicaNegocio.PeriodoEscolarDAO;
import dominio.PeriodoEscolar;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class PeriodoEscolarDAOTest {
    
    private PeriodoEscolar primerPeriodoRegistrado;
    private PeriodoEscolar segundoPeriodoRegistrado;
    private PeriodoEscolar tercerPeriodoRegistrado3;
    
    public PeriodoEscolarDAOTest() {
        this.primerPeriodoRegistrado = new PeriodoEscolar();
        this.primerPeriodoRegistrado.setIdPeriodoEscolar(2);
        this.primerPeriodoRegistrado.setFechaInicio(java.sql.Date.valueOf("2022-02-01"));
        this.primerPeriodoRegistrado.setFechaFin(java.sql.Date.valueOf("2022-08-01"));
        
        this.segundoPeriodoRegistrado = new PeriodoEscolar();
        this.segundoPeriodoRegistrado.setIdPeriodoEscolar(3);
        this.segundoPeriodoRegistrado.setFechaInicio(java.sql.Date.valueOf("2022-08-01"));
        this.segundoPeriodoRegistrado.setFechaFin(java.sql.Date.valueOf("2023-01-16"));
        
        this.tercerPeriodoRegistrado3 = new PeriodoEscolar();
        this.tercerPeriodoRegistrado3.setIdPeriodoEscolar(4);
        this.tercerPeriodoRegistrado3.setFechaInicio(java.sql.Date.valueOf("2023-02-01"));
        this.tercerPeriodoRegistrado3.setFechaFin(java.sql.Date.valueOf("2023-07-31"));
    }

    //@Test
    public void testRegistrarPeriodoEscolar(){
        try {
            System.out.println("registrarPeriodoEscolarExito");
            PeriodoEscolar periodoEscolar = new PeriodoEscolar();
            periodoEscolar.setFechaInicio(java.sql.Date.valueOf("2000-01-01"));
            periodoEscolar.setFechaFin(java.sql.Date.valueOf("2000-06-06"));
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.registrarPeriodoEscolar(periodoEscolar);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    //@Test
    public void testRegistrarPeriodoEscolarFallo()  {
        try {
            System.out.println("registrarPeriodoEscolarFallo");
            PeriodoEscolar periodoEscolar = new PeriodoEscolar();
            periodoEscolar.setFechaInicio(java.sql.Date.valueOf("2000-01-01"));
            periodoEscolar.setFechaFin(java.sql.Date.valueOf("2000-06-06"));
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            
            boolean resultadoEsperado = false;
            boolean resultadoObtenido = instancia.registrarPeriodoEscolar(periodoEscolar);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerPeriodoEscolarPorId(){
        try {
            System.out.println("obtenerPeriodoEscolarPorIdExito");
            int idPeriodoEscolar = 2;
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            PeriodoEscolar resultadoEsperado = new PeriodoEscolar();
            resultadoEsperado.setIdPeriodoEscolar(idPeriodoEscolar);
            resultadoEsperado.setFechaInicio(java.sql.Date.valueOf("2022-02-01"));
            resultadoEsperado.setFechaFin(java.sql.Date.valueOf("2022-08-01"));
            PeriodoEscolar resultadoObtenido = instancia.obtenerPeriodoEscolarPorId(idPeriodoEscolar);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @Test
    public void testObtenerPeriodoEscolarPorIdFallo() {
        try {
            System.out.println("obtenerPeriodoEscolarPorIdFallo");
            int idPeriodoEscolar = 0;
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            
            PeriodoEscolar resultadoEsperado = new PeriodoEscolar();
            PeriodoEscolar resultadoObtenido = instancia.obtenerPeriodoEscolarPorId(idPeriodoEscolar);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerTodosLosPeriodosEscolaresRegistrados() {
        try {
            System.out.println("obtenerTodosLosPeriodosEscolaresRegistradosExito");
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            ArrayList<PeriodoEscolar> resultadoEsperado = new ArrayList<>();
            resultadoEsperado.add(primerPeriodoRegistrado);
            resultadoEsperado.add(segundoPeriodoRegistrado);
            resultadoEsperado.add(tercerPeriodoRegistrado3);
            ArrayList<PeriodoEscolar> resultadoObtenido = instancia.obtenerTodosLosPeriodosEscolaresRegistrados();
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @Test
    public void testObtenerTodosLosPeriodosEscolaresRegistradosFallo() {
        try {
            System.out.println("obtenerTodosLosPeriodosEscolaresRegistradosFallo");
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            int resultadoEsperado = 3;
            ArrayList<PeriodoEscolar>resultadoObtenido= instancia.obtenerTodosLosPeriodosEscolaresRegistrados();
            assertEquals(resultadoEsperado, resultadoObtenido.size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testValidarPeriodoEscolarNuevo() {
        try {
            System.out.println("validarPeriodoEscolarNuevoExito");
            PeriodoEscolar periodo = new PeriodoEscolar();
            periodo.setNombrePeriodo("Febrero 2022 - Junio 2023");
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.validarPeriodoEscolarNuevo(periodo);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testValidarPeriodoEscolarNuevoFallo() {
        try {
            System.out.println("validarPeriodoEscolarNuevoFallo");
            PeriodoEscolar periodo = new PeriodoEscolar();
            periodo.setNombrePeriodo("Febrero 2022 - Agosto 2022");
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            boolean resultadoEsperado = false;
            boolean resultadoObtenido = instancia.validarPeriodoEscolarNuevo(periodo);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerPeriodoEscolarActualPorFechaDelSistema(){
        try {
            System.out.println("obtenerPeriodoEscolarActualPorFechaDelSistemaExito");
            LocalDate fechaDelSistema = (java.sql.Date.valueOf("2022-03-02").toLocalDate());
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            PeriodoEscolar resultadoEsperado = new PeriodoEscolar();
            resultadoEsperado.setIdPeriodoEscolar(2);
            resultadoEsperado.setFechaInicio(java.sql.Date.valueOf("2022-02-01"));
            resultadoEsperado.setFechaFin(java.sql.Date.valueOf("2022-08-01"));
            PeriodoEscolar resultadoObtenido = instancia.obtenerPeriodoEscolarActualPorFechaDelSistema(fechaDelSistema);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerPeriodoEscolarActualPorFechaDelSistemaFallo(){
        try {
            System.out.println("obtenerPeriodoEscolarActualPorFechaDelSistemaFallo");
            LocalDate fechaDelSistema = (java.sql.Date.valueOf("1999-06-06").toLocalDate());
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            PeriodoEscolar resultadoEsperado = new PeriodoEscolar();
            resultadoEsperado.setIdPeriodoEscolar(1);
            resultadoEsperado.setFechaInicio(java.sql.Date.valueOf("2022-02-02"));
            resultadoEsperado.setFechaFin(java.sql.Date.valueOf("2022-06-06"));
            PeriodoEscolar resultadoObtenido = instancia.obtenerPeriodoEscolarActualPorFechaDelSistema(fechaDelSistema);
            assertNotEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativo(){
        try {
            System.out.println("obtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativoExito");
            int idProgramaEducativo = 0;
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            ArrayList<PeriodoEscolar> resultadoEsperado = new ArrayList<>();
            resultadoEsperado.add(tercerPeriodoRegistrado3);
            ArrayList<PeriodoEscolar> resultadoObtenido = 
                instancia.obtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativo(idProgramaEducativo);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    
    @Test
    public void testObtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativoFallo(){
        try {
            System.out.println("obtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativoFallo");
            int idProgramaEducativo = 99;
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            int resultadoEsperado = 3;
            ArrayList<PeriodoEscolar> resultadoObtenido = 
                instancia.obtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativo(idProgramaEducativo);
            assertEquals(resultadoObtenido.size(), resultadoEsperado);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativo(){
        try {
            System.out.println("obtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativoExito");
            int idProgramaEducativo = 0;
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            ArrayList<PeriodoEscolar> resultadoEsperado = new ArrayList<>();
            resultadoEsperado.add(this.primerPeriodoRegistrado);
            resultadoEsperado.add(this.segundoPeriodoRegistrado);
            ArrayList<PeriodoEscolar> resultadoObtenido =
                instancia.obtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativo(idProgramaEducativo);
            
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativoFallo() {
        try {
            System.out.println("obtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativoFallo");
            int idProgramaEducativo = 9;
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            ArrayList<PeriodoEscolar> resultadoObtenido =
                instancia.obtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativo(idProgramaEducativo);
            assertTrue(resultadoObtenido.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testVerificarExistenciaDePeriodosEscolares() {
        try {
            System.out.println("verificarExistenciaDePeriodosEscolaresExito");
            PeriodoEscolarDAO instancia = new PeriodoEscolarDAO();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.verificarExistenciaDePeriodosEscolares();
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
