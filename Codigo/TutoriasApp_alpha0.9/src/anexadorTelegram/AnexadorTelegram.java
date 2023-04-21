package anexadorTelegram;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class AnexadorTelegram extends AppenderSkeleton {

    private final static String LOCALIZADOR_RECURSOS_BASE = "https://api.telegram.org/bot";

    private String token;
    private long chatid;

    private HttpsURLConnection crearConexion(String token) {
        HttpsURLConnection conexion = null;
        if (token != null && !token.isEmpty()) {
            final String peticion = String.format("%s%s/sendMessage", LOCALIZADOR_RECURSOS_BASE, token);
            try {
                URL localizadorRecursos = new URL(peticion);
                conexion = (HttpsURLConnection) localizadorRecursos.openConnection();
                conexion.setDoOutput(true);
                conexion.setInstanceFollowRedirects(false);
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("charset", "utf-8");
                conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            } catch (IOException ioException) {
                LogLog.warn(String.format(
                        "Error al crear la conexión con el bot %s", token), ioException);
            }
        }
        return conexion;
    }

    @Override
    protected void append(LoggingEvent evento) {
        HttpsURLConnection conexion = crearConexion(getToken());
        if (conexion != null && getChatid() > 0) {
            String mensaje = String.format("chat_id=%s&text=%s", getChatid(), layout.format(evento));
            byte[] datosDelMensaje = mensaje.getBytes(StandardCharsets.UTF_8);
            int longitudDeDatosDelMensaje = datosDelMensaje.length;

            conexion.setRequestProperty("Content-Length", Integer.toString(longitudDeDatosDelMensaje));

            try (DataOutputStream dataOutputStream = new DataOutputStream(conexion.getOutputStream())) {
                dataOutputStream.write(datosDelMensaje);
                dataOutputStream.flush();
                dataOutputStream.close();

                int codigoDeRespuesta = conexion.getResponseCode();
                if (codigoDeRespuesta != 200) {
                    LogLog.warn("La respuesta falló. Quizá no ha comenzado una conversación con su bot.");
                }
          } catch (IOException ioException) {
                LogLog.warn(String.format("Fallo en la entrega del mensaje: %s", layout.format(evento)), 
                        ioException);
          }
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getChatid() {
        return chatid;
    }

    public void setChatid(long chatid) {
        this.chatid = chatid;
    }
  
}
