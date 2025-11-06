package ec.edu.monster.controller;

import ec.edu.monster.model.ConversionModel;

public class ConversionController {

    private final ec.edu.monster.controller.SoapClientManual client;

    public ConversionController(String serviceUrl) {
        this.client = new ec.edu.monster.controller.SoapClientManual(serviceUrl);
    }

    // === LOGIN ===
    public boolean login(String usuario, String contrasena) throws Exception {
        String soapBody =
                "<ws:login>"
                        + "<arg0>" + escape(usuario) + "</arg0>"
                        + "<arg1>" + escape(contrasena) + "</arg1>"
                        + "</ws:login>";

        String resp = client.call(soapBody);
        return resp.contains("SUCCESS");
    }

    // === CONVERSIONES ===
    public double convertir(ConversionModel model) throws Exception {
        String metodo = mapMetodo(model.getTipoConversion());
        String soapBody =
                "<ws:" + metodo + ">"
                        + "<arg0>" + model.getValorEntrada() + "</arg0>"
                        + "</ws:" + metodo + ">";

        String resp = client.call(soapBody);
        String value = ec.edu.monster.controller.SoapClientManual.extractReturn(resp);
        double out = Double.parseDouble(value);
        model.setValorSalida(out);
        return out;
    }

    private String mapMetodo(String tipo) {
        switch (tipo) {
            case "Kilogramos a Libras":   return "kilogramosAlibras";
            case "Libras a Kilogramos":   return "librasAkilogramos";
            case "Gramos a Onzas":        return "gramosAonzas";
            case "Celsius a Fahrenheit":  return "celsiusAfahrenheit";
            case "Celsius a Kelvin":      return "celsiusAkelvin";
            case "Fahrenheit a Celsius":  return "fahrenheitAcelsius";
            case "Kilómetros a Millas":   return "kilometrosAmillas";
            case "Metros a Pies":         return "metrosApies";
            case "Pulgadas a Centímetros":return "pulgadasAcentimetros";
            default: throw new IllegalArgumentException("Tipo de conversión no válida");
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}
