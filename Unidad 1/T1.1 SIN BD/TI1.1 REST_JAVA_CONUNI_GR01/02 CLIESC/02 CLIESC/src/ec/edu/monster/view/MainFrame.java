package ec.edu.monster.view;

import ec.edu.monster.controller.ConversionController;
import ec.edu.monster.model.ConversionResult;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final ConversionController ctrl = new ConversionController();

    private final JComboBox<String> cboTipo = new JComboBox<>(new String[]{
            // LONGITUD
            "Kilómetros → Millas",
            "Metros → Pies",
            "Pulgadas → Centímetros",
            // MASA
            "Kilogramos → Libras",
            "Gramos → Onzas",
            "Libras → Kilogramos",
            // TEMPERATURA
            "Celsius → Fahrenheit",
            "Fahrenheit → Celsius",
            "Celsius → Kelvin"
    });

    private final JTextField txtValor = new JTextField();
    private final JButton btnConvertir = new JButton("Convertir");
    private final JTextArea txtSalida = new JTextArea(6, 30);
    private final JLabel lblEstado = new JLabel("Listo", SwingConstants.LEFT);

    public MainFrame(String usuario) {
        setTitle("02_CLIESC - Conversor (Usuario: " + usuario + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);

        JPanel content = new LoginFrame.ImagePanel("/ec/edu/monster/view/fondo2.png");
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = stylizedLabel("Conversor de Unidades (REST)", 22, Color.WHITE);

        txtSalida.setEditable(false);
        txtSalida.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(txtSalida);

        // Fila 0
        c.gridx=0; c.gridy=0; c.gridwidth=3;
        content.add(title, c);

        // Fila 1
        c.gridy=1; c.gridwidth=1;
        content.add(stylizedLabel("Tipo de conversión:", 14, Color.WHITE), c);
        c.gridx=1; c.weightx=1.0;
        content.add(cboTipo, c);
        c.gridx=2; c.weightx=0;
        JButton btnPing = new JButton("Probar Servidor");
        content.add(btnPing, c);

        // Fila 2
        c.gridx=0; c.gridy=2;
        content.add(stylizedLabel("Valor:", 14, Color.WHITE), c);
        c.gridx=1;
        content.add(txtValor, c);
        c.gridx=2;
        content.add(btnConvertir, c);

        // Fila 3 (salida)
        c.gridx=0; c.gridy=3; c.gridwidth=3; c.fill = GridBagConstraints.BOTH; c.weighty = 1.0;
        content.add(scroll, c);

        // Fila 4 (estado)
        c.gridx=0; c.gridy=4; c.gridwidth=3; c.fill = GridBagConstraints.HORIZONTAL; c.weighty=0;
        lblEstado.setForeground(Color.YELLOW);
        content.add(lblEstado, c);

        setContentPane(content);

        // Actions
        btnConvertir.addActionListener(e -> convertir());
        btnPing.addActionListener(e -> {
            boolean ok = ctrl.ping();
            lblEstado.setText(ok ? "✓ Servidor REST activo" : "✗ No responde (revisar Payara/WAR)");
        });
        getRootPane().setDefaultButton(btnConvertir);
    }

    private void convertir() {
        String sel = (String) cboTipo.getSelectedItem();
        String raw = txtValor.getText().trim().replace(',', '.');

        if (raw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double val;
        try { val = Double.parseDouble(raw); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor no numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ConversionResult r;
            switch (sel) {
                case "Kilómetros → Millas":
                    r = ctrl.kilometrosAMillas(val); break;
                case "Metros → Pies":
                    r = ctrl.metrosAPies(val); break;
                case "Pulgadas → Centímetros":
                    r = ctrl.pulgadasACentimetros(val); break;
                case "Kilogramos → Libras":
                    r = ctrl.kilogramosALibras(val); break;
                case "Gramos → Onzas":
                    r = ctrl.gramosAOnzas(val); break;
                case "Libras → Kilogramos":
                    r = ctrl.librasAKilogramos(val); break;
                case "Celsius → Fahrenheit":
                    r = ctrl.celsiusAFahrenheit(val); break;
                case "Fahrenheit → Celsius":
                    r = ctrl.fahrenheitACelsius(val); break;
                case "Celsius → Kelvin":
                    r = ctrl.celsiusAKelvin(val); break;
                default:
                    throw new IllegalStateException("Selección desconocida");
            }
            txtSalida.append(r.toString() + "\n");
            lblEstado.setText("OK");
        } catch (RuntimeException ex) {
            lblEstado.setText("Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error REST", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel stylizedLabel(String text, int size, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD, size));
        l.setForeground(color);
        return l;
    }
}
