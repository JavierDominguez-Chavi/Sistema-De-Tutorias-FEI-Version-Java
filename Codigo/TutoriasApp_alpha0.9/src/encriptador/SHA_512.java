package encriptador;

import accesoADatos.DataBaseConnection;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SHA_512 {
private static Logger logger = LoggerFactory.getLogger(SHA_512.class);
    public String getSHA512(String textoPlanoContrasenia){
        String hexadecimalContrasenia= null;
        try {
            MessageDigest mensajeProcesado = MessageDigest.getInstance("SHA-512");
            mensajeProcesado.reset();
            mensajeProcesado.update(textoPlanoContrasenia.getBytes("utf8"));
            hexadecimalContrasenia = String.format("%0128x", new BigInteger(1, mensajeProcesado.digest())); 
        } catch (NoSuchAlgorithmException nsaException) {
            logger.error(nsaException.getClass()+": "+nsaException.getMessage());
        } catch (UnsupportedEncodingException ueException) {
            logger.error(ueException.getClass()+": "+ueException.getMessage());
        }
        return hexadecimalContrasenia;
    } 
}
