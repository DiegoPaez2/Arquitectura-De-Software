package com.example.a02climov.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

object SoapClient {

    private const val NAMESPACE = "http://tempuri.org/"
    private const val SERVICE = "IConversionService" // Según tu WSDL
    private const val TIMEOUT_MS = 20_000

    /**
     * Llama a un método del servicio WCF que recibe 1 parámetro double y retorna double.
     * @param baseUrl  p.ej. "http://10.0.2.2:57003/ConversionService.svc"
     * @param method   p.ej. "CelsiusAKelvin"
     * @param param    nombre EXACTO del parámetro (km, m, p, kg, g, lb, c, f)
     */
    suspend fun callDoubleMethodKsoap(
        baseUrl: String,
        method: String,
        param: String,
        value: Double
    ): Double = withContext(Dispatchers.IO) {

        require(!baseUrl.contains("?wsdl", ignoreCase = true)) {
            "Usa la URL del .svc sin ?wsdl"
        }

        val soapAction = "$NAMESPACE/$SERVICE/$method"
        val request = SoapObject(NAMESPACE, method)

        // Propiedad con el nombre EXACTO del parámetro
        val pi = PropertyInfo().apply {
            name = param
            type = Double::class.javaPrimitiveType // primitive double
            this.value = value
        }
        request.addProperty(pi)

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11).apply {
            dotNet = true                 // MUY IMPORTANTE para WCF
            setOutputSoapObject(request)  // request dentro del body
        }

        val http = HttpTransportSE(baseUrl, TIMEOUT_MS).apply {
            debug = true // para ver dumps en caso de error
        }

        try {
            http.call(soapAction, envelope)  // envía la solicitud

            val resp = envelope.response
            return@withContext when (resp) {
                is Double -> resp
                is String -> resp.toDouble()
                else -> resp.toString().toDouble()
            }
        } catch (e: Exception) {
            val reqDump = runCatching { http.requestDump }.getOrNull()
            val resDump = runCatching { http.responseDump }.getOrNull()
            throw IllegalStateException(
                "SOAP Fault/HTTP error: ${e.message}\nREQ:\n$reqDump\nRES:\n$resDump", e
            )
        }
    }
}
