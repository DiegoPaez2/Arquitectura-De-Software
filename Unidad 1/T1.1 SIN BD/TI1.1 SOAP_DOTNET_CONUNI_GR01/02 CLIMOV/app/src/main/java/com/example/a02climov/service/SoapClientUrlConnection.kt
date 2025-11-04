package com.example.a02climov.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

class SoapClientUrlConnection(
    private val baseUrl: String = "http://10.0.2.2:57003/ConversionService.svc",
    private val namespace: String = "http://tempuri.org/"
) {
    suspend fun callDouble(method: String, paramName: String, value: Double): Double =
        withContext(Dispatchers.IO) {
            val soapAction = "${namespace}IConversionService/$method"
            val valueStr = String.format(Locale.US, "%.6f", value)

            val envelope = """
                <?xml version="1.0" encoding="utf-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:tem="$namespace">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <tem:$method>
                      <tem:$paramName>$valueStr</tem:$paramName>
                    </tem:$method>
                  </soapenv:Body>
                </soapenv:Envelope>
            """.trimIndent()

            val url = URL(baseUrl)
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                doOutput = true
                doInput = true
                useCaches = false
                instanceFollowRedirects = false
                connectTimeout = 30000
                readTimeout = 60000

                setRequestProperty("Content-Type", "text/xml; charset=utf-8")
                setRequestProperty("SOAPAction", "\"$soapAction\"")
                setRequestProperty("Accept", "text/xml")
                setRequestProperty("Accept-Encoding", "identity")
                setRequestProperty("Connection", "close")
            }

            conn.outputStream.use { os ->
                OutputStreamWriter(os, Charsets.UTF_8).use { w ->
                    w.write(envelope)
                    w.flush()
                }
            }

            val code = conn.responseCode
            val stream = if (code in 200..299) conn.inputStream else conn.errorStream
            val response = BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).use { br ->
                buildString {
                    var line: String?
                    while (br.readLine().also { line = it } != null) append(line)
                }
            }
            conn.disconnect()

            if (code !in 200..299) {
                val shortHtml = response.take(400)
                error("HTTP $code: ${conn.responseMessage}\n$shortHtml")
            }

            parseResult(response, method)
                ?: run {
                    val tag = "${method}Result"
                    error("No se encontró <$tag> en la respuesta.\n$response")
                }
        }

    private fun parseResult(xml: String, method: String): Double? {
        val tag = "${method}Result"
        val rgx = Regex("<$tag>([-0-9\\.Ee]+)</$tag>")
        return rgx.find(xml)?.groupValues?.get(1)?.toDoubleOrNull()
    }
}
