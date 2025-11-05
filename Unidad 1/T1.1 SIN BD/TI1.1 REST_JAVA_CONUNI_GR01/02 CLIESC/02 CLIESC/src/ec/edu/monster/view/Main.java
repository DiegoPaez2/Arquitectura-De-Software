package ec.edu.monster.view;

import javax.swing.UIManager;

/**
 * Clase principal de arranque del cliente REST.
 * Muestra primero la pantalla de Login.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Establece un look & feel moderno (Nimbus)
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception ignored) { }

        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
