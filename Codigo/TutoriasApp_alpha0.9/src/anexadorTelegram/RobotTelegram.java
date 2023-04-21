package anexadorTelegram;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RobotTelegram {

    private final static String URL_BASE = "https://api.telegram.org/bot";

    private String token;
    private long chatId;

    public RobotTelegram(String token, long chatId) {
        this.token = token;
        this.chatId = chatId;
    }

    public int mandarMensaje(String mensaje) throws IOException {
        return mandarMensaje(getToken(), getChatId(), mensaje);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        RobotTelegram telegramBot = (RobotTelegram) objeto;
        return Objects.equals(token, telegramBot.token) &&
            Objects.equals(chatId, telegramBot.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, chatId);
    }

    @Override
    public String toString() {
        return "TelegramBot{" +
            "token='" + token + '\'' +
            ", chatId='" + chatId + '\'' +
            '}';
    }

    public static int mandarMensaje(String token, long chatId, String mensaje) throws IOException {
        String peticion = String.format("%s%s/sendMessage", URL_BASE, token);
        URL localizadorRecursos = new URL(peticion);
        HttpsURLConnection conexion = (HttpsURLConnection) localizadorRecursos.openConnection();

        String parametros = String.format("chat_id=%s&text=%s", chatId, mensaje);
        byte[] datosDelMensaje = parametros.getBytes(StandardCharsets.UTF_8);
        int longitudDeDatosDelMensaje = datosDelMensaje.length;

        conexion.setDoOutput(true);
        conexion.setInstanceFollowRedirects(false);
        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexion.setRequestProperty("charset", "utf-8");
        conexion.setRequestProperty("Content-Length", Integer.toString(longitudDeDatosDelMensaje));

        try (DataOutputStream dataOutputStream = new DataOutputStream(conexion.getOutputStream())) {
            dataOutputStream.write(datosDelMensaje);
            dataOutputStream.flush();
            dataOutputStream.close();
        }

        return conexion.getResponseCode();
    }
}
