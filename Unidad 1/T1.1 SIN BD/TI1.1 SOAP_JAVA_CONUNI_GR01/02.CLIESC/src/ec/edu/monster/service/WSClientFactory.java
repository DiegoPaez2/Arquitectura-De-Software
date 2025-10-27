package ec.edu.monster.service;

import ec.edu.monster.ws.WSConversionUnidades;
import ec.edu.monster.ws.WSConversionUnidades_Service;

/** Obtiene y reutiliza el puerto SOAP. */
public class WSClientFactory {
    private static WSConversionUnidades port;

    public static synchronized WSConversionUnidades getPort() {
        if (port == null) {
            WSConversionUnidades_Service svc = new WSConversionUnidades_Service();
            port = svc.getWSConversionUnidadesPort();
        }
        return port;
    }
}
