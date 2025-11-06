package ec.edu.monster.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;
import ec.edu.monster.controller.MainController;

public class MainFrame extends JFrame {

    private JComboBox<String> comboTipo;
    private JTextField valorField;
    private JButton convertirButton;
    private JButton cerrarSesionButton;
    private JLabel resultadoLabel;
    private JLabel unidadLabel;

    private final Map<String, String> tipoMap = new LinkedHashMap<>();
    private final Map<String, String> unidadFinalMap = new LinkedHashMap<>();

    public MainFrame() {
        setTitle("Conversor de Unidades - Cliente SOAP");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Imagen de fondo
        ImageIcon icon = new ImageIcon(getClass().getResource("/ec/edu/monster/view/monstersinc2.jpg"));
        Image scaledImage = icon.getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH);
        ImageIcon backgroundIcon = new ImageIcon(scaledImage);

        // Panel principal con imagen de fondo estable
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0, 0, 0, 100)); // overlay oscuro para contraste
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        inicializarMapas();

        // Panel de formulario flotante
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(80, 100, 540, 300);
        formPanel.setOpaque(false); // transparente para evitar el fondo blanco
        formPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        // Fondo translúcido como capa adicional (sin interferir con repintado)
        JLabel glassBg = new JLabel();
        glassBg.setOpaque(true);
        glassBg.setBackground(new Color(255, 255, 255, 180));
        glassBg.setBounds(0, 0, 540, 300);
        formPanel.add(glassBg);
        formPanel.setComponentZOrder(glassBg, formPanel.getComponentCount() - 1);

        JLabel titulo = new JLabel("Conversor de Unidades");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 144, 255));
        titulo.setBounds(150, 20, 300, 30);
        formPanel.add(titulo);

        JLabel tipoLabel = new JLabel("Tipo de conversión:");
        tipoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        tipoLabel.setForeground(Color.DARK_GRAY);
        tipoLabel.setBounds(40, 80, 200, 25);
        formPanel.add(tipoLabel);

        comboTipo = new JComboBox<>(tipoMap.keySet().toArray(new String[0]));
        comboTipo.setBounds(230, 80, 250, 30);
        comboTipo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(comboTipo);

        JLabel valorLabel = new JLabel("Valor:");
        valorLabel.setFont(new Font("Arial", Font.BOLD, 15));
        valorLabel.setForeground(Color.DARK_GRAY);
        valorLabel.setBounds(40, 130, 100, 25);
        formPanel.add(valorLabel);

        valorField = new JTextField();
        valorField.setBounds(230, 130, 250, 30);
        valorField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(valorField);

        convertirButton = new JButton("Convertir");
        convertirButton.setBounds(200, 190, 140, 40);
        convertirButton.setBackground(new Color(30, 144, 255));
        convertirButton.setForeground(Color.WHITE);
        convertirButton.setFont(new Font("Arial", Font.BOLD, 16));
        convertirButton.setFocusPainted(false);
        convertirButton.setBorder(BorderFactory.createEmptyBorder());
        convertirButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efecto hover moderno
        convertirButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                convertirButton.setBackground(new Color(0, 120, 215));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                convertirButton.setBackground(new Color(30, 144, 255));
            }
        });

        formPanel.add(convertirButton);

        resultadoLabel = new JLabel("Resultado:");
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 17));
        resultadoLabel.setForeground(Color.DARK_GRAY);
        resultadoLabel.setBounds(40, 250, 200, 25);
        formPanel.add(resultadoLabel);

        unidadLabel = new JLabel("");
        unidadLabel.setFont(new Font("Arial", Font.BOLD, 17));
        unidadLabel.setForeground(Color.DARK_GRAY);
        unidadLabel.setBounds(230, 250, 150, 25);
        formPanel.add(unidadLabel);

        // Mover el fondo translúcido al fondo real del form
        formPanel.setComponentZOrder(glassBg, formPanel.getComponentCount() - 1);

        panel.add(formPanel);

        cerrarSesionButton = new JButton("Cerrar sesión");
        cerrarSesionButton.setBounds(550, 15, 120, 30);
        cerrarSesionButton.setBackground(new Color(220, 20, 60));
        cerrarSesionButton.setForeground(Color.WHITE);
        cerrarSesionButton.setFont(new Font("Arial", Font.BOLD, 13));
        cerrarSesionButton.setFocusPainted(false);
        cerrarSesionButton.setBorder(BorderFactory.createEmptyBorder());
        cerrarSesionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cerrarSesionButton.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?",
                    "Cerrar sesión", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginFrame().setVisible(true);
            }
        });

        panel.add(cerrarSesionButton);
        add(panel);

        // Controlador del botón convertir
        new MainController(this);

        valorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    convertirButton.doClick();
            }
        });
    }

    private void inicializarMapas() {
        tipoMap.put("Kilómetros → millas", "km_a_millas");
        tipoMap.put("Metros → pies", "m_a_pies");
        tipoMap.put("Pulgadas → centímetros", "in_a_cm");
        tipoMap.put("Kilogramos → libras", "kg_a_lb");
        tipoMap.put("Gramos → onzas", "g_a_oz");
        tipoMap.put("Libras → kilogramos", "lb_a_kg");
        tipoMap.put("Celsius → Fahrenheit", "c_a_f");
        tipoMap.put("Fahrenheit → Celsius", "f_a_c");
        tipoMap.put("Celsius → Kelvin", "c_a_k");

        unidadFinalMap.put("km_a_millas", "mi");
        unidadFinalMap.put("m_a_pies", "ft");
        unidadFinalMap.put("in_a_cm", "cm");
        unidadFinalMap.put("kg_a_lb", "lb");
        unidadFinalMap.put("g_a_oz", "oz");
        unidadFinalMap.put("lb_a_kg", "kg");
        unidadFinalMap.put("c_a_f", "°F");
        unidadFinalMap.put("f_a_c", "°C");
        unidadFinalMap.put("c_a_k", "K");
    }

    public String getTipoSeleccionado() {
        String amigable = (String) comboTipo.getSelectedItem();
        return tipoMap.get(amigable);
    }

    public Double getValorIngresado() {
        try {
            String text = valorField.getText().trim();
            if (text.isEmpty()) throw new NumberFormatException();
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            mostrarError("Ingrese un valor numérico válido.");
            return null;
        }
    }

    public void mostrarResultado(double r) {
        String tipo = getTipoSeleccionado();
        resultadoLabel.setText("Resultado: " + String.format("%.6f", r));
        unidadLabel.setText(unidadFinalMap.get(tipo));
        // Forzar repintado limpio sin fondo blanco
        resultadoLabel.repaint();
        unidadLabel.repaint();
    }

    public void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setConvertAction(java.awt.event.ActionListener listener) {
        convertirButton.addActionListener(listener);
    }
}
