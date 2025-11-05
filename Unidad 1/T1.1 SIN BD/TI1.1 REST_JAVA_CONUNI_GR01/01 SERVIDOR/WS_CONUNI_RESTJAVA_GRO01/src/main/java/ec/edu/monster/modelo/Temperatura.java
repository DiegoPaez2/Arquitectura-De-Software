package ec.edu.monster.modelo;

public class Temperatura {
    public double celsiusAFahrenheit(double c) { return c * 9.0/5.0 + 32.0; }
    public double fahrenheitACelsius(double f) { return (f - 32.0) * 5.0/9.0; }
    public double celsiusAKelvin(double c)     { return c + 273.15; }
}
