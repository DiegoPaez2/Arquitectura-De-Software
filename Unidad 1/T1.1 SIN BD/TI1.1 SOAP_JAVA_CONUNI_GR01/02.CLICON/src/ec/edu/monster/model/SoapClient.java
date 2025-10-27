package ec.edu.monster.model;

import ec.edu.monster.wsclient.WSConversionUnidades;
import ec.edu.monster.wsclient.WSConversionUnidades_Service;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;

public class SoapClient {

    private final WSConversionUnidades port;

    public SoapClient(String wsdlUrl, String endpointUrl) throws Exception {
        // Crear el Service solo con la URL del WSDL (sin QName)
        WSConversionUnidades_Service service = new WSConversionUnidades_Service(new URL(wsdlUrl));
        this.port = service.getWSConversionUnidadesPort();

        // Forzar el endpoint real y mantener sesión
        BindingProvider bp = (BindingProvider) port;
        Map<String, Object> ctx = bp.getRequestContext();
        ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
        ctx.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);
    }

    // ====== LOGIN ======
    // El servicio devuelve "SUCCESS" o "FAIL" (String)
    public String login(String user, String pass) {
        try {
            return port.login(user, pass);
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // ====== CONVERSIONES ======
    public double celsiusAfahrenheit(double c)   { return port.celsiusAfahrenheit(c); }
    public double fahrenheitAcelsius(double f)   { return port.fahrenheitAcelsius(f); }
    public double celsiusAkelvin(double c)       { return port.celsiusAkelvin(c); }

    public double pulgadasAcentimetros(double in){ return port.pulgadasAcentimetros(in); }
    public double metrosApies(double m)          { return port.metrosApies(m); }
    public double kilometrosAmillas(double km)   { return port.kilometrosAmillas(km); }

    public double librasAkilogramos(double lb)   { return port.librasAkilogramos(lb); }
    public double kilogramosAlibras(double kg)   { return port.kilogramosAlibras(kg); }
    public double gramosAonzas(double g)         { return port.gramosAonzas(g); }
}
