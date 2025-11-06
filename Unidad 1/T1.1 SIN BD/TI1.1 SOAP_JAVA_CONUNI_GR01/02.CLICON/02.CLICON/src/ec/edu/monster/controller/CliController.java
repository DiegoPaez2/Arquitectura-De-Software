package ec.edu.monster.controller;

import ec.edu.monster.model.SoapClient;
import ec.edu.monster.model.User;
import ec.edu.monster.view.ConsoleView;

public class CliController {

    private final ConsoleView view = new ConsoleView();

    private static boolean isLoginOk(String s) {
        if (s == null) return false;
        String t = s.trim().toLowerCase();
        return t.equals("success") || t.equals("true") || t.contains("autentic");
    }

    public void run() {
        try {
            view.title();

            String wsdl = view.askWsdl();
            String endpoint = view.askEndpoint();

            SoapClient client = new SoapClient(wsdl, endpoint);

            // ===== LOGIN =====
            String u = view.ask("Usuario");
            String p = view.ask("Contraseña");

            String resp = client.login(u, p);
            view.println("Respuesta login: " + resp);

            if (!isLoginOk(resp)) {
                view.error("❌ Login inválido. Fin del programa.");
                return;
            }

            User user = new User(u, p);
            user.setAuthenticated(true);
            view.println("✅ Sesión iniciada.\n");

            // ===== MENÚ =====
            while (true) {
                int op = view.menu();
                switch (op) {
                    case 1: {
                        double c = view.askDouble("Celsius");
                        view.println(String.format("→ %.4f °F", client.celsiusAfahrenheit(c)));
                        break;
                    }
                    case 2: {
                        double f = view.askDouble("Fahrenheit");
                        view.println(String.format("→ %.4f °C", client.fahrenheitAcelsius(f)));
                        break;
                    }
                    case 3: {
                        double c = view.askDouble("Celsius");
                        view.println(String.format("→ %.4f K", client.celsiusAkelvin(c)));
                        break;
                    }
                    case 4: {
                        double in = view.askDouble("Pulgadas");
                        view.println(String.format("→ %.4f cm", client.pulgadasAcentimetros(in)));
                        break;
                    }
                    case 5: {
                        double m = view.askDouble("Metros");
                        view.println(String.format("→ %.4f ft", client.metrosApies(m)));
                        break;
                    }
                    case 6: {
                        double km = view.askDouble("Kilómetros");
                        view.println(String.format("→ %.4f mi", client.kilometrosAmillas(km)));
                        break;
                    }
                    case 7: {
                        double lb = view.askDouble("Libras");
                        view.println(String.format("→ %.4f kg", client.librasAkilogramos(lb)));
                        break;
                    }
                    case 8: {
                        double kg = view.askDouble("Kilogramos");
                        view.println(String.format("→ %.4f lb", client.kilogramosAlibras(kg)));
                        break;
                    }
                    case 9: {
                        double g = view.askDouble("Gramos");
                        view.println(String.format("→ %.4f oz", client.gramosAonzas(g)));
                        break;
                    }
                    case 10:
                        view.println("👋 Cerrando sesión. ¡Hasta luego!");
                        return;
                    default:
                        view.error("Opción inválida. Intenta otra vez.");
                }
            }

        } catch (Exception e) {
            view.error("💥 Error en la ejecución: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
