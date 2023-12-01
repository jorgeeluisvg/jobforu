package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        firebaseAuth = FirebaseAuth.getInstance()

        // EditTexts
        val etRegistroCorreo: EditText = findViewById(R.id.etLoginCorreo)
        val etRegistroPassword: EditText = findViewById(R.id.etLoginPassword)
        val etRegistroPassword2: EditText = findViewById(R.id.etRegistroPassword2)

        // Botones
        val bSignUp: Button = findViewById(R.id.bSignUp)

        bSignUp.setOnClickListener {
            // Move the email and password retrieval inside the click listener
            val mail = etRegistroCorreo.text.toString()
            val pass = etRegistroPassword.text.toString()
            val confpass = etRegistroPassword2.text.toString()

            if (mail.isNotEmpty() && pass.isNotEmpty() && confpass.isNotEmpty()) {
                if (pass == confpass) {
                    firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            Log.i("ERROR", it.exception.toString())
                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_LONG).show()
            }
        }
    }
}
