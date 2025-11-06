package ec.edu.monster.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SoapClientManual {

    private final String serviceUrl; // p.ej. http://10.0.2.2:8080/.../WS_Conversion_Unidades
    private static final String NAMESPACE = "http://ws.monster.edu.ec/";

    public SoapClientManual(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String call(String soapBody) throws Exception {
        // Envoltorio SOAP 1.1 (Metro usa style=document/literal)
        String soapEnvelope =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                        + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                        + "xmlns:ws=\"" + NAMESPACE + "\">"
                        + "<soapenv:Header/>"
                        + "<soapenv:Body>"
                        + soapBody
                        + "</soapenv:Body>"
                        + "</soapenv:Envelope>";

        URL url = new URL(serviceUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        // soapAction en tu WSDL es "", por eso no seteamos SOAPAction
        conn.setDoOutput(true);
        conn.setDoInput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(soapEnvelope.getBytes("UTF-8"));
            os.flush();
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            return sb.toString();
        } finally {
            conn.disconnect();
        }
    }

    /** Extrae el contenido dentro de la primera aparición de <return>...</return> */
    public static String extractReturn(String xml) {
        int a = xml.indexOf("<return>");
        int b = xml.indexOf("</return>");
        if (a == -1 || b == -1 || b <= a) {
            return "";
        }
        return xml.substring(a + 8, b);
    }
}
