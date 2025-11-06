package ec.edu.monster.view;

import ec.edu.monster.controller.ConversionController;
import ec.edu.monster.model.ConversionResult;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    // --- Colores para la UI ---
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_FONDO_CAMPO = new Color(30, 41, 59, 200);
    private static final Color COLOR_BOTON = new Color(59, 130, 246);
    private static final Color COLOR_BOTON_HOVER = new Color(37, 99, 235);
    private static final Color COLOR_EXITO = new Color(34, 197, 94);
    private static final Color COLOR_ERROR = new Color(239, 68, 68);
    private static final Color COLOR_NEUTRO = Color.YELLOW;

    private final ConversionController ctrl = new ConversionController();

    private final JComboBox<String> cboTipo = new JComboBox<>(new String[]{
            "Kilómetros → Millas", "Metros → Pies", "Pulgadas → Centímetros",
            "Kilogramos → Libras", "Gramos → Onzas", "Libras → Kilogramos",
            "Celsius → Fahrenheit", "Fahrenheit → Celsius", "Celsius → Kelvin"
    });

    private final JTextField txtValor = new JTextField(10);
    private final JButton btnConvertir = new JButton("Convertir");
    private final JButton btnPing = new JButton("Probar Servidor");
    private final JButton btnCerrarSesion = new JButton("Cerrar Sesión"); // <-- Nuevo botón
    private final JTextArea txtSalida = new JTextArea(8, 30);
    private final JLabel lblEstado = new JLabel("Listo", SwingConstants.LEFT);

    public MainFrame(String usuario) {
        setTitle("02_CLIESC - Conversor (Usuario: " + usuario + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 550); // Un poco más de altura para acomodar el nuevo botón
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel de fondo con imagen
        JPanel backgroundPanel = new LoginFrame.ImagePanel("/ec/edu/monster/view/monstersinc2.jpg");
        backgroundPanel.setLayout(new GridBagLayout()); // Para centrar el panel principal

        // Panel central semitransparente y redondeado
        JPanel mainPanel = createRoundedPanel();
        backgroundPanel.add(mainPanel, new GridBagConstraints());
        
        setContentPane(backgroundPanel);

        // --- FUNCIONALIDAD ORIGINAL (SIN CAMBIOS EN LÓGICA) ---
        btnConvertir.addActionListener(e -> convertir());
        btnPing.addActionListener(e -> {
            boolean ok = ctrl.ping();
            if (ok) {
                lblEstado.setForeground(COLOR_EXITO);
                lblEstado.setText("✓ Servidor REST activo y respondiendo.");
            } else {
                lblEstado.setForeground(COLOR_ERROR);
                lblEstado.setText("✗ Servidor no responde (revisar Payara o el despliegue del WAR).");
            }
        });
        
        // --- NUEVA FUNCIONALIDAD: Botón Cerrar Sesión ---
        btnCerrarSesion.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new LoginFrame().setVisible(true); // Abre la ventana de login
        });
        
        getRootPane().setDefaultButton(btnConvertir);
    }

    private JPanel createRoundedPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(15, 23, 42, 220)); // Un azul oscuro muy opaco
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- Estilizar todos los componentes ---
        styleComponents();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 8, 10, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: Título
        c.gridx = 0; c.gridy = 0; c.gridwidth = 3;
        panel.add(stylizedLabel("Conversor de Unidades (REST)", 24, COLOR_TEXTO), c);

        // Fila 1: Tipo de conversión, Ping y Cerrar Sesión
        c.gridy = 1; c.gridwidth = 1; c.weightx = 0;
        panel.add(stylizedLabel("Tipo:", 14, COLOR_TEXTO), c);
        c.gridx = 1; c.weightx = 1.0;
        panel.add(cboTipo, c);
        c.gridx = 2; c.weightx = 0;
        panel.add(btnPing, c);

        // Fila 2: Valor, Convertir y Espacio para Botón Cerrar Sesión (si hubiese, sino se salta)
        c.gridx = 0; c.gridy = 2; c.weightx = 0;
        panel.add(stylizedLabel("Valor:", 14, COLOR_TEXTO), c);
        c.gridx = 1; c.weightx = 1.0;
        panel.add(txtValor, c);
        c.gridx = 2; c.weightx = 0;
        panel.add(btnConvertir, c);

        // Fila 3: Botón de Cerrar Sesión (colocado en la esquina superior derecha)
        // Este GridBagLayout es para posicionar el botón de cerrar sesión de forma específica
        // Usamos un GridBagLayout aquí para que este botón se alinee arriba a la derecha.
        GridBagConstraints cBtnClose = new GridBagConstraints();
        cBtnClose.gridx = 0; cBtnClose.gridy = 0; // Posicionamos en la celda (0,0) del panel padre
        cBtnClose.insets = new Insets(0, 0, 5, 0); // Un poco de espacio abajo
        cBtnClose.anchor = GridBagConstraints.NORTHEAST; // Alineación arriba a la derecha
        cBtnClose.weightx = 1.0; // Permite que el espacio se expanda a la izquierda
        cBtnClose.weighty = 1.0; // Permite que el espacio se expanda hacia arriba

        // Creamos un panel temporal solo para el botón de cerrar sesión, para poder controlar su posición
        JPanel closeButtonContainer = new JPanel(new GridBagLayout());
        closeButtonContainer.setOpaque(false);
        closeButtonContainer.add(btnCerrarSesion, cBtnClose);

        // Agregamos este contenedor de botón al panel principal.
        // Para que no interfiera con los otros elementos, lo ponemos en una celda vacía pero la colocamos al final
        // del panel principal si lo pensamos como una matriz de componentes.
        // La forma más fácil es usar un GridBagLayout en el panel principal y añadirlo en una celda que se expanda.
        // Aquí asumimos que el panel principal tiene un GridBagLayout y lo añadimos.
        // Si esto causa problemas de alineación, se podría usar un BorderLayout en el panel principal.
        // Por ahora, lo añadimos al GridBagLayout del 'panel' principal con una posición que se alinee a la derecha.
        // Lo más limpio es añadirlo como último componente del GridBagLayout principal, con peso y anclaje.
        // Revisamos la celda 3 para añadirlo con anclaje
        c.gridx = 0; c.gridy = 3; c.gridwidth = 3; c.fill = GridBagConstraints.BOTH; c.weighty = 1.0;
        // Aquí debemos insertar el botón de cerrar sesión de forma que no desplaze los demás.
        // La forma más segura es hacer que el componente principal (mainPanel) tenga un BorderLayout,
        // y dentro de él un JPanel para el contenido principal y otro para el botón de cerrar sesión.
        // Pero para no alterar tu código original, intentaremos insertarlo en el GridBagLayout.
        // La mejor forma es ponerlo al final del GridBagLayout del panel principal.
        // Si el botón de cerrar sesión se superpone, modificaremos la estructura de `mainPanel`.

        // Alternativa más limpia: usar un BorderLayout en el mainPanel
        // y dentro de él el contenido principal y el botón de cerrar sesión.
        // Por ahora, trataremos de integrarlo en el GridBagLayout.

        // Vamos a poner el botón de cerrar sesión en la fila del título, alineado a la derecha
        c.gridx = 2; c.gridy = 0; c.gridwidth = 1; c.weightx = 0; c.fill = GridBagConstraints.NONE; // No expandir, alinear a la derecha
        panel.add(btnCerrarSesion, c); // Lo ponemos en la primera fila, alineado a la derecha

        // Ahora ajustamos las celdas restantes:
        // Título: se queda en la celda (0,0), ocupando 2 columnas.
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        panel.add(stylizedLabel("Conversor de Unidades (REST)", 24, COLOR_TEXTO), c); // Redefinimos el título para que ocupe las columnas restantes.

        // Fila 3: Área de salida
        c.gridx = 0; c.gridy = 4; c.gridwidth = 3; // Se desplaza una fila más abajo
        c.fill = GridBagConstraints.BOTH; c.weighty = 1.0;
        JScrollPane scroll = new JScrollPane(txtSalida);
        scroll.setBorder(null); // Quitar borde para un look más limpio
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        panel.add(scroll, c);

        // Fila 4: Barra de estado
        c.gridx = 0; c.gridy = 5; c.gridwidth = 3; // Se desplaza una fila más abajo
        c.fill = GridBagConstraints.HORIZONTAL; c.weighty = 0;
        panel.add(lblEstado, c);
        
        return panel;
    }

    private void styleComponents() {
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Border fieldBorder = new CompoundBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105)),
            new EmptyBorder(5, 8, 5, 8)
        );

        // ComboBox
        cboTipo.setFont(fieldFont);
        cboTipo.setBackground(COLOR_FONDO_CAMPO);
        cboTipo.setForeground(COLOR_TEXTO);
        cboTipo.setBorder(fieldBorder);

        // Campo de texto
        txtValor.setFont(fieldFont);
        txtValor.setBackground(COLOR_FONDO_CAMPO);
        txtValor.setForeground(COLOR_TEXTO);
        txtValor.setCaretColor(COLOR_BOTON);
        txtValor.setBorder(fieldBorder);

        // Botones
        styleButton(btnConvertir, "Segoe UI Semibold", 15);
        styleButton(btnPing, "Segoe UI", 14);
        styleButton(btnCerrarSesion, "Segoe UI Semibold", 14); // Estilo para el botón de cerrar sesión

        // Área de salida
        txtSalida.setEditable(false);
        txtSalida.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtSalida.setBackground(new Color(30, 41, 59, 180));
        txtSalida.setForeground(new Color(220, 220, 220));
        txtSalida.setMargin(new Insets(10,10,10,10));
        
        // Etiqueta de estado
        lblEstado.setForeground(COLOR_NEUTRO);
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }

    private void styleButton(JButton button, String fontName, int fontSize) {
        button.setFont(new Font(fontName, Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBackground(COLOR_BOTON);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(COLOR_BOTON_HOVER);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(COLOR_BOTON);
            }
        });
    }

    private void convertir() {
        String sel = (String) cboTipo.getSelectedItem();
        String raw = txtValor.getText().trim().replace(',', '.');

        if (raw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double val;
        try {
            val = Double.parseDouble(raw);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El valor ingresado no es un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ConversionResult r;
            // --- ESTA LÓGICA ESTÁ INTACTA ---
            switch (sel) {
                case "Kilómetros → Millas": r = ctrl.kilometrosAMillas(val); break;
                case "Metros → Pies": r = ctrl.metrosAPies(val); break;
                case "Pulgadas → Centímetros": r = ctrl.pulgadasACentimetros(val); break;
                case "Kilogramos → Libras": r = ctrl.kilogramosALibras(val); break;
                case "Gramos → Onzas": r = ctrl.gramosAOnzas(val); break;
                case "Libras → Kilogramos": r = ctrl.librasAKilogramos(val); break;
                case "Celsius → Fahrenheit": r = ctrl.celsiusAFahrenheit(val); break;
                case "Fahrenheit → Celsius": r = ctrl.fahrenheitACelsius(val); break;
                case "Celsius → Kelvin": r = ctrl.celsiusAKelvin(val); break;
                default: throw new IllegalStateException("Selección desconocida");
            }
            // --- FIN DE LA LÓGICA INTACTA ---
            txtSalida.append(r.toString() + "\n");
            lblEstado.setForeground(COLOR_EXITO);
            lblEstado.setText("✓ Conversión realizada con éxito.");
        } catch (RuntimeException ex) {
            lblEstado.setForeground(COLOR_ERROR);
            lblEstado.setText("✗ Error en la conversión: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Comunicación REST", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel stylizedLabel(String text, int size, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI Semibold", Font.BOLD, size));
        l.setForeground(color);
        return l;
    }
}