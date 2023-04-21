package logicaNegocio.helpers;

import dominio.PeriodoEscolar;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class PeriodoEscolarHelperTest {
    
    public PeriodoEscolarHelperTest() {
    }

    @Test
    public void testValidarOrdenDeFechas() {
        System.out.println("testValidarOrdenDeFechasExito");
        PeriodoEscolar periodoEscolar = new PeriodoEscolar();
        periodoEscolar.setFechaInicio(java.sql.Date.valueOf("2000-01-01"));
        periodoEscolar.setFechaFin(java.sql.Date.valueOf("2000-06-06"));
        PeriodoEscolarHelper instancia = new PeriodoEscolarHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarOrdenDeFechas(periodoEscolar);
        assertEquals(resultadoEsperado,resultadoObtenido);
    }
    
    @Test
    public void testValidarOrdenDeFechasFallo() {
        System.out.println("testValidarFechasFallo");
        PeriodoEscolar periodoEscolar = new PeriodoEscolar();
        periodoEscolar.setFechaInicio(java.sql.Date.valueOf("2001-01-01"));
        periodoEscolar.setFechaFin(java.sql.Date.valueOf("2000-06-06"));
        PeriodoEscolarHelper instancia = new PeriodoEscolarHelper();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            instancia.validarOrdenDeFechas(periodoEscolar);
        });
        String mensajeEsperado = "Las fechas del nuevo periodo escolar están desordenadas. "+ 
                "Por favor, verifique la información e intente de nuevo.";
        String mensajeObtenido = exception.getMessage();
        assertEquals(mensajeEsperado,mensajeObtenido);
    }

    @Test
    public void testValidarPeriodoEscolarNuevo() {
        try {
            System.out.println("validarPeriodoEscolarNuevoExito");
            PeriodoEscolar periodoEscolar = new PeriodoEscolar();
            periodoEscolar.setFechaInicio(java.sql.Date.valueOf("2600-01-01"));
            periodoEscolar.setFechaFin(java.sql.Date.valueOf("2650-06-06"));
            PeriodoEscolarHelper instancia = new PeriodoEscolarHelper();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.validarPeriodoEscolarNuevo(periodoEscolar);
            assertEquals(resultadoEsperado,resultadoObtenido);
        } catch (IllegalArgumentException iaException) {
            iaException.printStackTrace();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testValidarPeriodoEscolarNuevoFallo() {
        System.out.println("validarPeriodoEscolarNuevoFallo");
        PeriodoEscolar periodoEscolar = new PeriodoEscolar();
        periodoEscolar.setNombrePeriodo("Febrero 2022 - Agosto 2022");
        PeriodoEscolarHelper instancia = new PeriodoEscolarHelper();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            instancia.validarPeriodoEscolarNuevo(periodoEscolar);
        });
        String mensajeEsperado = "El periodo escolar ya existe en el sistema. "
                + "Por favor, verifique la información e intente de nuevo.";
        String mensajeObtenido = exception.getMessage();
        assertEquals(mensajeEsperado,mensajeObtenido);
    }
    
    @Test
    public void testCrearNombreDePeriodo() {
        System.out.println("crearNombreDePeriodoExito");
        LocalDate fechaInicio = java.sql.Date.valueOf("2022-02-01").toLocalDate();
        LocalDate fechaFin = java.sql.Date.valueOf("2022-08-01").toLocalDate();
        String resultadoEsperado = "Febrero 2022 - Agosto 2022"; 
        PeriodoEscolarHelper instancia = new PeriodoEscolarHelper();
        String resultadoObtenido = instancia.crearNombreDePeriodo(fechaInicio, fechaFin);
        assertEquals(resultadoEsperado,resultadoObtenido);
    }
    
    @Test
    public void testCrearNombreDePeriodoFallo() {
        System.out.println("crearNombreDePeriodoFallo");
        LocalDate fechaInicio = java.sql.Date.valueOf("2022-02-01").toLocalDate();
        LocalDate fechaFin = java.sql.Date.valueOf("2022-08-01").toLocalDate();
        String resultadoEsperado = "Febresro 2022 - Agosto 2022"; 
        PeriodoEscolarHelper instancia = new PeriodoEscolarHelper();
        String resultadoObtenido = instancia.crearNombreDePeriodo(fechaInicio, fechaFin);
        assertNotEquals(resultadoEsperado,resultadoObtenido);
    }
}
