package com.example.a02climov.controller

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a02climov.R
import com.example.a02climov.model.SoapClient
import kotlinx.coroutines.launch

class ConversionActivity : AppCompatActivity() {

    private val operations = listOf(
        Triple("Kilómetros a Millas", "KilometrosAMillas", "km"),
        Triple("Metros a Pies", "MetrosAPies", "m"),
        Triple("Pulgadas a Centímetros", "PulgadasACentimetros", "p"),
        Triple("Kilogramos a Libras", "KilogramosALibras", "kg"),
        Triple("Gramos a Onzas", "GramosAOnzas", "g"),
        Triple("Libras a Kilogramos", "LibrasAKilogramos", "lb"),
        Triple("Celsius a Fahrenheit", "CelsiusAFahrenheit", "c"),
        Triple("Fahrenheit a Celsius", "FahrenheitACelsius", "f"),
        Triple("Celsius a Kelvin", "CelsiusAKelvin", "c")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)

        val spOperation = findViewById<Spinner>(R.id.spOperation)
        val etBaseUrl = findViewById<EditText>(R.id.etBaseUrl)
        val etValue = findViewById<EditText>(R.id.etValue)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val progress = findViewById<ProgressBar>(R.id.progress)

        spOperation.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            operations.map { it.first }
        )

        etBaseUrl.setText("http://10.0.2.2:57003/ConversionService.svc")

        btnConvert.setOnClickListener {
            val baseUrl = etBaseUrl.text.toString().trim()
            val value = etValue.text.toString().toDoubleOrNull()
            if (baseUrl.isEmpty()) { toast("Ingresa la URL del .svc"); return@setOnClickListener }
            if (value == null) { toast("Ingresa un valor numérico válido"); return@setOnClickListener }

            val (label, method, paramName) = operations[spOperation.selectedItemPosition]

            progress.visibility = View.VISIBLE
            btnConvert.isEnabled = false

            lifecycleScope.launch {
                try {
                    val result = SoapClient.callDoubleMethodKsoap(
                        baseUrl = baseUrl,
                        method = method,
                        param = paramName,
                        value = value
                    )
                    tvResult.text = "Resultado de $label: $result"
                } catch (e: Exception) {
                    tvResult.text = "Error"
                    toast("Error: ${e.message?.take(300)}")
                } finally {
                    progress.visibility = View.GONE
                    btnConvert.isEnabled = true
                }
            }
        }
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
