package ec.edu.monster.view;

import ec.edu.monster.auth.AuthService;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class LoginFrame extends JFrame {

    // Tus componentes originales, sin cambios
    private final JTextField txtUser = new JTextField(15); // Tamaño sugerido
    private final JPasswordField txtPass = new JPasswordField(15);
    private final JButton btnLogin = new JButton("Ingresar");
    private final JLabel lblMsg = new JLabel(" ", SwingConstants.CENTER);

    public LoginFrame() {
        setTitle("02_CLIESC - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 400); // Un poco más alto para mejor espaciado
        setLocationRelativeTo(null);
        setResizable(false);

        // 1. Panel principal con la imagen de fondo
        JPanel backgroundPanel = new ImagePanel("/ec/edu/monster/view/monstersinc.jpg");
        backgroundPanel.setLayout(new GridBagLayout()); // Para centrar el contenido

        // 2. Panel de formulario semitransparente
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Hacemos el fondo semitransparente y con bordes redondeados
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 180)); // Negro con ~70% de opacidad
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        formPanel.setOpaque(false); // Esencial para que se vea el fondo personalizado
        formPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // --- Configuración de Layout para el formulario ---
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 5, 10, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        // --- Estilización de componentes ---
        styleComponents();

        // Título
        JLabel title = stylizedLabel("Cliente REST - Conversor", 22, Color.WHITE);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        formPanel.add(title, c);

        // Usuario
        JLabel lbUser = stylizedLabel("Usuario", 14, Color.LIGHT_GRAY);
        c.gridy = 1; c.gridwidth = 1;
        formPanel.add(lbUser, c);
        c.gridx = 1;
        formPanel.add(txtUser, c);

        // Contraseña
        JLabel lbPass = stylizedLabel("Contraseña", 14, Color.LIGHT_GRAY);
        c.gridx = 0; c.gridy = 2;
        formPanel.add(lbPass, c);
        c.gridx = 1;
        formPanel.add(txtPass, c);
        
        // Espacio antes del botón
        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        formPanel.add(Box.createVerticalStrut(10), c);

        // Botón
        c.gridy = 4;
        formPanel.add(btnLogin, c);

        // Mensaje de feedback
        c.gridy = 5;
        formPanel.add(lblMsg, c);

        // Añadimos el panel del formulario centrado sobre el fondo
        backgroundPanel.add(formPanel, new GridBagConstraints());
        
        setContentPane(backgroundPanel);

        // --- FUNCIONALIDAD ORIGINAL (SIN CAMBIOS) ---
        btnLogin.addActionListener(e -> tryLogin());
        getRootPane().setDefaultButton(btnLogin);
    }
    
    private void styleComponents() {
        // Estilo para campos de texto
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);
        Color fieldBg = new Color(30, 30, 40);
        Color fieldFg = Color.WHITE;
        Color caretColor = new Color(59, 130, 246);
        Border fieldBorder = new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, Color.GRAY), // Línea inferior
                new EmptyBorder(5, 5, 5, 5) // Padding interno
        );

        txtUser.setFont(fieldFont);
        txtUser.setBackground(fieldBg);
        txtUser.setForeground(fieldFg);
        txtUser.setCaretColor(caretColor);
        txtUser.setBorder(fieldBorder);

        txtPass.setFont(fieldFont);
        txtPass.setBackground(fieldBg);
        txtPass.setForeground(fieldFg);
        txtPass.setCaretColor(caretColor);
        txtPass.setBorder(fieldBorder);

        // Estilo para el botón
        Color btnColor = new Color(59, 130, 246);
        Color btnHoverColor = new Color(37, 99, 235);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setBackground(btnColor);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(new EmptyBorder(10, 15, 10, 15));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(btnHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(btnColor);
            }
        });
    }

    // --- TU LÓGICA DE LOGIN (EXACTAMENTE IGUAL, INTACTA) ---
    private void tryLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword());

        AuthService auth = new AuthService();
        if (auth.login(u, p)) {
            lblMsg.setForeground(new Color(34, 197, 94)); // Verde para éxito
            lblMsg.setText("✓ Bienvenido, " + u);
            // El dispose() y new MainFrame() se ejecutan en el hilo de Swing
            SwingUtilities.invokeLater(() -> {
                dispose();
                new MainFrame(u).setVisible(true);
            });
        } else {
            lblMsg.setForeground(new Color(239, 68, 68)); // Rojo para error
            lblMsg.setText("✗ Usuario o contraseña incorrectos");
        }
    }

    // Tu método original para estilizar labels, ahora más versátil
    private JLabel stylizedLabel(String text, int size, Color color) {
        JLabel l = new JLabel(text); // Quitamos el centrado por defecto
        l.setFont(l.getFont().deriveFont(Font.BOLD, (float)size));
        l.setForeground(color);
        return l;
    }

    // Tu clase original para el panel de imagen, ligeramente mejorada para manejar errores
    static class ImagePanel extends JPanel {
        private final Image bg;
        ImagePanel(String resourcePath) {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                System.err.println("Error: No se pudo encontrar la imagen en la ruta: " + resourcePath);
                bg = null;
            } else {
                bg = new ImageIcon(url).getImage();
            }
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg != null) {
                // Dibuja la imagen para que cubra todo el panel
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}