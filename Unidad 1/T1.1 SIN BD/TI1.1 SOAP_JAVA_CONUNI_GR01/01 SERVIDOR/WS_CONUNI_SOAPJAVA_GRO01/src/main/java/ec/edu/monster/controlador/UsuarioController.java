package ec.edu.monster.controlador;

import ec.edu.monster.modelo.UsuarioModel;

public class UsuarioController {
    private final UsuarioModel modelo = new UsuarioModel();

    /** Devuelve "SUCCESS" o "FAIL" para que el cliente decida. */
    public String login(String usuario, String contrasenia) {
        return modelo.verificar(usuario, contrasenia) ? "SUCCESS" : "FAIL";
    }
}
