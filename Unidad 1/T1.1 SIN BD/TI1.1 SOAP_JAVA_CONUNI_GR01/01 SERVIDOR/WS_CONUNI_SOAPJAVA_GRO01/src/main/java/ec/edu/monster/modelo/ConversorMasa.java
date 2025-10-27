/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.modelo;

/**
 *
 * @author diego
 */
public class ConversorMasa {

    // 1 kg = 2.20462 lb
    public double kilogramosALibras(double kg) {
        return kg * 2.20462;
    }

    // 1 g = 0.035274 oz
    public double gramosAOnzas(double g) {
        return g * 0.035274;
    }

    // 1 lb = 0.453592 kg
    public double librasAKilogramos(double lb) {
        return lb * 0.453592;
    }
}