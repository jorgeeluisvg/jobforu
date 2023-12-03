package me.jorgegamboa.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class WorkerFullViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workerprofile)

        if(intent != null){
            configUI()

        }
    }

    private fun configUI() {
        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")

        val tvNombre : TextView = findViewById(R.id.tvNombreFV)
        tvNombre.text = nombre

        val etDescripcionFV : EditText = findViewById(R.id.etDescripcionFV)
        etDescripcionFV.setText(descripcion.toString())

        val bChat : Button = findViewById(R.id.bchat)
        bChat.setOnClickListener {
            // CHAT
        }

    }
}