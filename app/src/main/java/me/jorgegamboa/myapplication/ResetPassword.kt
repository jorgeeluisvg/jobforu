package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val bReset : Button = findViewById(R.id.bReset)
        val etLoginCorreo2 : EditText = findViewById(R.id.etLoginCorreo2)

        bReset.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            val correo = etLoginCorreo2.text.toString()
            auth.sendPasswordResetEmail(correo).addOnSuccessListener {
                Toast.makeText(this,"Por favor checa tu correo",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
                .addOnFailureListener{
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                }
        }
    }
}