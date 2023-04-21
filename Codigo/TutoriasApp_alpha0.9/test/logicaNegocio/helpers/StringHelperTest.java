package logicaNegocio.helpers;

import logicaNegocio.helpers.StringHelper;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringHelperTest {
    
    public StringHelperTest() {
    }

    @Test
    public void testValidarCaracteresDeCadena_Alfabetica() {
        System.out.println("validarCadenaAlfabeticaExito");
        String cadena = "sadf";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresAlfabeticos()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_AlfabeticaFallo_Numero() {
        System.out.println("validarCadenaAlfabeticaFallo_Numero");
        String cadena = "1s2adf";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresAlfabeticos()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_AlfabeticaFallo_Signo() {
        System.out.println("validarCadenaAlfabeticaFallo_Signo");
        String cadena = "?s-adf";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresAlfabeticos()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_Numerica() {
        System.out.println("validarCadenaNumericaExito");
        String cadena = "1234567890";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresNumericos()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_NumericaFallo_Letra() {
        System.out.println("validarCadenaNumericaFallo_Letra");
        String cadena = "123456w7890";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresNumericos()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_NumericaFallo_Signo() {
        System.out.println("validarCadenaNumericaFallo_Signo");
        String cadena = "123456_7890";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresNumericos()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_Alfanumerica() {
        System.out.println("validarCaracteresDeCadena_AlfanumericaExito");
        String cadena = "asdf 123456f  7890";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresAlfanumericos()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_AlfanumericaFallo() {
        System.out.println("validarCaracteresDeCadena_AlfanumericaFallo");
        String cadena = "asdf?? 1--!!23456f+-*  7890";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresAlfanumericos()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_CorreoElectronico() {
        System.out.println("validarCaracteresDeCadena_CorreoElectronicoExito");
        String cadena = "a-sd_f1234@qwerty.com";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresCorreoElectronico()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_CorreoElectronicoFallo_Signo1() {
        System.out.println("validarCaracteresDeCadena_CorreoElectronicoFallo_Signo1");
        String cadena = "as+df@qwerty.com";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresCorreoElectronico()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_CorreoElectronicoFallo_Signo2() {
        System.out.println("validarCaracteresDeCadena_CorreoElectronicoFallo_Signo2");
        String cadena = "asdf@qw+erty.com";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresCorreoElectronico()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_CorreoElectronicoFallo_Signo3() {
        System.out.println("validarCaracteresDeCadena_CorreoElectronicoFallo_Signo3");
        String cadena = "asdf@qwerty.co+m";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresCorreoElectronico()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_CorreoElectronicoFallo_Arroba() {
        System.out.println("validarCaracteresDeCadena_CorreoElectronicoFallo_Arroba");
        String cadena = "asdfqwerty.com";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresCorreoElectronico()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarCaracteresDeCadena_CorreoElectronicoFallo_com() {
        System.out.println("validarCaracteresDeCadenaFallo_com");
        String cadena = "asdf@qwerty";
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarCaracteresDeCadena(
                cadena, instancia.getCaracteresCorreoElectronico()
        );
        assertEquals(resultadoEsperado, resultadoObtenido);
    } 

    @Test
    public void testValidarLogitudDeCadena() {
        System.out.println("validarLogitudDeCadenaExito");
        String cadena = "asdf";
        int longitudMaxima = 4;
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = true;
        boolean resultadoObtenido = instancia.validarLogitudDeCadena(cadena, longitudMaxima);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void testValidarLogitudDeCadenaFallo() {
        System.out.println("validarLogitudDeCadenaFallo");
        String cadena = "asdf";
        int longitudMaxima = 3;
        StringHelper instancia = new StringHelper();
        boolean resultadoEsperado = false;
        boolean resultadoObtenido = instancia.validarLogitudDeCadena(cadena, longitudMaxima);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
}
