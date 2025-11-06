package ec.edu.monster.controller;

import ec.edu.monster.model.ConversionModel;
import ec.edu.monster.wsclient.WSConversionUnidades;
import ec.edu.monster.wsclient.WSConversionUnidades_Service;

public class ConversionController {

    private WSConversionUnidades_Service service;
    private WSConversionUnidades port;

    public ConversionController() {
        service = new WSConversionUnidades_Service();
        port = service.getWSConversionUnidadesPort();
    }

    public boolean login(String usuario, String contrasenia) {
        try {
            String respuesta = port.login(usuario, contrasenia);
            System.out.println("[CLIENTE] Respuesta del WS: " + respuesta);
            return "SUCCESS".equalsIgnoreCase(respuesta.trim());
        } catch (Exception e) {
            System.err.println("❌ Error en login: " + e.getMessage());
            return false;
        }
    }

    public double convertir(String tipo, double valorEntrada) {
        double salida = 0;

        switch (tipo) {
            case "Kilogramos a Libras":
                salida = port.kilogramosAlibras(valorEntrada);
                break;
            case "Libras a Kilogramos":
                salida = port.librasAkilogramos(valorEntrada);
                break;
            case "Gramos a Onzas":
                salida = port.gramosAonzas(valorEntrada);
                break;
            case "Celsius a Fahrenheit":
                salida = port.celsiusAfahrenheit(valorEntrada);
                break;
            case "Celsius a Kelvin":
                salida = port.celsiusAkelvin(valorEntrada);
                break;
            case "Fahrenheit a Celsius":
                salida = port.fahrenheitAcelsius(valorEntrada);
                break;
            case "Kilómetros a Millas":
                salida = port.kilometrosAmillas(valorEntrada);
                break;
            case "Metros a Pies":
                salida = port.metrosApies(valorEntrada);
                break;
            case "Pulgadas a Centímetros":
                salida = port.pulgadasAcentimetros(valorEntrada);
                break;
            default:
                throw new IllegalArgumentException("Tipo de conversión no válida");
        }

        return salida;
    }
}
