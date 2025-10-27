package ec.edu.monster.modelo;

public class UsuarioModel {

    private static final String USER = "MONSTER";
    private static final String PASS = "MONSTER9";

    public boolean verificar(String usuario, String contrasenia) {
        if (usuario == null || contrasenia == null) return false;
        return USER.equals(usuario.trim()) && PASS.equals(contrasenia.trim());
    }
}
    