package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TipoDePerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tipo_perfil)

        // Botones
        val bNormal : Button = findViewById(R.id.bNormal)
        val bProfesional : Button = findViewById(R.id.bProfesional)

        bNormal.setOnClickListener {
            val intent = Intent(this, UserDataNormal::class.java)
            startActivity(intent)
        }

        bProfesional.setOnClickListener {
            val intent = Intent(this, UserData::class.java)
            startActivity(intent)
        }
    }
}