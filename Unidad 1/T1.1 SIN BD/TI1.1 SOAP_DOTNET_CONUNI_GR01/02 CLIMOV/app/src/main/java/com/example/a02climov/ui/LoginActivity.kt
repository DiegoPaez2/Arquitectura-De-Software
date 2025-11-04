package com.example.a02climov.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a02climov.R

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val USER = "MONSTER"
        private const val PASS = "MONSTER9"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etUser)
        val etPass = findViewById<EditText>(R.id.etPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val u = etUser.text.toString().trim()
            val p = etPass.text.toString().trim()
            if (u == USER && p == PASS) {
                startActivity(Intent(this, ConvertActivity::class.java))
            } else {
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
