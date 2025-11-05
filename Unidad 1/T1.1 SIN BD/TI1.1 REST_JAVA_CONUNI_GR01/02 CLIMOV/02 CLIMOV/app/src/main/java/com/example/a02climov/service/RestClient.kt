package com.example.a02climov.service

import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RestClient {

    // Emulador: 10.0.2.2   |  Dispositivo físico en la misma red: IP de tu PC
    var baseUrl: String =
        "http://10.0.2.2:8080/WS_CONUNI_RESTJAVA_GRO01/api/conversion"

    private suspend fun httpGet(fullUrl: String): String {
        val conn = (URL(fullUrl).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 10000
            readTimeout = 10000
        }
        return try {
            val code = conn.responseCode
            val stream = if (code in 200..299) conn.inputStream else conn.errorStream
            val body = stream.bufferedReader().use { it.readText() }
            if (code !in 200..299) throw RuntimeException("HTTP $code: $body")
            body
        } finally {
            conn.disconnect()
        }
    }

    private suspend fun getNumber(pathAndQuery: String): Double {
        val url = "$baseUrl/$pathAndQuery"
        val raw = httpGet(url).trim()
        // Soporta texto plano ("6.21371") o JSON simple ({"resultado":6.21})
        raw.toDoubleOrNull()?.let { return it }
        val regex = Regex("""([-+]?\d+(\.\d+)?([eE][-+]?\d+)?)""")
        val num = regex.find(raw)?.value?.toDoubleOrNull()
        return num ?: throw RuntimeException("Respuesta no numérica: $raw")
    }

    // LONGITUD
    suspend fun kmToMi(v: Double) = getNumber("kilometros-a-millas?km=${enc(v)}")
    suspend fun mToFt(v: Double)  = getNumber("metros-a-pies?m=${enc(v)}")
    suspend fun inToCm(v: Double) = getNumber("pulgadas-a-centimetros?p=${enc(v)}")

    // MASA
    suspend fun kgToLb(v: Double) = getNumber("kilogramos-a-libras?kg=${enc(v)}")
    suspend fun gToOz(v: Double)  = getNumber("gramos-a-onzas?g=${enc(v)}")
    suspend fun lbToKg(v: Double) = getNumber("libras-a-kilogramos?lb=${enc(v)}")

    // TEMPERATURA
    suspend fun cToF(v: Double)   = getNumber("celsius-a-fahrenheit?c=${enc(v)}")
    suspend fun fToC(v: Double)   = getNumber("fahrenheit-a-celsius?f=${enc(v)}")
    suspend fun cToK(v: Double)   = getNumber("celsius-a-kelvin?c=${enc(v)}")

    private fun enc(v: Double) = URLEncoder.encode(v.toString(), "UTF-8")
}
