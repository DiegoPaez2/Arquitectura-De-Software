package com.example.a02climov.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.a02climov.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etUser)
        val etPass = findViewById<EditText>(R.id.etPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvMsg = findViewById<TextView>(R.id.tvMsg)

        btnLogin.setOnClickListener {
            val user = etUser.text.toString().trim()
            val pass = etPass.text.toString().trim()
            if (user == "MONSTER" && pass == "MONSTER9") {
                startActivity(Intent(this, ConversionActivity::class.java))
                finish()
            } else {
                tvMsg.text = "Usuario o contraseña incorrectos"
            }
        }
    }
}
