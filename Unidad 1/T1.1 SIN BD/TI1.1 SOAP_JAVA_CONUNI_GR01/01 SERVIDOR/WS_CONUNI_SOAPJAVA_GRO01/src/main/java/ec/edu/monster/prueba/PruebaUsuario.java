package ec.edu.monster.prueba;

import ec.edu.monster.controlador.UsuarioController;

public class PruebaUsuario {
    public static void main(String[] args) {
        UsuarioController usuarioController = new UsuarioController();

        String resultado1 = usuarioController.login("MONSTER", "MONSTER9");
        String resultado2 = usuarioController.login("otro", "1234");

        System.out.println("Prueba 1 (correcto): " + resultado1);
        System.out.println("Prueba 2 (incorrecto): " + resultado2);
    }
}
