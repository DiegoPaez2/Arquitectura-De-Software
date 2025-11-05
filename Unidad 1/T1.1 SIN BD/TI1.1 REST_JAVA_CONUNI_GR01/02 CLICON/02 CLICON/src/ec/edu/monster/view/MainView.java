package ec.edu.monster.view;

import ec.edu.monster.auth.AuthService;
import ec.edu.monster.controller.ConversionController;
import ec.edu.monster.model.ConversionResult;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {

    private final AuthService auth = new AuthService();
    private final ConversionController ctrl = new ConversionController();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        System.out.println("=== CLIENTE REST 02_CLICON ===");

        // ---- LOGIN QUEMADO ----
        System.out.print("Usuario: ");
        String u = sc.nextLine();
        System.out.print("Contraseña: ");
        String p = sc.nextLine();

        if (!auth.login(u, p)) {
            System.out.println("❌ Usuario o contraseña incorrectos.");
            return;
        }
        System.out.println("✅ Autenticación exitosa.\n");

        int op;
        do {
            menu();
            op = readInt("Opción: ");

            try {
                switch (op) {
                    // LONGITUD
                    case 1: {
                        double km = readDouble("Kilómetros: ");
                        ConversionResult r = ctrl.kilometrosAMillas(km);
                        System.out.println(r);
                        break;
                    }
                    case 2: {
                        double m = readDouble("Metros: ");
                        ConversionResult r = ctrl.metrosAPies(m);
                        System.out.println(r);
                        break;
                    }
                    case 3: {
                        double pgi = readDouble("Pulgadas: ");
                        ConversionResult r = ctrl.pulgadasACentimetros(pgi);
                        System.out.println(r);
                        break;
                    }

                    // MASA
                    case 4: {
                        double kg = readDouble("Kilogramos: ");
                        ConversionResult r = ctrl.kilogramosALibras(kg);
                        System.out.println(r);
                        break;
                    }
                    case 5: {
                        double g = readDouble("Gramos: ");
                        ConversionResult r = ctrl.gramosAOnzas(g);
                        System.out.println(r);
                        break;
                    }
                    case 6: {
                        double lb = readDouble("Libras: ");
                        ConversionResult r = ctrl.librasAKilogramos(lb);
                        System.out.println(r);
                        break;
                    }

                    // TEMPERATURA
                    case 7: {
                        double c1 = readDouble("Celsius: ");
                        ConversionResult r = ctrl.celsiusAFahrenheit(c1);
                        System.out.println(r);
                        break;
                    }
                    case 8: {
                        double f = readDouble("Fahrenheit: ");
                        ConversionResult r = ctrl.fahrenheitACelsius(f);
                        System.out.println(r);
                        break;
                    }
                    case 9: {
                        double c2 = readDouble("Celsius: ");
                        ConversionResult r = ctrl.celsiusAKelvin(c2);
                        System.out.println(r);
                        break;
                    }

                    case 0:
                        System.out.println("👋 Saliendo…");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            } catch (RuntimeException ex) {
                System.out.println("⚠️  Error en consumo: " + ex.getMessage());
            }

            System.out.println();
        } while (op != 0);
    }

    private void menu() {
        System.out.println("----------- MENÚ -----------");
        System.out.println("1) Kilómetros → Millas");
        System.out.println("2) Metros → Pies");
        System.out.println("3) Pulgadas → Centímetros");
        System.out.println("4) Kilogramos → Libras");
        System.out.println("5) Gramos → Onzas");
        System.out.println("6) Libras → Kilogramos");
        System.out.println("7) Celsius → Fahrenheit");
        System.out.println("8) Fahrenheit → Celsius");
        System.out.println("9) Celsius → Kelvin");
        System.out.println("0) Salir");
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int v = sc.nextInt();
                sc.nextLine(); // limpiar fin de línea
                return v;
            } catch (InputMismatchException e) {
                System.out.println("Ingrese un número entero válido.");
                sc.nextLine();
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double v = sc.nextDouble();
                sc.nextLine(); // limpiar fin de línea
                return v;
            } catch (InputMismatchException e) {
                System.out.println("Ingrese un número válido.");
                sc.nextLine();
            }
        }
    }
}
