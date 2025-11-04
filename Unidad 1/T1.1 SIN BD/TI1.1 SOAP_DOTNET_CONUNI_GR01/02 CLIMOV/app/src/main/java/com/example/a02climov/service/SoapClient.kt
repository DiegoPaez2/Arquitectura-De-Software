package com.example.a02climov.service

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.math.abs

object SoapClient {

    // targetNamespace de tu WSDL
    private const val NAMESPACE = "http://tempuri.org/"
    private const val CONTRACT  = "IConversionService"

    // ⚠️ CAMBIA SOLO ESTO:
    // Emulador: 10.0.2.2 | Equipo real: IP LAN del PC
    private const val ENDPOINT = "http://10.0.2.2:57003/ConversionService.svc"

    /**
     * Llama a un método de IConversionService que recibe 1 double y devuelve double.
     * @param method nombre exacto del método en WCF (ej: "KilometrosAMillas")
     * @param paramName nombre EXACTO del parámetro en C# (ej: "km", "m", "p", "kg", "g", "lb", "c", "f")
     */
    fun callDoubleMethod(method: String, paramName: String, value: Double): Double {
        // 1) Envelope SOAP 1.1 (basicHttpBinding)
        val envelope = """
            <?xml version="1.0" encoding="utf-8"?>
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="$NAMESPACE">
              <soapenv:Header/>
              <soapenv:Body>
                <tem:$method>
                  <tem:$paramName>${value}</tem:$paramName>
                </tem:$method>
              </soapenv:Body>
            </soapenv:Envelope>
        """.trimIndent()

        // 2) SOAPAction
        val soapAction = "$NAMESPACE$CONTRACT/$method"

        // 3) POST
        val url = URL(ENDPOINT)
        val conn = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 20000
            readTimeout = 20000
            doOutput = true
            setRequestProperty("Content-Type", "text/xml; charset=utf-8")
            setRequestProperty("SOAPAction", soapAction)
        }

        // 4) Enviar
        conn.outputStream.use { os: OutputStream ->
            os.write(envelope.toByteArray(StandardCharsets.UTF_8))
            os.flush()
        }

        // 5) Leer respuesta (manejar errores 4xx/5xx mostrando HTML para depurar)
        val code = conn.responseCode
        val stream = if (code in 200..299) conn.inputStream else conn.errorStream
        val body = stream.bufferedReader().use(BufferedReader::readText)

        if (code !in 200..299) {
            throw RuntimeException("HTTP $code: ${body.take(200)}")
        }

        // 6) Parsear <methodResult>...</methodResult>
        return parseDoubleResult(body, "${method}Result")
    }

    // Parse sencillo con XmlPullParser: busca el tag <methodResult> y devuelve su texto como double
    private fun parseDoubleResult(xml: String, resultTag: String): Double {
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(xml.reader())

        var event = parser.eventType
        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.START_TAG && parser.name == resultTag) {
                parser.next() // debería estar en TEXT
                val raw = parser.text?.trim() ?: "0"
                return raw.toDouble()
            }
            event = parser.next()
        }
        // Algunos bindings responden sin wrapper Result; intenta buscar primer número
        val candidate = Regex("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")
            .find(xml)?.value ?: "0"
        return candidate.toDouble()
    }
}
