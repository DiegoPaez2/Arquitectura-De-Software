package ec.edu.monster.view;

import javax.swing.*;
import java.awt.*;
import ec.edu.monster.controller.LoginController;

public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Login Cliente SOAP");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Imagen de fondo
        ImageIcon icon = new ImageIcon(getClass().getResource("/ec/edu/monster/view/monstersinc.jpg"));
        Image scaled = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        ImageIcon backgroundIcon = new ImageIcon(scaled);

        // Panel con fondo personalizado
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                Graphics2D g2 = (Graphics2D) g;
                // Capa negra translúcida para contraste
                g2.setColor(new Color(0, 0, 0, 100));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        // Panel de login semitransparente
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(70, 120, 360, 270);
        loginPanel.setBackground(new Color(255, 255, 255, 190));
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 180), 2, true));

        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 144, 255));
        titulo.setBounds(110, 20, 200, 30);
        loginPanel.add(titulo);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 15));
        userLabel.setForeground(Color.DARK_GRAY);
        userLabel.setBounds(40, 80, 100, 25);
        loginPanel.add(userLabel);

        userField = new JTextField();
        userField.setBounds(140, 80, 180, 30);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setBackground(new Color(255, 255, 255, 200));
        userField.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2, true));
        loginPanel.add(userField);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passLabel.setForeground(Color.DARK_GRAY);
        passLabel.setBounds(40, 130, 100, 25);
        loginPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(140, 130, 180, 30);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBackground(new Color(255, 255, 255, 200));
        passField.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2, true));
        loginPanel.add(passField);

        loginButton = new JButton("Ingresar");
        loginButton.setBounds(110, 190, 140, 40);
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efecto hover
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 120, 215));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(30, 144, 255));
            }
        });

        loginPanel.add(loginButton);

        panel.add(loginPanel);
        add(panel);

        // Controlador
        LoginController controller = new LoginController(this);
        loginButton.addActionListener(e -> controller.validarLogin());
        getRootPane().setDefaultButton(loginButton);
    }

    public String getUsuario() { return userField.getText(); }

    public String getContrasena() { return new String(passField.getPassword()); }

    public void mostrarMensaje(String msg, boolean exito) {
        JOptionPane.showMessageDialog(this, msg, exito ? "Bienvenido" : "Error",
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}
