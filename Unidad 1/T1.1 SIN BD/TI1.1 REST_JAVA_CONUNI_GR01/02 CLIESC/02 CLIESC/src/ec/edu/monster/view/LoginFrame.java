package ec.edu.monster.view;

import ec.edu.monster.auth.AuthService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField txtUser = new JTextField();
    private final JPasswordField txtPass = new JPasswordField();
    private final JButton btnLogin = new JButton("Ingresar");
    private final JLabel lblMsg = new JLabel(" ", SwingConstants.CENTER);

    public LoginFrame() {
        setTitle("02_CLIESC - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 360);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel con fondo
        JPanel content = new ImagePanel("/ec/edu/monster/view/fondo1.png");
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = stylizedLabel("Cliente REST - Conversor", 20, Color.WHITE);
        JLabel lbUser = stylizedLabel("Usuario", 14, Color.WHITE);
        JLabel lbPass = stylizedLabel("Contraseña", 14, Color.WHITE);

        txtUser.setFont(txtUser.getFont().deriveFont(14f));
        txtPass.setFont(txtPass.getFont().deriveFont(14f));
        btnLogin.setFont(btnLogin.getFont().deriveFont(Font.BOLD, 14f));
        lblMsg.setForeground(Color.YELLOW);

        c.gridx=0; c.gridy=0; c.gridwidth=2;
        content.add(title, c);

        c.gridy=1; c.gridwidth=2;
        content.add(new JLabel(" "), c);

        c.gridy=2; c.gridwidth=1;
        content.add(lbUser, c);
        c.gridx=1;
        content.add(txtUser, c);

        c.gridx=0; c.gridy=3;
        content.add(lbPass, c);
        c.gridx=1;
        content.add(txtPass, c);

        c.gridx=0; c.gridy=4; c.gridwidth=2;
        content.add(btnLogin, c);

        c.gridy=5;
        content.add(lblMsg, c);

        setContentPane(content);

        btnLogin.addActionListener(e -> tryLogin());
        getRootPane().setDefaultButton(btnLogin);
    }

    private void tryLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword());

        AuthService auth = new AuthService();
        if (auth.login(u, p)) {
            lblMsg.setText("✓ Bienvenido, " + u);
            SwingUtilities.invokeLater(() -> {
                dispose();
                new MainFrame(u).setVisible(true);
            });
        } else {
            lblMsg.setText("✗ Usuario o contraseña incorrectos");
        }
    }

    private JLabel stylizedLabel(String text, int size, Color color) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(l.getFont().deriveFont(Font.BOLD, size));
        l.setForeground(color);
        return l;
    }

    // Panel que pinta una imagen de fondo
    static class ImagePanel extends JPanel {
        private final Image bg;
        ImagePanel(String resourcePath) {
            ImageIcon icon = null;
            try {
                icon = new ImageIcon(getClass().getResource(resourcePath));
            } catch (Exception ignored) { }
            bg = (icon != null) ? icon.getImage() : null;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg != null) {
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
