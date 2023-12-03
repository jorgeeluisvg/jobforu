package me.jorgegamboa.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Iconos
        val ivPerfil : ImageView = findViewById(R.id.ivPerfil)

        // Edit texts
        val etBuscar: EditText = findViewById(R.id.etBuscar)

        etBuscar.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Realiza la acción que deseas aquí
                val oficio = etBuscar.text.toString()
                val intent = Intent(this, SearchResults::class.java)
                intent.putExtra("oficio",oficio)
                startActivity(intent)
                return@setOnKeyListener true
            }
            false
        }

        ivPerfil.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }
    }
}
