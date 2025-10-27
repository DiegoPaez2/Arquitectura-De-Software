package ec.edu.monster.controlador;

import ec.edu.monster.modelo.ConversorLongitud;
import ec.edu.monster.modelo.ConversorMasa;
import ec.edu.monster.modelo.ConversorTemperatura;

public class ConversionController {

    private final ConversorLongitud longit = new ConversorLongitud();
    private final ConversorMasa masa = new ConversorMasa();
    private final ConversorTemperatura temp = new ConversorTemperatura();

    // LONGITUD
    public double kilometrosAMillas(double km) { return longit.kilometrosAMillas(km); }
    public double metrosAPies(double m)       { return longit.metrosAPies(m); }
    public double pulgadasACentimetros(double p) { return longit.pulgadasACentimetros(p); }

    // MASA
    public double kilogramosALibras(double kg) { return masa.kilogramosALibras(kg); }
    public double gramosAOnzas(double g)       { return masa.gramosAOnzas(g); }
    public double librasAKilogramos(double lb) { return masa.librasAKilogramos(lb); }

    // TEMPERATURA
    public double celsiusAFahrenheit(double c)   { return temp.celsiusAFahrenheit(c); }
    public double fahrenheitACelsius(double f)   { return temp.fahrenheitACelsius(f); }
    public double celsiusAKelvin(double c)       { return temp.celsiusAKelvin(c); }
}
