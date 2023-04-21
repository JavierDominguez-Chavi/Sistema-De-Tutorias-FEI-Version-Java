package logicaNegocio.helpers;

import logicaNegocio.helpers.DateHelper;
import java.time.LocalDate;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class DateHelperTest {
    
    public DateHelperTest() {
    }

    @Test
    public void testADate() {
        System.out.println("aDateExito");
        LocalDate fecha = java.sql.Date.valueOf("1999-01-01").toLocalDate();
        DateHelper instance = new DateHelper();
        Date resultadoEsperado = java.sql.Date.valueOf("1999-01-01");
        Date resultadoObtenido = instance.aDate(fecha);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testALocalDate() {
        System.out.println("aLocalDateExito");
        Date fecha = java.sql.Date.valueOf("1999-01-01");
        DateHelper instance = new DateHelper();
        LocalDate expResult = java.sql.Date.valueOf("1999-01-01").toLocalDate();
        LocalDate result = instance.aLocalDate(fecha);
        assertEquals(expResult, result);
    }
    
}
