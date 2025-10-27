package ec.edu.monster.prueba;

import ec.edu.monster.controlador.ConversionController;

public class PruebaConversiones {
    public static void main(String[] args) {
        ConversionController conversion = new ConversionController();

        // === Longitud ===
        System.out.println("10 km → " + conversion.kilometrosAMillas(10) + " millas");
        System.out.println("3 metros → " + conversion.metrosAPies(3) + " pies");
        System.out.println("5 pulgadas → " + conversion.pulgadasACentimetros(5) + " cm");

        // === Masa ===
        System.out.println("2 kg → " + conversion.kilogramosALibras(2) + " libras");
        System.out.println("500 g → " + conversion.gramosAOnzas(500) + " onzas");
        System.out.println("10 lb → " + conversion.librasAKilogramos(10) + " kg");

        // === Temperatura ===
        System.out.println("0 °C → " + conversion.celsiusAFahrenheit(0) + " °F");
        System.out.println("32 °F → " + conversion.fahrenheitACelsius(32) + " °C");
        System.out.println("25 °C → " + conversion.celsiusAKelvin(25) + " K");
    }
}
