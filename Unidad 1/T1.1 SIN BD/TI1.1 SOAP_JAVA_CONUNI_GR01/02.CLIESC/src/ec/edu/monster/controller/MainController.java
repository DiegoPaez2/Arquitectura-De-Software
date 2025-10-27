package ec.edu.monster.controller;

import ec.edu.monster.model.ConversorModel;
import ec.edu.monster.service.ConversionService;
import ec.edu.monster.view.MainFrame;

import java.awt.event.ActionListener;

/**
 * Controlador de la pantalla principal (conversión de unidades).
 * Compatible con Java 8/11/17 (usa switch clásico).
 */
public class MainController {

    private final MainFrame view;
    private final ConversionService service = new ConversionService();

    public MainController(MainFrame view) {
        this.view = view;

        // Conectar la acción del botón "Convertir"
        ActionListener listener = e -> convertir();
        this.view.setConvertAction(listener);
    }

    private void convertir() {
        // Lee selección y valor desde la vista
        String tipo = view.getTipoSeleccionado();
        Double valor = view.getValorIngresado();
        if (valor == null) return; // la vista ya mostró el error

        ConversorModel m = new ConversorModel(tipo, valor);

        try {
            double r;

            // ==== switch clásico compatible con cualquier JDK ====
            switch (tipo) {
                // ----- LONGITUD -----
                case "km_a_millas":
                    r = service.kilometrosAmillas(m.getValor());
                    break;
                case "m_a_pies":
                    r = service.metrosApies(m.getValor());
                    break;
                case "in_a_cm":
                    r = service.pulgadasAcentimetros(m.getValor());
                    break;

                // ----- MASA -----
                case "kg_a_lb":
                    r = service.kilogramosAlibras(m.getValor());
                    break;
                case "g_a_oz":
                    r = service.gramosAonzas(m.getValor());
                    break;
                case "lb_a_kg":
                    r = service.librasAkilogramos(m.getValor());
                    break;

                // ----- TEMPERATURA -----
                case "c_a_f":
                    r = service.celsiusAfahrenheit(m.getValor());
                    break;
                case "f_a_c":
                    r = service.fahrenheitAcelsius(m.getValor());
                    break;
                case "c_a_k":
                    r = service.celsiusAkelvin(m.getValor());
                    break;

                default:
                    throw new IllegalArgumentException("Tipo no soportado: " + tipo);
            }

            m.setResultado(r);
            view.mostrarResultado(r);

        } catch (Exception ex) {
            view.mostrarError("Error llamando al servicio SOAP: " + ex.getMessage());
        }
    }
}
