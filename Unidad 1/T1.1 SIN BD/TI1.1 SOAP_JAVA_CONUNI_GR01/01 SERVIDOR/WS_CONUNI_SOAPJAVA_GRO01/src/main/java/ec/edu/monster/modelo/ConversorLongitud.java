/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.modelo;

/**
 *
 * @author diego
 */

public class ConversorLongitud {

    // 1 km = 0.621371 mi
    public double kilometrosAMillas(double km) {
        return km * 0.621371;
    }

    // 1 m = 3.28084 ft
    public double metrosAPies(double m) {
        return m * 3.28084;
    }

    // 1 in = 2.54 cm
    public double pulgadasACentimetros(double pulgadas) {
        return pulgadas * 2.54;
    }
}

