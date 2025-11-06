package ec.edu.monster.view;

import ec.edu.monster.controller.ConversionController;
import java.util.Scanner;

public class ConversionConsole {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConversionController controller = new ConversionController();

        System.out.println("===== CLIENTE SOAP - CONVERSOR DE UNIDADES =====");

        // LOGIN
        System.out.print("Usuario: ");
        String user = sc.nextLine();

        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        boolean loginOK = controller.login(user, pass);

        if (!loginOK) {
            System.out.println("❌ Acceso denegado. Fin del programa.");
            return;
        }

        System.out.println("✅ Login correcto. Puede realizar conversiones.\n");

        // MENÚ DE CONVERSIONES
        String[] opciones = {
            "Kilogramos a Libras",
            "Libras a Kilogramos",
            "Gramos a Onzas",
            "Celsius a Fahrenheit",
            "Celsius a Kelvin",
            "Fahrenheit a Celsius",
            "Kilómetros a Millas",
            "Metros a Pies",
            "Pulgadas a Centímetros"
        };

        while (true) {
            System.out.println("=== Seleccione tipo de conversión ===");
            for (int i = 0; i < opciones.length; i++) {
                System.out.println((i + 1) + ". " + opciones[i]);
            }
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            if (opcion == 0) {
                System.out.println("👋 Gracias por usar el cliente SOAP.");
                break;
            }

            if (opcion < 1 || opcion > opciones.length) {
                System.out.println("⚠️ Opción no válida.");
                continue;
            }

            String tipo = opciones[opcion - 1];
            System.out.print("Ingrese valor: ");
            double valor = sc.nextDouble();
            sc.nextLine();

            try {
                double resultado = controller.convertir(tipo, valor);
                System.out.printf("Resultado de %s: %.4f%n%n", tipo, resultado);
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }

        sc.close();
    }
}
