package com.example.a02climov.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.a02climov.R
import com.example.a02climov.controller.ConversionController
import com.example.a02climov.controller.ConversionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConvertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert)

        val spType = findViewById<Spinner>(R.id.spType)
        val etValue = findViewById<EditText>(R.id.etValue)
        val btnGo = findViewById<Button>(R.id.btnGo)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val items = ConversionType.values()
        spType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            items.map { it.name }
        )

        btnGo.setOnClickListener {
            val pos = spType.selectedItemPosition
            val type = items[pos]
            val value = etValue.text.toString().toDoubleOrNull()
            if (value == null) {
                Toast.makeText(this, "Ingresa un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = ConversionController.convert(type, value)
                    withContext(Dispatchers.Main) {
                        tvResult.text = "Resultado: $result"
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ConvertActivity,
                            "Fallo: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}
