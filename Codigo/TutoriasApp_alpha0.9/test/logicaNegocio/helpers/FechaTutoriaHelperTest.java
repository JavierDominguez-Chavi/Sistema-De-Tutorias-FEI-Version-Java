package logicaNegocio.helpers;

import logicaNegocio.helpers.FechaTutoriaHelper;
import dominio.FechaTutoria;
import dominio.PeriodoEscolar;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class FechaTutoriaHelperTest {

    private FechaTutoria fechaTutoria1;
    private FechaTutoria fechaTutoria2;
    private FechaTutoria fechaTutoria3;
    private PeriodoEscolar periodoEscolar1;
    
    public FechaTutoriaHelperTest() {
        this.periodoEscolar1=new PeriodoEscolar();
        this.periodoEscolar1.setFechaInicio(java.sql.Date.valueOf("2022-02-01"));
        this.periodoEscolar1.setFechaFin(java.sql.Date.valueOf("2022-08-01"));
        
        this.fechaTutoria1=new FechaTutoria();
        this.fechaTutoria1.setIdFechaTutoria(13);
        this.fechaTutoria1.setFechaTutoria(java.sql.Date.valueOf("2022-03-01"));
        this.fechaTutoria1.setFechaCierreDeReporte(java.sql.Date.valueOf("2022-03-05"));
        this.fechaTutoria1.setNumeroSesion(1);
        this.fechaTutoria1.setPeriodo(periodoEscolar1);
        this.fechaTutoria2=new FechaTutoria();
        this.fechaTutoria2.setIdFechaTutoria(14);
        this.fechaTutoria2.setFechaTutoria(java.sql.Date.valueOf("2022-05-25"));
        this.fechaTutoria2.setFechaCierreDeReporte(java.sql.Date.valueOf("2022-05-29"));
        this.fechaTutoria2.setNumeroSesion(2);
        this.fechaTutoria2.setPeriodo(periodoEscolar1);
        this.fechaTutoria3=new FechaTutoria();
        this.fechaTutoria3.setIdFechaTutoria(15);
        this.fechaTutoria3.setFechaTutoria(java.sql.Date.valueOf("2022-06-01"));
        this.fechaTutoria3.setFechaCierreDeReporte(java.sql.Date.valueOf("2022-07-16"));
        this.fechaTutoria3.setNumeroSesion(3);
        this.fechaTutoria3.setPeriodo(periodoEscolar1);
    }

    @Test
    public void testValidarFechaTutoriaDespuesDePeriodoFechaInicio() {
        System.out.println("validarFechaTutoriaDespuesDePeriodoFechaInicioExito");
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarFechaTutoriaDespuesDePeriodoFechaInicio(this.fechaTutoria1);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarFechaTutoriaDespuesDePeriodoFechaInicioFallo(){
        System.out.println("validarFechaTutoriaDespuesDePeriodoFechaInicioFallo");
        this.fechaTutoria1.setFechaTutoria(java.sql.Date.valueOf("1999-03-01"));
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarFechaTutoriaDespuesDePeriodoFechaInicio(this.fechaTutoria1);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testValidarFechaTutoriaAntesDeFechaCierre()  {
        System.out.println("validarFechaTutoriaAntesDeFechaCierreExito");
        this.fechaTutoria1.setFechaTutoria(java.sql.Date.valueOf("2022-03-01"));
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarFechaTutoriaAntesDeFechaCierre(this.fechaTutoria1);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testValidarFechaTutoriaAntesDeFechaCierreFallo() {
        System.out.println("validarFechaTutoriaAntesDeFechaCierreFallo");
        this.fechaTutoria1.setFechaTutoria(java.sql.Date.valueOf("2999-03-01"));
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarFechaTutoriaAntesDeFechaCierre(this.fechaTutoria1);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testValidarFechaTutoriaAntesDePeriodoFechaFin() {
        System.out.println("validarFechaTutoriaAntesDePeriodoFechaFinExito");
        this.fechaTutoria1.setFechaTutoria(java.sql.Date.valueOf("2022-03-01"));
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarFechaTutoriaAntesDePeriodoFechaFin(this.fechaTutoria1);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarFechaTutoriaAntesDePeriodoFechaFinFallo() throws SQLException {
        System.out.println("validarFechaTutoriaAntesDeFechaFinFallo");
        this.fechaTutoria1.setFechaTutoria(java.sql.Date.valueOf("2999-03-01"));
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarFechaTutoriaAntesDePeriodoFechaFin(this.fechaTutoria1);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarOrdenDeMultiplesFechaTutorias() {
        System.out.println("validarOrdenDeMultiplesFechaTutoriasExito");
        ArrayList<FechaTutoria> fechasDeTutoria = new ArrayList<FechaTutoria>();
        fechasDeTutoria.add(this.fechaTutoria1);
        fechasDeTutoria.add(this.fechaTutoria2);
        fechasDeTutoria.add(this.fechaTutoria3);
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarOrdenDeMultiplesFechaTutorias(fechasDeTutoria);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarOrdenDeMultiplesFechaTutoriasFallo()  {
        System.out.println("validarOrdenDeMultiplesFechaTutoriasFallo");
        ArrayList<FechaTutoria> fechasDeTutoria = new ArrayList<FechaTutoria>();
        fechasDeTutoria.add(this.fechaTutoria1);
        fechasDeTutoria.add(this.fechaTutoria3);
        fechasDeTutoria.add(this.fechaTutoria2);
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarOrdenDeMultiplesFechaTutorias(fechasDeTutoria);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testValidarFechasDeTutoria() {
        System.out.println("validarFechasDeTutoriasExito");
        ArrayList<FechaTutoria> fechasDeTutoria = new ArrayList<FechaTutoria>();
        fechasDeTutoria.add(this.fechaTutoria1);
        fechasDeTutoria.add(this.fechaTutoria2);
        fechasDeTutoria.add(this.fechaTutoria3);
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarFechasDeTutoria(fechasDeTutoria);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarFechasDeTutoriaFallo_SegundaFechaDesordenada() {
        System.out.println("validarFechasDeTutoriasFallo_SegundaFechaDesordenada");
        ArrayList<FechaTutoria> fechasDeTutoria = new ArrayList<FechaTutoria>();
        fechasDeTutoria.add(this.fechaTutoria2);
        fechasDeTutoria.add(this.fechaTutoria1);
        fechasDeTutoria.add(this.fechaTutoria3);
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarFechasDeTutoria(fechasDeTutoria);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testValidarFechasDeTutoriaFallo_PrimeraFechaFueraDelPeriodo() {
        System.out.println("validarFechasDeTutoriasFallo_PrimeraFechaFueraDelPeriodo");
        ArrayList<FechaTutoria> fechasDeTutoria = new ArrayList<FechaTutoria>();
        this.fechaTutoria1.setFechaTutoria(java.sql.Date.valueOf("1999-01-01"));
        fechasDeTutoria.add(this.fechaTutoria1);
        fechasDeTutoria.add(this.fechaTutoria2);
        fechasDeTutoria.add(this.fechaTutoria3);
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarFechasDeTutoria(fechasDeTutoria);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarFechasDeTutoriaFallo_SegundaFechaFueraDelPeriodo() {
        System.out.println("validarFechasDeTutoriasFallo_SegundaFechaFueraDelPeriodo");
        ArrayList<FechaTutoria> fechasDeTutoria = new ArrayList<FechaTutoria>();
        this.fechaTutoria2.setFechaTutoria(java.sql.Date.valueOf("3999-01-01"));
        fechasDeTutoria.add(this.fechaTutoria1);
        fechasDeTutoria.add(this.fechaTutoria2);
        fechasDeTutoria.add(this.fechaTutoria3);
        FechaTutoriaHelper instancia = new FechaTutoriaHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarFechasDeTutoria(fechasDeTutoria);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    

}
