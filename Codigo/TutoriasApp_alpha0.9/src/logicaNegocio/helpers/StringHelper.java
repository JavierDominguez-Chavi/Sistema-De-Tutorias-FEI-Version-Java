package logicaNegocio.helpers;

public class StringHelper {
    private final String caracteresAlfabeticos = "[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+";
    private final String caracteresAlfanumericos = "[\\d*a-zA-ZáéíóúÁÉÍÓÚ ]+";
    private final String caracteresNumericos = "\\d*";
    private final String caracteresCorreoElectronico = 
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public String getCaracteresAlfabeticos() {
        return caracteresAlfabeticos;
    }

    public String getCaracteresAlfanumericos() {
        return caracteresAlfanumericos;
    }

    public String getCaracteresNumericos() {
        return caracteresNumericos;
    }

    public String getCaracteresCorreoElectronico() {
        return caracteresCorreoElectronico;
    }
    
    public boolean validarCaracteresDeCadena(String cadena, String caracteresPermitidos){
        boolean laCadenaEsValida=true;
        if (!cadena.matches(caracteresPermitidos)){
            laCadenaEsValida=false;
        }
        return laCadenaEsValida;
    }
    
    public boolean validarLogitudDeCadena(String cadena, int longitudMaxima){
        boolean laCadenaEsValida=true;
        if (cadena.length()>longitudMaxima){
            laCadenaEsValida=false;
        }
        return laCadenaEsValida;
    }
}
