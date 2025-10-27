package ec.edu.monster.ws;

import ec.edu.monster.modelo.ConversorLongitud;
import ec.edu.monster.modelo.ConversorMasa;
import ec.edu.monster.modelo.ConversorTemperatura;
import ec.edu.monster.modelo.UsuarioModel;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(serviceName = "WS_Conversion_Unidades")
public class WS_Conversion_Unidades {

    // =======================
    //  AUTH
    // =======================
    @WebMethod(operationName = "login")
    public String login(
            @WebParam(name = "arg0") String usuario,
            @WebParam(name = "arg1") String contrasenia) {

        System.out.println("===== LOGIN REQUEST =====");
        System.out.println("Usuario recibido: [" + usuario + "]");
        System.out.println("Contraseña recibida: [" + contrasenia + "]");

        UsuarioModel model = new UsuarioModel();
        boolean ok = model.verificar(usuario, contrasenia);

        if (ok) {
            System.out.println("[WS] ✅ Usuario autenticado correctamente");
            return "SUCCESS";
        } else {
            System.out.println("[WS] ❌ Usuario o contraseña incorrectos");
            return "FAIL";
        }
    }

    // =======================
    //  CONVERSORES
    // =======================

    private final ConversorTemperatura temp = new ConversorTemperatura();
    private final ConversorLongitud longi   = new ConversorLongitud();
    private final ConversorMasa masa        = new ConversorMasa();

    // Utilidad para validar nulos
    private double requireArg0(Double v) {
        if (v == null) {
            throw new IllegalArgumentException("arg0 requerido");
        }
        return v.doubleValue();
    }

    // ====== TEMPERATURA ======
    @WebMethod(operationName = "celsiusAfahrenheit")
    public double celsiusAfahrenheit(@WebParam(name = "arg0") Double c) {
        return temp.celsiusAFahrenheit(requireArg0(c));
    }

    @WebMethod(operationName = "fahrenheitAcelsius")
    public double fahrenheitAcelsius(@WebParam(name = "arg0") Double f) {
        return temp.fahrenheitACelsius(requireArg0(f));
    }

    @WebMethod(operationName = "celsiusAkelvin")
    public double celsiusAkelvin(@WebParam(name = "arg0") Double c) {
        return temp.celsiusAKelvin(requireArg0(c));
    }

    // ====== LONGITUD ======
    @WebMethod(operationName = "kilometrosAmillas")
    public double kilometrosAmillas(@WebParam(name = "arg0") Double km) {
        return longi.kilometrosAMillas(requireArg0(km));
    }

    @WebMethod(operationName = "metrosApies")
    public double metrosApies(@WebParam(name = "arg0") Double m) {
        return longi.metrosAPies(requireArg0(m));
    }

    @WebMethod(operationName = "pulgadasAcentimetros")
    public double pulgadasAcentimetros(@WebParam(name = "arg0") Double pulgadas) {
        return longi.pulgadasACentimetros(requireArg0(pulgadas));
    }

    // ====== MASA ======
    @WebMethod(operationName = "kilogramosAlibras")
    public double kilogramosAlibras(@WebParam(name = "arg0") Double kg) {
        return masa.kilogramosALibras(requireArg0(kg));
    }

    @WebMethod(operationName = "gramosAonzas")
    public double gramosAonzas(@WebParam(name = "arg0") Double g) {
        return masa.gramosAOnzas(requireArg0(g));
    }

    @WebMethod(operationName = "librasAkilogramos")
    public double librasAkilogramos(@WebParam(name = "arg0") Double lb) {
        return masa.librasAKilogramos(requireArg0(lb));
    }
}