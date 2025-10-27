package ec.edu.monster.controller;

import ec.edu.monster.service.ConversionService;
import ec.edu.monster.view.LoginFrame;
import ec.edu.monster.view.MainFrame;

public class LoginController {
    private final LoginFrame view;
    private final ConversionService service = new ConversionService();

    public LoginController(LoginFrame view) { this.view = view; }

    public void validarLogin() {
        String u = view.getUsuario().trim();
        String p = view.getContrasena().trim();

        if (u.isEmpty() || p.isEmpty()) {
            view.mostrarMensaje("Ingrese usuario y contraseña", false);
            return;
        }

        try {
            String r = service.login(u, p); // "SUCCESS" o "FAIL"
            if ("SUCCESS".equalsIgnoreCase(r)) {
                view.mostrarMensaje("Inicio de sesión correcto", true);
                view.dispose();
                new MainFrame().setVisible(true);
            } else {
                view.mostrarMensaje("Usuario o contraseña inválidos", false);
            }
        } catch (Exception ex) {
            view.mostrarMensaje("No se pudo conectar al servicio SOAP.\n" + ex.getMessage(), false);
        }
    }
}
