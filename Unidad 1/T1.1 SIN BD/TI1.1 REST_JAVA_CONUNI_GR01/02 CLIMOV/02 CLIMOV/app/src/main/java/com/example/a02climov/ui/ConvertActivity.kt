package com.example.a02climov.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a02climov.R
import com.example.a02climov.controller.ConversionController
import com.example.a02climov.model.ConversionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConvertActivity : AppCompatActivity() {

    private lateinit var txtWelcome: TextView
    private lateinit var spinnerTipo: Spinner
    private lateinit var etValor: EditText
    private lateinit var btnConvertir: Button
    private lateinit var txtResultado: TextView
    private lateinit var progressBar: ProgressBar

    private val controller = ConversionController()
    private var tipoSeleccionado: ConversionType = ConversionType.KM_A_MILLAS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert)

        txtWelcome   = findViewById(R.id.txtWelcome)
        spinnerTipo  = findViewById(R.id.spinnerTipo)
        etValor      = findViewById(R.id.etValor)
        btnConvertir = findViewById(R.id.btnConvertir)
        txtResultado = findViewById(R.id.txtResultado)
        progressBar  = findViewById(R.id.progressBar)

        txtWelcome.text = "Bienvenido, MONSTER"

        val labels = ConversionType.values().map { it.label }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, labels).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerTipo.adapter = adapter

        spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                tipoSeleccionado = ConversionType.byPosition(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnConvertir.setOnClickListener { convertirAsync() }
    }

    private fun convertirAsync() {
        val raw = etValor.text.toString().trim().replace(',', '.')
        val valor = raw.toDoubleOrNull()
        if (valor == null) {
            Toast.makeText(this, "Ingrese un número válido", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = View.VISIBLE
        txtResultado.text = ""

        // ⬇️ Llamada a función suspend dentro de una corrutina de lifecycle
        lifecycleScope.launch {
            try {
                val resultado = withContext(Dispatchers.IO) {
                    controller.convertir(tipoSeleccionado, valor)  // suspend ✅
                }
                txtResultado.text = "Resultado: %.5f".format(resultado)
            } catch (e: Exception) {
                txtResultado.text = "⚠ Error: ${e.message ?: e::class.java.simpleName}"
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
