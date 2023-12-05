package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    // Declaramos firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        // Botones
        val blogin: Button = findViewById(R.id.bSignUp)

        // Textos
        val tvRegister: TextView = findViewById(R.id.tvRegister)
        val tvFP : TextView = findViewById(R.id.tvFP)
        val etLoginCorreo: EditText = findViewById(R.id.etLoginCorreo)
        val etLoginPassword: EditText = findViewById(R.id.etLoginPassword)

        blogin.setOnClickListener {
            // Mover la obtención de valores dentro del bloque onClickListener
            val mail = etLoginCorreo.text.toString()
            val pass = etLoginPassword.text.toString()

            if (mail.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, Homepage::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Manejar el caso en el que el correo electrónico o la contraseña estén vacíos
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            // Inicias la nueva actividad
            startActivity(intent)
        }

        tvFP.setOnClickListener {
            val intent = Intent(this, ResetPassword::class.java)
            // Inicias la nueva actividad
            startActivity(intent)

        }
    }
}
