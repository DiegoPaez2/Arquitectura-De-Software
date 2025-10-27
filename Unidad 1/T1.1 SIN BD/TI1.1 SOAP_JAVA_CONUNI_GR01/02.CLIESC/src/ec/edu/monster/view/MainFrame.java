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

        JPanel panel = new JPanel(){
            @Override protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0,0,new Color(30,144,255),
                        0,getHeight(),new Color(135,206,250));
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };
        panel.setLayout(null);

        // Logo
        ImageIcon icon = new ImageIcon(getClass().getResource("/ec/edu/monster/view/monstersinc2.jpg"));
        int iw = icon.getIconWidth(), ih = icon.getIconHeight();
        double scale = Math.min(600.0 / iw, 200.0 / ih);
        Image scaled = icon.getImage().getScaledInstance((int)(iw*scale), (int)(ih*scale), Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaled));
        logoLabel.setBounds((700 - (int)(iw*scale))/2, 10, (int)(iw*scale), (int)(ih*scale));
        panel.add(logoLabel);

        inicializarMapas();

        JLabel tipoLabel = new JLabel("Tipo de conversión:");
        tipoLabel.setForeground(Color.WHITE);
        tipoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tipoLabel.setBounds(50, 230, 200, 25);
        panel.add(tipoLabel);

        comboTipo = new JComboBox<>(tipoMap.keySet().toArray(new String[0]));
        comboTipo.setBounds(250, 230, 300, 30);
        panel.add(comboTipo);

        JLabel valorLabel = new JLabel("Valor:");
        valorLabel.setForeground(Color.WHITE);
        valorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        valorLabel.setBounds(50, 280, 100, 25);
        panel.add(valorLabel);

        valorField = new JTextField();
        valorField.setBounds(250, 280, 300, 30);
        panel.add(valorField);

        convertirButton = new JButton("Convertir");
        convertirButton.setBounds(250, 330, 150, 40);
        convertirButton.setBackground(new Color(255, 165, 0));
        convertirButton.setForeground(Color.WHITE);
        convertirButton.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(convertirButton);

        cerrarSesionButton = new JButton("Cerrar sesión");
        cerrarSesionButton.setBounds(550, 10, 120, 30);
        cerrarSesionButton.setBackground(new Color(220, 20, 60));
        cerrarSesionButton.setForeground(Color.WHITE);
        cerrarSesionButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(cerrarSesionButton);

        cerrarSesionButton.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?",
                    "Cerrar sesión", JOptionPane.YES_NO_OPTION);
            if(resp == JOptionPane.YES_OPTION){
                this.dispose();
                new LoginFrame().setVisible(true);
            }
        });

        resultadoLabel = new JLabel("Resultado:");
        resultadoLabel.setForeground(Color.WHITE);
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultadoLabel.setBounds(50, 400, 200, 30);
        panel.add(resultadoLabel);

        unidadLabel = new JLabel("");
        unidadLabel.setForeground(Color.WHITE);
        unidadLabel.setFont(new Font("Arial", Font.BOLD, 18));
        unidadLabel.setBounds(260, 400, 80, 30);
        panel.add(unidadLabel);

        add(panel);

        new MainController(this); // engancha acción
        valorField.addKeyListener(new KeyAdapter(){
            @Override public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER) convertirButton.doClick();
            }
        });
    }

    private void inicializarMapas(){
        // nombres amigables -> claves internas usadas en el controlador
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

    public String getTipoSeleccionado(){
        String amigable = (String) comboTipo.getSelectedItem();
        return tipoMap.get(amigable);
    }

    public Double getValorIngresado(){
        try{
            String text = valorField.getText().trim();
            if(text.isEmpty()) throw new NumberFormatException();
            return Double.parseDouble(text);
        }catch(NumberFormatException e){
            mostrarError("Ingrese un valor numérico válido.");
            return null;
        }
    }

    public void mostrarResultado(double r){
        String tipo = getTipoSeleccionado();
        resultadoLabel.setText("Resultado: " + String.format("%.6f", r));
        unidadLabel.setText(unidadFinalMap.get(tipo));
    }

    public void mostrarError(String msg){
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setConvertAction(java.awt.event.ActionListener listener){
        convertirButton.addActionListener(listener);
    }
}
