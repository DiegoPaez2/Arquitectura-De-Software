/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.modelo;

/**
 *
 * @author diego
 */
public class ConversorTemperatura {

    // °F = (°C × 9/5) + 32
    public double celsiusAFahrenheit(double c) {
        return (c * 9.0 / 5.0) + 32.0;
    }

    // °C = (°F − 32) × 5/9
    public double fahrenheitACelsius(double f) {
        return (f - 32.0) * 5.0 / 9.0;
    }

    // K = °C + 273.15
    public double celsiusAKelvin(double c) {
        return c + 273.15;
    }
}