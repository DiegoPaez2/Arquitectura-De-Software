package ec.edu.monster.view;

import java.util.Locale;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public void title() {
        println("======================================");
        println("  SOAP en Java - Consola (Cliente)");
        println("======================================");
    }

    public String askWsdl() {
        print("WSDL [ENTER=default]: ");
        String s = sc.nextLine().trim();
        if (s.isEmpty()) {
            s = "http://localhost:8080/WS_CONUNI_SOAPJAVA_GRO01/WS_Conversion_Unidades?wsdl";
        }
        return s;
    }

    public String askEndpoint() {
        print("Endpoint [ENTER=default]: ");
        String s = sc.nextLine().trim();
        if (s.isEmpty()) {
            s = "http://localhost:8080/WS_CONUNI_SOAPJAVA_GRO01/WS_Conversion_Unidades";
        }
        return s;
    }

    public String ask(String label) {
        print(label + ": ");
        return sc.nextLine().trim();
    }

    public double askDouble(String label) {
        while (true) {
            try {
                print(label + ": ");
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                error("Valor numérico inválido. Intenta de nuevo.");
            }
        }
    }

    public int menu() {
        println("\n----- MENÚ -----");
        println(" 1) Celsius → Fahrenheit");
        println(" 2) Fahrenheit → Celsius");
        println(" 3) Celsius → Kelvin");
        println(" 4) Pulgadas → Centímetros");
        println(" 5) Metros → Pies");
        println(" 6) Kilómetros → Millas");
        println(" 7) Libras → Kilogramos");
        println(" 8) Kilogramos → Libras");
        println(" 9) Gramos → Onzas");
        println("10) Cerrar sesión / Salir");
        print("Elige opción: ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void println(String s) { System.out.println(s); }
    public void print(String s)   { System.out.print(s); }
    public void error(String s)   { System.err.println(s); }
}
