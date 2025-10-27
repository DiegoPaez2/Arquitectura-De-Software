package ec.edu.monster.service;

public class ConversionService {

    // ====== LOGIN ======
    public String login(String usuario, String password) {
        return WSClientFactory.getPort().login(usuario, password); // "SUCCESS" / "FAIL"
    }

    // ====== LONGITUD ======
    public double kilometrosAmillas(double km)     { return WSClientFactory.getPort().kilometrosAmillas(km); }
    public double metrosApies(double m)            { return WSClientFactory.getPort().metrosApies(m); }
    public double pulgadasAcentimetros(double in)  { return WSClientFactory.getPort().pulgadasAcentimetros(in); }

    // ====== MASA ======
    public double kilogramosAlibras(double kg)     { return WSClientFactory.getPort().kilogramosAlibras(kg); }
    public double gramosAonzas(double g)           { return WSClientFactory.getPort().gramosAonzas(g); }
    public double librasAkilogramos(double lb)     { return WSClientFactory.getPort().librasAkilogramos(lb); }

    // ====== TEMPERATURA ======
    public double celsiusAfahrenheit(double c)     { return WSClientFactory.getPort().celsiusAfahrenheit(c); }
    public double fahrenheitAcelsius(double f)     { return WSClientFactory.getPort().fahrenheitAcelsius(f); }
    public double celsiusAkelvin(double c)         { return WSClientFactory.getPort().celsiusAkelvin(c); }
}
