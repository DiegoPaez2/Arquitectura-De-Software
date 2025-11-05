package ec.edu.monster.controller;

import ec.edu.monster.model.ConversionResult;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversionController {

    // Ajusta si cambian puerto/contexto:
    private static final String BASE_URL =
            "http://localhost:8080/WS_CONUNI_RESTJAVA_GRO01/api/conversion/";

    // Extrae el primer número (sirve si viene JSON o texto)
    private static double parseNumber(String body) {
        try { return Double.parseDouble(body.trim()); } catch (Exception ignore) {}
        Pattern p = Pattern.compile("[-+]?\\d*\\.?\\d+(?:[eE][-+]?\\d+)?");
        Matcher m = p.matcher(body);
        if (m.find()) return Double.parseDouble(m.group());
        throw new RuntimeException("No pude interpretar número en la respuesta: " + body);
    }

    private String getBody(String endpoint) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "*/*"); // evita 406

            int code = conn.getResponseCode();
            if (code != 200) return "HTTP " + code;

            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
            } finally { conn.disconnect(); }
            return sb.toString();
        } catch (Exception e) {
            return "ERR " + e.getMessage();
        }
    }

    private ConversionResult build(String tipo, String queryName, double valor, String path) {
        String body = getBody(path + "?" + queryName + "=" + valor);
        if (body.startsWith("HTTP") || body.startsWith("ERR")) {
            throw new RuntimeException("Fallo consumiendo " + tipo + ": " + body);
        }
        double out = parseNumber(body);
        return new ConversionResult(tipo, valor, out);
    }

    // ===== LONGITUD =====
    public ConversionResult kilometrosAMillas(double km) {
        return build("Kilómetros a Millas", "km", km, "kilometros-a-millas");
    }
    public ConversionResult metrosAPies(double m) {
        return build("Metros a Pies", "m", m, "metros-a-pies");
    }
    public ConversionResult pulgadasACentimetros(double p) {
        return build("Pulgadas a Centímetros", "p", p, "pulgadas-a-centimetros");
    }

    // ===== MASA =====
    public ConversionResult kilogramosALibras(double kg) {
        return build("Kilogramos a Libras", "kg", kg, "kilogramos-a-libras");
    }
    public ConversionResult gramosAOnzas(double g) {
        return build("Gramos a Onzas", "g", g, "gramos-a-onzas");
    }
    public ConversionResult librasAKilogramos(double lb) {
        return build("Libras a Kilogramos", "lb", lb, "libras-a-kilogramos");
    }

    // ===== TEMPERATURA =====
    public ConversionResult celsiusAFahrenheit(double c) {
        return build("Celsius a Fahrenheit", "c", c, "celsius-a-fahrenheit");
    }
    public ConversionResult fahrenheitACelsius(double f) {
        return build("Fahrenheit a Celsius", "f", f, "fahrenheit-a-celsius");
    }
    public ConversionResult celsiusAKelvin(double c) {
        return build("Celsius a Kelvin", "c", c, "celsius-a-kelvin");
    }

    // ping opcional (si lo usas)
    public boolean ping() {
        String body = getBody("ping");
        return !(body.startsWith("HTTP") || body.startsWith("ERR"));
    }
}
